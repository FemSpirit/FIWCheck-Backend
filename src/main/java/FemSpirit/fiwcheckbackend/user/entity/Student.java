package FemSpirit.fiwcheckbackend.user.entity;

import FemSpirit.fiwcheckbackend.stupo.entity.Stupo;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "student")
@PrimaryKeyJoinColumn(name = "student_id")
public class Student extends User {

    @Column
    private LocalDateTime letzterLogin;

    /*
     * vollzeit, studiumBeginn, aweVariante und stupo waren ursprünglich Pflichtfelder (nullable = false) ,
     * werden aber bei der Registrierung nicht abgefragt.
     * Sie können daher zunächst null sein und werden später bei der Profilvervollständigung ergänzt.
     * Deshalb wird nullable=true
     */
    @Column
    private Boolean vollzeit;

    @Column(length = 20)
    private String studiumBeginn;

    @Column
    private Integer aweVariante;

    /*
    Der Fremdschluessel stupo_id wird von optional=false auf optional=true gesetzt, weil die Studentin nach der Registrierung
    die Stupo angibt
     */
    @ManyToOne
    @JoinColumn(name = "stupo_id")
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

    public String getStudiumBeginn() {
        return studiumBeginn;
    }

    public void setStudiumBeginn(String studiumBeginn) {
        this.studiumBeginn = studiumBeginn;
    }

    public Integer getAweVariante() {
        return aweVariante;
    }

    public void setAweVariante(Integer aweVariante) {
        this.aweVariante = aweVariante;
    }

    public Stupo getStupo() {
        return stupo;
    }

    public void setStupo(Stupo stupo) {
        this.stupo = stupo;
    }
}
