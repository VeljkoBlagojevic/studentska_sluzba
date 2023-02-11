package rs.fon.studentska_sluzba.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.Molba;
import rs.fon.studentska_sluzba.domain.StatusMolbe;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.repository.MolbaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MolbaService {

    private final MolbaRepository molbaRepository;

    private final StudentService studentService;

    public MolbaService(MolbaRepository molbaRepository, StudentService studentService) {
        this.molbaRepository = molbaRepository;
        this.studentService = studentService;
    }

    public List<Molba> findAll() {
        if(studentService.jelTrenutniKorisnikAdmin())
            return molbaRepository.findAll();
        Student trenutniStudent = studentService.getTrenutniStudent();
        return molbaRepository.findByStudent(trenutniStudent);
    }

    public Molba getMolbaSaId(Long id) {
        if(studentService.jelTrenutniKorisnikAdmin())
            return molbaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Student trenutniStudent = studentService.getTrenutniStudent();
        return molbaRepository.findByStudentAndId(trenutniStudent, id).orElseThrow(EntityNotFoundException::new);
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
        if(studentService.jelTrenutniKorisnikAdmin())
            return molbaRepository
                    .findAll()
                    .stream()
                    .filter(molba -> molba.getStatusMolbe().equals(StatusMolbe.U_OBRADI))
                    .toList();

        Student trenutniStudent = studentService.getTrenutniStudent();
        return molbaRepository
                .findByStudent(trenutniStudent)
                .stream()
                .filter(molba -> molba.getStatusMolbe().equals(StatusMolbe.U_OBRADI))
                .toList();
    }

    public List<Molba> findAllRazresene() {
        if(studentService.jelTrenutniKorisnikAdmin())
            return molbaRepository
                    .findAll()
                    .stream()
                    .filter(molba -> molba.getStatusMolbe().equals(StatusMolbe.RAZRESENA))
                    .toList();


        Student trenutniStudent = studentService.getTrenutniStudent();
        return molbaRepository
                .findByStudent(trenutniStudent)
                .stream()
                .filter(molba -> molba.getStatusMolbe().equals(StatusMolbe.RAZRESENA))
                .toList();
    }

    public Molba razresiMolbu(Molba molba) {
        if(!studentService.jelTrenutniKorisnikAdmin()) {
            throw new RuntimeException("Niste ulogovani kao administrator da bi ste razresili molbu");
        }

        molba.setDatumOdgovora(LocalDate.now());
        molba.setStatusMolbe(StatusMolbe.RAZRESENA);
        return molbaRepository.save(molba);
    }

    public Molba razresiMolbu(Long id, String odgovor) {
        if(!studentService.jelTrenutniKorisnikAdmin()) {
            throw new RuntimeException("Niste ulogovani kao administrator da bi ste razresili molbu");
        }

        Optional<Molba> optionalMolba = molbaRepository.findById(id);
        Molba molba = optionalMolba.orElseThrow(EntityNotFoundException::new);

        molba.setOdgovor(odgovor);
        molba.setDatumOdgovora(LocalDate.now());
        molba.setStatusMolbe(StatusMolbe.RAZRESENA);

        return molbaRepository.save(molba);
    }
}
