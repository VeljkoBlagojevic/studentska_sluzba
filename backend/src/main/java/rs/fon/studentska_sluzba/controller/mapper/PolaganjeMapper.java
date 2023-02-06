package rs.fon.studentska_sluzba.controller.mapper;

import org.springframework.stereotype.Component;
import rs.fon.studentska_sluzba.controller.dto.PolaganjeDTO;
import rs.fon.studentska_sluzba.domain.Polaganje;

@Component
public class PolaganjeMapper implements Mapper<Polaganje, PolaganjeDTO> {

    private PredmetMapper predmetMapper;

    public PolaganjeMapper(PredmetMapper predmetMapper) {
        this.predmetMapper = predmetMapper;
    }

    @Override
    public PolaganjeDTO entityToDTO(Polaganje polaganje) {
        return new PolaganjeDTO(polaganje.getId(), polaganje.getDatum(), polaganje.getPolozio(), polaganje.getOcena(), predmetMapper.entityToDTO(polaganje.getPredmet()));
    }

    @Override
    public Polaganje DTOToEntity(PolaganjeDTO polaganjeDTO) {
        return Polaganje.builder()
                .id(polaganjeDTO.id())
                .datum(polaganjeDTO.datum())
                .polozio(polaganjeDTO.polozio())
                .ocena(polaganjeDTO.ocena())
                .predmet(predmetMapper.DTOToEntity(polaganjeDTO.predmet()))
                .build();
    }
}
