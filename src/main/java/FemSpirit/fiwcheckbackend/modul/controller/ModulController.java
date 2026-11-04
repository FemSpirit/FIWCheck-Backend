package FemSpirit.fiwcheckbackend.modul.controller;

import FemSpirit.fiwcheckbackend.modul.dto.ModulResponseDTO;
import FemSpirit.fiwcheckbackend.modul.service.ModulService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/module")
public class ModulController {
    private final ModulService moduleService;
    public ModulController(ModulService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping
    public ResponseEntity<List<ModulResponseDTO>> getAllModules() {
        return ResponseEntity.ok(moduleService.getAllModules());
    }


}
