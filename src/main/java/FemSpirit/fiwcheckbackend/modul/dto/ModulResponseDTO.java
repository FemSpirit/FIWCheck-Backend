package FemSpirit.fiwcheckbackend.modul.dto;

public record ModulResponseDTO(
        Long modulId,
        String bezeichnung,
        String beschreibung,
        Integer ects,
        Integer sws,
        String pflichtart,
        String angebotssemester,
        Integer fliesstInGesamtnote,
        Boolean zeugnisrelevant
) {
}