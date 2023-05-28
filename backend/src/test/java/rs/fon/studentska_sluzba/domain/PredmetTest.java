package rs.fon.studentska_sluzba.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PredmetTest {

    private Predmet predmet;

    @BeforeEach
    void setUp() {
        predmet = new Predmet();
    }

    @AfterEach
    void tearDown() {
        predmet = null;
    }

    @Test
    void setId() {
        predmet.setId(1L);
        assertEquals(1L, predmet.getId());
    }

    @Test
    void setNaziv() {
        predmet.setNaziv("Naziv");
        assertEquals("Naziv", predmet.getNaziv());
    }

    @Test
    void setESPB() {
        predmet.setESPB(6);
        assertEquals(6, predmet.getESPB());
    }

    @Test
    void testToString() {
        predmet = new Predmet(1L, "NP", 4);
        assertTrue(predmet.toString().contains("NP"));
        assertTrue(predmet.toString().contains("4"));
        assertTrue(predmet.toString().contains("1"));
    }

    @ParameterizedTest
    @CsvSource({
            "1,Napredno Programiranje,4,1,Napredno Programiranje,4,true",
            "1,Napredno Programiranje,4,2,Napredno Programiranje,4,false",
            "1,Napredno Programiranje,4,1,Inteligentni Sistemi,4,false",
            "1,Napredno Programiranje,4,1,Napredno Programiranje,6,false",
    })
    void testEquals(long id1, String naziv1, int espb1,
                    long id2, String naziv2, int espb2,
                    boolean areEqual) {
        predmet = new Predmet(id1, naziv1, espb1);
        Predmet predmet2 = new Predmet(id2, naziv2, espb2);
        assertEquals(predmet.equals(predmet2), areEqual);

    }
}