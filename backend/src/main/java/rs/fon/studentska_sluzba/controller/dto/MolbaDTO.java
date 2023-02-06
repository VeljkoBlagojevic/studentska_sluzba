package rs.fon.studentska_sluzba.controller.dto;

import java.time.LocalDate;

public record MolbaDTO(Long id, String pitanje, LocalDate datumPitanja, String odgovor, LocalDate datumOdgovora, String tipMolbe, String statusMolbe) {
}
