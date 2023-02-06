package rs.fon.studentska_sluzba.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.repository.PredmetRepository;
import rs.fon.studentska_sluzba.repository.StudentRepository;

import java.util.Set;

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
        if(trenutniStudent.getPrijave().contains(prijava)) {
            return;
        }
        trenutniStudent.getPrijave().add(prijava);
        studentRepository.save(trenutniStudent);
    }

    public Set<Predmet> getPrijave() {
        return studentService.getTrenutniStudent().getPrijave();
    }

    public Predmet getPrijavaSaId(Long id) {
        return studentService.getTrenutniStudent().getPrijave().stream().filter(predmet -> predmet.getId().equals(id)).findAny().orElseThrow(EntityNotFoundException::new);
    }

    public boolean obrisiPrijavuSaId(Long id) {
        Student trenutniStudent = studentService.getTrenutniStudent();

        Predmet predmetIzBaze = predmetRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        if(trenutniStudent.getPrijave().remove(predmetIzBaze)) {
            studentRepository.save(trenutniStudent);
            return true;
        }

        return false;

    }


}
