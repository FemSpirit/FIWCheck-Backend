package FemSpirit.fiwcheckbackend.module;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String modulname;
    private String modulnummer;
    private Integer ectsPunkte;
    private String modulvoraussetzungen;
    private String modulbeschreibung;

    public Module() {}

    // Getter-Methoden
    public Long getId() { return id; }
    public String getModulname() { return modulname; }
    public String getModulnummer() { return modulnummer; }
    public Integer getEctsPunkte() { return ectsPunkte; }
    public String getModulvoraussetzungen() { return modulvoraussetzungen; }
    public String getModulbeschreibung() { return modulbeschreibung; }
}

