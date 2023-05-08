package rs.fon.studentska_sluzba.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import rs.fon.studentska_sluzba.domain.Polaganje;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.exception.EntityNotFoundException;
import rs.fon.studentska_sluzba.logging.Logger;
import rs.fon.studentska_sluzba.repository.PolaganjeRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PolaganjeServiceTest {

    @Mock
    private PolaganjeRepository polaganjeRepository;

    @Mock
    private StudentService studentService;

    @Mock
    private Logger logger;

    @InjectMocks
    private PolaganjeService polaganjeService;

    @Test
    void testGetAllAdmin() {
        Polaganje p1 = new Polaganje();
        Polaganje p2 = new Polaganje();
        p1.setId(1L);
        p2.setId(2L);
        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);
        when(polaganjeRepository.findAll()).thenReturn(List.of(p1, p2));

        List<Polaganje> polaganja = polaganjeService.getSvaPolaganja();

        assertNotNull(polaganja);
        assertEquals(2, polaganja.size());
        assertEquals(p1, polaganja.get(0));
        assertEquals(p2, polaganja.get(1));
        verify(polaganjeRepository, times(1)).findAll();
        verify(studentService, times(1)).jelTrenutniKorisnikAdmin();
        verifyNoMoreInteractions(polaganjeRepository, studentService);

    }

    @Test
    void testGetAllUser() {
        Student s = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .build();

        Polaganje p1 = new Polaganje();
        Polaganje p2 = new Polaganje();
        p1.setId(1L);
        p1.setStudent(s);
        p2.setId(2L);
        p2.setStudent(s);

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(false);
        when(studentService.getTrenutniStudent()).thenReturn(s);
        when(polaganjeRepository.findByStudent(s)).thenReturn(List.of(p1,p2));

        List<Polaganje> polaganja = polaganjeService.getSvaPolaganja();

        assertNotNull(polaganja);
        assertEquals(2, polaganja.size());
        assertEquals(p1, polaganja.get(0));
        assertEquals(p2, polaganja.get(1));
        verify(studentService, times(1)).jelTrenutniKorisnikAdmin();
        verifyNoMoreInteractions(polaganjeRepository, studentService);

    }

    @Test
    void testGetWithIdAdmin() {
        Polaganje p1 = new Polaganje();
        p1.setId(1L);

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);
        when(polaganjeRepository.findById(1L)).thenReturn(Optional.of(p1));

        Polaganje polaganje = polaganjeService.getPolaganjeSaId(1L);

        assertNotNull(polaganje);
        assertEquals(p1, polaganje);
        verify(polaganjeRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(polaganjeRepository);
    }
    @Test
    void testGetWithInvalidIdAdmin() {
        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);
        when(polaganjeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> polaganjeService.getPolaganjeSaId(1L));

        verify(polaganjeRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(polaganjeRepository);
    }
    @Test
    void testGetWithIdUser() {
        Student s = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .build();

        Polaganje p1 = new Polaganje();
        p1.setId(1L);

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(false);
        when(studentService.getTrenutniStudent()).thenReturn(s);
        when(polaganjeRepository.findByStudentAndId(s, 1L)).thenReturn(Optional.of(p1));

        Polaganje polaganje = polaganjeService.getPolaganjeSaId(1L);

        assertNotNull(polaganje);
        assertEquals(p1, polaganje);
        verify(polaganjeRepository, times(1)).findByStudentAndId(s,1L);
        verifyNoMoreInteractions(polaganjeRepository);
    }
    @Test
    void testGetWithInvalidIdUser() {
        Student s = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .build();
        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(false);
        when(studentService.getTrenutniStudent()).thenReturn(s);
        when(polaganjeRepository.findByStudentAndId(s,1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> polaganjeService.getPolaganjeSaId(1L));

        verify(polaganjeRepository, times(1)).findByStudentAndId(s,1L);
        verifyNoMoreInteractions(polaganjeRepository);
    }

    @Test
    void testSave() {
        Student s = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .build();
        Polaganje p1 = new Polaganje();
        p1.setId(1L);

        Polaganje p2 = new Polaganje();
        p2.setId(1L);
        p2.setStudent(s);

        when(studentService.getTrenutniStudent()).thenReturn(s);
        when(polaganjeRepository.save(p1)).thenReturn(p2);

        Polaganje polaganje = polaganjeService.ubaciPolaganje(p1);

        assertNotNull(polaganje);
        assertEquals(p2, polaganje);
        verify(polaganjeRepository, times(1)).save(p1);
        verifyNoMoreInteractions(polaganjeRepository);
    }

    @Test
    void testDeleteWithId() {
        long polaganjeId = 1L;

        Polaganje p1 = new Polaganje();
        p1.setId(polaganjeId);

        when(polaganjeRepository.existsById(polaganjeId)).thenReturn(true);

        assertTrue(polaganjeService.obrisiPolaganjeSaId(polaganjeId));

        verify(polaganjeRepository).deleteById(polaganjeId);
        verifyNoMoreInteractions(polaganjeRepository);
    }
    @Test
    void testDeleteWithIdInvalid() {
        long polaganjeId = 1L;

        Polaganje p1 = new Polaganje();
        p1.setId(polaganjeId);

        when(polaganjeRepository.existsById(polaganjeId)).thenReturn(false);

        assertFalse(polaganjeService.obrisiPolaganjeSaId(polaganjeId));
    }


    @ParameterizedTest
    @CsvSource(value = {
            "true", "false"
    })
    void testGetAllNeuspesnaIUspesnaAdmin(boolean polozio) {
        Polaganje p1 = new Polaganje();
        Polaganje p2 = new Polaganje();
        p1.setId(1L);
        p1.setPolozio(polozio);
        p2.setId(2L);
        p2.setPolozio(polozio);
        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);
        when(polaganjeRepository.findByPolozio(polozio)).thenReturn(List.of(p1, p2));
        List<Polaganje> polaganja;

        if (polozio) {
            polaganja = polaganjeService.getSvaUspesnaPolaganja();
        } else {
            polaganja = polaganjeService.getSvaNeuspesnaPolaganja();
        }
        assertNotNull(polaganja);
        assertEquals(2, polaganja.size());
        assertEquals(p1, polaganja.get(0));
        assertEquals(p2, polaganja.get(1));
        verify(polaganjeRepository, times(1)).findByPolozio(polozio);
    }
    @ParameterizedTest
    @CsvSource(value = {
            "true", "false"
    })
    void testGetAllNeuspesnaIUspesnaUser(boolean polozio) {
        Student s = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .build();
        Polaganje p1 = new Polaganje();
        Polaganje p2 = new Polaganje();
        p1.setId(1L);
        p1.setStudent(s);
        p1.setPolozio(polozio);
        p2.setId(2L);
        p2.setStudent(s);
        p2.setPolozio(polozio);
        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(false);
        when(studentService.getTrenutniStudent()).thenReturn(s);
        when(polaganjeRepository.findByStudentAndAndPolozio(s, polozio)).thenReturn(List.of(p1, p2));
        List<Polaganje> polaganja;

        if (polozio) {
            polaganja = polaganjeService.getSvaUspesnaPolaganja();
        } else {
            polaganja = polaganjeService.getSvaNeuspesnaPolaganja();
        }
        assertNotNull(polaganja);
        assertEquals(2, polaganja.size());
        assertEquals(p1, polaganja.get(0));
        assertEquals(p2, polaganja.get(1));
        verify(polaganjeRepository, times(1)).findByStudentAndAndPolozio(s,polozio);
    }


    @Test
    void testGetSvaNeuspesnaPolaganjaPageableAdmin() {
        Polaganje p1 = new Polaganje();
        Polaganje p2 = new Polaganje();
        p1.setId(1L);
        p1.setPolozio(true);
        p2.setId(2L);
        p2.setPolozio(true);

        int pageNumber = 0;
        int pageSize = 3;

        Pageable pageable = PageRequest.of(pageNumber,pageSize);

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);

        when(polaganjeRepository.findByPolozio(false, pageable)).thenReturn(new PageImpl<>(List.of(p1, p2)));

        Page<Polaganje> polaganja = polaganjeService.getSvaNeuspesnaPolaganja(pageNumber,pageSize);

        assertNotNull(polaganja);
        assertEquals(2, polaganja.getTotalElements());
        assertEquals(p1, polaganja.getContent().get(0));
        assertEquals(p2, polaganja.getContent().get(1));
        verify(polaganjeRepository, times(1)).findByPolozio(false, pageable);

    }
    @Test
    void testGetSvaNeuspesnaPolaganjaPageableUser() {
        Student s = Student.builder()
              .id(1L)
              .ime("Veljko")
              .prezime("Blagojevic")
              .build();
        Polaganje p1 = new Polaganje();
        Polaganje p2 = new Polaganje();
        p1.setId(1L);
        p1.setStudent(s);
        p1.setPolozio(true);
        p2.setId(2L);
        p2.setStudent(s);
        p2.setPolozio(true);

        int pageNumber = 0;
        int pageSize = 3;

        Pageable pageable = PageRequest.of(pageNumber,pageSize);

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(false);
        when(studentService.getTrenutniStudent()).thenReturn(s);
        when(polaganjeRepository.findByStudentAndAndPolozio(s, false, pageable)).thenReturn(new PageImpl<>(List.of(p1, p2)));

        Page<Polaganje> polaganja = polaganjeService.getSvaNeuspesnaPolaganja(pageNumber,pageSize);
        assertNotNull(polaganja);
        assertEquals(2, polaganja.getTotalElements());
        assertEquals(p1, polaganja.getContent().get(0));
        assertEquals(p2, polaganja.getContent().get(1));
        verify(polaganjeRepository, times(1)).findByStudentAndAndPolozio(s,false, pageable);
        verifyNoMoreInteractions(polaganjeRepository);
        verifyNoMoreInteractions(studentService);

    }





}