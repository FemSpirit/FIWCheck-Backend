package FemSpirit.fiwcheckbackend.user.controller;


import FemSpirit.fiwcheckbackend.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

    /**
     * WebMvc-Test für den UserController (DELETE /user/{id}).
     */
    @WebMvcTest(UserController.class)
    class UserControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private UserService userService;

        @Test
        @DisplayName("DELETE /user/{id} - sollte Status 204 No Content zurückgeben, wenn Benutzer erfolgreich gelöscht wurde")
        void shouldDeleteUserWhenUserExists() throws Exception {
            // ARRANGE
            Long userId = 1L;
            willDoNothing().given(userService).deleteUser(userId);

            // ACT & ASSERT
            mockMvc.perform(delete("/user/{id}", userId))
                    .andExpect(status().isNoContent());

            verify(userService).deleteUser(userId);
        }

        @Test
        @DisplayName("DELETE /user/{id} - sollte Status 404 Not Found zurückgeben, wenn Benutzer nicht existiert")
        void shouldReturn404WhenUserNotFound() throws Exception {
            // ARRANGE
            Long nonExistingId = 99L;
            willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Benutzer nicht gefunden: " + nonExistingId))
                    .given(userService).deleteUser(nonExistingId);

            // ACT & ASSERT
            mockMvc.perform(delete("/user/{id}", nonExistingId))
                    .andExpect(status().isNotFound());

            verify(userService).deleteUser(nonExistingId);
        }
    }

