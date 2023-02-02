package rs.fon.studentska_sluzba.repository.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.repository.PredmetRepository;
import rs.fon.studentska_sluzba.repository.StudentRepository;

import java.time.LocalDate;

@Component
public class PredmetDataLoader implements CommandLineRunner {

    private final PredmetRepository predmetRepository;

    public PredmetDataLoader(PredmetRepository predmetRepository) {
        this.predmetRepository = predmetRepository;
    }

    @Override
    public void run(String... args) {
        seedData();
    }

    private void seedData() {
        if (predmetRepository.count() == 0) {

            Predmet spa = Predmet.builder()
                    .naziv("Strukture podataka i algoritmi")
                    .ESPB(6)
                    .build();

            Predmet pis = Predmet.builder()
                    .naziv("Projektovanje informacionih sistema")
                    .ESPB(6)
                    .build();

            predmetRepository.save(spa);
            predmetRepository.save(pis);
        }
    }
}
