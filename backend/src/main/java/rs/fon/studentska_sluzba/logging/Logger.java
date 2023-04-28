package rs.fon.studentska_sluzba.logging;

public interface Logger {
    void info(String message, Object object);
    void error(Throwable errorCause);
}