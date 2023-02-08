package rs.fon.studentska_sluzba.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record PredmetDTO(
        Long id,
        String naziv,
        @Min(value = 1, message = "Predmet mora vredeti barem 1 ESPB")
        @Max(value = 30, message = "Predmet ne moze vredeti vise od 30 ESPB")
        Integer ESPB) {
}
