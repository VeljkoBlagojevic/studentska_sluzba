package rs.fon.studentska_sluzba.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record PolaganjeDTO(
        Long id,
        @Past(message = "Polaganje mora da se desilo u proslosti")
        LocalDate datum,
        Boolean polozio,
        @Min(value = 5, message = "Ocena ne moze biti manja od 5")
        @Max(value = 10, message = "Ocena ne moze biti veca od 10")
        Integer ocena,
        PredmetDTO predmet) {
}
