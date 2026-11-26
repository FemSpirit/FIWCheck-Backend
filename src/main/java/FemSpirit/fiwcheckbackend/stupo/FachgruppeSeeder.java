package FemSpirit.fiwcheckbackend.stupo;

import FemSpirit.fiwcheckbackend.stupo.entity.Fachgruppe;
import FemSpirit.fiwcheckbackend.stupo.repository.FachgruppeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FachgruppeSeeder implements CommandLineRunner {

    private final FachgruppeRepository fachgruppeRepository;

    public FachgruppeSeeder(FachgruppeRepository fachgruppeRepository) {
        this.fachgruppeRepository = fachgruppeRepository;
    }

    @Override
    public void run(String... args) {
        List<String> defaultFachgruppen = List.of(
                "Informatik",
                "Wirtschaft",
                "Praxis",
                "Fachübergreifend"
        );

        for (String bezeichnung : defaultFachgruppen) {
            if (fachgruppeRepository.findByFachgruppeBezeichnung(bezeichnung).isEmpty())
            {
                Fachgruppe fachgruppe = new Fachgruppe();
                fachgruppe.setFachgruppeBezeichnung(bezeichnung);
                fachgruppeRepository.save(fachgruppe);
            }
        }
    }
}
