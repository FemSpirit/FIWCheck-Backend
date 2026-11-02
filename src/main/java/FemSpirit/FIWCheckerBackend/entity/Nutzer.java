package FemSpirit.FIWCheckerBackend.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "nutzer")
@Inheritance(strategy = InheritanceType.JOINED)
public class Nutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nutzerId;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 255, nullable = false)
    private String passwort;

    @ManyToOne
    @JoinColumn(name = "rolle_id")
    private Rolle rolle;

    public Nutzer() {
    }

    public Long getNutzerId() {
        return nutzerId;
    }

    public void setNutzerId(Long nutzerId) {
        this.nutzerId = nutzerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public Rolle getRolle() {
        return rolle;
    }

    public void setRolle(Rolle rolle) {
        this.rolle = rolle;
    }
}
