package FemSpirit.fiwcheckbackend.modul.service;
import FemSpirit.fiwcheckbackend.modul.dto.ModulResponseDTO;
import FemSpirit.fiwcheckbackend.modul.entity.Modul;
import FemSpirit.fiwcheckbackend.modul.repository.ModulRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ModulService {

    private final ModulRepository moduleRepository;

    public ModulService(ModulRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<ModulResponseDTO> getAllModules() {
        return moduleRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
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
