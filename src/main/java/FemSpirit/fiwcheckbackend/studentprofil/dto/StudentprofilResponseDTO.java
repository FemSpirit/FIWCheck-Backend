package FemSpirit.fiwcheckbackend.studentprofil.dto;

public record StudentprofilResponseDTO(
        String name,
        String email,
        Long stupoId,
        Integer stupoGueltigAb,
        String startsemester,
        Integer aweVariante
) {
}
