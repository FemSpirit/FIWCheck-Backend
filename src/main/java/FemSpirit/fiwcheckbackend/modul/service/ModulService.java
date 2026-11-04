package FemSpirit.fiwcheckbackend.modul.service;
import FemSpirit.fiwcheckbackend.modul.dto.ModulResponseDTO;
import FemSpirit.fiwcheckbackend.modul.entity.Modul;
import FemSpirit.fiwcheckbackend.modul.repository.ModulRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class ModulService {

    private final ModulRepository modulRepository;

    public ModulService(ModulRepository modulRepository) {
        this.modulRepository = modulRepository;
    }

    public List<ModulResponseDTO> getAllModules() {
        return modulRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    /**
     * holt ein spezifisches Modul anhand seiner ID aus der Datenbank
     * wirft einen HTTP-404-NOT_FOUND-Fehler, falls kein Mosul mit dieser ID existiert
     *
     * @param id Die ID des gesuchten Moduls
     * @return Das gefundene Modul als DTO
     */
    public ModulResponseDTO getModulById(Long id)
    {
        Modul modul = modulRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Modul mit der ID " + id + "wurde nicht gefunden"));
        return mapToDTO(modul);
    }


    private ModulResponseDTO mapToDTO(Modul module) {
        return new ModulResponseDTO(
                module.getModulId(),
                module.getBezeichnung(),
                module.getBeschreibung(),
                module.getEcts(),
                module.getSws(),
                module.getPflichtart(),
                module.getAngebotssemester(),
                module.getFliesstInGesamtnote(),
                module.getZeugnisrelevant()
        );
    }}
