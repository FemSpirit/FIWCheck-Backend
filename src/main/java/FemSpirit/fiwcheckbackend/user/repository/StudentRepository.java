package FemSpirit.fiwcheckbackend.user.repository;

import FemSpirit.fiwcheckbackend.user.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}