package rs.fon.studentska_sluzba.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.Molba;
import rs.fon.studentska_sluzba.domain.StatusMolbe;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.logging.Logger;
import rs.fon.studentska_sluzba.repository.MolbaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MolbaService {

    private final MolbaRepository molbaRepository;

    private final StudentService studentService;

    private final Logger logger;


    public MolbaService(MolbaRepository molbaRepository, StudentService studentService, Logger logger) {
        this.molbaRepository = molbaRepository;
        this.studentService = studentService;
        this.logger = logger;
    }

    public List<Molba> findAll() {
        if (studentService.jelTrenutniKorisnikAdmin()) {
            return molbaRepository.findAll();
        }
        Student trenutniStudent = studentService.getTrenutniStudent();
        return molbaRepository.findByStudent(trenutniStudent);
    }

    public Molba getMolbaSaId(Long id) {
        if (studentService.jelTrenutniKorisnikAdmin()) {
            return molbaRepository.findById(id).orElseThrow(() -> {
                EntityNotFoundException exception = new EntityNotFoundException();
                logger.error(exception);
                return exception;
            });
        }
        Student trenutniStudent = studentService.getTrenutniStudent();
        return molbaRepository.findByStudentAndId(trenutniStudent, id).orElseThrow(() -> {
            EntityNotFoundException exception = new EntityNotFoundException();
            logger.error(exception);
            return exception;
        });
    }

    public Molba dodajMolbuUObradi(Molba molba) {
        Student trenutniStudent = studentService.getTrenutniStudent();
        molba.setStudent(trenutniStudent);

        molba.setDatumPitanja(LocalDate.now());

        molba.setStatusMolbe(StatusMolbe.U_OBRADI);

        Molba sacuvanaMolba = molbaRepository.save(molba);

        logger.info("Uspesno sacuvana molba sa id = " + sacuvanaMolba.getId());
        return sacuvanaMolba;
    }

    public boolean obrisiMolbuSaId(Long id) {
        if (molbaRepository.findById(id).isPresent()) {
            molbaRepository.deleteById(id);
            logger.info("Uspesno obrisana molba sa id: " + id);
            return true;
        }
        logger.error(new EntityNotFoundException("Nije pronadjena molba sa id: " + id));
        return false;
    }

    public List<Molba> findAllUObradi() {
        if (studentService.jelTrenutniKorisnikAdmin())
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
        if (studentService.jelTrenutniKorisnikAdmin())
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
        if (!studentService.jelTrenutniKorisnikAdmin()) {
            RuntimeException exception = new RuntimeException("Niste ulogovani kao administrator da bi ste razresili molbu");
            logger.error(exception);
            throw exception;
        }

        molba.setDatumOdgovora(LocalDate.now());
        molba.setStatusMolbe(StatusMolbe.RAZRESENA);
        Molba razresenaMolba = molbaRepository.save(molba);
        logger.info("Uspesno razresili molbu sa id = " + razresenaMolba.getId());
        return razresenaMolba;
    }

    public Molba razresiMolbu(Long id, String odgovor) {
        if (!studentService.jelTrenutniKorisnikAdmin()) {
            RuntimeException exception = new RuntimeException("Niste ulogovani kao administrator da bi ste razresili molbu");
            logger.error(exception);
            throw exception;
        }

        Optional<Molba> optionalMolba = molbaRepository.findById(id);
        Molba molba = optionalMolba.orElseThrow(EntityNotFoundException::new);

        molba.setOdgovor(odgovor);
        molba.setDatumOdgovora(LocalDate.now());
        molba.setStatusMolbe(StatusMolbe.RAZRESENA);

        return molbaRepository.save(molba);
    }
}
