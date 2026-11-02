package FemSpirit.fiwcheckbackend.module;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<ModuleResponseDTO> getAllModules() {
        return moduleRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }


    private ModuleResponseDTO mapToDTO(Module module) {
        return new ModuleResponseDTO(
                module.getId(),
                module.getModulname(),
                module.getModulnummer(),
                module.getEctsPunkte(),
                module.getModulvoraussetzungen(),
                module.getModulbeschreibung()
        );
    }
}
