package FemSpirit.fiwcheckbackend.modul.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "modul_aequivalenz")
public class ModulAequivalenz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aequivalenzId;

    @OneToOne(optional = false)
    @JoinColumn(name = "modul_id_alt", nullable = false)
    private Modul modulAlt;

    @OneToOne(optional = false)
    @JoinColumn(name = "modul_id_neu", nullable = false)
    private Modul modulNeu;

    public ModulAequivalenz() {
    }

    public Long getAequivalenzId() {
        return aequivalenzId;
    }

    public void setAequivalenzId(Long aequivalenzId) {
        this.aequivalenzId = aequivalenzId;
    }

    public Modul getModulAlt() {
        return modulAlt;
    }

    public void setModulAlt(Modul modulAlt) {
        this.modulAlt = modulAlt;
    }

    public Modul getModulNeu() {
        return modulNeu;
    }

    public void setModulNeu(Modul modulNeu) {
        this.modulNeu = modulNeu;
    }
}