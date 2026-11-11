package FemSpirit.fiwcheckbackend.role;

import FemSpirit.fiwcheckbackend.role.controller.RoleController;
import FemSpirit.fiwcheckbackend.role.service.RoleService;
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

@WebMvcTest(RoleController.class)
class RoleControllerTest {

    //  Injectet MockMvc: Damit können wir HTTP-Aufrufe (GET, POST, DELETE) simulieren,ohne einen echten Server zu starten.
    @Autowired
    private MockMvc mockMvc;

    // Erstellt ein Mock-Objekt des RoleService.
    // @MockitoBean ersetzt den echten Bean im Spring-Kontext durch diesen Mock
    @MockitoBean
    private RoleService roleService;


    //Test 1: Erfolgreiches Löschen einer Rolle.
    // Erwartung: Der Service löscht ohne Fehler, der Controller antwortet mit HTTP 204 (No Content).
    @Test
    @DisplayName("DELETE /role/{id} - sollte Status 204 No Content zurückgeben, wenn Rolle erfolgreich gelöscht wurde")
    void shouldDeleteRoleWhenNotAssignedToUser() throws Exception {
        Long roleId = 1L;

        willDoNothing().given(roleService).deleteRole(roleId);

        mockMvc.perform(delete("/role/{id}", roleId))
                .andExpect(status().isNoContent());

        verify(roleService).deleteRole(roleId);
    }


    // Test 2: Rolle existiert nicht in der Datenbank.
    // Erwartung: Der Service wirft eine ResponseStatusException (404 NOT_FOUND), der Controller leitet diese als HTTP-Status 404 Not Found an den Client weiter.
    @Test
    @DisplayName("DELETE /role/{id} - sollte Status 404 Not Found zurückgeben, wenn Rolle nicht existiert")
    void shouldReturn404WhenRoleNotFound() throws Exception {
        Long nonExistingId = 99L;

        willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Rolle nicht gefunden"))
                .given(roleService).deleteRole(nonExistingId);

        mockMvc.perform(delete("/role/{id}", nonExistingId))
                .andExpect(status().isNotFound());

        verify(roleService).deleteRole(nonExistingId);
    }


    // Test 3: Rolle kann nicht gelöscht werden, sie noch keinem Nutzer zugeordnet ist.
    // Erwartung: Der Service wirft eine ResponseStatusException (400 BAD_REQUEST), der Controller leitet diese als HTTP-Status 400 Bad Request weiter.
    @Test
    @DisplayName("DELETE /role/{id} - sollte Status 400 Bad Request zurückgeben, wenn Rolle noch einem User zugeordnet ist")
    void shouldReturn400WhenRoleIsAssignedToUser() throws Exception {
        Long assignedRoleId = 2L;

        willThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rolle ist noch einem User zugeordnet"))
                .given(roleService).deleteRole(assignedRoleId);

        mockMvc.perform(delete("/role/{id}", assignedRoleId))
                .andExpect(status().isBadRequest());

        verify(roleService).deleteRole(assignedRoleId);
    }
}

