package rs.fon.studentska_sluzba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.NepolozeniPredmet;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.repository.NepolozeniPredmetRepository;

import java.util.List;

@Service
public class PredmetService {

    private final NepolozeniPredmetRepository nepolozeniPredmetRepository;

    private final StudentService studentService;

    @Autowired
    public PredmetService(NepolozeniPredmetRepository nepolozeniPredmetRepository, StudentService studentService) {
        this.nepolozeniPredmetRepository = nepolozeniPredmetRepository;
        this.studentService = studentService;
    }

    public List<Predmet> getTrenutnoSlusani() {
        if(studentService.jelTrenutniKorisnikAdmin())
            return nepolozeniPredmetRepository
                    .findByTrenutnoSlusa(true)
                    .stream()
                    .map(NepolozeniPredmet::getPredmet)
                    .toList();

        Student trenutniStudent = studentService.getTrenutniStudent();
        return nepolozeniPredmetRepository
                .findByStudentAndTrenutnoSlusa(trenutniStudent, true)
                .stream()
                .map(NepolozeniPredmet::getPredmet)
                .toList();

//        return nepolozeniPredmetRepository.findAll().stream()
//                .filter(nepolozeniPredmet -> nepolozeniPredmet.getStudent().equals(trenutniStudent))
//                .filter(NepolozeniPredmet::getTrenutnoSlusa)
//                .map(NepolozeniPredmet::getPredmet)
//                .toList();
    }

    public List<NepolozeniPredmet> getSveNepolozenePredmete() {
        if(studentService.jelTrenutniKorisnikAdmin())
            return nepolozeniPredmetRepository
                    .findByTrenutnoSlusa(false)
                    .stream()
                    .toList();

        Student trenutniStudent = studentService.getTrenutniStudent();
        return nepolozeniPredmetRepository
                .findByStudent(trenutniStudent)
                .stream().toList();
    }

//    public List<Predmet> izaberiPredmeteZaSlusanje(List<Predmet> noviPredmetiZaSlusanje) {
//        Student trenutniStudent = studentService.getTrenutniStudent();
//
//        nepolozeniPredmetRepository.findByStudent(trenutniStudent)
//                .forEach(stariNepolozenPredmet -> {
//                            boolean updateSlusa = noviPredmetiZaSlusanje.contains(stariNepolozenPredmet);
//                            NepolozeniPredmet nepolozeniPredmetZaUpdate = nepolozeniPredmetRepository.findByPredmetAndStudent(stariNepolozenPredmet.getPredmet(), trenutniStudent);
//                            nepolozeniPredmetZaUpdate.setTrenutnoSlusa(updateSlusa);
//                            nepolozeniPredmetRepository.save(nepolozeniPredmetZaUpdate);
//                        }
//                );
//
//        return nepolozeniPredmetRepository.findByStudentAndTrenutnoSlusa(trenutniStudent, true).stream().map(NepolozeniPredmet::getPredmet).toList();
//    }


    public NepolozeniPredmet dodajZaSlusanje(NepolozeniPredmet nepolozeniPredmet) {
        nepolozeniPredmet.setStudent(studentService.getTrenutniStudent());
        return nepolozeniPredmetRepository.save(nepolozeniPredmet);
    }

    public Page<Predmet> getTrenutnoSlusani(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        if(studentService.jelTrenutniKorisnikAdmin())
            return nepolozeniPredmetRepository
                    .findByTrenutnoSlusa(true, pageable)
                    .map(NepolozeniPredmet::getPredmet);

        Student trenutniStudent = studentService.getTrenutniStudent();
        return nepolozeniPredmetRepository
                .findByStudentAndTrenutnoSlusa(trenutniStudent, true, pageable)
                .map(NepolozeniPredmet::getPredmet);
    }
}
