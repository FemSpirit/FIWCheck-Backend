package FemSpirit.fiwcheckbackend.user.controller;

import FemSpirit.fiwcheckbackend.user.entity.User;
import FemSpirit.fiwcheckbackend.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc // ermoeglichst HTTP-Anfragen zu simulieren.
class UserControllerTest {

    private final MockMvc mockMvc;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Testdaten für Registrierung
    private static final String USERNAME = "Talaa";
    private static final String EMAIL = "Talaa@student.htw-berlin.de";
    private static final String PASSWORD = "password123";


    @Autowired
    public UserControllerTest(
            MockMvc mockMvc,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Löscht vor jedem Test die Daten aus der H2-Testdatenbank.
    // Die Entwicklungsdatenbank ist davon nicht betroffen.
    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    // Test 1: Prüft, ob eine Studentin mit gültigen Daten erfolgreich registriert wird
    // und ob ihr Passwort korrekt gehasht in der Datenbank gespeichert wird.
    @Test
    void register_WithValidData_ShouldReturnCreated() throws Exception {

        String request = """
                {
                    "username": "%s",
                    "email": "%s",
                    "password": "%s",
                     "role": "student"
                }
                """.formatted(USERNAME, EMAIL, PASSWORD);

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(USERNAME)))
                .andExpect(jsonPath("$.email", is(EMAIL.toLowerCase())));

        User gespeicherterUser = userRepository.findByEmail(EMAIL.toLowerCase())
                .orElseThrow();

        assertTrue(passwordEncoder.matches(
                PASSWORD,
                gespeicherterUser.getPassword()
        ));
    }

    // Test 2: Prüft, ob ein ungueltiges Domain abgelehnt wird.
    @Test
    void register_WithInvalidEmailDomain_ShouldReturnBadRequest() throws Exception {

        String request = """
            {
                "username": "%s",
                "email": "Talaa@gmail.com",
                "password": "%s",
                "role": "student"
            }
            """.formatted(USERNAME, PASSWORD);

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    // Test 3: Prüft, ob ein zu kurzes Passwort abgelehnt wird.
    @Test
    void register_WithTooShortPassword_ShouldReturnBadRequest() throws Exception {

        String request = """
            {
                "username": "%s",
                "email": "%s",
                "password": "12345",
                "role": "student"
            }
            """.formatted(USERNAME, EMAIL);

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    // Test 4: Prüft, ob ein zu langer Name abgelehnt wird.
    @Test
    void register_WithTooLongName_ShouldReturnBadRequest() throws Exception {

        String request = """
            {
                "username": "DasIstEineZuLangeName",
                "email": "%s",
                "password": "%s",
                "role": "student"
            }
            """.formatted(EMAIL, PASSWORD);

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    // Test 5: Prüft, ob eine bereits registrierte E-Mail-Adresse abgelehnt wird.
    @Test
    void register_WithExistingEmail_ShouldReturnConflict() throws Exception {

        String request = """
        {
            "username": "%s",
            "email": "%s",
            "password": "%s",
            "role": "student"
        }
        """.formatted(USERNAME, EMAIL, PASSWORD);

        // Erste Registrierung: sollte erfolgreich sein.
        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());

        // Zweite Registrierung mit derselben E-Mail: sollte abgelehnt werden.
        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isConflict());
    }
}



