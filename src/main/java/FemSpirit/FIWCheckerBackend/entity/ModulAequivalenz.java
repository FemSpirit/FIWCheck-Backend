package FemSpirit.FIWCheckerBackend.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "modul_aequivalenz")
public class ModulAequivalenz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aequivalenzId;

    @OneToOne(optional = false)
    @JoinColumn(name = "modul_id_alt", nullable = false)
    private StupoModul modulAlt;

    @OneToOne(optional = false)
    @JoinColumn(name = "modul_id_neu", nullable = false)
    private StupoModul modulNeu;

    public ModulAequivalenz() {
    }

    public Long getAequivalenzId() {
        return aequivalenzId;
    }

    public void setAequivalenzId(Long aequivalenzId) {
        this.aequivalenzId = aequivalenzId;
    }

    public StupoModul getModulAlt() {
        return modulAlt;
    }

    public void setModulAlt(StupoModul modulAlt) {
        this.modulAlt = modulAlt;
    }

    public StupoModul getModulNeu() {
        return modulNeu;
    }

    public void setModulNeu(StupoModul modulNeu) {
        this.modulNeu = modulNeu;
    }
}
