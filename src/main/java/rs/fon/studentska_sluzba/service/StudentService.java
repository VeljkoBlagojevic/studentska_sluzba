package rs.fon.studentska_sluzba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.repository.StudentRepository;

import java.security.Security;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Autowired
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student getTrenutniStudent() {
        return (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
