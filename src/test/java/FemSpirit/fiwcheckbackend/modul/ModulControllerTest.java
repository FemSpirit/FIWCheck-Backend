package FemSpirit.fiwcheckbackend.modul;

import FemSpirit.fiwcheckbackend.modul.controller.ModulController;
import FemSpirit.fiwcheckbackend.modul.dto.ModulResponseDTO;
import FemSpirit.fiwcheckbackend.modul.service.ModulService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ModulController.class)
class ModulControllerTest
{
    //in der Variable mockMvc wird Test-Client gespeichert (Mock=simulieren, MCV=Model-View-Controller)
    @Autowired
    private MockMvc mockMvc;

    //mockt den ModulService mit Methoden von Mockito
    @MockitoBean
    private ModulService modulService;

    //Variablen für Testdaten
    private ModulResponseDTO standardModul;
    private ModulResponseDTO zweitesModul;

    // wird vor jedem Test ausgeführt und befüllt Variablen
    @BeforeEach
    void setUpTestdata() {
        standardModul = new ModulResponseDTO(
                1L, "Software Engineering", "Beschreibung",
                    5, 4, "Pflicht", "WiSe", 1, true
        );
        zweitesModul = new ModulResponseDTO(
                2L, "Datenbanken", "Beschreibung 2",
                5, 4, "Wahlpflicht", "SoSe",1, true
        );
    }

    @Test
    @DisplayName("GET /api/module - Sollte alle Module als Liste mit Status 200 OK zurückgeben")
    void should_ReturnListOfAllModules_when_ModulesExist() throws Exception {
        //Arrange: Wenn der Service nach allen Modulen gefragt wird,
        //gibt er die Liste mit standardModul und zweitesModul zurück
        given(modulService.getAllModules()).willReturn(List.of(standardModul, zweitesModul));

        /*
        Test-Client simuliert HTTP-GET-Anfrage im JSON-Format (MediaType)
        erwartet HTTP-Status 200 (OK)
        prüft Anzahl der erwarteten Elemente
        prüft erwarteten Inhalt (Bezeichnung) der Elemente
         */
        mockMvc.perform(get("/api/module")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].bezeichnung").value(standardModul.bezeichnung()));
    }

    @Test
    @DisplayName("GET /api/module - solte eine leere Liste zurückgeben, wenn keine Module existieren")
    void should_ReturnEmptyList_when_NoModulesExist() throws Exception {
        //Arrange: Wenn der Service nach allen Modulen gefragt wird,
        //gibt er eine leere Liste zurück
        given(modulService.getAllModules()).willReturn(List.of());

         /*
        Test-Client simuliert HTTP-GET-Anfrage im JSON-Format (MediaType)
        erwartet HTTP-Status 200 (OK)
        prüft Anzahl der erwarteten Elemente (0 = leere Liste)
         */
        mockMvc.perform(get("/api/module")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("GET /api/module/{id} - sollte ein spezifisches Modul anhand einer existierenden ID zurückgeben")
    void should_ReturnModule_when_IdExists() throws Exception {
        Long modulId = 1L;
        // Arrange: Wenn der Service nach dieser modulId gefragt wird, gibt er standardModul zurück
        given(modulService.getModulById(modulId)).willReturn(standardModul);

        /*
        Test-Client simuliert HTTP-GET-Anfrage für eine spezifische ID im JSON-Format
        erwartet HTTP-Status 200 (OK)
        prüft, ob die zurückgegebene Modul-ID korrekt ist
        prüft den erwarteten Inhalt (Bezeichnung) des Moduls
         */
        mockMvc.perform(get("/api/module/{id}", modulId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modulId").value(standardModul.modulId()))
                .andExpect(jsonPath("$.bezeichnung").value(standardModul.bezeichnung()));
    }

    @Test
    @DisplayName("get /api/module/{id} - sollte Status 404 zurückgeben, wenn die ID nicht existiert")
    void should_ReturnNotFound_when_IdDoesNotExist() throws Exception {
        Long nonExistentId = 999L;

        // Arrange: Wenn der Service nach dieser modulId gefragt wird, wird eine
        given(modulService.getModulById(nonExistentId))
                .willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Modul nicht gefunden"));

        /*
        Test-Client simuliert HTTP-GET-Anfrage für eine spezifische,
        nicht existierende ID im JSON-Format
        erwartet HTTP-Status 404 (NOT FOUND)
         */
        mockMvc.perform(get("/api/module/{id}", nonExistentId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }



}
