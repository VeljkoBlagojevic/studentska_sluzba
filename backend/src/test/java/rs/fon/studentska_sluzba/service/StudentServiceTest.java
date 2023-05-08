package rs.fon.studentska_sluzba.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.repository.StudentRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void testFindAll() {
        Student s1 = new Student();
        s1.setId(1L);
        s1.setIme("Ime1");
        s1.setPrezime("Prezime1");

        Student s2 = new Student();
        s2.setId(2L);
        s2.setIme("Ime2");
        s2.setPrezime("Prezime2");

        when(studentRepository.findAll()).thenReturn(List.of(s1,s2));

        List<Student> students = studentService.findAll();

        assertEquals(2, students.size());
        assertEquals("Ime1", students.get(0).getIme());
        assertEquals("Prezime1", students.get(0).getPrezime());
        assertEquals("Ime2", students.get(1).getIme());
        assertEquals("Prezime2", students.get(1).getPrezime());
        verify(studentRepository, times(1)).findAll();
        verifyNoMoreInteractions(studentRepository);
    }


    @Test
    void testUpdateStudent() {
        Student s1 = new Student();
        s1.setId(1L);
        s1.setIme("Ime1");
        s1.setPrezime("Prezime1");

        Student s2 = new Student();
        s2.setId(1L);
        s2.setIme("Ime2");
        s2.setPrezime("Prezime2");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(s1));
        when(studentRepository.save(s1)).thenReturn(s2);

        Student updatedStudent = studentService.updateStudent(s1);

        assertEquals("Ime2", updatedStudent.getIme());
        assertEquals("Prezime2", updatedStudent.getPrezime());
    }

    @Test
    void testUpdateStudentReflect() {
        long studentId = 1L;

        Student s1 = new Student();
        s1.setId(studentId);
        s1.setIme("Ime1");
        s1.setPrezime("Prezime1");

        Student s2 = new Student();
        s2.setId(studentId);
        s2.setIme("Ime2");
        s2.setPrezime("Prezime2");

        Map<String, Object> fields = new HashMap<>();
        fields.put("ime", "Ime2");
        fields.put("prezime", "Prezime2");

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(s1));
        when(studentRepository.save(s2)).thenReturn(s2);

        Student updatedStudent = studentService.updateStudent(studentId, fields);

        assertEquals("Ime2", updatedStudent.getIme());
        assertEquals("Prezime2", updatedStudent.getPrezime());
    }
    @Test
    void testUpdateStudentReflectNepostojeciId() {
        long studentId = 1L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
        assertNull(studentService.updateStudent(studentId, anyMap()));
    }

}