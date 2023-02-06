package rs.fon.studentska_sluzba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.NepolozeniPredmet;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.repository.NepolozeniPredmetRepository;
import rs.fon.studentska_sluzba.repository.PredmetRepository;
import rs.fon.studentska_sluzba.repository.StudentRepository;

import java.util.List;

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

    public List<NepolozeniPredmet> getSveNepolozenePredmete() {
        Student trenutniStudent = studentService.getTrenutniStudent();

        return nepolozeniPredmetRepository.findByStudent(trenutniStudent).stream()
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


    public NepolozeniPredmet dodajZaSlusanje(NepolozeniPredmet nepolozeniPredmet) {
        return nepolozeniPredmetRepository.save(nepolozeniPredmet);
    }
}
