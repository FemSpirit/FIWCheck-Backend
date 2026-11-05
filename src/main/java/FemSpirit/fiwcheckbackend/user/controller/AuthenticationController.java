package FemSpirit.fiwcheckbackend.user.controller;

import FemSpirit.fiwcheckbackend.user.dto.LoginRequestDTO;
import FemSpirit.fiwcheckbackend.user.dto.LoginResponseDTO;
import FemSpirit.fiwcheckbackend.user.service.AuthenticationService;
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
