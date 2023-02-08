package rs.fon.studentska_sluzba.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record StudentDTO(
        Long id,
        @NotBlank(message = "Ime ne moze biti prazno")
        String ime,
        @NotBlank(message = "Prezime ne moze biti prazno")
        String prezime,
        @Size(min = 13, max = 13, message = "JMBG mora imati tacno 13 cifara")
        String jmbg,
        @Pattern(regexp = "\\d{4}/(19|20)\\d{2}")
        String indeks,
        LocalDate datumRodjenja,
        @Pattern(regexp = "\\+381\\d{9}", message = "Mobilni telefon treba da bude u formatu '+3816...'")
        String brojTelefona,
        @Email(regexp = "[a-z]{2}\\d{8}@student.fon.bg.ac.rs", message = "Studentski email nije u adekvatnom formatu")
        String studentskiEmail,
        @Email
        String licniEmail,
        String slika,
        String imeRoditelja,
        String mestoRodjenja,
        String username) {
}
