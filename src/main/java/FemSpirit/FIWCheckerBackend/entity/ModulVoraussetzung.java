package FemSpirit.FIWCheckerBackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "modul_voraussetzung")
public class ModulVoraussetzung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voraussetzungId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "modul_id", nullable = false)
    private StupoModul modul;

    @ManyToOne(optional = false)
    @JoinColumn(name = "voraussetzung_modul_id", nullable = false)
    private StupoModul voraussetzung;

    @Column(length = 20, nullable = false)
    private String voraussetzungstyp;

    public ModulVoraussetzung() {
    }

    public String getVoraussetzungstyp() {
        return voraussetzungstyp;
    }

    public void setVoraussetzungstyp(String voraussetzungstyp) {
        this.voraussetzungstyp = voraussetzungstyp;
    }
}
