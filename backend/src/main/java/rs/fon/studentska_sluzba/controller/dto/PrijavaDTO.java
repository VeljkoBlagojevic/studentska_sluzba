package rs.fon.studentska_sluzba.controller.dto;

import java.util.Set;

public record PrijavaDTO(StudentDTO studentDTO, Set<PredmetDTO> predmetDTOs) {
}
