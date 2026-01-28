package domain.models.db_models;

import fr.itii.apiweb.domain.models.db_models.Tables;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class TablesTest {
    @Test
    public void TablesIdentifiersTest()
    {
        assertInstanceOf(Tables.class, Tables.COMMUNES);
        assertInstanceOf(Tables.class, Tables.ETABLISSEMENTS);
    }

    @Test
    public void TablesSqlNameTest()
    {
        assertEquals("communes", Tables.COMMUNES.toString());
        assertEquals("etablissements", Tables.ETABLISSEMENTS.toString());
    }
}
