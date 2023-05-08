package rs.fon.studentska_sluzba.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.fon.studentska_sluzba.domain.Molba;
import rs.fon.studentska_sluzba.domain.Role;
import rs.fon.studentska_sluzba.domain.StatusMolbe;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.exception.EntityNotFoundException;
import rs.fon.studentska_sluzba.logging.Logger;
import rs.fon.studentska_sluzba.repository.MolbaRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MolbaServiceTest {

    @Mock
    private MolbaRepository molbaRepository;

    @Mock
    private StudentService studentService;

    @Mock
    private Logger logger;

    @InjectMocks
    private MolbaService molbaService;

    @Test
    void testFindAll() {
        // given
        Student student = new Student();
        Molba molba = new Molba();
        molba.setStudent(student);
        List<Molba> expectedMolbe = Collections.singletonList(molba);

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(false);
        when(studentService.getTrenutniStudent()).thenReturn(student);
        when(molbaRepository.findByStudent(student)).thenReturn(expectedMolbe);

        // when
        List<Molba> actualMolbe = molbaService.findAll();

        // then
        assertThat(actualMolbe).isEqualTo(expectedMolbe);
    }

    @Test
    void testGetMolbaSaIdAdmin() {
        // Arrange
        Long molbaId = 1L;
        Molba molba = Molba.builder().id(molbaId).build();
        when(molbaRepository.findById(molbaId)).thenReturn(Optional.of(molba));
        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);
        // Act
        Molba result = molbaService.getMolbaSaId(molbaId);

        // Assert
        assertEquals(molba, result);
    }

    @Test
    void testGetMolbaSaIdKorisnik() {
        // Arrange
        Long molbaId = 1L;
        Molba molba = Molba.builder().id(molbaId).build();
        Student student = Student.builder().id(1L).ime("Veljko").prezime("Blagojevic").build();
        when(molbaRepository.findByStudentAndId(student, molbaId)).thenReturn(Optional.of(molba));
        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(false);
        when(studentService.getTrenutniStudent()).thenReturn(student);
        // Act
        Molba result = molbaService.getMolbaSaId(molbaId);

        // Assert
        assertEquals(molba, result);
    }

    @Test
    void testGetMolbaSaIdNijePronadjenaAdmin() {
        // Arrange
        Long molbaId = 1L;
        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);


        // Assert
        assertThrows(EntityNotFoundException.class, () -> molbaService.getMolbaSaId(molbaId));
    }

    @Test
    void testGetMolbaSaIdNijePronadjenaKorisnik() {
        // Arrange
        Long molbaId = 1L;

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(false);

        // Assert
        assertThrows(EntityNotFoundException.class, () -> molbaService.getMolbaSaId(molbaId));
    }

    @Test
    void testFindAllAsAdmin() {
        // Given
        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);
        List<Molba> expectedMolbe = Collections.singletonList(new Molba());
        when(molbaRepository.findAll()).thenReturn(expectedMolbe);

        // When
        List<Molba> molbe = molbaService.findAll();

        // Then
        assertThat(molbe).isEqualTo(expectedMolbe);
    }

    @Test
    void testFindAllAsStudent() {
        // Given
        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(false);
        Student trenutniStudent = new Student();
        when(studentService.getTrenutniStudent()).thenReturn(trenutniStudent);
        List<Molba> expectedMolbe = Collections.singletonList(new Molba());
        when(molbaRepository.findByStudent(trenutniStudent)).thenReturn(expectedMolbe);

        // When
        List<Molba> molbe = molbaService.findAll();

        // Then
        assertThat(molbe).isEqualTo(expectedMolbe);
    }

    @Test
    void testGetMolbaSaIdAsAdmin() {
        // Given
        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);
        Molba expectedMolba = new Molba();
        when(molbaRepository.findById(any(Long.class))).thenReturn(Optional.of(expectedMolba));

        // When
        Molba molba = molbaService.getMolbaSaId(1L);

        // Then
        assertThat(molba).isEqualTo(expectedMolba);
    }

    @Test
    void testDodajMolbuUObradi() {
        Student student = new Student();
        student.setId(1L);
        student.setIme("John");
        student.setPrezime("Doe");

        Molba molba = new Molba();
        molba.setId(1L);
        molba.setDatumPitanja(LocalDate.now());
        molba.setStatusMolbe(StatusMolbe.U_OBRADI);
        molba.setStudent(student);

        when(studentService.getTrenutniStudent()).thenReturn(student);
        when(molbaRepository.save(molba)).thenReturn(molba);

        Molba savedMolba = molbaService.dodajMolbuUObradi(molba);

        verify(molbaRepository).save(molba);
    }

    @Test
    void testObrisiMolbuSaId() {
        Long molbaId = 1L;

        Molba molba = new Molba();
        molba.setId(molbaId);
        molba.setDatumPitanja(LocalDate.now());
        molba.setStudent(new Student());

        when(molbaRepository.findById(molbaId)).thenReturn(Optional.of(molba));

        molbaService.obrisiMolbuSaId(molbaId);

        verify(molbaRepository).deleteById(molbaId);
    }

    @Test
    void testObrisiMolbuSaIdWhenMethodReturnsFalse() {
        Long molbaId = 1L;

        Molba molba = new Molba();
        molba.setId(molbaId);
        molba.setDatumPitanja(LocalDate.now());
        molba.setStudent(new Student());

        when(molbaRepository.findById(molbaId)).thenReturn(Optional.empty());

        boolean result = molbaService.obrisiMolbuSaId(molbaId);

        assertFalse(result);
    }

    @Test
    void testFindAllUObradiKorisnik() {
        // given
        Student student = new Student();
        Molba molba1 = new Molba();
        molba1.setStatusMolbe(StatusMolbe.U_OBRADI);
        molba1.setStudent(student);
        Molba molba2 = new Molba();
        molba2.setStatusMolbe(StatusMolbe.RAZRESENA);
        molba2.setStudent(student);
        List<Molba> expectedMolbe = List.of(molba1, molba2);

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(false);
        when(studentService.getTrenutniStudent()).thenReturn(student);
        when(molbaRepository.findByStudent(student)).thenReturn(expectedMolbe);

        // when
        List<Molba> actualMolbe = molbaService.findAllUObradi();

        // then
        assertThat(actualMolbe).isEqualTo(Collections.singletonList(molba1));
    }

    @Test
    void testFindAllUObradiAdmin() {
        // given
        Student student = new Student();
        Molba molba1 = new Molba();
        molba1.setStatusMolbe(StatusMolbe.U_OBRADI);
        molba1.setStudent(student);
        Molba molba2 = new Molba();
        molba2.setStatusMolbe(StatusMolbe.RAZRESENA);
        molba2.setStudent(student);
        List<Molba> expectedMolbe = List.of(molba1, molba2);

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);
        when(molbaRepository.findAll()).thenReturn(expectedMolbe);

        // when
        List<Molba> actualMolbe = molbaService.findAllUObradi();

        // then
        assertThat(actualMolbe).isEqualTo(Collections.singletonList(molba1));
    }
    @Test
    void testFindAllRazreseneKorisnik() {
        // given
        Student student = new Student();
        Molba molba1 = new Molba();
        molba1.setStatusMolbe(StatusMolbe.U_OBRADI);
        molba1.setStudent(student);
        Molba molba2 = new Molba();
        molba2.setStatusMolbe(StatusMolbe.RAZRESENA);
        molba2.setStudent(student);
        List<Molba> expectedMolbe = List.of(molba1, molba2);

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(false);
        when(studentService.getTrenutniStudent()).thenReturn(student);
        when(molbaRepository.findByStudent(student)).thenReturn(expectedMolbe);

        // when
        List<Molba> actualMolbe = molbaService.findAllRazresene();

        // then
        assertThat(actualMolbe).isEqualTo(Collections.singletonList(molba2));
    }

    @Test
    void testFindAllRazreseneAdmin() {
        // given
        Student student = new Student();
        Molba molba1 = new Molba();
        molba1.setStatusMolbe(StatusMolbe.U_OBRADI);
        molba1.setStudent(student);
        Molba molba2 = new Molba();
        molba2.setStatusMolbe(StatusMolbe.RAZRESENA);
        molba2.setStudent(student);
        List<Molba> expectedMolbe = List.of(molba1, molba2);

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);
        when(molbaRepository.findAll()).thenReturn(expectedMolbe);

        // when
        List<Molba> actualMolbe = molbaService.findAllRazresene();

        // then
        assertThat(actualMolbe).isEqualTo(Collections.singletonList(molba2));
    }


    @Test
    void testRazresiMolbuWhenMolbaPostojiAdmin() {
        Long molbaId = 1L;

        Molba molba = new Molba();
        molba.setId(molbaId);
        molba.setDatumPitanja(LocalDate.now());
        molba.setStudent(new Student());

        when(molbaRepository.save(molba)).thenReturn(molba);
        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);

        Molba actual = molbaService.razresiMolbu(molba);

        Molba expected = Molba.builder()
                .id(molba.getId())
                .datumPitanja(molba.getDatumPitanja())
                .datumOdgovora(LocalDate.now())
                .statusMolbe(StatusMolbe.RAZRESENA)
                .student(molba.getStudent())
                .build();

        assertEquals(expected, actual);
    }

    @Test
    void testRazresiMolbuWhenMolbaPostojiKorisnik() {
        Long molbaId = 1L;

        Molba molba = new Molba();
        molba.setId(molbaId);
        molba.setDatumPitanja(LocalDate.now());
        molba.setStudent(Student.builder().role(Role.ADMIN).build());

        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(false);

        assertThrows(RuntimeException.class, () -> molbaService.razresiMolbu(molba));
    }

    @Test
    void testRazresiMolbuSaIdIOdgovor() {
        Long molbaId = 1L;
        String odgovor = "Odgovor";

        Molba molba = new Molba();
        molba.setId(molbaId);
        molba.setDatumPitanja(LocalDate.now());
        molba.setStudent(new Student());

        when(molbaRepository.findById(molbaId)).thenReturn(Optional.of(molba));
        when(molbaRepository.save(molba)).thenReturn(molba);
        when(studentService.jelTrenutniKorisnikAdmin()).thenReturn(true);

        Molba actual = molbaService.razresiMolbu(molbaId, odgovor);

        Molba expected = Molba.builder()
                .id(molba.getId())
                .datumPitanja(molba.getDatumPitanja())
                .datumOdgovora(LocalDate.now())
                .statusMolbe(StatusMolbe.RAZRESENA)
                .student(molba.getStudent())
                .odgovor(odgovor)
                .build();

        assertEquals(expected, actual);
    }

    @Test
    void testRazresiMolbuSaIdIOdgovorNijeAdmin() {
        assertThrows(RuntimeException.class, () -> molbaService.razresiMolbu(anyLong(), anyString()), "Niste ulogovani kao administrator da bi ste razresili molbu");
    }




}
