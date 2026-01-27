package data.local;
import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.domain.models.Commune;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DBManagerTest {

    @Test
    public void deleteAllEntriesTest() throws SQLException {
        DBManager _db = DBManager.getInstance();
        List<Commune> _emptyList = new ArrayList<>();
        _db.deleteAll();
        assertEquals(_emptyList, _db.getAll());
    }

    @Test
    public void saveCityTest() throws SQLException {
        DBManager _db = DBManager.getInstance();
        _db.deleteAll();
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
        assertEquals(_communes.size(), _db.getAll().size());
    }

    @Test
    public void getCityExplicitTest() throws SQLException {
        DBManager _db = DBManager.getInstance();
        _db.deleteAll();
        Commune.Builder c = new Commune.Builder();
        c.setNom("Paris");
        c.setCodeCommune("001");
        c.setPopulation(55000);
        c.setCodeDepartement("60");
        c.setCodeRegion("66");
        c.setCodePostal("60250");
        Commune _commune = c.build();
        List<Commune> _communes = new ArrayList<Commune>();
        _communes.add(_commune);
        _db.save(_communes);
        List<Commune> _dbResult = _db.get("nom", "Paris", true);
        assertEquals(_communes.getFirst().getNom(), _dbResult.getFirst().getNom());
        assertEquals(_communes.getFirst().getCodeCommune(), _dbResult.getFirst().getCodeCommune());
        assertEquals(_communes.getFirst().getPopulation(), _dbResult.getFirst().getPopulation());
        assertEquals(_communes.getFirst().getCodeDepartement(), _dbResult.getFirst().getCodeDepartement());
        assertEquals(_communes.getFirst().getCodeRegion(), _dbResult.getFirst().getCodeRegion());
        assertEquals(_communes.getFirst().getCodePostal(), _dbResult.getFirst().getCodePostal());
    }

}
