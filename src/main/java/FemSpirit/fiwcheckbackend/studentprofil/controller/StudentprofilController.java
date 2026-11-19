package FemSpirit.fiwcheckbackend.studentprofil.controller;

import FemSpirit.fiwcheckbackend.auth.util.JwtUtil;
import FemSpirit.fiwcheckbackend.studentprofil.dto.StudentprofilRequestDTO;
import FemSpirit.fiwcheckbackend.studentprofil.dto.StudentprofilResponseDTO;
import FemSpirit.fiwcheckbackend.studentprofil.service.StudentprofilService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/studentprofil")
@CrossOrigin(origins="http://localhost:4200")

public class StudentprofilController {

    /**
     * @param studentprofilService zur Verarbeitung der Profildaten
     * @param jwtUtil Hilfsklasse zur Validierung und Auswertung von JWT-Tokens
     */
    private final StudentprofilService studentprofilService;
    private final JwtUtil jwtUtil;
    public StudentprofilController(StudentprofilService studentprofilService, JwtUtil jwtUtil) {
        this.studentprofilService = studentprofilService;
        this.jwtUtil = jwtUtil;
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

    /**
     * Speichert die Studiendaten der angemeldeten Studentin
     * @param authHeader der HTTP-Authorization-Header im Format {@code Bearer <token>}
     * @param request die validierten Studiendaten aus dem Request-Body
     * @return {@link ResponseEntity} mit dem aktualisierten {@link StudentprofilResponseDTO} und Status {@code 200 OK}
     */
    @PutMapping("/studieninformationen")
        public ResponseEntity<StudentprofilResponseDTO>
        updateStudieninformationen(
                @RequestHeader(value = "Authorization", required = false) String authHeader,
                @Valid @RequestBody StudentprofilRequestDTO request) {

        //Mit authHeader bekommt BE ein JWT und speichert es
            String email = getEmailFromHeader(authHeader);
            return ResponseEntity.ok(studentprofilService.updateStudieninformationen(email, request));
        }

        private String getEmailFromHeader(String authHeader) {
          if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Kein gültiges Token vorhanden");
          }

        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token ungültig oder abgelaufen");
        }

        return jwtUtil.getClaims(token).getSubject();
    }


    }

