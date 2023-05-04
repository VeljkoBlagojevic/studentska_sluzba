package rs.fon.studentska_sluzba.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import rs.fon.studentska_sluzba.domain.Grad;
import rs.fon.studentska_sluzba.domain.Predmet;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.exception.EntityNotFoundException;
import rs.fon.studentska_sluzba.service.PrijavaService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PrijavaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrijavaService prijavaService;


    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void getPrijave() throws Exception {
        Predmet spa = new Predmet(1L, "SPA", 6);
        Predmet uis = new Predmet(2L, "UIS", 5);
        Predmet ekonomija = new Predmet(3L, "Ekonomija", 6);

        Set<Predmet> prijave = Set.of(spa, uis, ekonomija);
        when(prijavaService.getPrijave()).thenReturn(prijave);

        mockMvc.perform(get("/api/v1/prijave"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void getPrijaveZaAdminaUlogovanKaoAdmin() throws Exception {
        Student veljko = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .mestoRodjenja(new Grad(1L, "Pancevo", 26230))
                .build();
        Student nikola = Student.builder()
                .id(2L)
                .ime("Nikola")
                .prezime("Vujcic")
                .mestoRodjenja(new Grad(2L, "Beograd", 11000))
                .build();

        Predmet spa = new Predmet(1L, "SPA", 6);
        Predmet uis = new Predmet(2L, "UIS", 5);
        Predmet ekonomija = new Predmet(3L, "Ekonomija", 6);


        Map<Student, Set<Predmet>> prijaveZaAdmina = new HashMap<>();
        ;
        prijaveZaAdmina.put(veljko, Set.of(spa, uis));
        prijaveZaAdmina.put(nikola, Set.of(ekonomija, spa));

        when(prijavaService.getPrijaveZaAdmina()).thenReturn(prijaveZaAdmina);

        mockMvc.perform(get("/api/v1/prijave/admin"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void getPrijaveZaAdminaUlogovanKaoKorisnik() throws Exception {
        mockMvc.perform(get("/api/v1/prijave/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void getPrijaveZaAdminaList2UlogovanKaoAdmin() throws Exception {
        Student veljko = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .mestoRodjenja(new Grad(1L, "Pancevo", 26230))
                .build();
        Student nikola = Student.builder()
                .id(2L)
                .ime("Nikola")
                .prezime("Vujcic")
                .mestoRodjenja(new Grad(2L, "Beograd", 11000))
                .build();

        Predmet spa = new Predmet(1L, "SPA", 6);
        Predmet uis = new Predmet(2L, "UIS", 5);
        Predmet ekonomija = new Predmet(3L, "Ekonomija", 6);

        Map<Student, Set<Predmet>> prijaveZaAdmina = new HashMap<>();
        ;
        prijaveZaAdmina.put(veljko, Set.of(spa, uis));
        prijaveZaAdmina.put(nikola, Set.of(ekonomija, spa));

        when(prijavaService.getPrijaveZaAdmina()).thenReturn(prijaveZaAdmina);

        mockMvc.perform(get("/api/v1/prijave/adminList2"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void getPrijaveZaAdminaList2UlogovanKaoKorisnik() throws Exception {
        mockMvc.perform(get("/api/v1/prijave/adminList2"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void getPrijaveZaAdminaListUlogovanKaoAdmin() throws Exception {
        Student veljko = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .mestoRodjenja(new Grad(1L, "Pancevo", 26230))
                .build();
        Student nikola = Student.builder()
                .id(2L)
                .ime("Nikola")
                .prezime("Vujcic")
                .mestoRodjenja(new Grad(2L, "Beograd", 11000))
                .build();

        Predmet spa = new Predmet(1L, "SPA", 6);
        Predmet uis = new Predmet(2L, "UIS", 5);
        Predmet ekonomija = new Predmet(3L, "Ekonomija", 6);

        Map<Student, Set<Predmet>> prijaveZaAdmina = new HashMap<>();
        ;
        prijaveZaAdmina.put(veljko, Set.of(spa, uis));
        prijaveZaAdmina.put(nikola, Set.of(ekonomija, spa));

        when(prijavaService.getPrijaveZaAdmina()).thenReturn(prijaveZaAdmina);

        mockMvc.perform(get("/api/v1/prijave/adminList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].studentDTO.id", Matchers.not(0)));
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void getPrijaveZaAdminaListUlogovanKaoKorisnik() throws Exception {
        mockMvc.perform(get("/api/v1/prijave/adminList"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void getPrijavaSaId() throws Exception {

        Predmet spa = new Predmet(1L, "SPA", 6);
        Predmet uis = new Predmet(2L, "UIS", 5);
        Predmet ekonomija = new Predmet(3L, "Ekonomija", 6);

        Student veljko = Student.builder()
                .id(1L)
                .ime("Veljko")
                .prezime("Blagojevic")
                .mestoRodjenja(new Grad(1L, "Pancevo", 26230))
                .prijave(Set.of(spa, uis))
                .build();

        Student nikola = Student.builder()
                .id(2L)
                .ime("Nikola")
                .prezime("Vujcic")
                .mestoRodjenja(new Grad(2L, "Beograd", 11000))
                .prijave(Set.of(ekonomija, spa))
                .build();

        when(prijavaService.getPrijavaSaId(2L)).thenReturn(uis);

        mockMvc.perform(get("/api/v1/prijave/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)));

    }

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void getPrijavaSaNevazecimId() throws Exception {
        when(prijavaService.getPrijavaSaId(3L)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/api/v1/prijave/{id}", 3))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void obrisiPrijavaSaId() throws Exception {
        when(prijavaService.obrisiPrijavuSaId(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/prijave/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void obrisiPrijavaSaNevazecimId() throws Exception {
        when(prijavaService.obrisiPrijavuSaId(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/prijave/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void dodajPrijavu() throws Exception {
        Predmet predmet = Predmet.builder()
                .naziv("SPA")
                .ESPB(6)
                .build();

        mockMvc.perform(post("/api/v1/prijave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(predmet)))
                .andExpect(status().isCreated());
    }

}