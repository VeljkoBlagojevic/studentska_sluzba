package rs.fon.studentska_sluzba.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MolbaTest {

    private Molba molba;

    @BeforeEach
    void setUp() {
        molba = new Molba();
    }

    @AfterEach
    void tearDown() {
        molba = null;
    }

    @Test
    void setId() {
        molba.setId(1L);
        assertEquals(1L, molba.getId());
    }

    @Test
    void setPitanje() {
        molba.setPitanje("Pitanje");
        assertEquals("Pitanje", molba.getPitanje());
    }

    @Test
    void setDatumPitanja() {
        molba.setDatumPitanja(LocalDate.of(2021, 2, 2));
        assertEquals(LocalDate.of(2021, 2, 2), molba.getDatumPitanja());
    }

    @Test
    void setOdgovor() {
        molba.setOdgovor("Odgovor");
        assertEquals("Odgovor", molba.getOdgovor());
    }

    @Test
    void setDatumOdgovora() {
        molba.setDatumOdgovora(LocalDate.of(2015, 4, 4));
        assertEquals(LocalDate.of(2015, 4, 4), molba.getDatumOdgovora());
    }

    @Test
    void setTipMolbe() {
        molba.setTipMolbe(TipMolbe.PROMENA_PODATAKA_O_STUDENTU);
        assertEquals(TipMolbe.PROMENA_PODATAKA_O_STUDENTU, molba.getTipMolbe());
    }

    @Test
    void setStatusMolbe() {
        molba.setStatusMolbe(StatusMolbe.U_OBRADI);
        assertEquals(StatusMolbe.U_OBRADI, molba.getStatusMolbe());
    }

    @Test
    void setStudent() {
        molba.setStudent(Student.builder().id(1L).ime("Veljko").build());
        assertEquals("Veljko", molba.getStudent().getIme());
        assertEquals(1L, molba.getStudent().getId());
    }

    @Test
    void testToString() {
        molba.setId(1L);
        molba.setStudent(Student.builder().ime("Veljko").build());
        molba.setStatusMolbe(StatusMolbe.U_OBRADI);
        assertTrue(molba.toString().contains("U_OBRADI"));
        assertTrue(molba.toString().contains("Veljko"));
        assertTrue(molba.toString().contains("1"));
    }

    @ParameterizedTest
    @CsvSource({
            "1,Pitanje,1,Pitanje,true",
            "1,Pitanje,99,Pitanje,false",
            "1,Pitanje,1,xxx,false",
    })
    void testEquals(Long id1, String pitanje1,
                    Long id2, String pitanje2,
                    Boolean areEqual) {
        molba = Molba.builder().id(id1).pitanje(pitanje1).build();
        Molba molba2 = Molba.builder().id(id2).pitanje(pitanje2).build();
        assertEquals(molba.equals(molba2), areEqual);
    }
}