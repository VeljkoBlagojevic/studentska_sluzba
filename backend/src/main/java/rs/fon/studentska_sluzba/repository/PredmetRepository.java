package rs.fon.studentska_sluzba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.fon.studentska_sluzba.domain.Predmet;

import java.util.List;

@Repository
public interface PredmetRepository extends JpaRepository<Predmet, Long> {
}
