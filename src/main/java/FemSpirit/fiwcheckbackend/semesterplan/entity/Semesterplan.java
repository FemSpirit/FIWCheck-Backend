package FemSpirit.fiwcheckbackend.semesterplan.entity;

import FemSpirit.fiwcheckbackend.belegung.entity.Belegung;
import FemSpirit.fiwcheckbackend.user.entity.Student;
import jakarta.persistence.*;

@Entity
@Table(name = "semesterplan")
public class Semesterplan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "semesterplan_id")
  private Long semesterplanId;

  @Column(nullable = false)
  private Integer semester;

  @ManyToOne(optional = false)
  @JoinColumn(name = "student_id", nullable = false)
  private Student student;

  public Semesterplan() {
  }
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

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }
}
