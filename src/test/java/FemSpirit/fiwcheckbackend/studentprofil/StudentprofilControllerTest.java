package FemSpirit.fiwcheckbackend.studentprofil;

import FemSpirit.fiwcheckbackend.auth.util.JwtUtil;
import FemSpirit.fiwcheckbackend.studentprofil.controller.StudentprofilController;
import FemSpirit.fiwcheckbackend.studentprofil.dto.StudentprofilRequestDTO;
import FemSpirit.fiwcheckbackend.studentprofil.dto.StudentprofilResponseDTO;
import FemSpirit.fiwcheckbackend.studentprofil.service.StudentprofilService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
                "Name", email, 2L, 2024,"WiSe 2025/26", 1);
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

    /*
     * Testet {@code PUT /studentprofil/studieninformationen} beim erstmaligen
     * speichern gültiger Studiendaten ({@code stupoId}, {@code startsemester}, {@code aweVariante})
     * mit gültigem JWT und erwartet HTTP {@code 200 OK}und die gespeicherten Werte im JSON-Body.
     */
    @Test
    @DisplayName("PUT/studentprofil/studieninformationen/200 OK")
    void updateInfos() throws Exception {
        String token = "token";
        String email = "<EMAIL>";
        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn(email);
        when(jwtUtil.validateToken(token)).thenReturn(true);
        when(jwtUtil.getClaims(token)).thenReturn(claims);
        StudentprofilResponseDTO res = new StudentprofilResponseDTO(
                "Name", email, 2L, 2024, "WiSe 2025/26", 1);
        when(studentprofilService.updateStudieninformationen(eq(email), any(StudentprofilRequestDTO.class)))
                .thenReturn(res);

        String reqBody = """
                {
                    "stupoId": 2,
                    "startsemester": "WiSe 2025/26",
                    "aweVariante": 1
                }
                """;
        mockMvc.perform(put("/studentprofil/studieninformationen")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stupoId").value(2))
                .andExpect(jsonPath("$.startsemester").value("WiSe 2025/26"))
                .andExpect(jsonPath("$.aweVariante").value(1));
    }

    /*
     * Testet {@code PUT /studentprofil/studieninformationen} mit leere
     * Pflichtfelder und prüft, ob die Bean-Validation ({@code @Valid})
     * mit HTTP {@code 400 Bad Request} greift
     */
    @Test
    @DisplayName("PUT/studentprofil/studieninformationen/400")
    void updateInfosInvalid() throws Exception {
        String token = "token";
        String invalidBody = """
                {
                    "stupoId": null,
                    "startsemester": "",
                    "aweVariante": null
                }
                """;

        mockMvc.perform(put("/studentprofil/studieninformationen")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(invalidBody))
                .andExpect(status().isBadRequest());
    }

    /*
     * Testet {@code PUT /studentprofil/studieninformationen} wenn,
     * {@code stupo} {@code startsemester} bereits gespeichert wurden da sie nicht
     * erneut geändert werden dürfen (erwartet HTTP {@code 400 Bad Request})
     */
    @Test
    @DisplayName("PUT /studentprofil/studieninformationen/400")
    void updateInfosIsThere() throws Exception {
        String token = "token";
        String email = "<EMAIL>";
        Claims claims = mock(Claims.class);

        when(claims.getSubject()).thenReturn(email);
        when(jwtUtil.validateToken(token)).thenReturn(true);
        when(jwtUtil.getClaims(token)).thenReturn(claims);
        when(studentprofilService.updateStudieninformationen(eq(email), any(StudentprofilRequestDTO.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "StuPO und Startsemester können nicht mehr geändert werden"));

              String reqBody = """
                {
                    "stupoId": 1,
                    "startsemester": "SoSe 2024",
                    "aweVariante": 1
                }
                """;
        mockMvc.perform(put("/studentprofil/studieninformationen")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBody))
                .andExpect(status().isBadRequest());
    }
}



