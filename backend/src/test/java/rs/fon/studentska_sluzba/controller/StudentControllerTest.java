package rs.fon.studentska_sluzba.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rs.fon.studentska_sluzba.domain.Grad;
import rs.fon.studentska_sluzba.domain.Student;
import rs.fon.studentska_sluzba.service.StudentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void getAllStudents() throws Exception {
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

        List<Student> studenti = List.of(veljko, nikola);
        when(studentService.findAll()).thenReturn(studenti);

        mockMvc.perform(get("/api/v1/studenti"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void getTrenutniStudent() throws Exception {
        Student veljko = Student.builder()
               .id(1L)
               .ime("Veljko")
               .prezime("Blagojevic")
               .mestoRodjenja(new Grad(1L, "Pancevo", 26230))
               .build();

        when(studentService.getTrenutniStudent()).thenReturn(veljko);

        mockMvc.perform(get("/api/v1/studenti/trenutni"))
               .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.ime", is("Veljko")));
    }

    @Test
    @WithMockUser(username = "aa00000000", authorities = "ADMIN")
    void updateStudentKaoAdmin() throws Exception {
        Student veljko = Student.builder()
              .id(1L)
              .ime("Veljko")
              .prezime("Blagojevic")
              .mestoRodjenja(new Grad(1L, "Pancevo", 26230))
              .build();

        Map<String, Object> noviAtributi = new HashMap<>();
        noviAtributi.put("ime", "Veljko");

        when(studentService.updateStudent(1L, noviAtributi)).thenReturn(veljko);

        mockMvc.perform(patch("/api/v1/studenti/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ime\": \"Veljko\"}"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.ime", is("Veljko")));
    }
    @Test
    @WithMockUser(username = "vb20190353", authorities = "USER")
    void updateStudentKaoKorisnik() throws Exception {
        mockMvc.perform(patch("/api/v1/studenti/{id}", any(Long.class)))
              .andExpect(status().isForbidden());
    }

}