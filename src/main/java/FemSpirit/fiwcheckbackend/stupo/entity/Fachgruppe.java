package FemSpirit.fiwcheckbackend.stupo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fachgruppe")
/**
 * Repräsentiert eine fachliche Gruppe (z. B. Informatik oder Wirtschaft),
 * der Module zugeordnet werden können (etwa für Farbcodierung und Legende
 * in der Modulübersicht).
 */
public class Fachgruppe
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fachgruppe_id")
    private long fachgruppeId;

    @Column(name = "fachgruppe_bezeichnung", length = 20, unique = true)
    private String fachgruppeBezeichnung;

    public Fachgruppe()
    {
    }

    public long getFachgruppeId()
    {
        return fachgruppeId;
    }

    public void setFachgruppeBezeichnung(String fachgruppeBezeichnung)
    {
        this.fachgruppeBezeichnung = fachgruppeBezeichnung;
    }

    public String getFachgruppeBezeichnung()
    {
        return fachgruppeBezeichnung;
    }

}
