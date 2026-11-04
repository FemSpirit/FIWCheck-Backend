package FemSpirit.fiwcheckbackend.user.entity;
import FemSpirit.fiwcheckbackend.stupo.entity.Stupo;
import FemSpirit.fiwcheckbackend.user.entity.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "student")
public class Student extends User {
    @Column
    private LocalDateTime letzterLogin;

    @Column(nullable = false)
    private Boolean vollzeit;

    @Column(nullable = false)
    private Integer studiumBeginn;

    @Column(length = 10, nullable = false)
    private String aweVariante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "stupo_id", nullable = false)
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