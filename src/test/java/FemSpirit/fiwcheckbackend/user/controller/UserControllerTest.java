package FemSpirit.fiwcheckbackend.user.controller;

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

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc // ermoeglichst HTTP-Anfragen zu simulieren.
class UserControllerTest {

    private final MockMvc mockMvc;
    private final UserRepository userRepository;

    // Testdaten für Registrierung
    private static final String NAME = "Talaa";
    private static final String EMAIL = "Talaa@student.htw-berlin.de";
    private static final String PASSWORD = "password123";


    @Autowired
    public UserControllerTest(
            MockMvc mockMvc,
            UserRepository userRepository) {

        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
    }
    // Löscht vor jedem Test die Daten aus der H2-Testdatenbank.
    // Die Entwicklungsdatenbank ist davon nicht betroffen.
    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    //Test 1: Prüft, ob Studentin mit gültigen Daten erfolgreich registriert werden kann.
    @Test
    void register_WithValidData_ShouldReturnCreated() throws Exception {

        String request = """
                {
                    "name": "%s",
                    "email": "%s",
                    "password": "%s",
                     "role": "student"
                }
                """.formatted(NAME, EMAIL, PASSWORD);

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());
    }

    // Test 2: Prüft, ob ein ungueltiges Domain abgelehnt wird.
    @Test
    void register_WithInvalidEmailDomain_ShouldReturnBadRequest() throws Exception {

        String request = """
            {
                "name": "%s",
                "email": "Talaa@gmail.com",
                "password": "%s",
                "role": "student"
            }
            """.formatted(NAME, PASSWORD);

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }
}



