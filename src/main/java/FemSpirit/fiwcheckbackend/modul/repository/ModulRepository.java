package FemSpirit.fiwcheckbackend.modul.repository;

import FemSpirit.fiwcheckbackend.modul.entity.Modul;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModulRepository extends JpaRepository<Modul, Long> {

}
