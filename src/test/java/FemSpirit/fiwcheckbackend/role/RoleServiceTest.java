package FemSpirit.fiwcheckbackend.role;

import FemSpirit.fiwcheckbackend.role.entity.Role;
import FemSpirit.fiwcheckbackend.role.repository.RoleRepository;
import FemSpirit.fiwcheckbackend.role.service.RoleService;
import FemSpirit.fiwcheckbackend.user.repository.UserRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // 1. Wichtig: Aktiviert Mockito für JUnit 5
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository; // 2. Gefehltes Mock-Repository

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RoleService roleService; // 3. Injiziert die Mocks in den Service (verhindert NullPointerException)

    @Test
    @DisplayName("deleteRole() - sollte Status 409 Conflict werfen, wenn die Rolle noch einem Nutzer zugeordnet ist")
    void deleteRole_wirft409_wennRolleNochVonUserVerwendetWird() {
        // ARRANGE
        Role role = new Role("user");
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(userRepository.existsByRoleId(1L)).thenReturn(true);

        // ACT
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> roleService.deleteRole(1L));

        // ASSERT
        // Stelle sicher, dass der Statuscode im Service (BAD_REQUEST oder CONFLICT) mit der Behauptung übereinstimmt:
        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode()); // Oder ex.getStatusCode().value() == 409
        verify(roleRepository, never()).delete(any());
    }
}
