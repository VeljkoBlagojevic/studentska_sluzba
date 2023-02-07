package rs.fon.studentska_sluzba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.studentska_sluzba.domain.Polaganje;
import rs.fon.studentska_sluzba.domain.Student;

import java.util.List;
import java.util.Optional;

public interface PolaganjeRepository extends JpaRepository<Polaganje, Long> {

    List<Polaganje> findByStudent(Student student);
    Optional<Polaganje> findByStudentAndId(Student student, Long id);

    List<Polaganje> findByStudentAndAndPolozio(Student student, Boolean polozio);

    List<Polaganje> findByPolozio(Boolean polozio);
}
