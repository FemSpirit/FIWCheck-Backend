
package FemSpirit.fiwcheckbackend.leistungsstand.entity;

import FemSpirit.fiwcheckbackend.modul.entity.Modul;
import FemSpirit.fiwcheckbackend.user.entity.Student;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "leistungsstand")
public class Leistungsstand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leistungsstandId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "modul_id", nullable = false)
    private Modul modul;

    @Column(length = 20, nullable = false)
    private String status;

    @Column(precision = 2, scale = 1)
    private BigDecimal teilnote;

    @Column(nullable = false)
    private Integer versuchsnummer;

    public Leistungsstand() {
    }

    public Long getLeistungsstandId() {
        return leistungsstandId;
    }

    public void setLeistungsstandId(Long leistungsstandId) {
        this.leistungsstandId = leistungsstandId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Modul getModul() {
        return modul;
    }

    public void setModul(Modul modul) {
        this.modul = modul;
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
}
