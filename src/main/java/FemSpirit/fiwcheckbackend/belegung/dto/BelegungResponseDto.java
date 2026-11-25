package FemSpirit.fiwcheckbackend.belegung.dto;

import java.math.BigDecimal;

public class BelegungResponseDto {
  private Long belegungId;
  private String status;
  private BigDecimal teilnote;
  private Integer versuchsnummer;
  private Long modulId;
  // Das wird im UI fuer den Benutzer angezeigt
  private String modulnummer;
  private String modulName;
  private Integer ects;

  public Long getBelegungId() {
    return belegungId;
  }

  public void setBelegungId(Long belegungId) {
    this.belegungId = belegungId;
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

  public String getModulName() {
    return modulName;
  }

  public void setModulName(String modulName) {
    this.modulName = modulName;
  }

  public Integer getEcts() {
    return ects;
  }

  public void setEcts(Integer ects) {
    this.ects = ects;
  }

}

