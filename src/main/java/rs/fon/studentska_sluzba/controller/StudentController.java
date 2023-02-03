package rs.fon.studentska_sluzba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/studenti")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getSviStudenti() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("trenutni")
    public ResponseEntity<Student> getTrenutniStudent() {
        return ResponseEntity.ok(studentService.getTrenutniStudent());
    }

}
