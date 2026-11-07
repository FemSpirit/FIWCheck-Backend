package FemSpirit.fiwcheckbackend.user.entity;
import FemSpirit.fiwcheckbackend.stupo.entity.Stupo;
import FemSpirit.fiwcheckbackend.user.entity.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "student")
@PrimaryKeyJoinColumn(name = "nutzer_id")
public class Student extends User {
    @Column
    private LocalDateTime letzterLogin;

    /*
     * vollzeit, studiumBeginn, aweVariante und stupo waren ursprünglich Pflichtfelder ( nullable = false) ,
     * werden aber bei der Registrierung nicht abgefragt.
     * Sie können daher zunächst null sein und werden später bei der Profilvervollständigung ergänzt.
     * Deshalb wird nullable=true
     */
    @Column(nullable = true)
    private Boolean vollzeit;

    @Column(nullable = true)
    private Integer studiumBeginn;

    @Column(length = 10, nullable = true)
    private String aweVariante;

    @ManyToOne(optional = true)
    @JoinColumn(name = "stupo_id", nullable = true)
    private Stupo stupo;

    public Student() {
    }

    public LocalDateTime getLetzterLogin() {
        return letzterLogin;
    }

    public void setLetzterLogin(LocalDateTime letzterLogin) {
        this.letzterLogin = letzterLogin;
    }

    public Boolean getVollzeit() {
        return vollzeit;
    }

    public void setVollzeit(Boolean vollzeit) {
        this.vollzeit = vollzeit;
    }

    public Integer getStudiumBeginn() {
        return studiumBeginn;
    }

    public void setStudiumBeginn(Integer studiumBeginn) {
        this.studiumBeginn = studiumBeginn;
    }

    public String getAweVariante() {
        return aweVariante;
    }

    public void setAweVariante(String aweVariante) {
        this.aweVariante = aweVariante;
    }

    public Stupo getStupo() {
        return stupo;
    }

    public void setStupo(Stupo stupo) {
        this.stupo = stupo;
    }
}