package rs.fon.studentska_sluzba.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import rs.fon.studentska_sluzba.domain.Grad;
import rs.fon.studentska_sluzba.exception.EntityNotFoundException;
import rs.fon.studentska_sluzba.logging.Logger;
import rs.fon.studentska_sluzba.repository.GradRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GradServiceTest {

    @Mock
    private GradRepository gradRepository;

    @Mock
    private Logger logger;

    @InjectMocks
    private GradService gradService;


    @Test
    @DisplayName("Test findAll()")
    void testFindAll() {
        // setup mock repository data
        List<Grad> gradovi = new ArrayList<>();
        gradovi.add(new Grad(1L, "Grad1", 10000));
        gradovi.add(new Grad(2L, "Grad2", 20000));
        gradovi.add(new Grad(3L, "Grad3", 30000));

        // set up mock repository response
        when(gradRepository.findAll()).thenReturn(gradovi);

        // call service method
        List<Grad> result = gradService.findAll();

        // assert results
        assertThat(result).isEqualTo(gradovi);
        verify(gradRepository, times(1)).findAll();
    }

    @Test
    void findAll_withPagination() {
        // given
        List<Grad> gradovi = new ArrayList<>();
        gradovi.add(new Grad(1L, "Grad 1", 11000));
        gradovi.add(new Grad(2L, "Grad 2", 21000));
        int pageNo = 0;
        int pageSize = 2;
        Page<Grad> page = new PageImpl<>(gradovi);
        when(gradRepository.findAll(any(Pageable.class))).thenReturn(page);

        // when
        Page<Grad> result = gradService.findAll(pageNo, pageSize);

        // then
        verify(gradRepository, times(1)).findAll(any(Pageable.class));
        assertEquals(page, result);
    }

    @Test
    @DisplayName("Test findAll() with pagination")
    void testFindAllWithPagination() {
        // setup mock repository data
        List<Grad> gradovi = new ArrayList<>();
        gradovi.add(new Grad(1L, "Grad1", 10000));
        gradovi.add(new Grad(2L, "Grad2", 20000));
        gradovi.add(new Grad(3L, "Grad3", 30000));
        int pageNo = 0;
        int pageSize = 10;

        // set up mock repository response
        Page<Grad> gradPage = mock(Page.class);
        when(gradRepository.findAll(any(PageRequest.class))).thenReturn(gradPage);
        when(gradPage.getContent()).thenReturn(gradovi);

        // call service method
        Page<Grad> result = gradService.findAll(pageNo, pageSize);

        // assert results
        assertThat(result.getContent()).isEqualTo(gradovi);
        verify(gradRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void getGradSaId() {
        // given
        Grad grad = new Grad(1L, "Grad 1", 11000);
        Long gradId = 1L;
        when(gradRepository.findById(gradId)).thenReturn(Optional.of(grad));

        // when
        Grad result = gradService.getGradSaId(gradId);

        // then
        verify(gradRepository, times(1)).findById(gradId);
        assertEquals(grad, result);
    }

    @Test
    @DisplayName("Test getGradSaId() with valid id")
    void testGetGradSaIdWithValidId() {
        // setup mock repository data
        // given
        Grad grad = new Grad(1L, "Grad1", 10000);

        // set up mock repository response
        when(gradRepository.findById(grad.getId())).thenReturn(Optional.of(grad));

        // call service method
        // when
        Grad result = gradService.getGradSaId(grad.getId());

        // assert results
        // then
        assertThat(result).isEqualTo(grad);
        verify(gradRepository, times(1)).findById(grad.getId());
    }
    @Test
    void getGradSaId_notFound() {
        // setup mock repository data
        // set up mock repository response
        // given
        Long gradId = 1L;
        when(gradRepository.findById(gradId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(EntityNotFoundException.class, () -> gradService.getGradSaId(gradId));
        verify(gradRepository, times(1)).findById(gradId);
        verify(logger, times(1)).error(any(EntityNotFoundException.class));
    }

    @Test
    void ubaciGrad() {
        // given
        Grad grad = new Grad(1L, "Grad 1", 11000);
        when(gradRepository.save(grad)).thenReturn(grad);

        // when
        Grad result = gradService.ubaciGrad(grad);

        // then
        verify(gradRepository, times(1)).save(grad);
        verify(logger, times(1)).info("Uspesno sacuvan grad = " + grad);
        assertEquals(grad, result);
    }

    @Test
    void ubaciGrad_error() {
        // given
        Grad grad = new Grad(1L, "Grad 1", 11000);
        when(gradRepository.save(grad)).thenThrow(new RuntimeException());

        // when, then
        assertThrows(RuntimeException.class, () -> gradService.ubaciGrad(grad));
        verify(gradRepository, times(1)).save(grad);
        verify(logger, times(1)).error(any(RuntimeException.class));
    }

    @Test
    void testObrisiGradSaIdSuccess() {
        Long id = 1L;

        Grad grad = new Grad();
        grad.setId(id);

        when(gradRepository.findById(id)).thenReturn(Optional.of(grad));

        boolean result = gradService.obrisiGradSaId(id);

        assertTrue(result);
        verify(gradRepository, times(1)).deleteById(id);
        verify(logger, times(1)).info("Grad obrisan sa id = " + id);
    }

    @Test
    void testObrisiGradSaIdNotFound() {
        Long id = 1L;

        when(gradRepository.findById(id)).thenReturn(Optional.empty());

        boolean result = gradService.obrisiGradSaId(id);

        assertFalse(result);
        verify(gradRepository, times(0)).deleteById(id);
        verify(logger, times(1)).error(any(EntityNotFoundException.class));
    }

}
