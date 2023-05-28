package rs.fon.studentska_sluzba.controller;

import com.google.gson.Gson;
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
import rs.fon.studentska_sluzba.service.GradService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class GradControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GradService gradService;

    @Autowired
    private Gson gson;

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void getAll() throws Exception {
        List<Grad> gradovi = Arrays.asList(new Grad(1L, "Beograd", 11000), new Grad(2L, "Novi Sad", 21000));
        when(gradService.findAll()).thenReturn(gradovi);

        mockMvc.perform(get("/api/v1/gradovi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].naziv", is("Beograd")))
                .andExpect(jsonPath("$[0].zipcode", is(11000)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].naziv", is("Novi Sad")))
                .andExpect(jsonPath("$[1].zipcode", is(21000)));
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void getGradSaId() throws Exception {
        Grad grad = new Grad(1L, "Beograd", 11000);
        when(gradService.getGradSaId(1L)).thenReturn(grad);

        mockMvc.perform(get("/api/v1/gradovi/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.naziv", is("Beograd")))
                .andExpect(jsonPath("$.zipcode", is(11000)));
    }

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void ubaciGrad() throws Exception {
        Grad grad = new Grad(null, "Beograd", 11000);
        when(gradService.ubaciGrad(grad)).thenReturn(new Grad(1L, "Beograd", 11000));

        mockMvc.perform(post("/api/v1/gradovi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(grad)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.naziv", is("Beograd")))
                .andExpect(jsonPath("$.zipcode", is(11000)));
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void ubaciGradObicanKorisnik() throws Exception {
        Grad grad = new Grad(null, "Beograd", 11000);
        when(gradService.ubaciGrad(grad)).thenReturn(new Grad(1L, "Beograd", 11000));

        mockMvc.perform(post("/api/v1/gradovi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(grad)))
                .andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @CsvSource({"999", "420"})
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void obrisiGradNepostojeci(Long id) throws Exception {
        when(gradService.obrisiGradSaId(id)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/gradovi/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void obrisiGrad() throws Exception {
        when(gradService.obrisiGradSaId(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/gradovi/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}