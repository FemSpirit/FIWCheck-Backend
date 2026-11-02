package FemSpirit.fiwcheckbackend.user;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "nutzer_id")
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;

    // passwort-hash

    //Konsturktor für JPA
    public User() {}
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public String getEmail() {return email;}
}
