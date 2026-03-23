package junit.domain.tools;

import fr.itii.apiweb.domain.models.objets.Commune;
import fr.itii.apiweb.domain.tools.Backend;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BackendTest {

    Backend backend = new Backend();

    @Test
    public void backendSearchCommuneTest() {
        List<Commune> communes = backend.searchCommuneFromAPIByNom("Beauvais");
        assertNotNull(communes);
    }

    @Test
    public void backendSearchCommuneCodePostalTest() {
        List<Commune> communes = backend.searchCommuneFromAPIByCodePostal("60000");
        assertNotNull(communes);
    }

    @Test
    public void backendSearchCommuneCodeDepartementTest() {
        List<Commune> communes = backend.searchCommuneFromAPIByDepartement("60");
        assertNotNull(communes);
    }
}
