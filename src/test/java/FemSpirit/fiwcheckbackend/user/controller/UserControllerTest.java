package FemSpirit.fiwcheckbackend.user.controller;

import FemSpirit.fiwcheckbackend.user.entity.User;
import FemSpirit.fiwcheckbackend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc // ermoeglicht HTTP-Anfragen zu simulieren.
@Transactional
class UserControllerTest {

    private final MockMvcTester mockMvcTester;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Testdaten für Registrierung
    private static final String USERNAME = "Talaa";
    private static final String EMAIL = "Talaa@student.htw-berlin.de";
    private static final String PASSWORD = "password123";

    @Autowired
    public UserControllerTest(
            MockMvcTester mockMvcTester,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.mockMvcTester = mockMvcTester;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Test 1: Prüft, ob eine Studentin mit gültigen Daten erfolgreich registriert wird
    // und ob ihr Passwort korrekt gehasht in der Datenbank gespeichert wird.
    @Test
    void register_WithValidData_ShouldReturnCreated() {

        String request = """
                {
                    "username": "%s",
                    "email": "%s",
                    "password": "%s",
                    "role": "student"
                }
                """.formatted(USERNAME, EMAIL, PASSWORD);

        assertThat(mockMvcTester.post().uri("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .doesNotHavePath("$.password")
                .extractingPath("$.username").isEqualTo(USERNAME);

        User gespeicherterUser = userRepository.findByEmail(EMAIL.toLowerCase())
                .orElseThrow();

        assertThat(passwordEncoder.matches(
                PASSWORD,
                gespeicherterUser.getPassword()
        )).isTrue();
    }

    // Test 2: Prüft, ob ein ungueltiges Domain abgelehnt wird.
    @Test
    void register_WithInvalidEmailDomain_ShouldReturnBadRequest() {

        String request = """
                {
                    "username": "%s",
                    "email": "Talaa@gmail.com",
                    "password": "%s",
                    "role": "student"
                }
                """.formatted(USERNAME, PASSWORD);

        assertThat(mockMvcTester.post().uri("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .hasStatus(HttpStatus.BAD_REQUEST);
    }

    // Test 3: Prüft, ob ein zu kurzes Passwort abgelehnt wird.
    @Test
    void register_WithTooShortPassword_ShouldReturnBadRequest() {

        String request = """
                {
                    "username": "%s",
                    "email": "%s",
                    "password": "12345",
                    "role": "student"
                }
                """.formatted(USERNAME, EMAIL);

        assertThat(mockMvcTester.post().uri("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .hasStatus(HttpStatus.BAD_REQUEST);
    }

    // Test 4: Prüft, ob ein zu langer Name abgelehnt wird.
    @Test
    void register_WithTooLongName_ShouldReturnBadRequest() {

        String request = """
                {
                    "username": "DasIstEineZuLangeName",
                    "email": "%s",
                    "password": "%s",
                    "role": "student"
                }
                """.formatted(EMAIL, PASSWORD);

        assertThat(mockMvcTester.post().uri("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .hasStatus(HttpStatus.BAD_REQUEST);
    }

    // Test 5: Prüft, ob eine bereits registrierte E-Mail-Adresse abgelehnt wird.
    @Test
    void register_WithExistingEmail_ShouldReturnConflict() {

        String request = """
                {
                    "username": "%s",
                    "email": "%s",
                    "password": "%s",
                    "role": "student"
                }
                """.formatted(USERNAME, EMAIL, PASSWORD);

        // Erste Registrierung: sollte erfolgreich sein.
        assertThat(mockMvcTester.post().uri("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .hasStatus(HttpStatus.CREATED);

        // Zweite Registrierung mit derselben E-Mail: sollte abgelehnt werden.
        assertThat(mockMvcTester.post().uri("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .hasStatus(HttpStatus.CONFLICT);
    }
}




