package rs.fon.studentska_sluzba.controller.dto;

import java.time.LocalDate;

public record StudentDTO(Long id, String ime, String prezime, String jmbg, String indeks, LocalDate datumRodjenja,
                         String brojTelefona, String studentskiEmail, String licniEmail, String slika,
                         String imeRoditelja,
                         String mestoRodjenja, String username) {
}
