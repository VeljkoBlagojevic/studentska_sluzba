package rs.fon.studentska_sluzba.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.Polaganje;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.repository.PolaganjeRepository;

import java.util.List;

@Service
public class PolaganjeService {

    private final PolaganjeRepository polaganjeRepository;

    private final StudentService studentService;

    public PolaganjeService(PolaganjeRepository polaganjeRepository, StudentService studentService) {
        this.polaganjeRepository = polaganjeRepository;
        this.studentService = studentService;
    }


    public List<Polaganje> getSvaPolaganja() {
        Student trenutniStudent = studentService.getTrenutniStudent();
        return polaganjeRepository.findByStudent(trenutniStudent);
    }

    public Polaganje getPolaganjeSaId(Long id) {
        return polaganjeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Polaganje ubaciPolaganje(Polaganje polaganje) {
        Student trenutniStudent = studentService.getTrenutniStudent();
        polaganje.setStudent(trenutniStudent);
        return polaganjeRepository.save(polaganje);
    }

    public boolean obrisiPolaganjeSaId(Long id) {
        if (polaganjeRepository.existsById(id)) {
            polaganjeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Polaganje> getSvaUspesnaPolaganja() {
        Student trenutniStudent = studentService.getTrenutniStudent();
        return polaganjeRepository.findByStudentAndAndPolozio(trenutniStudent, true);
    }

    public List<Polaganje> getSvaNeuspesnaPolaganja() {
        Student trenutniStudent = studentService.getTrenutniStudent();
        return polaganjeRepository.findByStudentAndAndPolozio(trenutniStudent, false);
    }
}
