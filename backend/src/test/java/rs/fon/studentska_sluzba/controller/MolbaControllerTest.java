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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rs.fon.studentska_sluzba.domain.Grad;
import rs.fon.studentska_sluzba.domain.Molba;
import rs.fon.studentska_sluzba.domain.StatusMolbe;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.domain.TipMolbe;
import rs.fon.studentska_sluzba.service.MolbaService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MolbaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MolbaService molbaService;

    @Autowired
    private Gson gson;

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void getAll() throws Exception {
        List<Molba> molbe = Arrays.asList(new Molba(1L,
                        "Pitanje 1",
                        LocalDate.of(2022, 11, 11),
                        "Odgovor 1",
                        LocalDate.of(2023, 2, 2),
                        TipMolbe.PROMENA_PODATAKA_O_STUDENTU,
                        StatusMolbe.U_OBRADI,
                        Student.builder()
                                .id(1L)
                                .ime("Veljko")
                                .prezime("Blagojevic")
                                .mestoRodjenja(new Grad(1L, "Beograd", 11000))
                                .build()
                ),
                new Molba(2L,
                        "Pitanje 2",
                        LocalDate.of(2022, 11, 11),
                        "Odgovor 2",
                        LocalDate.of(2023, 2, 2),
                        TipMolbe.PROMENA_PODATAKA_O_STUDENTU,
                        StatusMolbe.U_OBRADI,
                        Student.builder()
                                .id(1L)
                                .ime("Veljko")
                                .prezime("Blagojevic")
                                .mestoRodjenja(new Grad(1L, "Beograd", 11000))
                                .build()
                ));
        when(molbaService.findAll()).thenReturn(molbe);

        mockMvc.perform(get("/api/v1/molbe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].pitanje", is("Pitanje 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].pitanje", is("Pitanje 2")));
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void getById() throws Exception {
        Molba molba = new Molba(1L,
                "Pitanje 1",
                LocalDate.of(2022, 11, 11),
                "Odgovor 1",
                LocalDate.of(2023, 2, 2),
                TipMolbe.PROMENA_PODATAKA_O_STUDENTU,
                StatusMolbe.U_OBRADI,
                Student.builder()
                        .id(1L)
                        .ime("Veljko")
                        .prezime("Blagojevic")
                        .mestoRodjenja(new Grad(1L, "Beograd", 11000))
                        .build()
        );
        when(molbaService.getMolbaSaId(1L)).thenReturn(molba);

        mockMvc.perform(get("/api/v1/molbe/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void getAllUObradi() throws Exception {
        List<Molba> molbe = Arrays.asList(new Molba(1L,
                        "Pitanje 1",
                        LocalDate.of(2022, 11, 11),
                        null,
                        null,
                        TipMolbe.PROMENA_PODATAKA_O_STUDENTU,
                        StatusMolbe.U_OBRADI,
                        Student.builder()
                                .id(1L)
                                .ime("Veljko")
                                .prezime("Blagojevic")
                                .mestoRodjenja(new Grad(1L, "Beograd", 11000))
                                .build()
                ),
                new Molba(2L,
                        "Pitanje 2",
                        LocalDate.of(2022, 5, 5),
                        null,
                        null,
                        TipMolbe.PROMENA_PODATAKA_O_STUDENTU,
                        StatusMolbe.U_OBRADI,
                        Student.builder()
                                .ime("Veljko")
                                .prezime("Blagojevic")
                                .mestoRodjenja(new Grad(1L, "Beograd", 11000))
                                .build()));

        when(molbaService.findAllUObradi()).thenReturn(molbe);

        mockMvc.perform(get("/api/v1/molbe/uobradi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].pitanje", is("Pitanje 1")))
                .andExpect(jsonPath("$[0].odgovor", blankOrNullString()))
                .andExpect(jsonPath("$[0].datumOdgovora", blankOrNullString()))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].pitanje", is("Pitanje 2")))
                .andExpect(jsonPath("$[1].odgovor", blankOrNullString()))
                .andExpect(jsonPath("$[1].datumOdgovora", blankOrNullString()));
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void getAllUObradiNeSvi() throws Exception {
        List<Molba> molbe = Arrays.asList(Molba.builder()
                        .id(1L)
                        .pitanje("Pitanje 1")
                        .datumPitanja(LocalDate.of(2022, 11, 11))
                        .tipMolbe(TipMolbe.PROMENA_PODATAKA_O_STUDENTU)
                        .statusMolbe(StatusMolbe.U_OBRADI)
                        .student(Student.builder()
                                .id(1L)
                                .ime("Veljko")
                                .prezime("Blagojevic")
                                .mestoRodjenja(new Grad(1L, "Beograd", 11000))
                                .build()
                        ).build(),
                Molba.builder()
                        .id(2L)
                        .pitanje("Pitanje 2")
                        .datumPitanja(LocalDate.of(2022, 5, 5))
                        .odgovor("Odgovor 2")
                        .datumOdgovora(LocalDate.of(2023, 2, 2))
                        .tipMolbe(TipMolbe.PROMENA_PODATAKA_O_STUDENTU)
                        .statusMolbe(StatusMolbe.RAZRESENA)
                        .student(Student.builder()
                                .ime("Veljko")
                                .prezime("Blagojevic")
                                .mestoRodjenja(new Grad(1L, "Beograd", 11000))
                                .build()).build());

        when(molbaService.findAllUObradi()).thenReturn(List.of(molbe.get(0)));

        mockMvc.perform(get("/api/v1/molbe/uobradi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].pitanje", is("Pitanje 1")))
                .andExpect(jsonPath("$[0].odgovor", blankOrNullString()))
                .andExpect(jsonPath("$[0].datumOdgovora", blankOrNullString()));
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void getAllRazresene() throws Exception {
        List<Molba> molbe = Arrays.asList(new Molba(1L,
                        "Pitanje 1",
                        LocalDate.of(2022, 11, 11),
                        "Odgovor 1",
                        LocalDate.of(2023, 1, 1),
                        TipMolbe.PROMENA_PODATAKA_O_STUDENTU,
                        StatusMolbe.RAZRESENA,
                        Student.builder()
                                .id(1L)
                                .ime("Veljko")
                                .prezime("Blagojevic")
                                .mestoRodjenja(new Grad(1L, "Beograd", 11000))
                                .build()
                ),
                new Molba(2L,
                        "Pitanje 2",
                        LocalDate.of(2022, 5, 5),
                        "Odgovor 2",
                        LocalDate.of(2023, 4, 4),
                        TipMolbe.PROMENA_PODATAKA_O_STUDENTU,
                        StatusMolbe.RAZRESENA,
                        Student.builder()
                                .ime("Veljko")
                                .prezime("Blagojevic")
                                .mestoRodjenja(new Grad(1L, "Beograd", 11000))
                                .build()));

        when(molbaService.findAllRazresene()).thenReturn(molbe);

        mockMvc.perform(get("/api/v1/molbe/razresene"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].pitanje", is("Pitanje 1")))
                .andExpect(jsonPath("$[0].odgovor", is("Odgovor 1")))
                .andExpect(jsonPath("$[0].datumOdgovora", notNullValue()))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].pitanje", is("Pitanje 2")))
                .andExpect(jsonPath("$[1].odgovor", is("Odgovor 2")))
                .andExpect(jsonPath("$[1].datumOdgovora", notNullValue()));
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void getAllRazreseneNeSvi() throws Exception {
        List<Molba> molbe = Arrays.asList(Molba.builder()
                        .id(1L)
                        .pitanje("Pitanje 1")
                        .datumPitanja(LocalDate.of(2022, 11, 11))
                        .tipMolbe(TipMolbe.PROMENA_PODATAKA_O_STUDENTU)
                        .statusMolbe(StatusMolbe.U_OBRADI)
                        .student(Student.builder()
                                .id(1L)
                                .ime("Veljko")
                                .prezime("Blagojevic")
                                .mestoRodjenja(new Grad(1L, "Beograd", 11000))
                                .build()
                        ).build(),
                Molba.builder()
                        .id(2L)
                        .pitanje("Pitanje 2")
                        .datumPitanja(LocalDate.of(2022, 5, 5))
                        .odgovor("Odgovor 2")
                        .datumOdgovora(LocalDate.of(2023, 2, 2))
                        .tipMolbe(TipMolbe.PROMENA_PODATAKA_O_STUDENTU)
                        .statusMolbe(StatusMolbe.RAZRESENA)
                        .student(Student.builder()
                                .ime("Veljko")
                                .prezime("Blagojevic")
                                .mestoRodjenja(new Grad(1L, "Beograd", 11000))
                                .build()).build());

        when(molbaService.findAllRazresene()).thenReturn(List.of(molbe.get(1)));

        mockMvc.perform(get("/api/v1/molbe/razresene"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].pitanje", is("Pitanje 2")))
                .andExpect(jsonPath("$[0].odgovor", is("Odgovor 2")))
                .andExpect(jsonPath("$[0].datumOdgovora", notNullValue()));
    }

    @ParameterizedTest
    @CsvSource({"999", "420"})
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void obrisiMolbuNepostojecu(Long id) throws Exception {
        when(molbaService.obrisiMolbuSaId(id)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/molbe/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void obrisiGrad() throws Exception {
        when(molbaService.obrisiMolbuSaId(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/molbe/{id}", 1L))
                .andExpect(status().isNoContent());
    }

}