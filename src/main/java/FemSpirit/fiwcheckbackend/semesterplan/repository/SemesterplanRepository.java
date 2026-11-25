package FemSpirit.fiwcheckbackend.semesterplan.repository;

import FemSpirit.fiwcheckbackend.semesterplan.entity.Semesterplan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface SemesterplanRepository extends JpaRepository<Semesterplan, Long> {
  List<Semesterplan> findAllByStudent_UserId(Long studentId);
}
