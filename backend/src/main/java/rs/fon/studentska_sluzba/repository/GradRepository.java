package rs.fon.studentska_sluzba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.fon.studentska_sluzba.domain.Grad;

@Repository
public interface GradRepository extends JpaRepository<Grad, Long> {
}
