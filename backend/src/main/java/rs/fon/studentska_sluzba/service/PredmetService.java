package rs.fon.studentska_sluzba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.repository.StudentRepository;

import java.util.List;

@Service
public class PredmetService {

    private final StudentRepository studentRepository;

    @Autowired
    public PredmetService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Predmet> getTrenutnoSlusanePredmeteOdStudenta(String username) {
        return studentRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Korisnik sa username: " + username + " nije nadjen"))
                .getTrenutnoSlusa();

    }
}
