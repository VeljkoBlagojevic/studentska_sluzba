package rs.fon.studentska_sluzba.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.Molba;
import rs.fon.studentska_sluzba.domain.StatusMolbe;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.repository.MolbaRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class MolbaService {

    private MolbaRepository molbaRepository;

    private StudentService studentService;

    public MolbaService(MolbaRepository molbaRepository, StudentService studentService) {
        this.molbaRepository = molbaRepository;
        this.studentService = studentService;
    }

    public List<Molba> findAll() {
        Student trenutniStudent = studentService.getTrenutniStudent();
        return molbaRepository.findByStudent(trenutniStudent);
    }

    public Molba getMolbaSaId(Long id) {
        return molbaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Molba dodajMolbuUObradi(Molba molba) {
        Student trenutniStudent = studentService.getTrenutniStudent();
        molba.setStudent(trenutniStudent);

        molba.setDatumPitanja(LocalDate.now());

        molba.setStatusMolbe(StatusMolbe.U_OBRADI);

        return molbaRepository.save(molba);
    }

    public boolean obrisiMolbuSaId(Long id) {
        if (molbaRepository.findById(id).isPresent()) {
            molbaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Molba> findAllUObradi() {
        Student trenutniStudent = studentService.getTrenutniStudent();
        return molbaRepository
                .findByStudent(trenutniStudent)
                .stream()
                .filter(molba -> molba.getStatusMolbe().equals(StatusMolbe.U_OBRADI))
                .toList();
    }

    public List<Molba> findAllRazresene() {
        Student trenutniStudent = studentService.getTrenutniStudent();
        return molbaRepository
                .findByStudent(trenutniStudent)
                .stream()
                .filter(molba -> molba.getStatusMolbe().equals(StatusMolbe.RAZRESENA))
                .toList();
    }
}
