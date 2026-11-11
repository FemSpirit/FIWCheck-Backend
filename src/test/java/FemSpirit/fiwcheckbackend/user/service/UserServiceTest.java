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

    @Test
    @DisplayName("deleteUser() - sollte Benutzer löschen, wenn er in der Datenbank existiert")
    void deleteUser_sollteLoeschen_wennUserExistiert() {
        // ARRANGE
        Long userId = 1L;
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
        given(userRepository.findById(nonExistingId)).willReturn(Optional.empty());

        // ACT & ASSERT
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> userService.deleteUser(nonExistingId));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(userRepository, never()).delete(any());
    }
}
