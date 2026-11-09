package FemSpirit.fiwcheckbackend.user.controller;

import FemSpirit.fiwcheckbackend.user.service.UserService;
import org.springframework.web.bind.annotation.*;
import FemSpirit.fiwcheckbackend.user.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins="http://localhost:4200")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. GET /user/{id} - Einzelnen User anhand der ID abrufen
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO dto = userService.getUserById(id);
        return ResponseEntity.ok(dto);
    }

    // 2. GET /user - Alle User abrufen
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registrieren(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO registrierterStudent = userService.registrieren(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(registrierterStudent);
    }


    //DELETE/user/{id}
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}