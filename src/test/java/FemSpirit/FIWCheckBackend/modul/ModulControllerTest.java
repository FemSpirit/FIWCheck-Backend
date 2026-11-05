package FemSpirit.FIWCheckBackend.modul;

import FemSpirit.fiwcheckbackend.modul.controller.ModulController;
import FemSpirit.fiwcheckbackend.modul.dto.ModulResponseDTO;
import FemSpirit.fiwcheckbackend.modul.service.ModulService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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
        //
        given(modulService.getAllModules()).willReturn(List.of(standardModul, zweitesModul));

        /*
        Test-Client simuliert HTTP-GET-Anfrage im JSON-Format (MediaType)
        erwartet HTTP-Status 200 (OK)
        prüft Anzahl der erwarteten Elemente
        prüft erwarteten Inhalt der Elemente
         */
        mockMvc.perform(get("/api/module")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].bezeichnung").value(standardModul.bezeichnung()));

    }


}
