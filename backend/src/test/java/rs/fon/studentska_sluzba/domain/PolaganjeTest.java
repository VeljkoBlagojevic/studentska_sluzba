package rs.fon.studentska_sluzba.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PolaganjeTest {

    private Polaganje polaganje;

    @BeforeEach
    void setUp() {
        polaganje = new Polaganje();
    }

    @AfterEach
    void tearDown() {
        polaganje = null;
    }

    @Test
    void setId() {
        polaganje.setId(1L);
        assertEquals(1L, polaganje.getId());
    }

    @Test
    void setDatum() {
        polaganje.setDatum(LocalDate.of(2022, 2, 2));
        assertEquals(LocalDate.of(2022, 2, 2), polaganje.getDatum());
    }

    @Test
    void setPolozio() {
        polaganje.setPolozio(true);
        assertEquals(true, polaganje.getPolozio());
    }

    @Test
    void setOcena() {
        polaganje.setOcena(6);
        assertEquals(6, polaganje.getOcena());
    }

    @Test
    void setStudent() {
        polaganje.setStudent(Student.builder().id(1L).ime("Veljko").build());
        assertEquals(1, polaganje.getStudent().getId());
        assertEquals("Veljko", polaganje.getStudent().getIme());
    }

    @Test
    void setPredmet() {
        polaganje.setPredmet(new Predmet(1L, "SPA", 6));
        assertEquals(6, polaganje.getPredmet().getESPB());
        assertEquals(1, polaganje.getPredmet().getId());
        assertEquals("SPA", polaganje.getPredmet().getNaziv());
    }

    @Test
    void testToString() {
        polaganje.setId(1L);
        polaganje.setDatum(LocalDate.of(2022, 2, 2));
        polaganje.setPolozio(true);
        polaganje.setOcena(6);
        polaganje.setStudent(Student.builder().id(1L).ime("Veljko").build());
        polaganje.setPredmet(new Predmet(1L, "SPA", 6));
        assertTrue(polaganje.toString().contains("2022"));
        assertTrue(polaganje.toString().contains("SPA"));
        assertTrue(polaganje.toString().contains("Veljko"));
        assertTrue(polaganje.toString().contains("6"));
        assertTrue(polaganje.toString().contains("1"));
    }

    @ParameterizedTest
    @CsvSource({
            "1,true,6,1,true,6,true",
            "1,true,6,2,true,6,false",
            "1,true,6,1,false,6,false",
            "1,true,6,1,true,7,false",
    })
    void testEquals(Long id1, boolean polozio1, int ocena1,
                    Long id2, boolean polozio2, int ocena2,
                    boolean areEqual) {
        polaganje = Polaganje.builder().id(id1).polozio(polozio1).ocena(ocena1).build();
        Polaganje polaganje2 = Polaganje.builder().id(id2).polozio(polozio2).ocena(ocena2).build();
        assertEquals(polaganje.equals(polaganje2), areEqual);
    }
}