package data.local;


import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.domain.models.enumlist.db.DBCommune;
import fr.itii.apiweb.domain.models.objet.Commune;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class DBManagerTest {
    private final DBManager _db = DBManager.getInstance();

    @Test
    void getterHandledErrorsTest(){
        Assertions.assertDoesNotThrow(() -> {
            _db.getCommunes();
            _db.getEtablissements();
            _db.getJoin(DBCommune.CODE_COMMUNE, "", false);
        });
    }

    @Test
    void saversHandledErrorsTest(){
        Assertions.assertDoesNotThrow(() -> {
            _db.saveCommunes(new ArrayList<>());
            _db.saveEtablissements(new ArrayList<>());
        });
    }

    @Test
    void deletersHandledErrorsTest(){
        Assertions.assertDoesNotThrow(() -> {
            _db.deleteCommunes();
            _db.deleteEtablissements();
        });
    }
}
