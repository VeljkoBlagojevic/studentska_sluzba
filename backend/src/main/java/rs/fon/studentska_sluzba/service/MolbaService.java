package rs.fon.studentska_sluzba.service;

import rs.fon.studentska_sluzba.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.Molba;
import rs.fon.studentska_sluzba.domain.StatusMolbe;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.logging.Logger;
import rs.fon.studentska_sluzba.repository.MolbaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Sadrzi poslovnu logiku koja manipilise podatke vezane za molbe.
 * <p>
 * Klasa treba da upravlja sa molbama koje su izdate,
 * od strane koga su podnete,
 * kako se razresuju itd.
 *
 * @author VeljkoBlagojevic
 */
@Service
public class MolbaService {

    /**
     * Referenca ka brokeru baze podataka koji direktno manipulise sa tabelom koja se odnosi na molbe.
     */
    private final MolbaRepository molbaRepository;

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
     * @param molbaRepository Klasa koja implementira sve neophodne operacije sa radom i pristupom baze podataka.
     * @param studentService Poslovna logika za manipulaciju sa studentima i prijavljenim korisnicima.
     * @param logger Klasa za logovanje neophodnih informacija.
     */
    public MolbaService(MolbaRepository molbaRepository, StudentService studentService, Logger logger) {
        this.molbaRepository = molbaRepository;
        this.studentService = studentService;
        this.logger = logger;
    }

    /**
     * Metoda vraca listu svih molbi koje se nalaze u sistemu vezane za datog korisnika.
     * <p>
     * Nije bitno u kakvom su statusu ili kog su tipa.
     * Ukoliko korisnik sa Rolom 'ADMIN' pokusa da pristupi svim molbama, bice mu pruzena kompletna lista svih molbi u bazi podataka.
     * Ukoliko korisnik sa Rolom 'USER' pozove datu metodu, dobija sve molbe koje je on licno podneo.
     *
     * @return Listu molbi za ulogovanog korisnika.
     */
    public List<Molba> findAll() {
        if (studentService.jelTrenutniKorisnikAdmin()) {
            return molbaRepository.findAll();
        }
        Student trenutniStudent = studentService.getTrenutniStudent();
        return molbaRepository.findByStudent(trenutniStudent);
    }

    /**
     * Pronalazi molbu koja ima id koji je prosledjen.
     * Ukoliko je ulogovani korisnik 'ADMIN' onda se pretrazuje molba po id-ju svakako.
     * Dok, ako je korisnik tipa 'USER' on moze pristupiti molbi sa datim id-em
     * samo ako ju je on podneo.
     *
     * @throws EntityNotFoundException ukoliko ne postoji molba sa prosledjenim identifikatorom.
     *
     * @param id identifikator pretrage.
     * @return Molba koja ispunjava uslov pretrage po id-ju.
     */
    public Molba getMolbaSaId(Long id) {
        if (studentService.jelTrenutniKorisnikAdmin()) {
            return molbaRepository.findById(id).orElseThrow(() -> {
                EntityNotFoundException exception = new EntityNotFoundException();
                logger.error(exception);
                return exception;
            });
        }
        Student trenutniStudent = studentService.getTrenutniStudent();
        return molbaRepository.findByStudentAndId(trenutniStudent, id).orElseThrow(() -> {
            EntityNotFoundException exception = new EntityNotFoundException();
            logger.error(exception);
            return exception;
        });
    }


    /**
     * Dodavanje molbe u sistem koja ce imati status 'U OBRADI'.
     * <p>
     * Metoda postavlja dati atribut statusa, kao i vreme dodavanje molbe.
     * Takodje se i dodaje informacija o korisniku koji je podnosila molbe.
     *
     * @param molba Molba koja se dodaje u sistem ciji ce status biti 'U OBRADI'
     * @return Molbu koja je sacuvana, sa azuriranim informacijama.
     */
    public Molba dodajMolbuUObradi(Molba molba) {
        Student trenutniStudent = studentService.getTrenutniStudent();
        molba.setStudent(trenutniStudent);

        molba.setDatumPitanja(LocalDate.now());

        molba.setStatusMolbe(StatusMolbe.U_OBRADI);

        Molba sacuvanaMolba = molbaRepository.save(molba);

        logger.info("Uspesno sacuvana molba sa id = " + sacuvanaMolba.getId());
        return sacuvanaMolba;
    }

    /**
     * Brise molbu sa prosledjenim identifikatorom iz baze podataka.
     * <p>
     * Ukoliko molba ne postoji sa datim identifikatorom, metoda vraca false vrednost.
     * Ali, ako postoji i uspesno je izvrseno brisanje objekta sa datim atributom, metoda vraca true.
     *
     * @param id jedinstveni primarni kljuc po kojem se identifikuje molba za brisanje.
     * @return Uspesnost brisanja.
     */
    public boolean obrisiMolbuSaId(Long id) {
        if (molbaRepository.findById(id).isPresent()) {
            molbaRepository.deleteById(id);
            logger.info("Uspesno obrisana molba sa id: " + id);
            return true;
        }
        logger.error(new EntityNotFoundException("Nije pronadjena molba sa id: " + id));
        return false;
    }

