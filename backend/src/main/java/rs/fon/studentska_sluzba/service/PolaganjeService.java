package rs.fon.studentska_sluzba.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.Polaganje;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.logging.Logger;
import rs.fon.studentska_sluzba.repository.PolaganjeRepository;

import java.util.List;

@Service
public class PolaganjeService {

    private final PolaganjeRepository polaganjeRepository;

    private final StudentService studentService;

    private final Logger logger;

    public PolaganjeService(PolaganjeRepository polaganjeRepository, StudentService studentService, Logger logger) {
        this.polaganjeRepository = polaganjeRepository;
        this.studentService = studentService;
        this.logger = logger;
    }


    public List<Polaganje> getSvaPolaganja() {
        if (studentService.jelTrenutniKorisnikAdmin())
            return polaganjeRepository.findAll();

        Student trenutniStudent = studentService.getTrenutniStudent();
        return polaganjeRepository.findByStudent(trenutniStudent);
    }

    public Polaganje getPolaganjeSaId(Long id) {
        if (studentService.jelTrenutniKorisnikAdmin())
            return polaganjeRepository.findById(id).orElseThrow(() -> {
                EntityNotFoundException exception = new EntityNotFoundException("Nije pronadjeno polaganje sa id " + id);
                logger.error(exception);
                return exception;
            });

        Student trenutniStudent = studentService.getTrenutniStudent();
        return polaganjeRepository.findByStudentAndId(trenutniStudent, id).orElseThrow(() -> {
            EntityNotFoundException exception = new EntityNotFoundException("Nije pronadjeno polaganje sa id " + id);
            logger.error(exception);
            return exception;
        });
    }

    public Polaganje ubaciPolaganje(Polaganje polaganje) {
        Student trenutniStudent = studentService.getTrenutniStudent();
        polaganje.setStudent(trenutniStudent);
        Polaganje sacuvanoPolaganje = polaganjeRepository.save(polaganje);
        logger.info("Uspesno cuvanje polaganja = " + sacuvanoPolaganje);
        return sacuvanoPolaganje;
    }

    public boolean obrisiPolaganjeSaId(Long id) {
        if (polaganjeRepository.existsById(id)) {
            polaganjeRepository.deleteById(id);
            logger.info("Uspesno brisanje polaganja sa id: " + id);
            return true;
        }
        logger.error(new EntityNotFoundException("Nije pronadjeno polaganje sa id " + id));
        return false;
    }

    public List<Polaganje> getSvaUspesnaPolaganja() {
        if (studentService.jelTrenutniKorisnikAdmin())
            return polaganjeRepository.findByPolozio(true);


        Student trenutniStudent = studentService.getTrenutniStudent();
        return polaganjeRepository.findByStudentAndAndPolozio(trenutniStudent, true);
    }

    public List<Polaganje> getSvaNeuspesnaPolaganja() {
        if (studentService.jelTrenutniKorisnikAdmin())
            return polaganjeRepository.findByPolozio(false);

        Student trenutniStudent = studentService.getTrenutniStudent();
        return polaganjeRepository.findByStudentAndAndPolozio(trenutniStudent, false);
    }

    public Page<Polaganje> getSvaNeuspesnaPolaganja(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        if (studentService.jelTrenutniKorisnikAdmin())
            return polaganjeRepository.findByPolozio(false, pageable);

        Student trenutniStudent = studentService.getTrenutniStudent();
        return polaganjeRepository.findByStudentAndAndPolozio(trenutniStudent, false, pageable);
    }
}
