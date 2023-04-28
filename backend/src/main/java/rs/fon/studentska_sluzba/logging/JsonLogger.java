package rs.fon.studentska_sluzba.logging;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.service.StudentService;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JsonLogger implements Logger {

    @Value("${logging.error.json}")
    private String JSON_ERROR_LOG;

    @Value("${logging.info.json}")
    private String JSON_INFO_LOG;
    private final StudentService studentService;

    private final Gson gson;


    public JsonLogger(StudentService studentService, Gson gson) {
        this.studentService = studentService;
        this.gson = gson;
    }


    @Override
    public void info(String message, Object object) {
        Student trenutniStudent = studentService.getTrenutniStudent();
        LocalDateTime trenutnoVreme = LocalDateTime.now();
        try (FileWriter fileWriter = new FileWriter(JSON_INFO_LOG)) {
            InfoLog infoLog = new InfoLog(trenutniStudent, trenutnoVreme, message, object);
            gson.toJson(infoLog, InfoLog.class, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void error(Throwable errorCause) {
        Student trenutniStudent = studentService.getTrenutniStudent();
        LocalDateTime trenutnoVreme = LocalDateTime.now();
        try (FileWriter fileWriter = new FileWriter(JSON_ERROR_LOG)) {
            ErrorLog errorLog = new ErrorLog(trenutniStudent, trenutnoVreme, errorCause);
            gson.toJson(errorLog, ErrorLog.class, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }


}