    /**
     * Vraca sve molbe koji imaju status 'U OBRADI'.
     * <p>
     * Ukoliko ulogovani korisnik trazi molbe, dobice samo one koje je on podneo
     * Ukoliko administrator poziva ovu metodu, dobice sve molbe svih korisnika sa zadatim statusom.
     * @return lista molbi korisnika sa statusom da je u obradi.
     */
    public List<Molba> findAllUObradi() {
        if (studentService.jelTrenutniKorisnikAdmin())
            return molbaRepository
                    .findAll()
                    .stream()
                    .filter(molba -> molba.getStatusMolbe().equals(StatusMolbe.U_OBRADI))
                    .toList();

        Student trenutniStudent = studentService.getTrenutniStudent();
        return molbaRepository
                .findByStudent(trenutniStudent)
                .stream()
                .filter(molba -> molba.getStatusMolbe().equals(StatusMolbe.U_OBRADI))
                .toList();
    }

    /**
     * Vraca sve molbe koji imaju status 'RAZRESENA'.
     * <p>
     * Ukoliko ulogovani korisnik trazi molbe, dobice samo one koje je on podneo
     * Ukoliko administrator poziva ovu metodu, dobice sve molbe svih korisnika sa zadatim statusom.
     * @return lista molbi korisnika koje su razresene od strane administratora sistema.
     */
    public List<Molba> findAllRazresene() {
        if (studentService.jelTrenutniKorisnikAdmin())
            return molbaRepository
                    .findAll()
                    .stream()
                    .filter(molba -> molba.getStatusMolbe().equals(StatusMolbe.RAZRESENA))
                    .toList();

        Student trenutniStudent = studentService.getTrenutniStudent();
        return molbaRepository
                .findByStudent(trenutniStudent)
                .stream()
                .filter(molba -> molba.getStatusMolbe().equals(StatusMolbe.RAZRESENA))
                .toList();
    }

    /**
     * Metoda vrsti razresavanje molbe, tj dodavanje odgovora od strane administratora.
     * <p>
     * Molba koja je podneta treba biti pregledana od strane administratora a zatim i razresena sa odredjenim odgovorom.
     * Funkcionalnost ove metode je da se doda odgovor i njegovo vreme, kao i da se promeni status molbe.
     * Samo administraotor moze da razresi neku molbu.
     *
     * @throws RuntimeException Ukoliko ulogovani korisnik nema kredencijale za pristup ADMIN funkcionalnostima.
     *
     * @param molba Molba sa novim odgovorom koja se razresavana.
     * @return Razresena molba sa azuriranim informacijama kao sto su status i odgovor.
     */
    public Molba razresiMolbu(Molba molba) {
        if (!studentService.jelTrenutniKorisnikAdmin()) {
            RuntimeException exception = new RuntimeException("Niste ulogovani kao administrator da bi ste razresili molbu");
            logger.error(exception);
            throw exception;
        }

        molba.setDatumOdgovora(LocalDate.now());
        molba.setStatusMolbe(StatusMolbe.RAZRESENA);
        Molba razresenaMolba = molbaRepository.save(molba);
        logger.info("Uspesno razresili molbu sa id = " + razresenaMolba.getId());
        return razresenaMolba;
    }

    /**
     * Metoda vrsti razresavanje molbe, tj dodavanje odgovora od strane administratora.
     * <p>
     * Molba koja je podneta treba biti pregledana od strane administratora a zatim i razresena sa odredjenim odgovorom.
     * Funkcionalnost ove metode je da se doda odgovor i njegovo vreme, kao i da se promeni status molbe.
     * Samo administraotor moze da razresi neku molbu.
     *
     * @throws RuntimeException Ukoliko ulogovani korisnik nema kredencijale za pristup ADMIN funkcionalnostima.
     *
     * @param id Identifikator molbe koja se razresavana.
     * @param odgovor Odgovor koji postavlja administraotor u molbu.
     * @return Razresena molba sa azuriranim informacijama kao sto su status i odgovor.
     */
    public Molba razresiMolbu(Long id, String odgovor) {
        if (!studentService.jelTrenutniKorisnikAdmin()) {
            RuntimeException exception = new RuntimeException("Niste ulogovani kao administrator da bi ste razresili molbu");
            logger.error(exception);
            throw exception;
        }

        Optional<Molba> optionalMolba = molbaRepository.findById(id);
        Molba molba = optionalMolba.orElseThrow(EntityNotFoundException::new);

        molba.setOdgovor(odgovor);
        molba.setDatumOdgovora(LocalDate.now());
        molba.setStatusMolbe(StatusMolbe.RAZRESENA);

        return molbaRepository.save(molba);
    }
}
