package rs.fon.studentska_sluzba.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.repository.StudentRepository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public Boolean jelTrenutniKorisnikAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
    }

    public Student updateStudent(Student student) {
        studentRepository.findById(student.getId()).ifPresent(studentRepository::save);
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Map<String, Object> fields) {
        Optional<Student> postojeciStudent = studentRepository.findById(id);
        if (postojeciStudent.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Student.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, postojeciStudent.get(), value);
            });
            return studentRepository.save(postojeciStudent.get());
        }
        return null;
    }
}
