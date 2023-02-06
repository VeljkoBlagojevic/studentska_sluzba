package rs.fon.studentska_sluzba.controller.dto;

import java.time.LocalDate;

public record ObavestenjeDTO(Long id, LocalDate datum, String sadrzaj) {
}
