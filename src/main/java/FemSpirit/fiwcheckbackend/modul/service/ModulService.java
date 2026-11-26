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
     * wirft einen HTTP-404-NOT_FOUND-Fehler, falls kein Modul mit dieser ID existiert
     *
     * @param id Die ID des gesuchten Moduls
     * @return Das gefundene Modul als DTO
     */
    public ModulResponseDTO getModulById(Long id)
    {
        Modul modul = modulRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Modul mit der ID " + id + " wurde nicht gefunden"));
        return mapToDTO(modul);
    }


    private ModulResponseDTO mapToDTO(Modul modul) {
        // Ternärer Operator: Hat das Modul eine Fachgruppe (ist modul.getFachgruppe() nicht null)?
        String fachgruppeBezeichnung = modul.getFachgruppe() != null
                // Wenn ja: Bezeichnung auslesen und in die Variable schreiben
                ? modul.getFachgruppe().getFachgruppeBezeichnung()
                // Wenn nein (z. B. Wahlpflichtmodul): null verwenden
                : null;

        return new ModulResponseDTO(
                modul.getModulId(),
                modul.getModulnummer(),
                modul.getBezeichnung(),
                modul.getBeschreibung(),
                modul.getEcts(),
                modul.getForm(),
                modul.getSws(),
                modul.getPflichtart(),
                modul.getAngebotssemester(),
                modul.getEmpfohlenesSemester(),
                modul.getFliesstInModulgruppennote(),
                modul.getZeugnisrelevant(),
                modul.getGesamtnotenrelevant(),
                fachgruppeBezeichnung,
                modul.getRasterGroesseVz(),
                modul.getStupo(),
                modul.getModulgruppe()
        );
    }}
