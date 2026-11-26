package FemSpirit.fiwcheckbackend.stupo.repository;

import FemSpirit.fiwcheckbackend.stupo.entity.Fachgruppe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FachgruppeRepository extends JpaRepository<Fachgruppe, Long>
{
    Optional<Fachgruppe> findByFachgruppeBezeichnung(String fachgruppeBezeichnung);
}
