package rs.fon.studentska_sluzba.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.fon.studentska_sluzba.domain.Obavestenje;
import rs.fon.studentska_sluzba.exception.EntityNotFoundException;
import rs.fon.studentska_sluzba.logging.Logger;
import rs.fon.studentska_sluzba.repository.ObavestenjeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ObavestenjeServiceTest {


    @Mock
    private ObavestenjeRepository obavestenjeRepository;

    @Mock
    private Logger logger;

    @InjectMocks
    private ObavestenjeService obavestenjeService;

    @Test
    void testGetSvaObavestenja() {
        Obavestenje o1 = new Obavestenje();
        o1.setId(1L);
        o1.setDatum(LocalDate.of(2022,2,2));
        Obavestenje o2 = new Obavestenje();
        o2.setId(2L);
        o2.setDatum(LocalDate.of(2022,1,1));
        when(obavestenjeRepository.findAll()).thenReturn(List.of(o1, o2));

        List<Obavestenje> obavestenja = obavestenjeService.getSvaObavestenja();

        assertThat(obavestenja).isNotNull().hasSize(2);
        assertThat(obavestenja.get(0).getId()).isEqualTo(1L);
        assertThat(obavestenja.get(1).getId()).isEqualTo(2L);
        assertThat(obavestenja.get(0)).isEqualTo(o1);
        assertThat(obavestenja.get(1)).isEqualTo(o2);
    }

    @Test
    void testGetObavestenjeSaId() {
        Obavestenje o1 = new Obavestenje();
        o1.setId(1L);
        o1.setDatum(LocalDate.of(2022,2,2));

        when(obavestenjeRepository.findById(1L)).thenReturn(Optional.of(o1));

        Obavestenje obavestenje = obavestenjeService.getObavestenjeSaId(1L);

        assertThat(obavestenje).isNotNull();
        assertThat(obavestenje.getId()).isEqualTo(1L);
        assertThat(obavestenje.getDatum()).isEqualTo(LocalDate.of(2022,2,2));
        assertThat(obavestenje).isEqualTo(o1);
    }

    @Test
    void testGetObavestenjeNepostojeciID() {
        when(obavestenjeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> obavestenjeService.getObavestenjeSaId(anyLong()));
    }

    @Test
    void testSaveObavestenje() {
        Obavestenje o1 = new Obavestenje();
        o1.setId(1L);

        when(obavestenjeRepository.save(o1)).thenReturn(o1);

        Obavestenje obavestenje = obavestenjeService.ubaciObavestenje(o1);

        assertThat(obavestenje).isNotNull();
        assertThat(obavestenje.getId()).isEqualTo(1L);
        assertThat(obavestenje.getDatum()).isEqualTo(LocalDate.now());
        assertThat(obavestenje).isEqualTo(o1);
    }

    @Test
    void testDeleteObavestenje() {
        Obavestenje o1 = new Obavestenje();
        o1.setId(1L);

        when(obavestenjeRepository.existsById(1L)).thenReturn(true);

        obavestenjeService.obrisiObavestenjeSaId(1L);

        assertThat(obavestenjeRepository.findById(1L)).isNotPresent();
        assertThat(obavestenjeRepository.count()).isZero();
        assertThat(obavestenjeService.getSvaObavestenja()).isEmpty();
    }

    @Test
    void testObrisiObavestenjeNepostojece() {
        when(obavestenjeRepository.existsById(anyLong())).thenReturn(false);
        assertFalse(() -> obavestenjeService.obrisiObavestenjeSaId(anyLong()));
    }

}