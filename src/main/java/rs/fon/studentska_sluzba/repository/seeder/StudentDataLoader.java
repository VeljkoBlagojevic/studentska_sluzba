package rs.fon.studentska_sluzba.repository.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rs.fon.studentska_sluzba.domain.Grad;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.repository.GradRepository;
import rs.fon.studentska_sluzba.repository.StudentRepository;

import java.time.LocalDate;

@Component
public class StudentDataLoader implements CommandLineRunner {

    private final StudentRepository studentRepository;

    public StudentDataLoader(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) {
        seedData();
    }

    private void seedData() {
        if (studentRepository.count() == 0) {

            Student veljko = Student.builder()
                    .ime("Veljko")
                    .imeRoditelja("Borislav")
                    .prezime("Blagojevic")
                    .jmbg("0208000860069")
                    .indeks("0353/2019")
                    .datumRodjenja(LocalDate.of(2000, 8, 2))
                    .studentskiEmail("vb20190353@student.fon.bg.ac.rs")
                    .licniEmail("veljkoblagojevic008@gmail.com")
                    .brojTelefona("+381677116969")
                    .username("vb20190353")
                    .password("velja123")
                    .build();

            studentRepository.save(veljko);
        }
    }
}
