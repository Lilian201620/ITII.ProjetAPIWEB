package domain.models;

import fr.itii.apiweb.domain.models.Commune;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommuneTest {
    @Test
    public void creationCommuneTest() {
        Commune.Builder c = new Commune.Builder();
        c.setNom("Paris");
        c.setCodeCommune("001");
        c.setPopulation(55000);
        c.setCodeDepartement("60");
        c.setCodeEpci("0102030405");
        Commune _commune = c.build();
        assertEquals("Paris", _commune.getNom());
        assertEquals("001", _commune.getCodeCommune());
        assertEquals(55000, _commune.getPopulation());
        assertEquals("60", _commune.getCodeDepartement());
        assertEquals("0102030405", _commune.getCodeEpci());
    }
}
