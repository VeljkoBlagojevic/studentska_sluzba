package rs.fon.studentska_sluzba.logging;

import rs.fon.studentska_sluzba.controller.dto.StudentDTO;

import java.time.LocalDateTime;

public record InfoLog(StudentDTO trenutniKorisnik, LocalDateTime vreme, String message) implements Log {
}
