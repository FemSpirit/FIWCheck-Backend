package FemSpirit.fiwcheckbackend.module;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/module")
public class ModuleController {
    private final ModuleService moduleService;
    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping
    public ResponseEntity<List<ModuleResponseDTO>> getAllModules() {
        return ResponseEntity.ok(moduleService.getAllModules());
    }


}
