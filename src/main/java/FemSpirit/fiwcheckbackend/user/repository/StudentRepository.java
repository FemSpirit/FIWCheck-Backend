package FemSpirit.fiwcheckbackend.user.repository;

import FemSpirit.fiwcheckbackend.user.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository-Schnittstelle für den Datenbankzugriff auf {@link Student}-Entitäten
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
    /**
     * Sucht eine Studentin anhand ihrer eindeutigen E-Mail-Adresse
     * @param email der Studentin
     * @return ein {@link Optional}, das die gefundene {@link Student}-Entität enthält oder leer ist
     */
    Optional<Student> findByEmail(String email);
}