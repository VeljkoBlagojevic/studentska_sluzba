package rs.fon.studentska_sluzba.controller.mapper;

import org.springframework.stereotype.Component;
import rs.fon.studentska_sluzba.controller.dto.NepolozeniPredmetDTO;
import rs.fon.studentska_sluzba.domain.NepolozeniPredmet;

@Component
public class NepolozeniPredmetMapper implements Mapper<NepolozeniPredmet, NepolozeniPredmetDTO> {

    private final PredmetMapper predmetMapper;

    public NepolozeniPredmetMapper(PredmetMapper predmetMapper) {
        this.predmetMapper = predmetMapper;
    }

    @Override
    public NepolozeniPredmetDTO entityToDTO(NepolozeniPredmet nepolozeniPredmet) {
        return new NepolozeniPredmetDTO(nepolozeniPredmet.getId(), nepolozeniPredmet.getTrenutnoSlusa(), predmetMapper.entityToDTO(nepolozeniPredmet.getPredmet()));
    }

    @Override
    public NepolozeniPredmet DTOToEntity(NepolozeniPredmetDTO nepolozeniPredmetDTO) {
        return NepolozeniPredmet.builder()
                .id(nepolozeniPredmetDTO.id())
                .trenutnoSlusa(nepolozeniPredmetDTO.trenutnoSlusa())
                .predmet(predmetMapper.DTOToEntity(nepolozeniPredmetDTO.predmet()))
                .build();
    }
}
