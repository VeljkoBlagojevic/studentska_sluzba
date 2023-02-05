package rs.fon.studentska_sluzba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.fon.studentska_sluzba.domain.Obavestenje;

@Repository
public interface ObavestenjeRepository extends JpaRepository<Obavestenje, Long> {
}
