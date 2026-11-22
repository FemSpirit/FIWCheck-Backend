package FemSpirit.fiwcheckbackend.modul.entity;

import FemSpirit.fiwcheckbackend.stupo.entity.Modulgruppe;
import FemSpirit.fiwcheckbackend.stupo.entity.Stupo;
import jakarta.persistence.*;

@Entity
@Table(name = "modul")
public class Modul {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "modul_id")
    private Long modulId;

    @Column(length = 10, nullable = false)
    private String modulnummer;

    @Column(length = 50, nullable = false)
    private String bezeichnung;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String beschreibung;

    @Column(nullable = false)
    private Integer ects;

    @Column(length = 10, nullable = false)
    private String sws;

    @Column(nullable = false)
    private boolean pflichtart;

    @Column(length = 20, nullable = false)
    private String angebotssemester;

  @Column(nullable = false)
    private Integer empfohlenesSemester;

    @Column(nullable = false)
    private Integer fliesstInModulgruppennote;

    @Column(nullable = false)
    private Boolean zeugnisrelevant;

    @Column(length= 20,nullable = false)
    private String fachgruppe;

    @ManyToOne(optional = false)
    @JoinColumn(name = "stupo_id", nullable = false)
    private Stupo stupo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "modulgruppe_id", nullable = false)
    private Modulgruppe modulgruppe;

    public Modul() {
    }

    public Long getModulId() {
        return modulId;
    }

    public void setModulId(Long modulId) {
        this.modulId = modulId;
    }

    public String getModulnummer() {
      return modulnummer;
    }

    public void setModulnummer(String modulnummer) {
      this.modulnummer = modulnummer;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Integer getEcts() {
        return ects;
    }

    public void setEcts(Integer ects) {
      this.ects = ects;
    }

    public String getSws() {
      return sws;
    }

    public void setSws(String sws) {
      this.sws = sws;
    }

    public boolean getPflichtart() {
        return pflichtart;
    }

    public void setPflichtart(boolean pflichtart) {
        this.pflichtart = pflichtart;
    }

    public String getAngebotssemester() {
        return angebotssemester;
    }

    public void setAngebotssemester(String angebotssemester) {
        this.angebotssemester = angebotssemester;
    }

    public Integer getEmpfohlenesSemester() {
      return empfohlenesSemester;
    }

    public void setEmpfohlenesSemester(Integer empfohlenesSemester) {
      this.empfohlenesSemester = empfohlenesSemester;
    }

    public Integer getFliesstInModulgruppennote() {
        return fliesstInModulgruppennote;
    }

    public void setFliesstInModulgruppennote(Integer fliesstInGesamtnote) {
        this.fliesstInModulgruppennote = fliesstInGesamtnote;
    }

    public Boolean getZeugnisrelevant() {
        return zeugnisrelevant;
    }

    public void setZeugnisrelevant(Boolean zeugnisrelevant) {
        this.zeugnisrelevant = zeugnisrelevant;
    }

    public String getFachgruppe() {
      return fachgruppe;
    }

    public void setFachgruppe(String fachgruppe) {
      this.fachgruppe = fachgruppe;
    }

    public Stupo getStupo() {
        return stupo;
      }

    public void setStupo(Stupo stupo) {
        this.stupo = stupo;
      }

    public Modulgruppe getModulgruppe() {
        return modulgruppe;
      }

    public void setModulgruppe(Modulgruppe modulgruppe) {
      this.modulgruppe = modulgruppe;
    }
}
