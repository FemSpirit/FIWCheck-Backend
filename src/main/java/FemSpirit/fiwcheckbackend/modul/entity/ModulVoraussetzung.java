package FemSpirit.fiwcheckbackend.modul.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "modul_voraussetzung")
public class ModulVoraussetzung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voraussetzungId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "modul_id", nullable = false)
    private Modul modul;

    @ManyToOne(optional = false)
    @JoinColumn(name = "voraussetzung_modul_id", nullable = false)
    private Modul voraussetzung;

    @Column(length = 20, nullable = false)
    private String voraussetzungstyp;

    public ModulVoraussetzung() {
    }

    public Long getVoraussetzungId() {
        return voraussetzungId;
    }

    public void setVoraussetzungId(Long voraussetzungId) {
        this.voraussetzungId = voraussetzungId;
    }

    public Modul getModul() {
        return modul;
    }

    public void setModul(Modul modul) {
        this.modul = modul;
    }

    public Modul getVoraussetzung() {
        return voraussetzung;
    }

    public void setVoraussetzung(Modul voraussetzung) {
        this.voraussetzung = voraussetzung;
    }

    public String getVoraussetzungstyp() {
        return voraussetzungstyp;
    }

    public void setVoraussetzungstyp(String voraussetzungstyp) {
        this.voraussetzungstyp = voraussetzungstyp;
    }
}