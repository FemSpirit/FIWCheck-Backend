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
    

    //User anhand id loeschen
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User mit der ID " + id + " wurde nicht gefunden."));

        userRepository.delete(user);
    }

}