package domain.models.db_models;

import fr.itii.apiweb.domain.models.enumlist.db.DBCommune;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class CommunesColTest {
    @Test
    void CommunesColIdentifiersTest()
    {
        assertInstanceOf(DBCommune.class, DBCommune.ID);
        assertInstanceOf(DBCommune.class, DBCommune.NOM);
        assertInstanceOf(DBCommune.class, DBCommune.CODE_DEPARTEMENT);
        assertInstanceOf(DBCommune.class, DBCommune.CODE_POSTAL);
        assertInstanceOf(DBCommune.class, DBCommune.CODE_REGION);
        assertInstanceOf(DBCommune.class, DBCommune.POPULATION);
    }

    @Test
    void CommunesColSqlNameTest()
    {
        assertEquals("id", DBCommune.ID.toString());
        assertEquals("nom", DBCommune.NOM.toString());
        assertEquals("codecommune", DBCommune.CODE_COMMUNE.toString());
        assertEquals("codedepartement", DBCommune.CODE_DEPARTEMENT.toString());
        assertEquals("codepostal", DBCommune.CODE_POSTAL.toString());
        assertEquals("coderegion", DBCommune.CODE_REGION.toString());
        assertEquals("population", DBCommune.POPULATION.toString());
    }
}
