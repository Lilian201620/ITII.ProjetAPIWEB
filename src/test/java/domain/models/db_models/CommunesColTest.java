package domain.models.db_models;

import fr.itii.apiweb.domain.models.db_models.CommunesCol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class CommunesColTest {
    @Test
    void CommunesColIdentifiersTest()
    {
        assertInstanceOf(CommunesCol.class, CommunesCol.ID);
        assertInstanceOf(CommunesCol.class, CommunesCol.NOM);
        assertInstanceOf(CommunesCol.class, CommunesCol.CODE_DEPARTEMENT);
        assertInstanceOf(CommunesCol.class, CommunesCol.CODE_POSTAL);
        assertInstanceOf(CommunesCol.class, CommunesCol.CODE_REGION);
        assertInstanceOf(CommunesCol.class, CommunesCol.POPULATION);
    }

    @Test
    void CommunesColSqlNameTest()
    {
        assertEquals("id", CommunesCol.ID.toString());
        assertEquals("nom", CommunesCol.NOM.toString());
        assertEquals("codecommune", CommunesCol.CODE_COMMUNE.toString());
        assertEquals("codedepartement", CommunesCol.CODE_DEPARTEMENT.toString());
        assertEquals("codepostal", CommunesCol.CODE_POSTAL.toString());
        assertEquals("coderegion", CommunesCol.CODE_REGION.toString());
        assertEquals("population", CommunesCol.POPULATION.toString());
    }
}
