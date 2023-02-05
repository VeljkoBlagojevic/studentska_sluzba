package rs.fon.studentska_sluzba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.fon.studentska_sluzba.domain.NepolozeniPredmet;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.domain.Student;

import java.util.List;

@Repository
public interface NepolozeniPredmetRepository extends JpaRepository<NepolozeniPredmet, Long> {

    List<NepolozeniPredmet> findByStudentAndTrenutnoSlusa(Student student, Boolean trenutnoSlusa);
    List<NepolozeniPredmet> findByStudent(Student student);

    NepolozeniPredmet findByPredmetAndStudent(Predmet predmet, Student student);

    NepolozeniPredmet findByPredmetAndIdAndStudent(Predmet predmet, Long id, Student student);

}
