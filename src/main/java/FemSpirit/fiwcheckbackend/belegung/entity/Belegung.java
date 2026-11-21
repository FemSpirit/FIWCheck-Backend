package FemSpirit.fiwcheckbackend.belegung.entity;

import FemSpirit.fiwcheckbackend.modul.entity.Modul;
import FemSpirit.fiwcheckbackend.semesterplan.entity.Semesterplan;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "belegung")
public class Belegung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "belegung_id")
    private Long belegungId;

    @Column(length = 20, nullable = false)
    private String status;

    @Column(precision = 2, scale = 1)
    private BigDecimal teilnote;

    @Column(nullable = false)
    private Integer versuchsnummer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "semesterplan_id", nullable = false)
    private Semesterplan semesterplan;

    @ManyToOne(optional = false)
    @JoinColumn(name = "modul_id", nullable = false)
    private Modul modul;

    public Belegung() {
    }

    public Long getBelegungId() {
        return belegungId;
    }

    public void setBelegungId(Long belegungId) {
        this.belegungId = belegungId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTeilnote() {
        return teilnote;
    }

    public void setTeilnote(BigDecimal teilnote) {
        this.teilnote = teilnote;
    }

    public Integer getVersuchsnummer() {
        return versuchsnummer;
    }

    public void setVersuchsnummer(Integer versuchsnummer) {
        this.versuchsnummer = versuchsnummer;
    }

    public Semesterplan getSemesterplan() {
      return semesterplan;
    }

    public void setSemesterplan(Semesterplan semesterplan) {
      this.semesterplan = semesterplan;
    }

    public Modul getModul() {
      return modul;
    }

    public void setModul(Modul modul) {
      this.modul = modul;
    }
}
