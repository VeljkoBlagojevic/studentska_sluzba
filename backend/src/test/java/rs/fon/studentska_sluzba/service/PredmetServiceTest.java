package rs.fon.studentska_sluzba.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import rs.fon.studentska_sluzba.domain.NepolozeniPredmet;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.logging.Logger;
import rs.fon.studentska_sluzba.repository.NepolozeniPredmetRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PredmetServiceTest {

    @Mock
    private NepolozeniPredmetRepository nepolozeniPredmetRepository;

    @Mock
    private StudentService studentService;

    @Mock
    private Logger logger;

    @InjectMocks
    private PredmetService predmetService;

    @Test
    void testGetTrenutnoSlusaniAdmin() {
        NepolozeniPredmet np1 = new NepolozeniPredmet();
        NepolozeniPredmet np2 = new NepolozeniPredmet();

        np1.setTrenutnoSlusa(true);
        np2.setTrenutnoSlusa(true);

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);
        when(nepolozeniPredmetRepository.findByTrenutnoSlusa(true)).thenReturn(List.of(np1, np2));

        List<Predmet> predmeti = predmetService.getTrenutnoSlusani();

        assertEquals(2, predmeti.size());
        verify(nepolozeniPredmetRepository, times(1)).findByTrenutnoSlusa(true);
        verify(studentService, times(1)).jelTrenutniKorisnikAdmin();
        verifyNoMoreInteractions(nepolozeniPredmetRepository, studentService);
    }

    @Test
    void testGetTrenutnoSlusaniUser() {
        Student s = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .build();

        NepolozeniPredmet np1 = new NepolozeniPredmet();
        NepolozeniPredmet np2 = new NepolozeniPredmet();

        np1.setTrenutnoSlusa(true);
        np1.setStudent(s);
        np2.setTrenutnoSlusa(true);
        np2.setStudent(s);

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(false);
        when(studentService.getTrenutniStudent()).thenReturn(s);
        when(nepolozeniPredmetRepository.findByStudentAndTrenutnoSlusa(s, true)).thenReturn(List.of(np1, np2));

        List<Predmet> predmeti = predmetService.getTrenutnoSlusani();

        assertEquals(2, predmeti.size());
        verify(nepolozeniPredmetRepository, times(1)).findByStudentAndTrenutnoSlusa(s, true);
        verify(studentService, times(1)).jelTrenutniKorisnikAdmin();
        verifyNoMoreInteractions(nepolozeniPredmetRepository, studentService);
    }

    @Test
    void testGetSveNepolozenePredmeteAdmin() {
        NepolozeniPredmet np1 = new NepolozeniPredmet();
        NepolozeniPredmet np2 = new NepolozeniPredmet();

        np1.setTrenutnoSlusa(false);
        np2.setTrenutnoSlusa(false);

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);
        when(nepolozeniPredmetRepository.findByTrenutnoSlusa(false)).thenReturn(List.of(np1, np2));

        List<NepolozeniPredmet> predmeti = predmetService.getSveNepolozenePredmete();

        assertEquals(2, predmeti.size());
        verify(nepolozeniPredmetRepository, times(1)).findByTrenutnoSlusa(false);
        verify(studentService, times(1)).jelTrenutniKorisnikAdmin();
        verifyNoMoreInteractions(nepolozeniPredmetRepository, studentService);
    }

    @Test
    void testGetSveNepolozenePredmeteUser() {
        Student s = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .build();

        NepolozeniPredmet np1 = new NepolozeniPredmet();
        NepolozeniPredmet np2 = new NepolozeniPredmet();

        np1.setTrenutnoSlusa(false);
        np1.setStudent(s);
        np2.setTrenutnoSlusa(false);
        np2.setStudent(s);

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(false);
        when(studentService.getTrenutniStudent()).thenReturn(s);
        when(nepolozeniPredmetRepository.findByStudent(s)).thenReturn(List.of(np1, np2));

        List<NepolozeniPredmet> predmeti = predmetService.getSveNepolozenePredmete();

        assertEquals(2, predmeti.size());
        assertTrue(predmeti.stream().allMatch(nepolozeniPredmet -> nepolozeniPredmet.getStudent().equals(s)));
        verify(nepolozeniPredmetRepository, times(1)).findByStudent(s);
        verify(studentService, times(1)).jelTrenutniKorisnikAdmin();
        verifyNoMoreInteractions(nepolozeniPredmetRepository, studentService);
    }

    @Test
    void testDodajZaSlusanje() {
        Student s = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .build();

        NepolozeniPredmet np = new NepolozeniPredmet();
        np.setTrenutnoSlusa(true);
        np.setStudent(s);
        np.setPredmet(new Predmet());

        when(studentService.getTrenutniStudent()).thenReturn(s);
        when(nepolozeniPredmetRepository.save(np)).thenReturn(np);

        NepolozeniPredmet predmet = predmetService.dodajZaSlusanje(np);

        verify(studentService, times(1)).getTrenutniStudent();
        verify(nepolozeniPredmetRepository, times(1)).save(np);
        verifyNoMoreInteractions(studentService, nepolozeniPredmetRepository);
        assertEquals(np, predmet);
        verifyNoMoreInteractions(nepolozeniPredmetRepository);
    }

    @Test
    void testGetTrenutnoSlusaniPageableAdmin() {
        int page = 0;
        int size = 3;
        Pageable pageable = PageRequest.of(page, size);

        Predmet p1 = new Predmet(1L, "SPA", 6);
        Predmet p2 = new Predmet(2L, "UIS", 5);

        NepolozeniPredmet np1 = new NepolozeniPredmet();
        NepolozeniPredmet np2 = new NepolozeniPredmet();

        np1.setTrenutnoSlusa(true);
        np1.setPredmet(p1);
        np2.setTrenutnoSlusa(true);
        np2.setPredmet(p2);

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);
        when(nepolozeniPredmetRepository.findByTrenutnoSlusa(true, pageable)).thenReturn(new PageImpl<>(List.of(np1, np2)));

        Page<Predmet> predmeti = predmetService.getTrenutnoSlusani(page, size);

        assertEquals(2, predmeti.getContent().size());
        assertEquals(p1, predmeti.getContent().get(0));
        assertEquals(p2, predmeti.getContent().get(1));
        verify(nepolozeniPredmetRepository, times(1)).findByTrenutnoSlusa(true, pageable);
        verify(studentService, times(1)).jelTrenutniKorisnikAdmin();
        verifyNoMoreInteractions(nepolozeniPredmetRepository, studentService);
        verifyNoMoreInteractions(nepolozeniPredmetRepository);
    }

    @Test
    void testGetTrenutnoSlusaniPageableUser() {
        int page = 0;
        int size = 3;
        Pageable pageable = PageRequest.of(page, size);

        Student s = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .build();

        Predmet p1 = new Predmet(1L, "SPA", 6);
        Predmet p2 = new Predmet(2L, "UIS", 5);

        NepolozeniPredmet np1 = new NepolozeniPredmet();
        NepolozeniPredmet np2 = new NepolozeniPredmet();

        np1.setTrenutnoSlusa(true);
        np1.setPredmet(p1);
        np2.setTrenutnoSlusa(true);
        np2.setPredmet(p2);

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(false);
        when(studentService.getTrenutniStudent()).thenReturn(s);
        when(nepolozeniPredmetRepository.findByStudentAndTrenutnoSlusa(s,true, pageable)).thenReturn(new PageImpl<>(List.of(np1, np2)));

        Page<Predmet> predmeti = predmetService.getTrenutnoSlusani(page, size);

        assertEquals(2, predmeti.getContent().size());
        assertEquals(p1, predmeti.getContent().get(0));
        assertEquals(p2, predmeti.getContent().get(1));
        verify(nepolozeniPredmetRepository, times(1)).findByStudentAndTrenutnoSlusa(s,true, pageable);
        verify(studentService, times(1)).jelTrenutniKorisnikAdmin();
        verifyNoMoreInteractions(nepolozeniPredmetRepository, studentService);
        verifyNoMoreInteractions(nepolozeniPredmetRepository);

    }


}