package rs.fon.studentska_sluzba.logging.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rs.fon.studentska_sluzba.controller.dto.StudentDTO;
import rs.fon.studentska_sluzba.controller.mapper.StudentMapper;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.logging.ErrorLog;
import rs.fon.studentska_sluzba.logging.InfoLog;
import rs.fon.studentska_sluzba.logging.Log;
import rs.fon.studentska_sluzba.logging.Logger;
import rs.fon.studentska_sluzba.service.StudentService;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class JsonLogger implements Logger {


    @Value("${logging.error.json}")
    private String JSON_ERROR_LOG;

    @Value("${logging.info.json}")
    private String JSON_INFO_LOG;
    private final StudentService studentService;

    private final StudentMapper studentMapper;

    private final Gson gson;


    public JsonLogger(StudentService studentService, StudentMapper studentMapper, Gson gson) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
        this.gson = gson;
    }


    @Override
     public synchronized void info(String message) {
        Student trenutniStudent = studentService.getTrenutniStudent();
        StudentDTO studentDTO = studentMapper.entityToDTO(trenutniStudent);
        LocalDateTime trenutnoVreme = LocalDateTime.now();
        Type listType = new TypeToken<List<InfoLog>>(){}.getType();

        InfoLog infoLog = new InfoLog(studentDTO, trenutnoVreme, message);
        log(listType, infoLog, JSON_INFO_LOG);

    }

    @Override
    public synchronized void error(Throwable errorCause) {
        Student trenutniStudent = studentService.getTrenutniStudent();
        StudentDTO studentDTO = studentMapper.entityToDTO(trenutniStudent);
        LocalDateTime trenutnoVreme = LocalDateTime.now();
        Type listType = new TypeToken<List<ErrorLog>>(){}.getType();

        ErrorLog errorLog = new ErrorLog(studentDTO, trenutnoVreme, ExceptionUtils.getStackTrace(errorCause));
        log(listType, errorLog, JSON_ERROR_LOG);
    }

    private void log(Type listType, Log log, String filePath) {
        List<? super Log> logs = new ArrayList<>();
        try (FileReader fileReader = new FileReader(filePath)) {
            logs = gson.fromJson(fileReader, listType);
            if(logs == null)
                logs = new ArrayList<>();
            logs.add(log);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileWriter fileWriter = new FileWriter(filePath, false)) {
            gson.toJson(logs, listType, fileWriter);
        } catch (IOException e) {
            error(e);
        }
    }


}