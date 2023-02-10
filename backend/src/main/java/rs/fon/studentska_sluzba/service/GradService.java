package rs.fon.studentska_sluzba.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<Grad> findAll(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return gradRepository.findAll(pageable);
    }

    public Grad getGradSaId(Long id) {
        return gradRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Grad ubaciGrad(Grad grad) {
        return gradRepository.save(grad);
    }

    public boolean obrisiGradSaId(Long id) {
        if (gradRepository.findById(id).isPresent()) {
            gradRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
