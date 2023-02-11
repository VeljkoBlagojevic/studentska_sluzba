package rs.fon.studentska_sluzba.controller.mapper;

import org.springframework.stereotype.Component;
import rs.fon.studentska_sluzba.controller.dto.MolbaDTO;
import rs.fon.studentska_sluzba.domain.Molba;
import rs.fon.studentska_sluzba.domain.StatusMolbe;
import rs.fon.studentska_sluzba.domain.TipMolbe;

@Component
public class MolbaMapper implements Mapper<Molba, MolbaDTO>  {

    private StudentMapper studentMapper;

    public MolbaMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    public MolbaDTO entityToDTO(Molba molba) {
        return new MolbaDTO(molba.getId(),
                molba.getPitanje(),
                molba.getDatumPitanja(),
                molba.getOdgovor(),
                molba.getDatumOdgovora(),
                molba.getTipMolbe().name(),
                molba.getStatusMolbe() != null ? molba.getStatusMolbe().name() : null,
                molba.getStudent() != null ? studentMapper.entityToDTO(molba.getStudent()) : null);
    }

    @Override
    public Molba DTOToEntity(MolbaDTO molbaDTO) {
        return Molba.builder()
                .id(molbaDTO.id())
                .pitanje(molbaDTO.pitanje())
                .datumPitanja(molbaDTO.datumPitanja())
                .odgovor(molbaDTO.odgovor())
                .datumOdgovora(molbaDTO.datumOdgovora())
                .tipMolbe(TipMolbe.valueOf(molbaDTO.tipMolbe()))
                .statusMolbe(molbaDTO.statusMolbe() != null ? StatusMolbe.valueOf(molbaDTO.statusMolbe()) : null)
                .student(studentMapper.DTOToEntity(molbaDTO.student()))
                .build();
    }
}
