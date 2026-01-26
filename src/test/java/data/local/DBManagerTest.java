package data.local;
import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.domain.models.Commune;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class DBManagerTest {
    @Test
    public void initDBManager() throws SQLException {
        DBManager _db = DBManager.getInstance();
        _db.getAll();
    }
}
