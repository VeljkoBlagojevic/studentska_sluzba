package rs.fon.studentska_sluzba.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record MolbaDTO(
        Long id,
        @NotBlank(message = "Pitanje ne moze biti prazno")
        String pitanje,
        @PastOrPresent(message = "Datum pitanja ne moze biti u buducnosti")
        LocalDate datumPitanja,
        String odgovor,
        @PastOrPresent(message = "Datum odgovora ne moze biti u buducnosti")
        LocalDate datumOdgovora,

        String tipMolbe,
        String statusMolbe,
        StudentDTO student) {
}
