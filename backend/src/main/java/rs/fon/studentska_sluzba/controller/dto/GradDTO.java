package rs.fon.studentska_sluzba.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record GradDTO(
        Long id,
        @NotBlank(message = "Mora postojati naziv grada")

        String naziv,
        @Min(value = 10_000, message = "Zipcode mora imati tacno 5 cifara")
        @Max(value = 99_999, message = "Zipcode mora imati tacno 5 cifara")
        Integer zipcode) {
}
