package FemSpirit.fiwcheckbackend.user.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
        @NotBlank(message = "Der Name darf nicht leer sein.")
        String name,

        @NotBlank(message = "Die E-Mail-Adresse darf nicht leer sein.")
        @Email(message = "Bitte gib eine gültige E-Mail-Adresse ein.")
        String email,

        @NotBlank(message = "Das Passwort darf nicht leer sein.")
        String password
)

{
}