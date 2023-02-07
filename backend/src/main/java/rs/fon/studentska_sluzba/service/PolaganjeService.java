package rs.fon.studentska_sluzba.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.Polaganje;
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
        if(studentService.jelTrenutniKorisnikAdmin())
            return polaganjeRepository.findAll();

        Student trenutniStudent = studentService.getTrenutniStudent();
        return polaganjeRepository.findByStudent(trenutniStudent);
    }

    public Polaganje getPolaganjeSaId(Long id) {
        if(studentService.jelTrenutniKorisnikAdmin())
            return polaganjeRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        Student trenutniStudent = studentService.getTrenutniStudent();
        return polaganjeRepository.findByStudentAndId(trenutniStudent, id).orElseThrow(EntityNotFoundException::new);
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
        if(studentService.jelTrenutniKorisnikAdmin())
            return polaganjeRepository.findByPolozio(true);


            Student trenutniStudent = studentService.getTrenutniStudent();
        return polaganjeRepository.findByStudentAndAndPolozio(trenutniStudent, true);
    }

    public List<Polaganje> getSvaNeuspesnaPolaganja() {
        if(studentService.jelTrenutniKorisnikAdmin())
            return polaganjeRepository.findByPolozio(false);

        Student trenutniStudent = studentService.getTrenutniStudent();
        return polaganjeRepository.findByStudentAndAndPolozio(trenutniStudent, false);
    }
}
