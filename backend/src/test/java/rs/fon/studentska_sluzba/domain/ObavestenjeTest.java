package rs.fon.studentska_sluzba.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ObavestenjeTest {

    private Obavestenje obavestenje;

    @BeforeEach
    void setUp() {
        obavestenje = new Obavestenje();
    }

    @AfterEach
    void tearDown() {
        obavestenje = null;
    }

    @Test
    void setId() {
        obavestenje.setId(1L);
        assertEquals(1L, obavestenje.getId());
    }

    @Test
    void setDatum() {
        obavestenje.setDatum(LocalDate.of(2022,2,2));
        assertEquals(LocalDate.of(2022,2,2), obavestenje.getDatum());
    }

    @Test
    void setSadrzaj() {
        obavestenje.setSadrzaj("Sadrzaj");
        assertEquals("Sadrzaj", obavestenje.getSadrzaj());
    }

    @Test
    void testToString() {
        obavestenje.setId(1L);
        obavestenje.setDatum(LocalDate.of(2022,2,2));
        obavestenje.setSadrzaj("Sadrzaj");
        assertTrue(obavestenje.toString().contains("Sadrzaj"));
        assertTrue(obavestenje.toString().contains("1"));
        assertTrue(obavestenje.toString().contains("2022"));
    }

    @ParameterizedTest
    @CsvSource({
            "1,2022-02-02,Sadrzaj,1,2022-02-02,Sadrzaj,true",
            "1,2022-02-02,Sadrzaj,99,2022-02-02,Sadrzaj,false",
            "1,2022-02-02,Sadrzaj,1,2011-09-09,Sadrzaj,false",
            "1,2022-02-02,Sadrzaj,1,2022-02-02,xxx,false"
    })
    void testEquals(
            Long id1, LocalDate datum1, String sadrzaj1,
            Long id2, LocalDate datum2, String sadrzaj2,
            Boolean areEqual) {
        obavestenje = new Obavestenje(id1, datum1, sadrzaj1);
        Obavestenje obavestenje2 = new Obavestenje(id2, datum2, sadrzaj2);
        assertEquals(obavestenje.equals(obavestenje2), areEqual);
    }
}