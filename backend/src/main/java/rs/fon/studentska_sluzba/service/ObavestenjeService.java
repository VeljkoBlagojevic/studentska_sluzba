package rs.fon.studentska_sluzba.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.Obavestenje;
import rs.fon.studentska_sluzba.repository.ObavestenjeRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ObavestenjeService {

    private final ObavestenjeRepository obavestenjeRepository;

    public ObavestenjeService(ObavestenjeRepository obavestenjeRepository) {
        this.obavestenjeRepository = obavestenjeRepository;
    }

    public List<Obavestenje> getSvaObavestenja() {
        return obavestenjeRepository.findAll();
    }

    public Obavestenje getObavestenjeSaId(Long id) {
        return  obavestenjeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Obavestenje ubaciObavestenje(Obavestenje obavestenje) {
        obavestenje.setDatum(LocalDate.now());
        return obavestenjeRepository.save(obavestenje);
    }

    public boolean obrisiObavestenjeSaId(Long id) {
        if(obavestenjeRepository.existsById(id)) {
            obavestenjeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
