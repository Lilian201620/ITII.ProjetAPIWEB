package data.local;
import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.domain.models.Commune;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBManagerTest {
    @Test
    public void initDBManager() throws SQLException {
        DBManager _db = DBManager.getInstance();
        System.out.println(_db.getAll().toString());
    }

    @Test
    public void saveCity() throws SQLException {
        DBManager _db = DBManager.getInstance();
        Commune.Builder c = new Commune.Builder();
        c.setNom("Paris");
        c.setCodeCommune("001");
        c.setPopulation(55000);
        c.setCodeDepartement("60");
        c.setCodeRegion("66");
        c.setCodePostal("60250");
        c.setCodeEpci("0102030405");
        Commune _commune = c.build();
        Commune.Builder c2 = new Commune.Builder();
        c2.setNom("Parisot");
        c2.setCodeCommune("05165");
        c2.setPopulation(55000);
        c2.setCodeDepartement("60");
        c2.setCodeRegion("66");
        c2.setCodePostal("60250");
        c2.setCodeEpci("0102030405");
        Commune _commune2 = c2.build();
        List<Commune> _communes = new ArrayList<Commune>();
        _communes.add(_commune);
        _communes.add(_commune2);
        _db.save(_communes);
    }

    @Test
    public void getCity() throws SQLException {
        DBManager _db = DBManager.getInstance();
        List<Commune> _communes = _db.getByName("Paris");
        for (Commune _commune : _communes) {
            System.out.println(_commune.toString());
        }
    }

    @Test
    public void getAll() throws SQLException {
        DBManager _db = DBManager.getInstance();
        List<Commune> _communes = _db.getAll();
        for (Commune _commune : _communes) {
            System.out.println(_commune.toString());
        }
    }

    @Test
    public void deleteCity() throws SQLException {
        DBManager _db = DBManager.getInstance();
        _db.deleteByName("Paris");
        List<Commune> _communes = _db.getAll();
        for (Commune _commune : _communes) {
            System.out.println(_commune.toString());
        }
    }
}
