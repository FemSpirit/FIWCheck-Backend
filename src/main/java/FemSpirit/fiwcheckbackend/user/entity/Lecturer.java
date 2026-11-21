package FemSpirit.fiwcheckbackend.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lecturer")
@PrimaryKeyJoinColumn(name = "lecturer_id")
public class Lecturer extends User {

    public Lecturer() {
    }
}
