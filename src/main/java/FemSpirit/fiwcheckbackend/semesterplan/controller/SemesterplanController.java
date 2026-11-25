package FemSpirit.fiwcheckbackend.semesterplan.controller;

import FemSpirit.fiwcheckbackend.semesterplan.dto.SemesterplanResponseDto;
import FemSpirit.fiwcheckbackend.semesterplan.service.SemesterplanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/semesterplan")
public class SemesterplanController {

  private final SemesterplanService semesterplanService;

  public SemesterplanController(SemesterplanService semesterplanService) {
    this.semesterplanService = semesterplanService;
  }

  @GetMapping("/{studentId}")
  public ResponseEntity<List<SemesterplanResponseDto>> getSemesterplan(
    @PathVariable Long studentId) {

    List<SemesterplanResponseDto> response =
      semesterplanService.getSemesterplanByStudentId(studentId);

    return ResponseEntity.ok(response);
  }
}
