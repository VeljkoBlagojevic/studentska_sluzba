package rs.fon.studentska_sluzba.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.Grad;
import rs.fon.studentska_sluzba.logging.Logger;
import rs.fon.studentska_sluzba.repository.GradRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GradService {

    private final GradRepository gradRepository;

    private final Logger logger;

    @Autowired
    public GradService(GradRepository gradRepository, Logger logger) {
        this.gradRepository = gradRepository;
        this.logger = logger;
    }

    public List<Grad> findAll() {
        return gradRepository.findAll();
    }

    public Page<Grad> findAll(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return gradRepository.findAll(pageable);
    }

    public Grad getGradSaId(Long id) {
        return gradRepository.findById(id).orElseThrow(() -> {
            EntityNotFoundException exception = new EntityNotFoundException("Nije pronadjen grad sa id = " + id);
            logger.error(exception);
            return exception;
        });
    }

    public Grad ubaciGrad(Grad grad) {
        try {
            Grad sacuvaniGrad = gradRepository.save(grad);
            logger.info("Uspesno sacuvan grad = " + sacuvaniGrad);
            return sacuvaniGrad;
        } catch (Exception e) {
            logger.error(e);
            throw e;
        }
    }

    public boolean obrisiGradSaId(Long id) {
        Optional<Grad> optionalGrad = gradRepository.findById(id);
        if (optionalGrad.isPresent()) {
            gradRepository.deleteById(id);
            logger.info("Grad obrisan sa id = " + id);
            return true;
        }
        logger.error(new EntityNotFoundException("Nije pronadjen grad sa id = " + id));
        return false;
    }


}
