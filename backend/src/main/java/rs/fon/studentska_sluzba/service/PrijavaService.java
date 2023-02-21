package rs.fon.studentska_sluzba.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.domain.Student;
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

    public PrijavaService(StudentRepository studentRepository, StudentService studentService, PredmetRepository predmetRepository) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
        this.predmetRepository = predmetRepository;
    }

    public void dodajPrijavu(Predmet prijava) {
        Student trenutniStudent = studentService.getTrenutniStudent();
        if (trenutniStudent.getPrijave().contains(prijava)) {
            return;
        }
        trenutniStudent.getPrijave().add(prijava);
        studentRepository.save(trenutniStudent);
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
                return false;
            } else {
                Student pronadjedniStudent = studentRepository
                        .findAll()
                        .stream()
                        .filter(student ->
                                student.getPrijave()
                                        .stream()
                                        .anyMatch(predmet -> predmet.getId().equals(id)))
                        .findAny().orElseThrow(EntityNotFoundException::new);

                Predmet pronadjedniPredmetZaUklanjanje = predmetRepository.findById(id).get();

                if (!pronadjedniStudent.getPrijave().remove(pronadjedniPredmetZaUklanjanje)) {
                    return false;
                }
                studentRepository.save(pronadjedniStudent);
                return true;
            }
        }

        Student trenutniStudent = studentService.getTrenutniStudent();

        Predmet predmetIzBaze = predmetRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        if (!trenutniStudent.getPrijave().remove(predmetIzBaze)) {
            return false;
        }

        studentRepository.save(trenutniStudent);
        return true;
    }


    public Map<Student, Set<Predmet>> getPrijaveZaAdmina() {
        return studentRepository.findAll()
                .stream()
                .filter(student -> !student.equals(studentRepository.findById(0L).get()))
                .collect(Collectors.toMap(student -> student, Student::getPrijave));

    }

}
