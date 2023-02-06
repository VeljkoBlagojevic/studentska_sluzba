package rs.fon.studentska_sluzba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.studentska_sluzba.domain.Molba;
import rs.fon.studentska_sluzba.domain.Student;

import java.util.List;

public interface MolbaRepository extends JpaRepository<Molba, Long> {
    List<Molba> findByStudent(Student student);
}
