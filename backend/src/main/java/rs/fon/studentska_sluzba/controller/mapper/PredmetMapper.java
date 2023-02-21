package rs.fon.studentska_sluzba.controller.mapper;

import org.springframework.stereotype.Component;
import rs.fon.studentska_sluzba.controller.dto.PredmetDTO;
import rs.fon.studentska_sluzba.domain.Predmet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PredmetMapper implements Mapper<Predmet, PredmetDTO> {
    @Override
    public PredmetDTO entityToDTO(Predmet predmet) {
        return new PredmetDTO(predmet.getId(), predmet.getNaziv(), predmet.getESPB());
    }

    @Override
    public Predmet DTOToEntity(PredmetDTO predmetDTO) {
        return Predmet.builder()
                .id(predmetDTO.id())
                .naziv(predmetDTO.naziv())
                .ESPB(predmetDTO.ESPB())
                .build();
    }

    public Set<PredmetDTO> entitiesToDTOs(Set<Predmet> entities) {
        return entities.stream().map(this::entityToDTO).collect(Collectors.toSet());
    }

    public Set<Predmet> DTOsToEntities(Set<PredmetDTO> dtos) {
        return dtos.stream().map(this::DTOToEntity).collect(Collectors.toSet());
    }

}
