package FemSpirit.FIWCheckerBackend.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "rolle")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolleId;

    @Column(length = 20, nullable = false)
    private String bezeichnung;

    public Role() {
    }

    public Long getRolleId() {
        return rolleId;
    }

    public void setRolleId(Long rolleId) {
        this.rolleId = rolleId;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
}