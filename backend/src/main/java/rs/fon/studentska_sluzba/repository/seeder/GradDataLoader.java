package rs.fon.studentska_sluzba.repository.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rs.fon.studentska_sluzba.domain.Grad;
import rs.fon.studentska_sluzba.repository.GradRepository;

@Component
public class GradDataLoader implements CommandLineRunner {

    private final GradRepository gradRepository;

    public GradDataLoader(GradRepository gradRepository) {
        this.gradRepository = gradRepository;
    }

    @Override
    public void run(String... args) {
        seedData();
    }

    private void seedData() {
        if (gradRepository.count() == 0) {

            Grad omoljica = new Grad("Omoljica", 26230);
            Grad beograd = new Grad("Beograd", 11000);
            Grad nis = new Grad("Nis", 18000);
            Grad petrovacNaMlavi = new Grad("Petrovac na Mlavi", 12300);

            gradRepository.save(omoljica);
            gradRepository.save(beograd);
            gradRepository.save(nis);
            gradRepository.save(petrovacNaMlavi);
        }
    }
}
