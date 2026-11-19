package FemSpirit.fiwcheckbackend.studentprofil.service;

import FemSpirit.fiwcheckbackend.studentprofil.dto.StudentprofilResponseDTO;
import FemSpirit.fiwcheckbackend.user.entity.Student;
import FemSpirit.fiwcheckbackend.user.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service-Klasse für die Geschäftslogik für das persönliche Profil einer Studentin
 */
@Service
public class StudentprofilService {
    private final StudentRepository studentRepository;

    public StudentprofilService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Lädt die Profil und Studiendaten einer Studentin anhand ihrer E-Mail-Adresse
     *
     * @param email die E-Mail-Adresse der angemeldeten Studentin
     * @return ein {@link StudentprofilResponseDTO} mit Name, E-Mail, StuPO, Startsemester und AWE-Variante
     * @throws ResponseStatusException, falls keine Studentin mit dieser E-Mail existiert
     */
    public StudentprofilResponseDTO getStudentprofil(String email) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profil nicht gefunden"));

        return new StudentprofilResponseDTO(
                student.getUsername(),
                student.getEmail(),
                student.getStupo() != null ? student.getStupo().getStupoId() : null,
                student.getStupo() != null ? student.getStupo().getGueltigAb() : null,
                student.getStudiumBeginn(),
                student.getAweVariante()
        );
    }
}
