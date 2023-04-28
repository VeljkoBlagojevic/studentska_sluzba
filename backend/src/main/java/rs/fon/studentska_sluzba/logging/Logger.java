package rs.fon.studentska_sluzba.logging;

public interface Logger {
    void info(String message);
    void error(Throwable errorCause);
}