package rs.fon.studentska_sluzba.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.fon.studentska_sluzba.domain.NepolozeniPredmet;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.domain.Student;

import java.util.List;

@Repository
public interface NepolozeniPredmetRepository extends JpaRepository<NepolozeniPredmet, Long> {

    List<NepolozeniPredmet> findByStudentAndTrenutnoSlusa(Student student, Boolean trenutnoSlusa);
    Page<NepolozeniPredmet> findByStudentAndTrenutnoSlusa(Student student, Boolean trenutnoSlusa, Pageable pageable);
    List<NepolozeniPredmet> findByTrenutnoSlusa(Boolean trenutnoSlusa);
    Page<NepolozeniPredmet> findByTrenutnoSlusa(Boolean trenutnoSlusa, Pageable pageable);
    List<NepolozeniPredmet> findByStudent(Student student);

}
