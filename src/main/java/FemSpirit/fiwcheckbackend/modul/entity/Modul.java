package FemSpirit.fiwcheckbackend.modul.entity;

import FemSpirit.fiwcheckbackend.stupo.entity.Modulgruppe;
import FemSpirit.fiwcheckbackend.stupo.entity.Stupo;
import jakarta.persistence.*;

import java.math.BigDecimal;

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

    @Column(nullable = false, length = 10)
    private String form;

    @Column(length = 10, nullable = false)
    private String sws;

    @Column(nullable = false)
    private Boolean pflichtart;

    @Column(length = 20, nullable = false)
    private String angebotssemester;

  @Column(nullable = false)
    private Integer empfohlenesSemester;

    @Column(nullable = true)
    private Integer fliesstInModulgruppennote;

    @Column(nullable = false)
    private Boolean zeugnisrelevant;

    @Column (nullable = false)
    private Boolean gesamtnotenrelevant;

    @Column(length= 20,nullable = false)
    private String fachgruppe;

    @Column(nullable = true, precision = 2, scale = 1, columnDefinition = "NUMERIC(2,1) " +
            "DEFAULT 1.0 CHECK (raster_groesse_vz IS NULL OR raster_groesse_vz IN (0.5, 1.0, 2.0, 4.0))")
    private BigDecimal rasterGroesseVz = new BigDecimal("1.0");

    @ManyToOne(optional = false)
    @JoinColumn(name = "stupo_id", nullable = false)
    private Stupo stupo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "modulgruppe_id", nullable = false)
    private Modulgruppe modulgruppe;

    public Modul() {
    }

    public Long getModulId() { return modulId; }

    public void setModulId(Long modulId) { this.modulId = modulId; }

    public String getModulnummer() { return modulnummer; }

    public void setModulnummer(String modulnummer) { this.modulnummer = modulnummer; }

    public String getBezeichnung() { return bezeichnung; }

    public void setBezeichnung(String bezeichnung) { this.bezeichnung = bezeichnung; }

    public String getBeschreibung() { return beschreibung; }

    public void setBeschreibung(String beschreibung) { this.beschreibung = beschreibung; }

    public Integer getEcts() { return ects; }

    public void setEcts(Integer ects) { this.ects = ects; }

    public String getForm() { return form; }

    public void setForm(String form) { this.form = form; }

    public String getSws() { return sws; }

    public void setSws(String sws) { this.sws = sws; }

    public boolean getPflichtart() { return pflichtart; }

    public void setPflichtart(Boolean pflichtart) { this.pflichtart = pflichtart; }

    public String getAngebotssemester() { return angebotssemester; }

    public void setAngebotssemester(String angebotssemester) { this.angebotssemester = angebotssemester; }

    public Integer getEmpfohlenesSemester() { return empfohlenesSemester; }

    public void setEmpfohlenesSemester(Integer empfohlenesSemester)
        { this.empfohlenesSemester = empfohlenesSemester; }

    public Integer getFliesstInModulgruppennote() { return fliesstInModulgruppennote; }

    public void setFliesstInModulgruppennote(Integer fliesstInModulgruppennote)
        { this.fliesstInModulgruppennote = fliesstInModulgruppennote; }

    public Boolean getZeugnisrelevant() { return zeugnisrelevant; }

    public void setZeugnisrelevant(Boolean zeugnisrelevant) { this.zeugnisrelevant = zeugnisrelevant; }

    public Boolean getGesamtnotenrelevant() { return gesamtnotenrelevant; }

    public void setGesamtnotenrelevant(Boolean gesamtnotenrelevant)
        { this.gesamtnotenrelevant = gesamtnotenrelevant; }

    public String getFachgruppe() { return fachgruppe; }

    public void setFachgruppe(String fachgruppe) { this.fachgruppe = fachgruppe; }

    public BigDecimal getRasterGroesseVz() { return rasterGroesseVz; }

    public void setRasterGroesseVz(BigDecimal rasterGroesseVz) { this.rasterGroesseVz = rasterGroesseVz; }

    public Stupo getStupo() { return stupo; }

    public void setStupo(Stupo stupo) { this.stupo = stupo; }

    public Modulgruppe getModulgruppe() { return modulgruppe; }

    public void setModulgruppe(Modulgruppe modulgruppe) { this.modulgruppe = modulgruppe; }
}
