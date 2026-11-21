package FemSpirit.fiwcheckbackend.modul.dto;

public record ModulResponseDTO(
        Long modulId,
        String modulnummer,
        String bezeichnung,
        String beschreibung,
        Integer ects,
        String sws,
        Boolean pflichtart,
        String angebotssemester,
        Integer empfohlenesSemester,
        Integer fliesstInGesamtnote,
        Boolean zeugnisrelevant,
        String fachgruppe
) {
}
