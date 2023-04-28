package rs.fon.studentska_sluzba.logging;

import rs.fon.studentska_sluzba.domain.Student;

import java.time.LocalDateTime;

public record ErrorLog(Student trenutniKorisnik, LocalDateTime vreme, Throwable errorCause) {
}
