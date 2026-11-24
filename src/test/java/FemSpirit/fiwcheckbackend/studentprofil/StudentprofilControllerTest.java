package FemSpirit.fiwcheckbackend.studentprofil;

import FemSpirit.fiwcheckbackend.auth.util.JwtUtil;
import FemSpirit.fiwcheckbackend.studentprofil.controller.StudentprofilController;
import FemSpirit.fiwcheckbackend.studentprofil.dto.StudentprofilResponseDTO;
import FemSpirit.fiwcheckbackend.studentprofil.service.StudentprofilService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentprofilController.class)
@AutoConfigureMockMvc(addFilters = false)
public class StudentprofilControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private StudentprofilService studentprofilService;
    @MockitoBean
    private JwtUtil jwtUtil;


    /*
     * Testet {@code GET /studentprofil} mit gültigem JWT-Token im
     * {@code Authorization}-Header und erwartet HTTP {@code 200 OK} sowie alle
     * erwarteten Profil und Studiendaten im JSON-Body
     */
    @Test
    @DisplayName("GET/studentprofil/ 200 OK")
    void getStudentprofil() throws Exception {
        String token = "token";
        String email = "<EMAIL>";
        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn(email);
        when(jwtUtil.validateToken(token)).thenReturn(true);
        when(jwtUtil.getClaims(token)).thenReturn(claims);

        StudentprofilResponseDTO res = new StudentprofilResponseDTO(
                "Name", email, 2L, 2024,2024, "1");
        when(studentprofilService.getStudentprofil(email)).thenReturn(res);


        mockMvc.perform(get("/studentprofil")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    /*
     * Testet {@code GET /studentprofil} ohne Header
     * und stellt sicher, dass nicht authentifizierte Anfragen mit {@code 401 Unauthorized}
     * abgelehnt werden
     */
    @Test
    @DisplayName("GET/studentprofil/401 Unauthorized")
    void getStudentprofilWithoutToken() throws Exception {
        mockMvc.perform(get("/studentprofil"))
                .andExpect(status().isUnauthorized());
    }

    /*
     * Testet {@code GET /studentprofil} mit einem ungültigen
     * JWT-Token, prüft ob der Controller mit {@code 401 Unauthorized} antwortet
     */
    @Test
    @DisplayName("GET/studentprofil/401 Unauthorized")
    void getStudentprofilInvalidToken() throws Exception {
        String token = "invalid.token";
        when(jwtUtil.validateToken(token)).thenReturn(false);

        mockMvc.perform(get("/studentprofil")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());
    }
}
