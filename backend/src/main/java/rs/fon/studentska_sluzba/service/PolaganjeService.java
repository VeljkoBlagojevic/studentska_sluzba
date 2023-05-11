package rs.fon.studentska_sluzba.service;

import rs.fon.studentska_sluzba.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.Polaganje;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.logging.Logger;
import rs.fon.studentska_sluzba.repository.PolaganjeRepository;

import java.util.List;

/**
 * Sadrzi poslovnu logiku koja je povezana sa modelom Polaganja.
 * <p>
 * Sve operacije promene, dodavanja, prikazivanja i pretrage polaganja se obavlja unutar ovog servisa.
 *
 * @author VeljkoBlagojevic
 */
@Service
public class PolaganjeService {

    /**
     * Klasa brokera baze podataka koja upravlja sa tabelom polaganja.
     */
    private final PolaganjeRepository polaganjeRepository;

    /**
     * Referenca ka klasi koja vodi racuna o studentima i ulogovanim korisnicima.
     */
    private final StudentService studentService;

    /**
     * Logger koji sacuvava sve potrebne informacije o izvrsenim koracima.
     */
    private final Logger logger;

    /**
     * Parametrizovani konstruktor koji automatski injektuje sve zavinosti uz pomoc Springa.
     *
     * @param polaganjeRepository Klasa koja implementira sve neophodne operacije sa radom i pristupom baze podataka.
     * @param studentService Poslovna logika za manipulaciju sa studentima i prijavljenim korisnicima.
     * @param logger Klasa za logovanje neophodnih informacija.
     */
    public PolaganjeService(PolaganjeRepository polaganjeRepository, StudentService studentService, Logger logger) {
        this.polaganjeRepository = polaganjeRepository;
        this.studentService = studentService;
        this.logger = logger;
    }


    /**
     * Metoda koja vraca listu sa svim polaganjima od strane prijavljenog korisnika.
     * <p>
     * Ukoliko je prijavljeni korisnik administraotor sistema on dobija pristup ka svim polaganjima.
     *
     * @return Lista polaganja ulogovanog korisnika.
     */
    public List<Polaganje> getSvaPolaganja() {
        if (studentService.jelTrenutniKorisnikAdmin())
            return polaganjeRepository.findAll();

        Student trenutniStudent = studentService.getTrenutniStudent();
        return polaganjeRepository.findByStudent(trenutniStudent);
    }

    /**
     * Pronalazi polaganje koja ima id koji je prosledjen.
     * Ukoliko je ulogovani korisnik 'ADMIN' onda se pretrazuje celu bazu polaganja.
     * Dok, ako je korisnik tipa 'USER' on moze pristupiti polaganju samo ukoliko je njegovo.
     *
     * @throws EntityNotFoundException ukoliko ne postoji polaganje sa prosledjenim identifikatorom.
     *
     * @param id identifikator pretrage.
     * @return Polaganje koja ispunjava uslov pretrage po id-ju.
     */
    public Polaganje getPolaganjeSaId(Long id) {
        if (studentService.jelTrenutniKorisnikAdmin())
            return polaganjeRepository.findById(id).orElseThrow(() -> {
                EntityNotFoundException exception = new EntityNotFoundException("Nije pronadjeno polaganje sa id " + id);
                logger.error(exception);
                return exception;
            });

        Student trenutniStudent = studentService.getTrenutniStudent();
        return polaganjeRepository.findByStudentAndId(trenutniStudent, id).orElseThrow(() -> {
            EntityNotFoundException exception = new EntityNotFoundException("Nije pronadjeno polaganje sa id " + id);
            logger.error(exception);
            return exception;
        });
    }

    /**
     * Dodaje novo polaganje za ulogovanog studenta.
     *
     * @param polaganje Novo polaganje koje ce se sacuvati.
     * @return Sacuvano polaganje sa informacijama o studentu koji je polagao.
     */
    public Polaganje ubaciPolaganje(Polaganje polaganje) {
        Student trenutniStudent = studentService.getTrenutniStudent();
        polaganje.setStudent(trenutniStudent);
        Polaganje sacuvanoPolaganje = polaganjeRepository.save(polaganje);
        logger.info("Uspesno cuvanje polaganja = " + sacuvanoPolaganje);
        return sacuvanoPolaganje;
    }

    /**
     * Brise polaganje sa prosledjenim identifikatorom iz baze podataka.
     * <p>
     * Ukoliko polaganje ne postoji sa datim identifikatorom, metoda vraca false vrednost.
     * Ali, ako postoji i uspesno je izvrseno brisanje objekta sa datim atributom, metoda vraca true.
     *
     * @param id jedinstveni primarni kljuc po kojem se identifikuje polaganje za brisanje.
     * @return Uspesnost brisanja.
     */
    public boolean obrisiPolaganjeSaId(Long id) {
        if (polaganjeRepository.existsById(id)) {
            polaganjeRepository.deleteById(id);
            logger.info("Uspesno brisanje polaganja sa id: " + id);
            return true;
        }
        logger.error(new EntityNotFoundException("Nije pronadjeno polaganje sa id " + id));
        return false;
    }

    /**
     * Vraca kolekciju svih polaganja datog studenta koja su se uspesno zavrsila.
     * <p>
     * Ukoliko je ulogovani korisnik administrator, on dobija sve,
     * dok ostali studenti dobijaju uspesna polaganja na koja su oni izlazili.
     *
     * @return Listu uspesnih polaganja datog studenta.
     */
    public List<Polaganje> getSvaUspesnaPolaganja() {
        if (studentService.jelTrenutniKorisnikAdmin())
            return polaganjeRepository.findByPolozio(true);


        Student trenutniStudent = studentService.getTrenutniStudent();
        return polaganjeRepository.findByStudentAndAndPolozio(trenutniStudent, true);
    }

    /**
     * Vraca kolekciju svih polaganja datog studenta koja su se neuspesno zavrsila.
     * <p>
     * Ukoliko je ulogovani korisnik administrator, on dobija sve,
     * dok ostali studenti dobijaju neuspesna polaganja na koja su oni izlazili.
     *
     * @return Listu neuspesnih polaganja datog studenta.
     */
    public List<Polaganje> getSvaNeuspesnaPolaganja() {
        if (studentService.jelTrenutniKorisnikAdmin())
            return polaganjeRepository.findByPolozio(false);

        Student trenutniStudent = studentService.getTrenutniStudent();
        return polaganjeRepository.findByStudentAndAndPolozio(trenutniStudent, false);
    }

    /**
     * Vraca Pagination pakovanje svih polaganja datog studenta koja su se neuspesno zavrsila.
     * <p>
     * Ukoliko je ulogovani korisnik administrator, on dobija sve,
     * dok ostali studenti dobijaju neuspesna polaganja na koja su oni izlazili.
     *
     * @return Pageable kolekciju neuspesnih polaganja datog studenta.
     */
    public Page<Polaganje> getSvaNeuspesnaPolaganja(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        if (studentService.jelTrenutniKorisnikAdmin())
            return polaganjeRepository.findByPolozio(false, pageable);

        Student trenutniStudent = studentService.getTrenutniStudent();
        return polaganjeRepository.findByStudentAndAndPolozio(trenutniStudent, false, pageable);
    }
}
