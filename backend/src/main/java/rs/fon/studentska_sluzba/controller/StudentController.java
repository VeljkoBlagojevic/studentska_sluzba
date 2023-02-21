package rs.fon.studentska_sluzba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.fon.studentska_sluzba.controller.dto.StudentDTO;
import rs.fon.studentska_sluzba.controller.mapper.StudentMapper;
import rs.fon.studentska_sluzba.service.StudentService;

import java.util.List;
import java.util.Map;

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

    @PatchMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody Map<String, Object> attributes) {
        return ResponseEntity.ok(studentMapper.entityToDTO(studentService.updateStudent(id, attributes)));
    }

}
