package junit.data.local;

import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.domain.models.enums.DBTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class DBManagerTest {
    private final DBManager db = DBManager.getInstance();

    @Test
    void getterHandledErrorsTest(){
        Assertions.assertDoesNotThrow(() -> {
            db.getCommunes();
            db.getEtablissements();
            db.getJoin(DBTable.Commune.CODE_COMMUNE, "", false);
        });
    }

    @Test
    void saversHandledErrorsTest(){
        Assertions.assertDoesNotThrow(() -> {
            db.saveCommunes(new ArrayList<>());
            db.saveEtablissements(new ArrayList<>());
        });
    }

    @Test
    void deletersHandledErrorsTest(){
        Assertions.assertDoesNotThrow(() -> {
            db.deleteCommunes();
            db.deleteEtablissements();
        });
    }
}
