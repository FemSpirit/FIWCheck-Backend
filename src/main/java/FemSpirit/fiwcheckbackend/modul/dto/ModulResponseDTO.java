package FemSpirit.fiwcheckbackend.modul.dto;

import FemSpirit.fiwcheckbackend.stupo.entity.Modulgruppe;
import FemSpirit.fiwcheckbackend.stupo.entity.Stupo;

import java.math.BigDecimal;

public record ModulResponseDTO(
        Long modulId,
        String modulnummer,
        String bezeichnung,
        String beschreibung,
        Integer ects,
        String form,
        String sws,
        Boolean pflichtart,
        String angebotssemester,
        Integer empfohlenesSemester,
        Integer fliesstInModulgruppennote,
        Boolean zeugnisrelevant,
        Boolean gesamtnotenrelevant,
        String fachgruppeBezeichnung,  // Für Legende & Farbcodierung im Frontend (Modulübersicht > Alle Module)
        BigDecimal rasterGroesseVz,
        Stupo stupo,
        Modulgruppe modulgruppe
) {
}
