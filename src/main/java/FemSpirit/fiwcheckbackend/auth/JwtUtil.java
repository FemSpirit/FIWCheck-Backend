package FemSpirit.fiwcheckbackend.auth;


import org.springframework.beans.factory.annotation.Value;


public class JwtUtil {

    private final String SECRET_KEY;
    private final long EXPIRATION_TIME;
    public JwtUtil(@Value("${app.jwt.secret}") String secretKey, @Value("${app.jwt.expiration-time}") long expirationTime) {
        SECRET_KEY = secretKey;
        EXPIRATION_TIME = expirationTime;
    }

}
