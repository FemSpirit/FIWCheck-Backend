package FemSpirit.fiwcheckbackend.stupo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "modulgruppe")
public class Modulgruppe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "modulgruppe_id")
    private Long modulgruppeId;

    @Column(length = 50, nullable = false)
    private String bezeichnung;

    public Modulgruppe() {
    }

    public Long getModulgruppeId() {
        return modulgruppeId;
    }

    public void setModulgruppeId(Long modulgruppeId) {
        this.modulgruppeId = modulgruppeId;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
}
