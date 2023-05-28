package rs.fon.studentska_sluzba.service;

import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.exception.EntityNotFoundException;
import rs.fon.studentska_sluzba.logging.Logger;
import rs.fon.studentska_sluzba.repository.PredmetRepository;
import rs.fon.studentska_sluzba.repository.StudentRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Sadrzi poslovnu logiku sa radom sa prijavama.
 * <p>
 * Klasa sluzi da manipulise, upravlja sa modelom i podacima vezanim sa prijavima ispita.
 * Omogucavama dodavanje, citanje, brisanje i menjanje informacija prijava ispita.
 *
 * @author VeljkoBlagojevic
 */
@Service
public class PrijavaService {
    /**
     * Broker baze podataka koji je posrednik ka tabeli Student.
     */
    private final StudentRepository studentRepository;

    /**
     * Referenca ka klasi koja vodi racuna o studentima i ulogovanim korisnicima.
     */
    private final StudentService studentService;

    /**
     * Broker baze podataka koji je posrednik ka tabeli Predmet.
     */
    private final PredmetRepository predmetRepository;

    /**
     * Logger koji sacuvava sve potrebne informacije o izvrsenim koracima.
     */
    private final Logger logger;

    /**
     * Parametrizovani konstruktor koji automatski injektuje sve zavinosti uz pomoc Springa.
     *
     * @param studentRepository Klasa koja sadrzi sve operacije za perzistenciju studenta.
     * @param studentService Poslovna logika za manipulaciju sa studentima i prijavljenim korisnicima.
     * @param predmetRepository Klasa koja implementira sve neophodne operacije sa radom i pristupom baze podataka tabela predmeta.
     * @param logger Klasa za logovanje neophodnih informacija.
     */
    public PrijavaService(StudentRepository studentRepository, StudentService studentService, PredmetRepository predmetRepository, Logger logger) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
        this.predmetRepository = predmetRepository;
        this.logger = logger;
    }

    /**
     * Dodavanje nove prijave za datog studenta.
     * <p>
     * Metoda perzistira novo novu prijavu.
     * Ukoliko postoji vec data prijava za prijavljenog studenta, nece se nista dodatno desiti.
     *
     * @param prijava Predmet koji se zeli dodati kao prijava za ulogovanog studenta.
     */
    public void dodajPrijavu(Predmet prijava) {
        Student trenutniStudent = studentService.getTrenutniStudent();
        if (trenutniStudent.getPrijave().contains(prijava)) {
            logger.error(new Exception("Prijava se vec nalazi u sistemu"));
            return;
        }
        trenutniStudent.getPrijave().add(prijava);
        studentRepository.save(trenutniStudent);
        logger.info("Uspesno sacuvana prijava");
    }

    /**
     * Metoda vraca sve predmete koji su prijavljeni od strane studenta.
     * <p>
     * Ukoliko ulogovani korisnik ima rolu 'ADMIN' njemu se prikazuju svi predmeti koji su prijavljeni od strane svih studenata.
     * Ukoliko ulogovani korisnik ima rolu 'USER' njemu se prikazuju svi predmeti koje je on licno prijavio.
     *
     * @return skup bez duplikata predmeta koje je korisnik prijavio.
     */
    public Set<Predmet> getPrijave() {
        if (studentService.jelTrenutniKorisnikAdmin())
            return studentRepository
                    .findAll()
                    .stream()
                    .map(Student::getPrijave)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());

        return studentService.getTrenutniStudent().getPrijave();
    }

    /**
     * Metoda vraca predmet koja sadrzi identifikator koji je jednak prosledjenom parametru.
     * Ukoliko ne postoji baca se neproveravani izuzetak.
     *
     * @throws EntityNotFoundException Ukoliko ne postoji prijava ciji je predmet sa datim id-em.
     *
     * @param id Identifikator predmeta po kojem se vrsi pretraga prijava.
     * @return Prijava ciji predmet ima dati id.
     */
    public Predmet getPrijavaSaId(Long id) {
        if (studentService.jelTrenutniKorisnikAdmin())
            return studentRepository
                    .findAll()
                    .stream()
                    .map(Student::getPrijave)
                    .flatMap(Collection::stream)
                    .filter(predmet -> predmet.getId().equals(id))
                    .findAny()
                    .orElseThrow(EntityNotFoundException::new);

        return studentService
                .getTrenutniStudent()
                .getPrijave()
                .stream()
                .filter(predmet -> predmet.getId().equals(id))
                .findAny()
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Uklanja predmet sa id-em iz liste prijava studenta.
     * <p>
     * Ukoliko je ulogovani korisnik administrator sistema, proverava se da li postoji predmet sa datim id-em,
     * a da je pritom takodje prijavljen od strane nekog studenta.
     * Ukoliko nije pronadjena vraca se false vrednost.
     * Ukoliko je pronadjena, uklanja se iz liste prijava za datog studenta kod koga je pronadjen objekat.
     * <p>
     *
     * Ukoliko je ulogovani korisnik student, onda se za njega proverava da li je predmet sa datim id-em u njegovoj listi prijavljenih ispita.
     * Ukoliko jeste i uspesno se ukloni metoda vraca true vrednost, u suprotnom false.
     *
     * @throws EntityNotFoundException Ukoliko nije pronadjen entitet sa zadatim identifikatorom.
     *
     * @param id jedinstveni primarni kljuc po kojem se identifikuje predmet za koji je prijavljen ispit za brisanje.
     * @return Uspesnost brisanja.
     */
    public boolean obrisiPrijavuSaId(Long id) {
        if (studentService.jelTrenutniKorisnikAdmin()) {
            if (studentRepository
                    .findAll()
                    .stream()
                    .noneMatch(student ->
                            student.getPrijave()
                                    .stream()
                                    .map(Predmet::getId)
                                    .toList()
                                    .contains(id))) {
                logger.error(new EntityNotFoundException("Nije pronadjena prijava sa id " + id));
                return false;
            } else {
                Student pronadjedniStudent = studentRepository
                        .findAll()
                        .stream()
                        .filter(student ->
                                student.getPrijave()
                                        .stream()
                                        .anyMatch(predmet -> predmet.getId().equals(id)))
                        .findAny().orElseThrow(() -> {
                            EntityNotFoundException errorCause = new EntityNotFoundException("Nije pronadjena prijava sa id " + id);
                            logger.error(errorCause);
                            return errorCause;
                        });

                Predmet pronadjedniPredmetZaUklanjanje = predmetRepository.findById(id).orElseThrow(() -> {
                    EntityNotFoundException errorCause = new EntityNotFoundException("Nije pronadjen predmet sa id " + id);
                    logger.error(errorCause);
                    return errorCause;
                });

                if (!pronadjedniStudent.getPrijave().remove(pronadjedniPredmetZaUklanjanje)) {
                    logger.error(new EntityNotFoundException("Nije uspelo izbacivanje jer ne postoji predmet = " +pronadjedniPredmetZaUklanjanje));
                    return false;
                }
                studentRepository.save(pronadjedniStudent);
                logger.info("Uspesno uklanjanje predmeta = " + pronadjedniPredmetZaUklanjanje);
                return true;
            }
        }

        Student trenutniStudent = studentService.getTrenutniStudent();

        Predmet predmetIzBaze = predmetRepository.findById(id).orElseThrow(() -> {
            EntityNotFoundException errorCause = new EntityNotFoundException("Nije pronadjen predmet sa id " + id);
            logger.error(errorCause);
            return errorCause;
        });

        if (!trenutniStudent.getPrijave().remove(predmetIzBaze)) {
            logger.error(new EntityNotFoundException("Nije uspelo izbacivanje jer ne postoji predmet = " + predmetIzBaze));
            return false;
        }

        studentRepository.save(trenutniStudent);
        logger.info("Uspesno uklanjanje predmeta = " + predmetIzBaze);
        return true;
    }


    /**
     * Racuna i vraca mapu gde su kljucevi student, a vrednosti skup predmeta koje je prijavio.
     *
     * Metodi moze bi trebalo samo administrator da pristupi.
     * Ne racuna admina kao korisnika/studenta za koje vraca prijave.
     *
     * @return Mapa studenta i njegova lista prijavljenih predemta.
     */
    public Map<Student, Set<Predmet>> getPrijaveZaAdmina() {
        return studentRepository.findAll()
                .stream()
                .filter(student -> !student.equals(studentRepository.findById(0L).get()))
                .collect(Collectors.toMap(student -> student, Student::getPrijave));

    }

}
