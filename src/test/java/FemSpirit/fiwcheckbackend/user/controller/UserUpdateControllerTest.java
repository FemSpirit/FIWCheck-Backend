package FemSpirit.fiwcheckbackend.user.controller;

import FemSpirit.fiwcheckbackend.user.dto.UserResponseDTO;
import FemSpirit.fiwcheckbackend.user.dto.UserUpdateRequestDTO;
import FemSpirit.fiwcheckbackend.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserUpdateControllerTest {

    @Autowired
    private MockMvc mockMvc;
    //wandelt in JSON-String um
    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("PUT /user/{id} Aktualisiert Name und Passwort E-Mail bleibt unverändert")
    void shouldUpdateUsernameAndPassword() throws Exception {
            // ARRANGE
            Long userId = 1L;
            String email = "testuser@htw-berlin.de";
            UserResponseDTO response = new UserResponseDTO(userId, "NeuerName", email);

            given(userService.updateUser(eq(userId), any(UserUpdateRequestDTO.class)))
                    .willReturn(response);

            String requestBody = """
                {
                    "username": "NeuerName",
                    "password": "NeuesPasswort123"
                }
                """;

            // ACT & ASSERT
            mockMvc.perform(put("/user/{id}", userId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.userId").value(userId))
                    .andExpect(jsonPath("$.username").value("NeuerName"))
                    .andExpect(jsonPath("$.email").value(email));

            verify(userService).updateUser(eq(userId), any(UserUpdateRequestDTO.class));
        }

    @Test
    @DisplayName("PUT /user/{id} - sollte Status 400 zurückgeben, wenn Eingabedaten ungültig sind")
    void shouldReturn400WhenRequestIsInvalid() throws Exception {
        // ARRANGE
        Long userId = 1L;
        String invalidBody = """
                {
                    "username": "NeuerName",
                    "password": "123"
                }
                """;

        // ACT & ASSERT
        mockMvc.perform(put("/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /user/{id} - sollte Status 404 zurückgeben, wenn Benutzer nicht existiert")
    void shouldReturn404WhenUserNotFound() throws Exception {
        // ARRANGE
        Long nonExistingId = 99L;
        willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Benutzer nicht gefunden: " + nonExistingId))
                .given(userService).updateUser(eq(nonExistingId), any(UserUpdateRequestDTO.class));

        String requestBody = """
                {
                    "username": "NeuerName",
                    "password": "NeuesPasswort123"
                }
                """;

        // ACT & ASSERT
        mockMvc.perform(put("/user/{id}", nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());

        verify(userService).updateUser(eq(nonExistingId), any(UserUpdateRequestDTO.class));
    }
    }

