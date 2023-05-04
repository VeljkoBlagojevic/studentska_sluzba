package rs.fon.studentska_sluzba.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rs.fon.studentska_sluzba.domain.Polaganje;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.exception.EntityNotFoundException;
import rs.fon.studentska_sluzba.service.PolaganjeService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PolaganjeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PolaganjeService polaganjeService;

    @Autowired
    private Gson gson;

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void getSvaPolaganjaAdmin() throws Exception {
        Student veljko = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .build();
        Student nikola = Student.builder()
                .id(2L)
                .ime("Nikola")
                .prezime("Vujcic")
                .build();
        Predmet spa = new Predmet(1L, "SPA", 6);
        Predmet uis = new Predmet(2L, "UIS", 5);
        Predmet ekonomija = new Predmet(3L, "Ekonomija", 6);

        List<Polaganje> polaganja = Arrays.asList(
                new Polaganje(1L, LocalDate.of(2022, 3, 3), true, 6, veljko, spa),
                new Polaganje(2L, LocalDate.of(2023, 2, 2), true, 7, nikola, uis),
                new Polaganje(3L, LocalDate.of(2023, 1, 1), false, 5, veljko, ekonomija)
        );

        when(polaganjeService.getSvaPolaganja()).thenReturn(polaganja);

        mockMvc.perform(get("/api/v1/polaganja"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].polozio", is(true)))
                .andExpect(jsonPath("$[0].ocena", is(6)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].polozio", is(true)))
                .andExpect(jsonPath("$[1].ocena", is(7)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].polozio", is(false)))
                .andExpect(jsonPath("$[2].ocena", is(5)));
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void getSvaPolaganja() throws Exception {
        Student veljko = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .build();
        Predmet spa = new Predmet(1L, "SPA", 6);
        Predmet uis = new Predmet(2L, "UIS", 5);
        Predmet ekonomija = new Predmet(3L, "Ekonomija", 6);

        List<Polaganje> polaganja = Arrays.asList(
                new Polaganje(1L, LocalDate.of(2022, 3, 3), true, 6, veljko, spa),
                new Polaganje(2L, LocalDate.of(2022, 2, 2), true, 7, veljko, uis),
                new Polaganje(3L, LocalDate.of(2023, 1, 1), false, 5, veljko, ekonomija)
        );

        when(polaganjeService.getSvaPolaganja()).thenReturn(polaganja);

        mockMvc.perform(get("/api/v1/polaganja"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].polozio", is(true)))
                .andExpect(jsonPath("$[0].ocena", is(6)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].polozio", is(true)))
                .andExpect(jsonPath("$[1].ocena", is(7)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].polozio", is(false)))
                .andExpect(jsonPath("$[2].ocena", is(5)));
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void getPolaganjaSaId() throws Exception {
        Student veljko = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .build();
        Predmet spa = new Predmet(1L, "SPA", 6);

        Polaganje polaganje = new Polaganje(1L, LocalDate.of(2022, 3, 3), true, 6, veljko, spa);

        when(polaganjeService.getPolaganjeSaId(1L)).thenReturn(polaganje);

        mockMvc.perform(get("/api/v1/polaganja/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void getPolaganjaNemaId() throws Exception {
        when(polaganjeService.getPolaganjeSaId(999L)).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/api/v1/polaganja/{id}", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void dodajPolaganje() throws Exception {
        Student veljko = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .build();
        Predmet spa = new Predmet(1L, "SPA", 6);

        Polaganje polaganjeZaDodavanje = Polaganje.builder()
                .predmet(spa)
                .ocena(7)
                .build();
        Polaganje polaganjeDodato = Polaganje.builder()
                .predmet(spa)
                .ocena(7)
                .id(1L)
                .datum(LocalDate.now())
                .student(veljko)
                .polozio(true)
                .build();

        when(polaganjeService.ubaciPolaganje(polaganjeZaDodavanje)).thenReturn(polaganjeDodato);

        mockMvc.perform(post("/api/v1/polaganja")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(polaganjeZaDodavanje)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.polozio", is(true)))
                .andExpect(jsonPath("$.ocena", is(7)));
    }


    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void obrisiPolaganjeSaId() throws Exception {
        when(polaganjeService.obrisiPolaganjeSaId(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/polaganja/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void obrisiPolaganjeNemaId() throws Exception {
        when(polaganjeService.obrisiPolaganjeSaId(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/polaganja/{id}", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void getSvaNeuspesnaPolaganja() throws Exception {
        Student veljko = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .build();
        Predmet ekonomija = new Predmet(3L, "Ekonomija", 6);
        List<Polaganje> polaganja = List.of(
                new Polaganje(3L, LocalDate.of(2022, 3, 3), false, 5, veljko, ekonomija));

        when(polaganjeService.getSvaNeuspesnaPolaganja()).thenReturn(polaganja);

        mockMvc.perform(get("/api/v1/polaganja/neuspesna"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void getSvaUspesnaPolaganja() throws Exception {
        Student veljko = Student.builder()
               .id(1L)
               .ime("Veljko")
               .prezime("Blagojevic")
               .build();

        Predmet spa = new Predmet(1L, "SPA", 6);
        Predmet uis = new Predmet(2L, "UIS", 5);

        List<Polaganje> polaganja = Arrays.asList(
                new Polaganje(1L, LocalDate.of(2022, 3, 3), true, 6, veljko, spa),
                new Polaganje(2L, LocalDate.of(2022, 2, 2), true, 7, veljko, uis)
        );

        when(polaganjeService.getSvaUspesnaPolaganja()).thenReturn(polaganja);

        mockMvc.perform(get("/api/v1/polaganja/uspesna"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)));
    }


    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void getPolaganjaPageable() throws Exception {
        Student veljko = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .build();
        Predmet spa = new Predmet(1L, "SPA", 6);
        Predmet uis = new Predmet(2L, "UIS", 5);

        List<Polaganje> polaganja = Arrays.asList(
                new Polaganje(1L, LocalDate.of(2022, 3, 3), true, 6, veljko, spa),
                new Polaganje(2L, LocalDate.of(2022, 2, 2), true, 7, veljko, uis)
        );

        Page<Polaganje> polaganjePage = new PageImpl<>(polaganja);

        when(polaganjeService.getSvaNeuspesnaPolaganja(0,3)).thenReturn(polaganjePage);

        mockMvc.perform(get("/api/v1/polaganja/neuspesna/pageable"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource(value = {"0,3"})
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void getPolaganjaPageableSaParametrima(int pageNumber, int pageSize) throws Exception {
        Student veljko = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .build();

        Predmet spa = new Predmet(1L, "SPA", 6);
        Predmet uis = new Predmet(2L, "UIS", 5);

        List<Polaganje> polaganja = Arrays.asList(
                new Polaganje(1L, LocalDate.of(2022, 3, 3), true, 6, veljko, spa),
                new Polaganje(2L, LocalDate.of(2022, 2, 2), true, 7, veljko, uis)
        );

        Page<Polaganje> polaganjePage = new PageImpl<>(polaganja, PageRequest.of(pageNumber, pageSize), polaganja.size());

        when(polaganjeService.getSvaNeuspesnaPolaganja(pageNumber,pageSize)).thenReturn(polaganjePage);

        mockMvc.perform(get("/api/v1/polaganja/neuspesna/pageable"))
                .andExpect(status().isOk());
    }

}