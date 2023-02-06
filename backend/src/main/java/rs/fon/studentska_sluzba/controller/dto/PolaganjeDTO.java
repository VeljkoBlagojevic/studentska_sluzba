package rs.fon.studentska_sluzba.controller.dto;

import java.time.LocalDate;

public record PolaganjeDTO(Long id, LocalDate datum, Boolean polozio, Integer ocena, PredmetDTO predmet) {
}
