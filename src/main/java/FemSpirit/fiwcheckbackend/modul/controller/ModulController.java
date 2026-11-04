package FemSpirit.fiwcheckbackend.modul.controller;

import FemSpirit.fiwcheckbackend.modul.dto.ModulResponseDTO;
import FemSpirit.fiwcheckbackend.modul.service.ModulService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/module")
public class ModulController {

    private final ModulService modulService;

    public ModulController(ModulService modulService) {
        this.modulService = modulService;
    }

    @GetMapping
    public ResponseEntity<List<ModulResponseDTO>> getAllModules() {
        return ResponseEntity.ok(modulService.getAllModules());
    }

    /**
     * ruft ein spezifisches Modul anhand seiner ID ab
     *
     * @param id Die ID des Moduls aus der URL
     * @return Das Modul-DTO und HTTP-Status 200 (OK) oder 404, falls es nicht existiert
     */
    @GetMapping("/{id}")
    public ResponseEntity<ModulResponseDTO> getModulByID(@PathVariable Long id) {
        return ResponseEntity.ok(modulService.getModulById(id));
    }

}
