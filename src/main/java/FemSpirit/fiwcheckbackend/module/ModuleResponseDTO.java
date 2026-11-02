package FemSpirit.fiwcheckbackend.module;

public record ModuleResponseDTO
        (Long id,
         String modulname,
         String modulnummer,
         Integer ectsPunkte,
         String modulvoraussetzungen,
         String modulbeschreibung) {

}
