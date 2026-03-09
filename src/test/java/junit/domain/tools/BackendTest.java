package junit.domain.tools;

import fr.itii.apiweb.domain.models.objets.Commune;
import fr.itii.apiweb.domain.tools.Backend;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BackendTest {

    Backend backend = new Backend();

    @Test
    public void backendSearchCommuneTest() {
        List<Commune> communes = backend.searchCommuneFromAPIByNom("Beauvais");
        assertEquals(communes.getFirst().getNom(),"Beauvais");
    }

    @Test
    public void backendSearchCommuneCodePostalTest() {
        List<Commune> communes = backend.searchCommuneFromAPIByCodePostal("60000");
        assertEquals(communes.getFirst().getCodeCommune(),"60000");
    }

    @Test
    public void backendSearchCommuneCodeDepartementTest() {
        List<Commune> communes = backend.searchCommuneFromAPIByCodePostal("60");
        assertEquals(communes.getFirst().getCodeDepartement(),"60");
    }
}
