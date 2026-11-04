package FemSpirit.fiwcheckbackend.user.entity;
import FemSpirit.fiwcheckbackend.user.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "lecturer")
public class Lecturer extends User {

    public Lecturer() {
    }
}
