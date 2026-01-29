package domain.models.db_models;

import fr.itii.apiweb.domain.models.enumlist.db.DBTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class TablesTest {
    @Test
    public void TablesIdentifiersTest()
    {
        assertInstanceOf(DBTable.class, DBTable.COMMUNES);
        assertInstanceOf(DBTable.class, DBTable.ETABLISSEMENTS);
    }

    @Test
    public void TablesSqlNameTest()
    {
        assertEquals("communes", DBTable.COMMUNES.toString());
        assertEquals("etablissements", DBTable.ETABLISSEMENTS.toString());
    }
}
