package rs.fon.studentska_sluzba.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.studentska_sluzba.domain.Polaganje;
import rs.fon.studentska_sluzba.domain.Student;

import java.util.List;
import java.util.Optional;

public interface PolaganjeRepository extends JpaRepository<Polaganje, Long> {

    List<Polaganje> findByStudent(Student student);
    Optional<Polaganje> findByStudentAndId(Student student, Long id);

    List<Polaganje> findByStudentAndAndPolozio(Student student, Boolean polozio);
    Page<Polaganje> findByStudentAndAndPolozio(Student student, Boolean polozio, Pageable pageable);

    List<Polaganje> findByPolozio(Boolean polozio);
    Page<Polaganje> findByPolozio(Boolean polozio, Pageable pageable);
}
