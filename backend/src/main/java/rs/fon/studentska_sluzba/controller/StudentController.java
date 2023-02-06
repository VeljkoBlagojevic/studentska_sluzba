package rs.fon.studentska_sluzba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.fon.studentska_sluzba.controller.dto.StudentDTO;
import rs.fon.studentska_sluzba.controller.mapper.StudentMapper;
import rs.fon.studentska_sluzba.service.StudentService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/studenti")
public class StudentController {

    private final StudentService studentService;

    private final StudentMapper studentMapper;

    @Autowired
    public StudentController(StudentService studentService, StudentMapper studentMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getSviStudenti() {
        return ResponseEntity.ok(studentMapper.entitiesToDTOs(studentService.findAll()));
    }

    @GetMapping("/trenutni")
    public ResponseEntity<StudentDTO> getTrenutniStudent() {
        return ResponseEntity.ok(studentMapper.entityToDTO(studentService.getTrenutniStudent()));
    }

}
