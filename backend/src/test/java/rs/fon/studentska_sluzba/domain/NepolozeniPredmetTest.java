package rs.fon.studentska_sluzba.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class NepolozeniPredmetTest {

    private NepolozeniPredmet nepolozeniPredmet;

    @BeforeEach
    void setUp() {
        nepolozeniPredmet = new NepolozeniPredmet();
    }

    @AfterEach
    void tearDown() {
        nepolozeniPredmet = null;
    }

    @Test
    void setId() {
        nepolozeniPredmet.setId(1L);
        assertEquals(1L, nepolozeniPredmet.getId());
    }

    @Test
    void setTrenutnoSlusa() {
        nepolozeniPredmet.setTrenutnoSlusa(false);
        assertEquals(false, nepolozeniPredmet.getTrenutnoSlusa());
    }

    @Test
    void setStudent() {
        nepolozeniPredmet.setStudent(Student.builder().id(1L).ime("Veljko").build());
        assertEquals("Veljko", nepolozeniPredmet.getStudent().getIme());
        assertEquals(1, nepolozeniPredmet.getStudent().getId());
    }

    @Test
    void setPredmet() {
        nepolozeniPredmet.setPredmet(Predmet.builder().id(1L).ESPB(6).build());
        assertEquals(6, nepolozeniPredmet.getPredmet().getESPB());
        assertEquals(1, nepolozeniPredmet.getPredmet().getId());
    }

    @Test
    void testToString() {
        nepolozeniPredmet.setId(1L);
        nepolozeniPredmet.setStudent(Student.builder().id(1L).ime("Veljko").build());
        assertTrue(nepolozeniPredmet.toString().contains("Veljko"));
        assertTrue(nepolozeniPredmet.toString().contains("1"));
    }

    @ParameterizedTest
    @CsvSource({
            "1,true,1,true,true",
            "1,true,2,true,false",
            "1,true,1,false,false"
    })
    void testEquals(
            Long id1, Boolean trenutnoSlusa1,
            Long id2, Boolean trenutnoSlusa2,
            Boolean areEqual) {
        nepolozeniPredmet = NepolozeniPredmet.builder().id(id1).trenutnoSlusa(trenutnoSlusa1).build();
        NepolozeniPredmet nepolozeniPredmet2 = NepolozeniPredmet.builder().id(id2).trenutnoSlusa(trenutnoSlusa2).build();
        assertEquals(nepolozeniPredmet.equals(nepolozeniPredmet2), areEqual);


    }
}