package rs.fon.studentska_sluzba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.repository.PredmetRepository;

@Service
public class PredmetService {

    private final PredmetRepository predmetRepository;

    @Autowired
    public PredmetService(PredmetRepository predmetRepository) {
        this.predmetRepository = predmetRepository;
    }
}
