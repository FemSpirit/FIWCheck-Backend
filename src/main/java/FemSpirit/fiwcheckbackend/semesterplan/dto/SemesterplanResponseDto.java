package FemSpirit.fiwcheckbackend.semesterplan.dto;

import FemSpirit.fiwcheckbackend.belegung.dto.BelegungResponseDto;
import java.util.List;

public class SemesterplanResponseDto {
    private Long semesterplanId;
    private Integer semester;
    private Long studentId;
    private List<BelegungResponseDto> belegungen;

    public Long getSemesterplanId() {
      return semesterplanId;
    }
    public void setSemesterplanId(Long semesterplanId) {
      this.semesterplanId = semesterplanId;
    }
    public Integer getSemester() {
      return semester;
    }
    public void setSemester(Integer semester) {
      this.semester = semester;
    }
    public Long getStudentId() {
      return studentId;
    }
    public void setStudentId(Long studentId) {
      this.studentId = studentId;
    }
    public List<BelegungResponseDto> getBelegungen() {
      return belegungen;
    }
    public void setBelegungen(List<BelegungResponseDto> belegungen) {
      this.belegungen = belegungen;
    }

}
