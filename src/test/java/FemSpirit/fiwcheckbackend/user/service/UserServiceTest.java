package FemSpirit.fiwcheckbackend.user.service;


import FemSpirit.fiwcheckbackend.user.entity.User;
import FemSpirit.fiwcheckbackend.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import FemSpirit.fiwcheckbackend.user.dto.UserUpdateRequestDTO;
import org.junit.jupiter.api.AfterEach;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Unit-Test für die Geschäftslogik des UserService (deleteUser).
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    // Simuliert einen authentifizierten Benutzer
    private void authenticateUser(Long userId) {

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        List.of()
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
    }

    // Entfernt die Authentifizierung nach jedem Test
    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }
    @Test
    @DisplayName("deleteUser() - sollte Benutzer löschen, wenn er in der Datenbank existiert")
    void deleteUser_sollteLoeschen_wennUserExistiert() {
        // ARRANGE
        Long userId = 1L;
        authenticateUser(userId);
        User user = mock(User.class); // <-- Statt new User()

        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        // ACT
        userService.deleteUser(userId);

        // ASSERT
        verify(userRepository).delete(user);
    }

    @Test
    @DisplayName("deleteUser() - sollte 404 NOT_FOUND werfen, wenn Benutzer nicht existiert")
    void deleteUser_sollte404Werfen_wennUserNichtExistiert() {
        // ARRANGE
        Long nonExistingId = 99L;
        authenticateUser(nonExistingId);
        given(userRepository.findById(nonExistingId)).willReturn(Optional.empty());

        // ACT & ASSERT
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> userService.deleteUser(nonExistingId));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(userRepository, never()).delete(any());
    }
    @Test
    @DisplayName("deleteUser() - sollte 401 ohne Authentifizierung werfen")
    void deleteUser_sollte401Werfen_wennNichtAuthentifiziert() {

        // ARRANGE
        Long userId = 1L;
        SecurityContextHolder.clearContext();

        // ACT
        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> userService.deleteUser(userId)
        );

        // ASSERT
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());

        verifyNoInteractions(userRepository);
    }
    @Test
    @DisplayName("deleteUser() - sollte 403 bei fremdem Konto werfen")
    void deleteUser_sollte403Werfen_wennFremdesKonto() {

        // ARRANGE
        Long authenticatedUserId = 1L;
        Long requestedUserId = 2L;

        authenticateUser(authenticatedUserId);

        // ACT
        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> userService.deleteUser(requestedUserId)
        );

        // ASSERT
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());

        verifyNoInteractions(userRepository);
    }
    @Test
    @DisplayName("updateUser() - sollte 401 ohne Authentifizierung werfen")
    void updateUser_sollte401Werfen_wennNichtAuthentifiziert() {

        // ARRANGE
        Long userId = 1L;
        SecurityContextHolder.clearContext();

        UserUpdateRequestDTO request =
                new UserUpdateRequestDTO("NeuerName", null);

        // ACT
        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> userService.updateUser(userId, request)
        );

        // ASSERT
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());

        verifyNoInteractions(userRepository);
    }
    @Test
    @DisplayName("updateUser() - sollte 403 bei fremdem Konto werfen")
    void updateUser_sollte403Werfen_wennFremdesKonto() {

        // ARRANGE
        Long authenticatedUserId = 1L;
        Long requestedUserId = 2L;

        authenticateUser(authenticatedUserId);

        UserUpdateRequestDTO request =
                new UserUpdateRequestDTO("NeuerName", null);

        // ACT
        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> userService.updateUser(requestedUserId, request)
        );

        // ASSERT
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());

        verifyNoInteractions(userRepository);
    }
}
