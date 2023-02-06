package rs.fon.studentska_sluzba.controller.mapper;

import org.springframework.stereotype.Component;
import rs.fon.studentska_sluzba.controller.dto.ObavestenjeDTO;
import rs.fon.studentska_sluzba.domain.Obavestenje;

@Component
public class ObavestenjeMapper implements Mapper<Obavestenje, ObavestenjeDTO> {
    @Override
    public ObavestenjeDTO entityToDTO(Obavestenje obavestenje) {
        return new ObavestenjeDTO(obavestenje.getId(), obavestenje.getDatum(), obavestenje.getSadrzaj());
    }

    @Override
    public Obavestenje DTOToEntity(ObavestenjeDTO obavestenjeDTO) {
        return Obavestenje.builder()
                .id(obavestenjeDTO.id())
                .datum(obavestenjeDTO.datum())
                .sadrzaj(obavestenjeDTO.sadrzaj())
                .build();
    }
}
