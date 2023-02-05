package rs.fon.studentska_sluzba.controller.mapper;

import org.springframework.stereotype.Component;
import rs.fon.studentska_sluzba.controller.dto.GradDTO;
import rs.fon.studentska_sluzba.domain.Grad;

@Component
public class GradMapper implements Mapper<Grad, GradDTO>{
    @Override
    public GradDTO entityToDTO(Grad grad) {
        return new GradDTO(grad.getId(), grad.getNaziv(), grad.getZipcode());
    }

    @Override
    public Grad DTOToEntity(GradDTO gradDTO) {
        return new Grad(gradDTO.id(), gradDTO.naziv(), gradDTO.zipcode());
    }
}
