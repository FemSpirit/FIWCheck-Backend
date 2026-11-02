package FemSpirit.FIWCheckerBackend.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "stupo_modul")
public class StupoModul {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stupoModulId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "stupo_id", nullable = false)
    private Stupo stupo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "modulgruppe_id", nullable = false)
    private Modulgruppe modulgruppe;

    @Column(length = 50, nullable = false)
    private String bezeichnung;

    @Column(length = 200)
    private String beschreibung;

    @Column(nullable = false)
    private Integer ects;

    @Column(nullable = false)
    private Integer sws;

    @Column(length = 20, nullable = false)
    private String pflichtart;

    @Column(length = 20, nullable = false)
    private String angebotssemester;

    @Column(nullable = false)
    private Integer fliesstInGesamtnote;

    @Column(nullable = false)
    private Boolean zeugnisrelevant;

    public StupoModul() {
    }

    public Long getStupoModulId() {
        return stupoModulId;
    }

    public void setStupoModulId(Long stupoModulId) {
        this.stupoModulId = stupoModulId;
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

    public Integer getSws() {
        return sws;
    }

    public void setSws(Integer sws) {
        this.sws = sws;
    }

    public String getPflichtart() {
        return pflichtart;
    }

    public void setPflichtart(String pflichtart) {
        this.pflichtart = pflichtart;
    }

    public String getAngebotssemester() {
        return angebotssemester;
    }

    public void setAngebotssemester(String angebotssemester) {
        this.angebotssemester = angebotssemester;
    }

    public Integer getFliesstInGesamtnote() {
        return fliesstInGesamtnote;
    }

    public void setFliesstInGesamtnote(Integer fliesstInGesamtnote) {
        this.fliesstInGesamtnote = fliesstInGesamtnote;
    }

    public Boolean getZeugnisrelevant() {
        return zeugnisrelevant;
    }

    public void setZeugnisrelevant(Boolean zeugnisrelevant) {
        this.zeugnisrelevant = zeugnisrelevant;
    }
}