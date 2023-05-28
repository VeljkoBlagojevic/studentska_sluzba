package rs.fon.studentska_sluzba.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GradTest {

    private Grad grad;

    @BeforeEach
    void setUp() {
        grad = new Grad();
    }

    @AfterEach
    void tearDown() {
        grad = null;
    }

    @Test
    void setId() {
        grad.setId(1L);
        assertEquals(1L, grad.getId());
    }

    @Test
    void setNaziv() {
        grad.setNaziv("Naziv");
        assertEquals("Naziv", grad.getNaziv());
    }

    @Test
    void setZipcode() {
        grad.setZipcode(26230);
        assertEquals(26230, grad.getZipcode());
    }

    @Test
    void testToString() {
        grad = new Grad(1L, "Omoljica", 26230);
        assertTrue(grad.toString().contains("Omoljica"));
        assertTrue(grad.toString().contains("1"));
        assertTrue(grad.toString().contains("26230"));
    }

    @ParameterizedTest
    @CsvSource({
            "1,Pancevo,26230,1,Pancevo,26230,true",
            "1,Pancevo,26230,1,Pancevo,99999,false",
            "1,Pancevo,26230,1,Beograd,26230,false",
            "1,Pancevo,26230,99,Pancevo,26230,false"
    })
    void testEquals(Long id1, String naziv1, Integer zipcode1, Long id2, String naziv2, Integer zipcode2, Boolean areEqual ) {
        grad = new Grad(id1, naziv1, zipcode1);
        Grad grad2 = new Grad(id2, naziv2, zipcode2);
        assertEquals(grad.equals(grad2), areEqual);
    }
}