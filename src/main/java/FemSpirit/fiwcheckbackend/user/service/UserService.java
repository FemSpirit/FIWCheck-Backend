package FemSpirit.fiwcheckbackend.user.service;


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

    // Prüft, ob die angegebene E-Mail-Adresse eine erlaubte HTW-Domain besitzt
    private boolean istEmailDomainGueltig(String email) {

        return email.endsWith("@student.htw-berlin.de") || email.endsWith("@htw-berlin.de") ;
    }
    // Prüft, ob die angegebene Email im DB bereits existiert
    private boolean emailBereitsRegistriert(String email) {

        return userRepository.existsByEmail(email);
    }


    // Hasht das Passwort mit BCrypt, bevor es in der DB gespeichert wird.
    private String passwortHashen(String passwort) {
        return passwordEncoder.encode(passwort);
    }

    /**
     * Lädt die Standard-Rolle 'student' aus der Datenbank.
     *
     * @return Die {@link Role}-Entität mit dem Namen "student".
     * @throws ResponseStatusException wenn die Rolle in der Datenbank fehlt (HTTP 500 Internal Server Error).
     */
    private Role holeStudentRolle() {
        return roleRepository.findByName("student")
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "Rolle 'student' wurde nicht gefunden."));
    }

    @Transactional
    public UserResponseDTO registrieren(UserRequestDTO request) {

        String email = request.email().trim().toLowerCase();

        if (!istEmailDomainGueltig(email)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Die E-Mail-Domain ist ungültig.");
        }
        if (emailBereitsRegistriert(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Diese E-Mail-Adresse ist bereits registriert.");
        }

        Student student = new Student();
        student.setUsername(request.username());
        student.setEmail(email);
        student.setPassword(passwortHashen(request.password()));
        student.setRole(holeStudentRolle());

        // Dank der JPA JOINED-Vererbung reicht ein einziger save()-Befehl.
        // Hibernate speichert den User in app_user und den Studenten in student automatisch.
        Student gespeicherterStudent = studentRepository.save(student);

        return new UserResponseDTO(
                gespeicherterStudent.getUserId(),
                gespeicherterStudent.getUsername(),
                gespeicherterStudent.getEmail()
        );
    }

    /**
     * Ruft einen einzelnen Benutzer anhand seiner ID ab.
     *
     * @param id Die eindeutige ID des Benutzers (Primärschlüssel).
     * @return Ein {@link UserResponseDTO} mit den Benutzerdaten.
     * @throws ResponseStatusException wenn kein Benutzer mit dieser ID gefunden wurde (HTTP 404 Not Found).
     */
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User mit der ID " + id + " wurde nicht gefunden."));

        return new UserResponseDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    // 2. Alle User abrufen
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDTO(
                        user.getUserId(),
                        user.getUsername(),
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

        if (request.username() != null && !request.username().isBlank()) {
            user.setUsername(request.username());
        }

        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(passwortHashen(request.password()));
        }

        User gespeicherterUser = userRepository.save(user);

        return new UserResponseDTO(
                gespeicherterUser.getUserId(),
                gespeicherterUser.getUsername(),
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


}