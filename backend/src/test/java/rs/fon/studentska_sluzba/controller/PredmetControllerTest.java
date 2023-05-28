package rs.fon.studentska_sluzba.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rs.fon.studentska_sluzba.domain.NepolozeniPredmet;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.service.PredmetService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PredmetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PredmetService predmetService;

    @Autowired
    private Gson gson;

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void getNepolozeniPredmetiAdmin() throws Exception {
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

        NepolozeniPredmet veljkoSpa = NepolozeniPredmet.builder()
                .id(1L)
                .predmet(spa)
                .trenutnoSlusa(false)
                .student(veljko)
                .build();

        NepolozeniPredmet nikolaUis = NepolozeniPredmet.builder()
                .id(2L)
                .predmet(uis)
                .trenutnoSlusa(false)
                .student(nikola)
                .build();

        NepolozeniPredmet veljkoEkonomija = NepolozeniPredmet.builder()
                .id(3L)
                .predmet(ekonomija)
                .trenutnoSlusa(false)
                .student(veljko)
                .build();

        when(predmetService.getSveNepolozenePredmete()).thenReturn(Arrays.asList(veljkoSpa, nikolaUis, veljkoEkonomija));

        mockMvc.perform(get("/api/v1/predmeti/nepolozeni"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[*].trenutnoSlusa", everyItem(is(false))));
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void getNepolozeniPredmetiKorisnik() throws Exception {
        Student veljko = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .build();

        Predmet spa = new Predmet(1L, "SPA", 6);
        Predmet ekonomija = new Predmet(3L, "Ekonomija", 6);

        NepolozeniPredmet veljkoSpa = NepolozeniPredmet.builder()
                .id(1L)
                .predmet(spa)
                .trenutnoSlusa(true)
                .student(veljko)
                .build();

        NepolozeniPredmet veljkoEkonomija = NepolozeniPredmet.builder()
                .id(3L)
                .predmet(ekonomija)
                .trenutnoSlusa(false)
                .student(veljko)
                .build();

        when(predmetService.getSveNepolozenePredmete()).thenReturn(Arrays.asList(veljkoSpa, veljkoEkonomija));

        mockMvc.perform(get("/api/v1/predmeti/nepolozeni"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[*].student.id", everyItem(is(1))));
    }

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void getTrenutnoSlusanePredmeteAdmin() throws Exception {
        Predmet spa = new Predmet(1L, "SPA", 6);

        when(predmetService.getTrenutnoSlusani()).thenReturn(List.of(spa));

        mockMvc.perform(get("/api/v1/predmeti/slusa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].trenutnoSlusa", everyItem(is(true))));
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void getTrenutnoSlusanePredmeteKorisnik() throws Exception {
        Predmet spa = new Predmet(1L, "SPA", 6);

        when(predmetService.getTrenutnoSlusani()).thenReturn(List.of(spa));

        mockMvc.perform(get("/api/v1/predmeti/slusa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].trenutnoSlusa", everyItem(is(true))));
    }

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void getTrenutnoSlusanePredmetePageable() throws Exception {
        Predmet spa = new Predmet(1L, "SPA", 6);

        List<Predmet> predmeti = List.of(spa);
        Page<Predmet> predmetiPage = new PageImpl<>(predmeti);

        when(predmetService.getTrenutnoSlusani(0, 3)).thenReturn(predmetiPage);

        mockMvc.perform(get("/api/v1/predmeti/slusa/pageable"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void patchDodajZaSlusanje() throws Exception {
        Student veljko = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .build();

        Predmet spa = new Predmet(1L, "SPA", 6);

        NepolozeniPredmet nepolozeniPredmetZaObradu = NepolozeniPredmet.builder()
                .id(1L)
                .predmet(spa)
                .build();

        NepolozeniPredmet nepolozeniPredmetObradjen = NepolozeniPredmet.builder()
                .id(1L)
                .predmet(spa)
                .student(veljko)
                .trenutnoSlusa(true)
                .build();

        when(predmetService.dodajZaSlusanje(nepolozeniPredmetZaObradu)).thenReturn(nepolozeniPredmetObradjen);

        mockMvc.perform(patch("/api/v1/predmeti/slusa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(nepolozeniPredmetZaObradu)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trenutnoSlusa", is(true)));
    }






}