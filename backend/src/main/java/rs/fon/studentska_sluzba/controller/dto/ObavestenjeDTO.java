package rs.fon.studentska_sluzba.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ObavestenjeDTO(
        Long id,
        LocalDate datum,
        @NotBlank(message = "Sadrzaj obavestenja ne sme biti prazno")
        @Size(max = 1000, message = "Sadrzaj obavestenja ne sme prelaziti 1000 karaktera")
        String sadrzaj) {
}
