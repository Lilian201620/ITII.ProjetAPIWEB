package junit.domain.models.objets;

import fr.itii.apiweb.domain.models.enums.DBTable;
import fr.itii.apiweb.domain.models.objets.Commune;
import fr.itii.apiweb.domain.models.objets.Etablissement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EtablissementTest {
    @Test
    public void createEtablissementTest() {
        Etablissement e = new Etablissement();
        e.setCodeCommune("00000");
        e.setId(1);
        e.setMail("helloworld@test.fr");
        e.setTypeEtablissement("College");
        e.setNomEtablissement("Hello world");
        e.setNomCommune("Yo");
        e.setStatutPublicPrive("prive");
        assertEquals("00000", e.getCodeCommune());
        assertEquals(1, e.getId());
        assertEquals("helloworld@test.fr", e.getMail());
        assertEquals("College", e.getTypeEtablissement());
        assertEquals("Hello world", e.getNomEtablissement());
        assertEquals("Yo", e.getNomCommune());
        assertEquals("prive", e.getStatutPublicPrive());
    }
}
