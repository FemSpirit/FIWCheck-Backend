
package FemSpirit.FIWCheckerBackend.entity;

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
    @JoinColumn(name = "stupo_modul_id", nullable = false)
    private StupoModul stupoModul;

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

    public StupoModul getStupoModul() {
        return stupoModul;
    }

    public void setStupoModul(StupoModul stupoModul) {
        this.stupoModul = stupoModul;
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
