package rs.fon.studentska_sluzba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.studentska_sluzba.domain.Polaganje;
import rs.fon.studentska_sluzba.domain.Student;

import java.util.List;

public interface PolaganjeRepository extends JpaRepository<Polaganje, Long> {

    List<Polaganje> findByStudent(Student student);

    List<Polaganje> findByStudentAndAndPolozio(Student student, Boolean polozio);


}
