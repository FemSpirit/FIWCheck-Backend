package FemSpirit.FIWCheckBackend.modul;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled ("kann aktiviert werden, wenn DB & Tabellen korrekt angebunden / aufgesetzt sind")
//sagt Spring, dass für den Test die echte Umgebung der Anwendung simuliert wird (Application Context)
@SpringBootTest
//sorgt dafür, dass Spring im Hintergrund einen Test-Client, mit dem HTTP-Anfragen an den Controller geschickt werden können, einrichten kann
@AutoConfigureMockMvc
public class ModulIntegrationTest
{
    //in der Variable mockMvc wird Test-Client gespeichert (Mock=simulieren, MCV=Model-View-Controller)
    @Autowired
    private MockMvc mockMvc;

    //signalisiert JUnit, das ist eine eigenständige Test-Methode (werden dann ausgeführt)
    @Test
    // moderne Implementierung: weder public noch private
    // sprechender Name, was der Test testen soll (alle Module zurückgeben)
    // Exception für den Fall Anfrage mit MockMvc schlägt unerwartet fehl
    // (JUnit fängt Exception auf und markiert Test rot)
    void shouldReturnModules() throws Exception {
        // Test-Client mockMvc simuliert HTTP-GET-Request an die URL "/api/module"
        mockMvc.perform(get("/api/module"))
                .andExpect(status().isOk());
                // Test soll HTTP-Status 200 (OK) erwarten, dh. Test schlägt fehl, wenn Controller Fehler (zb. 404) wirft
    }

}
