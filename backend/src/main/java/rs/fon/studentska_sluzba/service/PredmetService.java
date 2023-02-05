package rs.fon.studentska_sluzba.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.NepolozeniPredmet;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.repository.NepolozeniPredmetRepository;
import rs.fon.studentska_sluzba.repository.PredmetRepository;
import rs.fon.studentska_sluzba.repository.StudentRepository;

import java.util.List;
import java.util.Set;

@Service
public class PredmetService {

    private final NepolozeniPredmetRepository nepolozeniPredmetRepository;

    private final PredmetRepository predmetRepository;
    private final StudentRepository studentRepository;
    private final StudentService studentService;

    @Autowired
    public PredmetService(NepolozeniPredmetRepository nepolozeniPredmetRepository, PredmetRepository predmetRepository, StudentRepository studentRepository, StudentService studentService) {
        this.nepolozeniPredmetRepository = nepolozeniPredmetRepository;
        this.predmetRepository = predmetRepository;
        this.studentRepository = studentRepository;
        this.studentService = studentService;
    }

    public List<Predmet> getTrenutnoSlusani() {
        Student trenutniStudent = studentService.getTrenutniStudent();

        return nepolozeniPredmetRepository.findByStudentAndTrenutnoSlusa(trenutniStudent, true).stream()
                .map(NepolozeniPredmet::getPredmet)
                .toList();

//        return nepolozeniPredmetRepository.findAll().stream()
//                .filter(nepolozeniPredmet -> nepolozeniPredmet.getStudent().equals(trenutniStudent))
//                .filter(NepolozeniPredmet::getTrenutnoSlusa)
//                .map(NepolozeniPredmet::getPredmet)
//                .toList();
    }

    public List<Predmet> getSveNepolozenePredmete() {
        Student trenutniStudent = studentService.getTrenutniStudent();

        return nepolozeniPredmetRepository.findByStudent(trenutniStudent).stream()
                .map(NepolozeniPredmet::getPredmet)
                .toList();
    }

    public List<Predmet> izaberiPredmeteZaSlusanje(List<Predmet> noviPredmetiZaSlusanje) {
        Student trenutniStudent = studentService.getTrenutniStudent();

        nepolozeniPredmetRepository.findByStudent(trenutniStudent)
                .forEach(stariNepolozenPredmet -> {
                            boolean updateSlusa = noviPredmetiZaSlusanje.contains(stariNepolozenPredmet);
                            NepolozeniPredmet nepolozeniPredmetZaUpdate = nepolozeniPredmetRepository.findByPredmetAndStudent(stariNepolozenPredmet.getPredmet(), trenutniStudent);
                            nepolozeniPredmetZaUpdate.setTrenutnoSlusa(updateSlusa);
                            nepolozeniPredmetRepository.save(nepolozeniPredmetZaUpdate);
                        }
                );

        return nepolozeniPredmetRepository.findByStudentAndTrenutnoSlusa(trenutniStudent, true).stream().map(NepolozeniPredmet::getPredmet).toList();
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
