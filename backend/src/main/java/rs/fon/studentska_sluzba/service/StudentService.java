package rs.fon.studentska_sluzba.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.repository.StudentRepository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Sadrzi poslovnu logiku sa radom sa studentima.
 * <p>
 * Klasa sluzi da manipulise, upravlja sa modelom i podacima vezanim sa studentima tj korisnicima sistema.
 * Omogucavama dodavanje, citanje, brisanje i menjanje informacija studenta.
 * Dodatna funkcionalnost pristupu SecurityContextu gde se nalaze informacije o trenutno ulogovanom korisniku.
 *
 * @author VeljkoBlagojevic
 */
@Service
public class StudentService {
    /**
     * Broker baze podataka koji je posrednik ka tabeli Student.
     */
    private final StudentRepository studentRepository;


    /**
     * Parametrizovani konstruktor koji automatski injektuje sve zavinosti uz pomoc Springa.
     * @param studentRepository Klasa koja implementira sve neophodne operacije sa radom tabelom Student u bazi podataka.
     */
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Metoda vraca listu svih studenata koji se nalazi u bazi podataka.
     * @return lista svih studenata
     */
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    /**
     * Metoda vraca studenata koji je trenutno ulogovan na sistem.
     * @return studenat koji je trenutno ulogovan na sistem.
     */
    public Student getTrenutniStudent() {
        return (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Metoda proverava da li trenutno ulogovani student ima kredencijale za administratora sistema.
     * @return Da li je trenutno prijavljeni korisnik administrator.
     */
    public Boolean jelTrenutniKorisnikAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
    }

    /**
     * Metoda koja azurira podatke o studentu.
     *
     * @param student Student sa novim informacijama za azuriranje
     * @return Azurirani student sa novim informacijama koje su perzistirane.
     */
    public Student updateStudent(Student student) {
        studentRepository.findById(student.getId()).ifPresent(studentRepository::save);
        return studentRepository.save(student);
    }

    /**
     * Metoda koja azurira podatke o studentu.
     * <p>
     * Ukoliko dodje do greske ili nepostoji dati student, metoda vraca null.
     *
     * @param id Identifikator studenta koji ce se azurirati.
     * @param fields Polja koja je potrebno azurirati.
     * @return Azurirani student sa novim informacijama koje su perzistirane.
     */
    public Student updateStudent(Long id, Map<String, Object> fields) {
        Optional<Student> postojeciStudent = studentRepository.findById(id);
        if (postojeciStudent.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Student.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, postojeciStudent.get(), value);
            });
            return studentRepository.save(postojeciStudent.get());
        }
        return null;
    }
}
