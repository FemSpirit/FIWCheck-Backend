package FemSpirit.fiwcheckbackend.semesterplan.service;

import FemSpirit.fiwcheckbackend.belegung.dto.BelegungResponseDto;
import FemSpirit.fiwcheckbackend.semesterplan.dto.SemesterplanResponseDto;
import FemSpirit.fiwcheckbackend.semesterplan.entity.Semesterplan;
import FemSpirit.fiwcheckbackend.semesterplan.repository.SemesterplanRepository;
import FemSpirit.fiwcheckbackend.user.repository.StudentRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SemesterplanService {

  private final SemesterplanRepository semesterplanRepository;
  private final StudentRepository studentRepository;

  public SemesterplanService(
    SemesterplanRepository semesterplanRepository,
    StudentRepository studentRepository) {

    this.semesterplanRepository = semesterplanRepository;
    this.studentRepository = studentRepository;
  }

  public List<SemesterplanResponseDto> getSemesterplanByStudentId(Long studentId) {

    // 1. Pruefen: Existiert der Student
    if (!studentRepository.existsById(studentId)) {
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Student mit der ID " + studentId + " nicht gefunden"
      );
    }

    // 2. Alle Semesterplaene des Studenten aus der Datenbank laden
    List<Semesterplan> semesterplaene =
      semesterplanRepository.findAllByStudent_UserId(studentId);

    // 3. Jeden Semesterplan in ein DTO umwandeln
    return semesterplaene.stream().map(semesterplan -> {

      // 4. Belegungen des Semesterplans in DTOs umwandeln
      List<BelegungResponseDto> belegungDtos =
        semesterplan.getBelegungen().stream().map(belegung -> {

          BelegungResponseDto dto = new BelegungResponseDto();

          dto.setBelegungId(belegung.getBelegungId());
          dto.setStatus(belegung.getStatus());
          dto.setTeilnote(belegung.getTeilnote());
          dto.setVersuchsnummer(belegung.getVersuchsnummer());

          if (belegung.getModul() != null) {
            dto.setModulId(belegung.getModul().getModulId());
            dto.setModulnummer(belegung.getModul().getModulnummer());
            dto.setModulName(belegung.getModul().getBezeichnung());
            dto.setEcts(belegung.getModul().getEcts());
          }

          return dto;

        }).collect(Collectors.toList());

      // 5. Semesterplan-DTO zusammenbauen
      SemesterplanResponseDto responseDto =
        new SemesterplanResponseDto();

      responseDto.setSemesterplanId(semesterplan.getSemesterplanId());
      responseDto.setSemester(semesterplan.getSemester());
      responseDto.setStudentId(semesterplan.getStudent().getUserId());
      responseDto.setBelegungen(belegungDtos);

      return responseDto;

    }).collect(Collectors.toList());
  }
}
