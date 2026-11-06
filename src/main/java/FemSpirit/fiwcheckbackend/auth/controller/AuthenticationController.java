package FemSpirit.fiwcheckbackend.auth.controller;

import FemSpirit.fiwcheckbackend.auth.dto.LoginRequestDTO;
import FemSpirit.fiwcheckbackend.auth.dto.LoginResponseDTO;
import FemSpirit.fiwcheckbackend.auth.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {

        LoginResponseDTO response = authenticationService.login(loginRequest);

        return ResponseEntity.ok(response);
    }
}
