package FemSpirit.fiwcheckbackend.studentprofil.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StudentprofilRequestDTO(
        @NotNull(message = "StuPO darf nicht leer sein")
        Long stupoId,

        @NotNull(message = "Startsemester darf nicht leer sein")
        Integer startsemester,

        @NotBlank(message = "AWE-Variante darf nicht leer sein")
        Integer aweVariante
) {
}
