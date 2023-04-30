package rs.fon.studentska_sluzba.controller;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rs.fon.studentska_sluzba.domain.Obavestenje;
import rs.fon.studentska_sluzba.exception.EntityNotFoundException;
import rs.fon.studentska_sluzba.service.ObavestenjeService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ObavestenjeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ObavestenjeService obavestenjeService;

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void getAll() throws Exception {
        List<Obavestenje> obavestenja = Arrays.asList(
                new Obavestenje(1L, LocalDate.of(2022, 3, 3), "Molim vas nosite maske"),
                new Obavestenje(2L, LocalDate.of(2022, 5, 5), "Ljudi prijavite ispite")
        );

        when(obavestenjeService.getSvaObavestenja()).thenReturn(obavestenja);

        mockMvc.perform(get("/api/v1/obavestenja"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].sadrzaj", is("Molim vas nosite maske")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].sadrzaj", is("Ljudi prijavite ispite")));
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void getObavestenjeSaId() throws Exception {
        Obavestenje obavestenje = new Obavestenje(1L, LocalDate.of(2022, 3, 3), "Molim vas nosite maske");

        when(obavestenjeService.getObavestenjeSaId(1L)).thenReturn(obavestenje);

        mockMvc.perform(get("/api/v1/obavestenja/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.sadrzaj", is("Molim vas nosite maske")));
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void getObavestenjeSaIdNepostojece() throws Exception {
        when(obavestenjeService.getObavestenjeSaId(999L)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/api/v1/obavestenja/{id}", 999))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void postObavestenje() throws Exception {
        Obavestenje primljenoObavestenje = Obavestenje.builder().sadrzaj("Molim vas nosite maske.").build();
        Obavestenje sacuvanoObavestenje = Obavestenje.builder()
                .id(1L)
                .datum(LocalDate.now())
                .sadrzaj("Molim vas nosite maske.")
                .build();

        when(obavestenjeService.ubaciObavestenje(primljenoObavestenje)).thenReturn(sacuvanoObavestenje);

        mockMvc.perform(post("/api/v1/obavestenja")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sadrzaj\": \"Molim vas nosite maske.\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.sadrzaj", is("Molim vas nosite maske.")))
                .andExpect(jsonPath("$.datum", Matchers.notNullValue()));
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void postObavestenjeZabranjenoUseru() throws Exception {
        mockMvc.perform(post("/api/v1/obavestenja")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"sadrzaj\": \"Molim vas nosite maske.\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void obrisiObavestenje() throws Exception {
        Long id = 1L;
        Obavestenje obavestenje = Obavestenje.builder()
                .id(id)
                .datum(LocalDate.now())
                .sadrzaj("Molim vas nosite maske.")
                .build();

        when(obavestenjeService.obrisiObavestenjeSaId(id)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/obavestenja/{id}", id))
                .andExpect(status().isNoContent());
    }
    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void obrisiObavestenjeNepostojece() throws Exception {
        Long id = 999L;
        when(obavestenjeService.obrisiObavestenjeSaId(id)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/obavestenja/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void deleteObavestenjeZabranjenoUseru() throws Exception {
        mockMvc.perform(delete("/api/v1/obavestenja/{id}", any(Long.class)))
                .andExpect(status().isForbidden());
    }
}