package junit.domain.models.objets;

import fr.itii.apiweb.domain.models.objets.Commune;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommuneTest {
    @Test
    public void creationCommuneTest() {
        Commune c = new Commune.Builder()
                .setNom("Beauvais")
                .setCodeCommune("60125")
                .setDepartement("60", "Oise")
                .setCodePostal("60000")
                .setPopulation(30000)
                .build();

        assertEquals("Beauvais", c.getNom());
        assertEquals("0", c.getCodeCommune());
        assertEquals("60", c.getCodeDepartement());
        assertEquals("Oise", c.getNomDepartement());
        assertEquals("60000", c.getCodePostal());
        assertEquals(55000, c.getPopulation());
    }
}
