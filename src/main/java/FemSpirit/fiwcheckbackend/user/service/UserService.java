package FemSpirit.fiwcheckbackend.user.service;

import FemSpirit.fiwcheckbackend.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import FemSpirit.fiwcheckbackend.role.repository.RoleRepository;
import FemSpirit.fiwcheckbackend.role.entity.Role;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
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

    //Verschlüsselt das Passwort mit BCrypt, bevor es in der DB gespeichert wird.
    private String passwortHashen(String passwort) {
        return passwordEncoder.encode(passwort);
    }


    private Role holeStudentRolle() {
        return roleRepository.findByName("student")
                .orElseThrow(() -> new RuntimeException("Rolle 'student' wurde nicht gefunden."));
    }


}