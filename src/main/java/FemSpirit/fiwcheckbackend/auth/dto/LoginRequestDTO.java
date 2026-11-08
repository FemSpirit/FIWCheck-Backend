package FemSpirit.fiwcheckbackend.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {
    @NotBlank(message = "E-Mail darf nicht leer sein")
    private String email;
    @NotBlank(message = "Passwort darf nicht leer sein")
    private String password;

    public LoginRequestDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
