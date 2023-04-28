package rs.fon.studentska_sluzba.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.logging.Logger;
import rs.fon.studentska_sluzba.repository.PredmetRepository;
import rs.fon.studentska_sluzba.repository.StudentRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PrijavaService {
    private final StudentRepository studentRepository;

    private final StudentService studentService;

    private final PredmetRepository predmetRepository;

    private final Logger logger;

    public PrijavaService(StudentRepository studentRepository, StudentService studentService, PredmetRepository predmetRepository, Logger logger) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
        this.predmetRepository = predmetRepository;
        this.logger = logger;
    }

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


    public Map<Student, Set<Predmet>> getPrijaveZaAdmina() {
        return studentRepository.findAll()
                .stream()
                .filter(student -> !student.equals(studentRepository.findById(0L).get()))
                .collect(Collectors.toMap(student -> student, Student::getPrijave));

    }

}
