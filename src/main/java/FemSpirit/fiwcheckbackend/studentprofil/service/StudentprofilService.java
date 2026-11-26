package FemSpirit.fiwcheckbackend.studentprofil.service;

import FemSpirit.fiwcheckbackend.studentprofil.dto.StudentprofilRequestDTO;
import FemSpirit.fiwcheckbackend.studentprofil.dto.StudentprofilResponseDTO;
import FemSpirit.fiwcheckbackend.stupo.entity.Stupo;
import FemSpirit.fiwcheckbackend.stupo.repository.StupoRepository;
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
    private final StupoRepository stupoRepository;
    public StudentprofilService(StudentRepository studentRepository, StupoRepository stupoRepository) {
        this.studentRepository = studentRepository;
        this.stupoRepository = stupoRepository;
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

    /**
     * Speichert oder aktualisiert die Studiendaten einer Studentin
     * StuPO und Startsemester können nach der erstmaligen Speicherung nicht mehr geändert werden
     * @param email   die E-Mail-Adresse der angemeldeten Studentin
     * @param request die zu speichernden Studiendaten ({@link StudentprofilRequestDTO})
     * @return das aktualisierte {@link StudentprofilResponseDTO}
     */
    public StudentprofilResponseDTO updateStudieninformationen(String email, StudentprofilRequestDTO request) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Studentin nicht gefunden"));

        Stupo stupo = stupoRepository.findById(request.stupoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "StuPO nicht gefunden"));

        // StuPO darf nach erstmaliger Speicherung nicht mehr geändert werden
        if (student.getStupo() != null && !student.getStupo().getStupoId().equals(stupo.getStupoId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "StuPO kann nach der erstmaligen Speicherung nicht mehr geändert werden");
        }

        // Startsemester darf nach erstmaliger Speicherung nicht mehr geändert werden
        if (student.getStudiumBeginn() != null && !student.getStudiumBeginn().equals(request.startsemester())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Startsemester kann nach der erstmaligen Speicherung nicht mehr geändert werden");
        }

        // AWE-Variante darf nach erstmaliger Speicherung nicht mehr geändert werden
        if (student.getAweVariante() != null && !student.getAweVariante().equals(request.aweVariante())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "AWE-Variante kann nach der erstmaligen Speicherung nicht mehr geändert werden");
        }
        student.setStupo(stupo);
        student.setStudiumBeginn(request.startsemester());
        student.setAweVariante(request.aweVariante());

        Student gespeicherterStudent = studentRepository.save(student);
        return mapToResponseDTO(gespeicherterStudent);
    }

    private StudentprofilResponseDTO mapToResponseDTO(Student student) {
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
