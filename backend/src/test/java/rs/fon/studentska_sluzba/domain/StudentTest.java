package rs.fon.studentska_sluzba.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentTest {

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
    }

    @AfterEach
    void tearDown() {
        student = null;
    }

    @Test
    void setId() {
        student.setId(1l);
        assertEquals(1l, student.getId());
    }

    @Test
    void setIndeks() {
        student.setIndeks("0353/2019");
        assertEquals("0353/2019", student.getIndeks());
    }

    @Test
    void setIme() {
        student.setIme("Veljko");
        assertEquals("Veljko", student.getIme());
    }

    @Test
    void setPrezime() {
        student.setPrezime("Blagojevic");
        assertEquals("Blagojevic", student.getPrezime());
    }

    @Test
    void setJmbg() {
        student.setJmbg("1234567890123");
        assertEquals("1234567890123", student.getJmbg());
    }

    @Test
    void setDatumRodjenja() {
        student.setDatumRodjenja(LocalDate.of(2000, 8, 2));
        assertEquals(LocalDate.of(2000, 8, 2), student.getDatumRodjenja());
    }

    @Test
    void setBrojTelefona() {
        student.setBrojTelefona("+381677116969");
        assertEquals("+381677116969", student.getBrojTelefona());
    }

    @Test
    void setStudentskiEmail() {
        student.setStudentskiEmail("vb20190353@student.fon.bg.ac.rs");
        assertEquals("vb20190353@student.fon.bg.ac.rs", student.getStudentskiEmail());
    }

    @Test
    void setSlika() {
        student.setSlika("slika.jpg");
        assertEquals("slika.jpg", student.getSlika());
    }

    @Test
    void setImeRoditelja() {
        student.setImeRoditelja("Borislav");
        assertEquals("Borislav", student.getImeRoditelja());
    }

    @Test
    void setLicniEmail() {
        student.setLicniEmail("veljko@gmail.com");
        assertEquals("veljko@gmail.com", student.getLicniEmail());
    }

    @Test
    void setMestoRodjenja() {
        student.setMestoRodjenja(new Grad(1L, "Pancevo", 26000));
        assertEquals("Pancevo", student.getMestoRodjenja().getNaziv());
        assertEquals(26000, student.getMestoRodjenja().getZipcode());
    }

    @Test
    void setPrijave() {
        HashSet<Predmet> prijave = new HashSet<>(List.of(new Predmet(1L, "Napredno Programiranje", 4), new Predmet(2L, "Inteligentni Sistemi", 6)));
        student.setPrijave(prijave);
        assertEquals(prijave, student.getPrijave());
    }

    @Test
    void setUsername() {
        student.setUsername("vb20190353");
        assertEquals("vb20190353", student.getUsername());
    }

    @Test
    void setPassword() {
        student.setPassword("password");
        assertEquals("password", student.getPassword());
    }

    @Test
    void setRole() {
        student.setRole(Role.USER);
        assertEquals(Role.USER, student.getRole());
    }

    @Test
    void getPassword() {
        student.setPassword("password");
        assertEquals("password", student.getPassword());
    }

    @Test
    void getUsername() {
        student.setUsername("vb20190353");
        assertEquals("vb20190353", student.getUsername());
    }

    @Test
    void isAccountNonExpired() {
        assertTrue(student.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked() {
        assertTrue(student.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        assertTrue(student.isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        assertTrue(student.isEnabled());
    }

    @Test
    void testToString() {
        student = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .mestoRodjenja(new Grad(1L, "Pancevo", 26000))
                .studentskiEmail("vb20190353@student.fon.bg.ac.rs")
                .licniEmail("veljko@gmail.com")
                .brojTelefona("+381677116969")
                .indeks("0353/2019")
                .jmbg("1234567890123")
                .build();
        assertTrue(student.toString().contains("Veljko"));
        assertTrue(student.toString().contains("Blagojevic"));
        assertTrue(student.toString().contains("Pancevo"));
        assertTrue(student.toString().contains("vb20190353@student.fon.bg.ac.rs"));
        assertTrue(student.toString().contains("veljko@gmail.com"));
        assertTrue(student.toString().contains("+381677116969"));
        assertTrue(student.toString().contains("0353/2019"));
        assertTrue(student.toString().contains("1234567890123"));
    }

    @ParameterizedTest
    @CsvSource({
            "1, John, Doe, 1234/5678, 1234567890123, +381123456789, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, 1, John, Doe, 1234/5678, 1234567890123, +381123456789, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, true",
            "1, John, Doe, 1234/5678, 1234567890123, +381123456789, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, 2, John, Doe, 1234/5678, 1234567890123, +381123456789, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, false",
            "1, John, Doe1, 1234/5678, 1234567890123, +381123456789, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, 2, John, Doe2, 1234/5678, 1234567890123, +381123456789, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, false",
            "1, John, Doe1, 1234/5678, 1234567890123, +381123456789, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, 2, John, Doe2, 1234/5678, 1234567890123, +381123456789, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, false",
            "1, John, Doe, 1234/5678, 1234567890123, +381123456789, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, 2, John, Doe, 9876/5432, 1234567890123, +381987654321, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, false",
            "1, John, Doe, 1234/5678, 1234567890123, +381123456789, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, 2, John, Doe, 1234/5678, 9876543210987, +381987654321, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, false",
            "1, John, Doe, 1234/5678, 1234567890123, +381123456789, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, 2, John, Doe, 1234/5678, 1234567890123, +381987654321, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, false",
            "1, John, Doe, 1234/5678, 1234567890123, +381123456789, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, 2, John, Doe, 1234/5678, 1234567890123, +381123456789, jane@student.com, image1.jpg, Parent1, jane@example.com, jane1, password1, false",
            "1, John, Doe, 1234/5678, 1234567890123, +381123456789, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, 2, John, Doe, 1234/5678, 1234567890123, +381123456789, john@student.com, image2.jpg, Parent1, john@example.com, john1, password1, false",
            "1, John, Doe, 1234/5678, 1234567890123, +381123456789, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, 2, John, Doe, 1234/5678, 1234567890123, +381123456789, john@student.com, image2.jpg, Parent2, john@example.com, john1, password1, false",
            "1, John, Doe, 1234/5678, 1234567890123, +381123456789, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, 2, John, Doe, 1234/5678, 1234567890123, +381123456789, john@student.com, image2.jpg, Parent2, john2@example.com, john1, password1, false",
            "1, John, Doe, 1234/5678, 1234567890123, +381123456789, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, 2, John, Doe, 1234/5678, 1234567890123, +381123456789, john@student.com, image2.jpg, Parent2, john@example.com, john2, password1, false",
            "1, John, Doe, 1234/5678, 1234567890123, +381123456789, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, 2, John, Doe, 1234/5678, 1234567890123, +381123456789, john@student.com, image2.jpg, Parent2, john@example.com, john1, password2, false",
            "1, John, Doe, 1234/5678, 1234567890123, +381123456789, john@student.com, image1.jpg, Parent1, john@example.com, john1, password1, 2, Jane, Smith, 9876/5432, 9876543210987, +381987654321, jane@student.com, image2.jpg, Parent2, jane@example.com, jane2, password2, false"
    })
    void testEquals(
            long id1, String ime1, String prezime1, String indeks1, String jmbg1, String brojTelefona1, String studentskiEmail1, String slika1, String imeRoditelja1, String licniEmail1, String username1, String password1,
            long id2, String ime2, String prezime2, String indeks2, String jmbg2, String brojTelefona2, String studentskiEmail2, String slika2, String imeRoditelja2, String licniEmail2, String username2, String password2,
            boolean areEqual
    ) {
        student = Student.builder()
                .id(id1)
                .ime(ime1)
                .prezime(prezime1)
                .indeks(indeks1)
                .jmbg(jmbg1)
                .brojTelefona(brojTelefona1)
                .studentskiEmail(studentskiEmail1)
                .slika(slika1)
                .imeRoditelja(imeRoditelja1)
                .licniEmail(licniEmail1)
                .username(username1)
                .password(password1)
                .build();

        Student student2 = Student.builder()
                .id(id2)
                .ime(ime2)
                .prezime(prezime2)
                .indeks(indeks2)
                .jmbg(jmbg2)
                .brojTelefona(brojTelefona2)
                .studentskiEmail(studentskiEmail2)
                .slika(slika2)
                .imeRoditelja(imeRoditelja2)
                .licniEmail(licniEmail2)
                .username(username2)
                .password(password2)
                .build();


        assertEquals(student.equals(student2), areEqual);
    }

}