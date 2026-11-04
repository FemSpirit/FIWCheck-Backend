package FemSpirit.fiwcheckbackend.auth;

public class LoginRequestDTO {
    public String email;
    public String password;

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public LoginRequestDTO() {
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

}
