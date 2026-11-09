package FemSpirit.fiwcheckbackend.user.service;

import FemSpirit.fiwcheckbackend.auth.dto.LoginRequestDTO;
import FemSpirit.fiwcheckbackend.auth.dto.LoginResponseDTO;
import FemSpirit.fiwcheckbackend.user.entity.User;
import FemSpirit.fiwcheckbackend.user.repository.UserRepository;
import FemSpirit.fiwcheckbackend.user.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import FemSpirit.fiwcheckbackend.role.repository.RoleRepository;
import FemSpirit.fiwcheckbackend.role.entity.Role;
import FemSpirit.fiwcheckbackend.user.dto.*;
import FemSpirit.fiwcheckbackend.user.entity.Student;
import jakarta.transaction.Transactional;
import FemSpirit.fiwcheckbackend.user.entity.User;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    // Der EntityManager wurde entfernt, da er nicht mehr benötigt wird
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository,
                       StudentRepository studentRepository){
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    // Prüft, ob die angegebene E-Mail-Adresse eine erlaubte HTW-Domain besitzt und noch nicht in der DB registriert ist.
    private boolean istEmailGueltig(String email) {

        if (email == null) {
            return false;
        }

        String formatierteEmail = email.trim().toLowerCase();
        boolean erlaubteDomain = formatierteEmail.endsWith("@student.htw-berlin.de")
                || formatierteEmail.endsWith("@htw-berlin.de") ;

        return erlaubteDomain && !userRepository.existsByEmail(formatierteEmail);
    }

    // Hasht das Passwort mit BCrypt, bevor es in der DB gespeichert wird.
    private String passwortHashen(String passwort) {
        return passwordEncoder.encode(passwort);
    }

    private Role holeStudentRolle() {
        return roleRepository.findByName("student")
                .orElseThrow(() -> new RuntimeException("Rolle 'student' wurde nicht gefunden."));
    }

    @Transactional
    public UserResponseDTO registrieren(UserRequestDTO request) {

        if (!istEmailGueltig(request.email())) {
            throw new RuntimeException("Die E-Mail-Domain ist ungueltig oder die E-Mail-Adresse ist bereits registriert.");
        }

        Student student = new Student();
        student.setName(request.name());
        student.setEmail(request.email().trim().toLowerCase());
        student.setPassword(passwortHashen(request.password()));
        student.setRole(holeStudentRolle());

        // Dank der JPA JOINED-Vererbung reicht ein einziger save()-Befehl.
        // Hibernate speichert den User in app_user und den Studenten in student automatisch.
        Student gespeicherterStudent = studentRepository.save(student);

        return new UserResponseDTO(
                gespeicherterStudent.getUserId(),
                gespeicherterStudent.getName(),
                gespeicherterStudent.getEmail()
        );
    }

    // 1. Einzelnen User anhand der ID abrufen
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User mit der ID " + id + " wurde nicht gefunden."));

        return new UserResponseDTO(
                user.getUserId(),
                user.getName(),
                user.getEmail()
        );
    }

    // 2. Alle User abrufen
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDTO(
                        user.getUserId(),
                        user.getName(),
                        user.getEmail()
                ))
                .collect(Collectors.toList());
    }

    // name oder passwort akktualisieren
    @Transactional
    public UserResponseDTO updateUser(Long id, UserUpdateRequestDTO request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User mit der ID " + id + " wurde nicht gefunden."
                ));

        if (request.name() != null && !request.name().isBlank()) {
            user.setName(request.name());
        }

        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(passwortHashen(request.password()));
        }

        User gespeicherterUser = userRepository.save(user);

        return new UserResponseDTO(
                gespeicherterUser.getUserId(),
                gespeicherterUser.getName(),
                gespeicherterUser.getEmail()
        );
    }



    //User anhand id loeschen
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User mit der ID " + id + " wurde nicht gefunden."));

        userRepository.delete(user);
    }

    /**
     * Authentifiziert einen Benutzer anhand von E-Mail-Adresse und Passwort.
     * <p>
     * Die E-Mail-Adresse wird vor der Prüfung normiert
     * Schlägt die Suche nach dem Benutzer oder der Passwortvergleich fehl,
     * wird eine {@link ResponseStatusException} geworfen.
     * </p>
     *
     * @param request Das {@link LoginRequestDTO} mit den Anmeldedaten (E-Mail und Passwort).
     * @return Ein {@link LoginResponseDTO} bei erfolgreicher Authentifizierung.
     * @throws ResponseStatusException Wenn kein Benutzer mit der E-Mail gefunden wird
     *                                 oder das Passwort ungültig ist (HTTP 401 Unauthorized).
     */
    public LoginResponseDTO login(LoginRequestDTO request) {
        String formatierteEmail = request.getEmail().trim().toLowerCase();

        //Prüfen ob der User in der Datenbank existiert
        User user = userRepository.findByEmail(formatierteEmail)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Ungueltige Anmeldedaten"
                ));

        //Passwort vergleichen (gehashtes Passwort aus DB mit Eingabe vergleichen)
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Ungueltige Anmeldedaten"
            );
        }
        return new LoginResponseDTO();
    }
}