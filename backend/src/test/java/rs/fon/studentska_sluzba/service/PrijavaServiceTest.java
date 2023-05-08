package rs.fon.studentska_sluzba.service;

import org.checkerframework.common.value.qual.IntRange;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.exception.EntityNotFoundException;
import rs.fon.studentska_sluzba.logging.Logger;
import rs.fon.studentska_sluzba.repository.PredmetRepository;
import rs.fon.studentska_sluzba.repository.StudentRepository;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrijavaServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentService studentService;

    @Mock
    private PredmetRepository predmetRepository;

    @Mock
    private Logger logger;

    @InjectMocks
    private PrijavaService prijavaService;

    @Test
    void testDodajPrijavuOK() {
        Predmet p1 = new Predmet(1L, "SPA", 6);
        Predmet p2 = new Predmet(2L, "UIS", 5);
        Predmet predmetZaDodavanje = new Predmet(3L, "Ekonomija", 5);

        Student s = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .prijave(new HashSet<>(List.of(p1, p2)))
                .build();

        when(studentService.getTrenutniStudent()).thenReturn(s);

        prijavaService.dodajPrijavu(predmetZaDodavanje);

        assertTrue(s.getPrijave().contains(predmetZaDodavanje));
        assertTrue(s.getPrijave().contains(p1));
        assertTrue(s.getPrijave().contains(p2));
    }

    @Test
    void testDodajPrijavuVecPostoji() {
        Predmet p1 = new Predmet(1L, "SPA", 6);
        Predmet p2 = new Predmet(2L, "UIS", 5);
        Predmet predmetZaDodavanje = p2;

        Student s = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .prijave(new HashSet<>(List.of(p1, p2)))
                .build();

        when(studentService.getTrenutniStudent()).thenReturn(s);

        prijavaService.dodajPrijavu(predmetZaDodavanje);

        assertTrue(s.getPrijave().contains(predmetZaDodavanje));
        assertTrue(s.getPrijave().contains(p1));
        assertTrue(s.getPrijave().contains(p2));
    }

    @Test
    void testGetPrijaveAdmin() {
        Predmet p1 = new Predmet(1L, "SPA", 6);
        Predmet p2 = new Predmet(2L, "UIS", 5);
        Predmet p3 = new Predmet(3L, "Ekonomija", 5);

        Student s1 = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .prijave(new HashSet<>(List.of(p1, p2)))
                .build();
        Student s2 = Student.builder()
                .id(2L)
                .ime("Nikola")
                .prezime("Vujcic")
                .prijave(new HashSet<>(List.of(p1, p3)))
                .build();

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);
        when(studentRepository.findAll()).thenReturn(List.of(s1, s2));

        Set<Predmet> prijave = prijavaService.getPrijave();

        assertTrue(prijave.contains(p1));
        assertTrue(prijave.contains(p2));
        assertTrue(prijave.contains(p3));
    }

    @Test
    void testGetPrijaveUser() {
        Predmet p1 = new Predmet(1L, "SPA", 6);
        Predmet p2 = new Predmet(2L, "UIS", 5);

        Student s = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .prijave(new HashSet<>(List.of(p1, p2)))
                .build();

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(false);
        when(studentService.getTrenutniStudent()).thenReturn(s);

        Set<Predmet> prijave = prijavaService.getPrijave();

        assertTrue(prijave.contains(p1));
        assertTrue(prijave.contains(p2));
    }

    @Test
    void testGetPrijavaSaIdAdmin() {
        Predmet p1 = new Predmet(1L, "SPA", 6);
        Predmet p2 = new Predmet(2L, "UIS", 5);
        Predmet p3 = new Predmet(3L, "Ekonomija", 5);

        long prijavaId = 1L;

        Student s1 = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .prijave(new HashSet<>(List.of(p1, p2)))
                .build();
        Student s2 = Student.builder()
                .id(2L)
                .ime("Nikola")
                .prezime("Vujcic")
                .prijave(new HashSet<>(List.of(p1, p3)))
                .build();

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);
        when(studentRepository.findAll()).thenReturn(List.of(s1, s2));

        Predmet prijava = prijavaService.getPrijavaSaId(prijavaId);

        assertTrue(s1.getPrijave().contains(prijava) || s2.getPrijave().contains(prijava));
        assertEquals(prijavaId, prijava.getId());
    }

    @Test
    void testGetPrijavaSaNepostojecimIdAdmin() {
        Predmet p1 = new Predmet(1L, "SPA", 6);
        Predmet p2 = new Predmet(2L, "UIS", 5);
        Predmet p3 = new Predmet(3L, "Ekonomija", 5);

        long prijavaId = 4L;

        Student s1 = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .prijave(new HashSet<>(List.of(p1, p2)))
                .build();
        Student s2 = Student.builder()
                .id(2L)
                .ime("Nikola")
                .prezime("Vujcic")
                .prijave(new HashSet<>(List.of(p1, p3)))
                .build();

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);
        when(studentRepository.findAll()).thenReturn(List.of(s1, s2));

        assertThrows(EntityNotFoundException.class, () -> prijavaService.getPrijavaSaId(prijavaId));
    }

    @Test
    void testGetPrijavaSaIdUser() {
        Predmet p1 = new Predmet(1L, "SPA", 6);
        Predmet p2 = new Predmet(2L, "UIS", 5);

        long prijavaId = 1L;

        Student s1 = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .prijave(new HashSet<>(List.of(p1, p2)))
                .build();

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(false);
        when(studentService.getTrenutniStudent()).thenReturn(s1);

        Predmet prijava = prijavaService.getPrijavaSaId(prijavaId);

        assertTrue(s1.getPrijave().contains(prijava));
        assertEquals(prijavaId, prijava.getId());
    }

    @Test
    void testGetPrijavaSaNepostojecimIdUser() {
        Predmet p1 = new Predmet(1L, "SPA", 6);
        Predmet p2 = new Predmet(2L, "UIS", 5);

        long prijavaId = 4L;

        Student s1 = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .prijave(new HashSet<>(List.of(p1, p2)))
                .build();

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(false);
        when(studentService.getTrenutniStudent()).thenReturn(s1);

        assertThrows(EntityNotFoundException.class, () -> prijavaService.getPrijavaSaId(prijavaId));
    }

    @Test
    void testObrisiPrijavuSaIdAdmin() {
        long prijavaId = 2L;

        Predmet p1 = new Predmet(1L, "SPA", 6);
        Predmet p2 = new Predmet(2L, "UIS", 5);
        Predmet p3 = new Predmet(3L, "Ekonomija", 5);

        Student s1 = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .prijave(new HashSet<>(List.of(p1, p2)))
                .build();
        Student s2 = Student.builder()
                .id(2L)
                .ime("Nikola")
                .prezime("Vujcic")
                .prijave(new HashSet<>(List.of(p1, p3)))
                .build();

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);
        when(studentRepository.findAll()).thenReturn(List.of(s1, s2));
        when(predmetRepository.findById(prijavaId)).thenReturn(Optional.of(p2));

        assertTrue(prijavaService.obrisiPrijavuSaId(prijavaId));

        assertFalse(s1.getPrijave().contains(p2));
        assertTrue(s1.getPrijave().contains(p1));
        assertTrue(s2.getPrijave().contains(p3));
        assertTrue(s2.getPrijave().contains(p1));
    }

    @Test
    void testObrisiPrijavuSaNepostojecimIdAdmin() {
        long prijavaId = 4L;

        Predmet p1 = new Predmet(1L, "SPA", 6);
        Predmet p2 = new Predmet(2L, "UIS", 5);
        Predmet p3 = new Predmet(3L, "Ekonomija", 5);

        Student s1 = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .prijave(new HashSet<>(List.of(p1, p2)))
                .build();
        Student s2 = Student.builder()
                .id(2L)
                .ime("Nikola")
                .prezime("Vujcic")
                .prijave(new HashSet<>(List.of(p1, p3)))
                .build();

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);
        when(studentRepository.findAll()).thenReturn(List.of(s1, s2));

        assertFalse(prijavaService.obrisiPrijavuSaId(prijavaId));
    }

    @Test
    void testObrisiPrijavuSaIdUser() {
        long prijavaId = 2L;

        Predmet p1 = new Predmet(1L, "SPA", 6);
        Predmet p2 = new Predmet(2L, "UIS", 5);

        Student s = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .prijave(new HashSet<>(List.of(p1, p2)))
                .build();

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(false);
        when(studentService.getTrenutniStudent()).thenReturn(s);
        when(predmetRepository.findById(prijavaId)).thenReturn(Optional.of(p2));

        assertTrue(prijavaService.obrisiPrijavuSaId(prijavaId));

        assertFalse(s.getPrijave().contains(p2));
        assertTrue(s.getPrijave().contains(p1));
    }

    @Test
    void testObrisiPrijavuSaNepostojecimIdUser() {
        long prijavaId = 4L;

        Predmet p1 = new Predmet(1L, "SPA", 6);
        Predmet p2 = new Predmet(2L, "UIS", 5);

        Student s = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .prijave(new HashSet<>(List.of(p1, p2)))
                .build();

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(false);
        when(studentService.getTrenutniStudent()).thenReturn(s);
        when(predmetRepository.findById(prijavaId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> prijavaService.obrisiPrijavuSaId(prijavaId));
    }

    @Test
    void testGetPrijaveZaAdmina() {
        Predmet p1 = new Predmet(1L, "SPA", 6);
        Predmet p2 = new Predmet(2L, "UIS", 5);
        Predmet p3 = new Predmet(3L, "Ekonomija", 5);

        Student s0 = Student.builder()
                .id(0L)
                .ime("Admin")
                .prezime("Admin")
                .build();
        Student s1 = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .prijave(new HashSet<>(List.of(p1, p2)))
                .build();
        Student s2 = Student.builder()
                .id(2L)
                .ime("Nikola")
                .prezime("Vujcic")
                .prijave(new HashSet<>(List.of(p1, p3)))
                .build();

        when(studentRepository.findAll()).thenReturn(List.of(s0, s1, s2));
        when(studentRepository.findById(0L)).thenReturn(Optional.of(s0));

        Map<Student, Set<Predmet>> prijave = prijavaService.getPrijaveZaAdmina();

        assertEquals(2, prijave.size());
        assertTrue(prijave.containsKey(s1));
        assertTrue(prijave.containsKey(s2));
        assertTrue(prijave.get(s1).contains(p1));
        assertTrue(prijave.get(s1).contains(p2));
        assertTrue(prijave.get(s2).contains(p3));
        assertTrue(prijave.get(s2).contains(p1));
    }
}