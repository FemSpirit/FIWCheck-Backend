package FemSpirit.fiwcheckbackend.studentprofil.controller;

import FemSpirit.fiwcheckbackend.auth.util.JwtUtil;
import FemSpirit.fiwcheckbackend.studentprofil.dto.StudentprofilResponseDTO;
import FemSpirit.fiwcheckbackend.studentprofil.service.StudentprofilService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/studentprofil")

public class StudentprofilController {

    /**
     * @param studentprofilService zur Verarbeitung der Profildaten
     * @param jwtUtil Hilfsklasse zur Validierung und Auswertung von JWT-Tokens
     */
    private final StudentprofilService studentprofilService;
    private final JwtUtil jwtUtil;
    public StudentprofilController(StudentprofilService studentprofilService) {
        this.studentprofilService = studentprofilService;
        this.jwtUtil = new JwtUtil();
    }

    /**
     * Gibt die persönlichen Daten und Studiendaten der aktuell authentifizierten Studentin zurück
     * @param authHeader im Format {@code Bearer <token>}
     * @return {@link ResponseEntity} mit dem {@link StudentprofilResponseDTO} und Status {@code 200 OK}
     * @throws ResponseStatusException, falls kein gültiges JWT-Token übermittelt wurde
     */
    @GetMapping
    public ResponseEntity<StudentprofilResponseDTO> getStudentprofil(
        @RequestHeader(value = "Authorization", required = false) String authHeader) {

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Kein gültiges Token vorhanden");
            }

            String token = authHeader.substring(7);
            if (!jwtUtil.validateToken(token)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token ungültig oder abgelaufen");
            }

            String email = jwtUtil.getClaims(token).getSubject();
            return ResponseEntity.ok(studentprofilService.getStudentprofil(email));
        }

    }

