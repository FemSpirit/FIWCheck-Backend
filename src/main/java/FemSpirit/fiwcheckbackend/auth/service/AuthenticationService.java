package FemSpirit.fiwcheckbackend.user.service;

import FemSpirit.fiwcheckbackend.auth.dto.LoginRequestDTO;
import FemSpirit.fiwcheckbackend.auth.dto.LoginResponseDTO;
import FemSpirit.fiwcheckbackend.user.entity.User;
import FemSpirit.fiwcheckbackend.user.repository.UserRepository;
import FemSpirit.fiwcheckbackend.auth.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {

        // User anhand der E-Mail suchen
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("E-Mail oder Passwort ist falsch."));

        // Passwort überprüfen
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("E-Mail oder Passwort ist falsch.");
        }

        // JWT erstellen
        String token = jwtUtil.generateToken(user);

        // Rolle des Users (STUDENT/LECTURER) ermitteln
        String role = user.getRole().getName();

        return new LoginResponseDTO(token, role);
    }
}
