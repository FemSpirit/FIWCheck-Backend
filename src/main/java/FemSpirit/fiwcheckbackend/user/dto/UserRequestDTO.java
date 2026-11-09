package FemSpirit.fiwcheckbackend.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(

        @NotBlank(message = "Der Name darf nicht leer sein.")
        @Size(max = 20, message = "Der Name darf maximal 20 Zeichen haben.")
        String name,

        @NotBlank(message = "Die E-Mail-Adresse darf nicht leer sein.")
        @Email(message = "Bitte gib eine gültige E-Mail-Adresse ein.")
        String email,

        @NotBlank(message = "Das Passwort darf nicht leer sein.")
        @Size(min = 8, message = "Das Passwort muss mindestens 8 Zeichen haben.")
        String password,

        // wird benötigt falls wir Dozent Registrierungsendpunkt schreiben
        // Aktuell wird die Rolle automatisch im UserService auf "student" gesetzt.
        @NotBlank(message = "Die Rolle darf nicht leer sein.")
        String role

) {
}