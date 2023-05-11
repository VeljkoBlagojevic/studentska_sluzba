package rs.fon.studentska_sluzba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.NepolozeniPredmet;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.logging.Logger;
import rs.fon.studentska_sluzba.repository.NepolozeniPredmetRepository;

import java.util.List;

/**
 * Sadrzi poslovnu logiku sa radom sa predmetima.
 * <p>
 * Klasa sluzi da manipulise, upravlja sa modelom i podacima vezanim sa predmetima.
 * Omogucavama dodavanje, citanje, brisanje i menjanje informacija predmeta.
 *
 * @author VeljkoBlagojevic
 */
@Service
public class PredmetService {

    /**
     * Broker baze podataka koji je posrednik ka tabeli NepolozeniPredmet.
     */
    private final NepolozeniPredmetRepository nepolozeniPredmetRepository;

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
     * @param nepolozeniPredmetRepository Klasa koja implementira sve neophodne operacije sa radom i pristupom baze podataka.
     * @param studentService Poslovna logika za manipulaciju sa studentima i prijavljenim korisnicima.
     * @param logger Klasa za logovanje neophodnih informacija.
     */
    @Autowired
    public PredmetService(NepolozeniPredmetRepository nepolozeniPredmetRepository, StudentService studentService, Logger logger) {
        this.nepolozeniPredmetRepository = nepolozeniPredmetRepository;
        this.studentService = studentService;
        this.logger = logger;
    }

    /**
     * Metoda vraca listu svih predmeta koje student trenutno slusa.
     * <p>
     * Ukoliko korisnik sa Rolom 'ADMIN' pokusa da pristupi ovoj metodi, bice mu pruzena kompletna lista predmeta koje studenti slusaju.
     * Ukoliko korisnik sa Rolom 'USER' pozove datu metodu, dobija sve predmete koje je on licno trenutno slusa.
     *
     * @return Listu predmeta koje korisnik slusa.
     */
    public List<Predmet> getTrenutnoSlusani() {
        if(studentService.jelTrenutniKorisnikAdmin())
            return nepolozeniPredmetRepository
                    .findByTrenutnoSlusa(true)
                    .stream()
                    .map(NepolozeniPredmet::getPredmet)
                    .toList();

        Student trenutniStudent = studentService.getTrenutniStudent();
        return nepolozeniPredmetRepository
                .findByStudentAndTrenutnoSlusa(trenutniStudent, true)
                .stream()
                .map(NepolozeniPredmet::getPredmet)
                .toList();

//        return nepolozeniPredmetRepository.findAll().stream()
//                .filter(nepolozeniPredmet -> nepolozeniPredmet.getStudent().equals(trenutniStudent))
//                .filter(NepolozeniPredmet::getTrenutnoSlusa)
//                .map(NepolozeniPredmet::getPredmet)
//                .toList();
    }

    /**
     * Metoda vraca listu svih nepolozenih predmeta za datog korisnika.
     * <p>
     * Ukoliko korisnik sa Rolom 'ADMIN' pokusa da pristupi ovoj metodi, bice mu pruzena kompletna lista svih nepolozenih predmeta u bazi podataka.
     * Ukoliko korisnik sa Rolom 'USER' pozove datu metodu, dobija sve nepolozene predmete koji su vezani za njega.
     *
     * @return Listu nepolozenih predmeta za ulogovanog korisnika.
     */
    public List<NepolozeniPredmet> getSveNepolozenePredmete() {
        if(studentService.jelTrenutniKorisnikAdmin())
            return nepolozeniPredmetRepository
                    .findByTrenutnoSlusa(false)
                    .stream()
                    .toList();

        Student trenutniStudent = studentService.getTrenutniStudent();
        return nepolozeniPredmetRepository
                .findByStudent(trenutniStudent)
                .stream().toList();
    }

//    public List<Predmet> izaberiPredmeteZaSlusanje(List<Predmet> noviPredmetiZaSlusanje) {
//        Student trenutniStudent = studentService.getTrenutniStudent();
//
//        nepolozeniPredmetRepository.findByStudent(trenutniStudent)
//                .forEach(stariNepolozenPredmet -> {
//                            boolean updateSlusa = noviPredmetiZaSlusanje.contains(stariNepolozenPredmet);
//                            NepolozeniPredmet nepolozeniPredmetZaUpdate = nepolozeniPredmetRepository.findByPredmetAndStudent(stariNepolozenPredmet.getPredmet(), trenutniStudent);
//                            nepolozeniPredmetZaUpdate.setTrenutnoSlusa(updateSlusa);
//                            nepolozeniPredmetRepository.save(nepolozeniPredmetZaUpdate);
//                        }
//                );
//
//        return nepolozeniPredmetRepository.findByStudentAndTrenutnoSlusa(trenutniStudent, true).stream().map(NepolozeniPredmet::getPredmet).toList();
//    }


    /**
     * Dodaje novi nepolozeni predmet za ulogovanog studenta.
     *
     * @param nepolozeniPredmet Novi nepolozeni predmet za perzistenciju.
     * @return Nepolozeni predmet koji je sacuvan u bazi podataka.
     */
    public NepolozeniPredmet dodajZaSlusanje(NepolozeniPredmet nepolozeniPredmet) {
        nepolozeniPredmet.setStudent(studentService.getTrenutniStudent());
        NepolozeniPredmet sacuvanNepolozeniPredmet = nepolozeniPredmetRepository.save(nepolozeniPredmet);
        logger.info("Dodat za slusanje predmet = " + sacuvanNepolozeniPredmet.getPredmet().getNaziv());
        return sacuvanNepolozeniPredmet;
    }

    /**
     * Metoda vraca listu svih predmeta koje student trenutno slusa koriscenjem Pageable paterna.
     * <p>
     * Ukoliko korisnik sa Rolom 'ADMIN' pokusa da pristupi ovoj metodi, bice mu pruzena kompletna lista predmeta koje studenti slusaju.
     * Ukoliko korisnik sa Rolom 'USER' pozove datu metodu, dobija sve predmete koje je on licno trenutno slusa.
     *
     * @param pageNumber Redni broj stranice.
     * @param pageSize Velicina stranice paginacije.
     * @return Paginaciju predmeta koje korisnik slusa trenutno.
     */
    public Page<Predmet> getTrenutnoSlusani(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        if(studentService.jelTrenutniKorisnikAdmin())
            return nepolozeniPredmetRepository
                    .findByTrenutnoSlusa(true, pageable)
                    .map(NepolozeniPredmet::getPredmet);

        Student trenutniStudent = studentService.getTrenutniStudent();
        return nepolozeniPredmetRepository
                .findByStudentAndTrenutnoSlusa(trenutniStudent, true, pageable)
                .map(NepolozeniPredmet::getPredmet);
    }
}
