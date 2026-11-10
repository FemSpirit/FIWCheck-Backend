package FemSpirit.fiwcheckbackend.user.dto;
import jakarta.validation.constraints.Size;

//beide Optional, da beide Variablen einzeln aktualisiert werden können
// Email bleibt unveränderbar
public record UserUpdateRequestDTO(

        @Size(max = 20, message = "Der Name darf maximal 20 Zeichen haben.")
        String username,

        @Size(min = 8, message = "Das Passwort muss mindestens 8 Zeichen haben.")
        String password
) {
}
