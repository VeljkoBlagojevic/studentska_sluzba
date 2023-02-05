package rs.fon.studentska_sluzba.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.Grad;
import rs.fon.studentska_sluzba.repository.GradRepository;

import java.util.List;

@Service
public class GradService {

    private final GradRepository gradRepository;

    @Autowired
    public GradService(GradRepository gradRepository) {
        this.gradRepository = gradRepository;
    }

    public List<Grad> findAll() {
        return gradRepository.findAll();
    }

    public Grad ubaciGrad(Grad grad) {
        return gradRepository.save(grad);
    }

    public boolean obrisiGradSaId(Long id) {
        if(gradRepository.findById(id).isPresent()) {
            gradRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Grad getGradSaId(Long id) {
        return gradRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
