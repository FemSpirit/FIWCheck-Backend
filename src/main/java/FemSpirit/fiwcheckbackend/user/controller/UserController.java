package FemSpirit.fiwcheckbackend.user.controller;

import FemSpirit.fiwcheckbackend.user.service.UserService;
import org.springframework.web.bind.annotation.*;
import FemSpirit.fiwcheckbackend.user.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registrieren(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO registrierterStudent = userService.registrieren(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(registrierterStudent);
    }

}