package fr.itii.apiweb.data.local;

import fr.itii.apiweb.domain.models.Commune;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DBManager implements DataRepository {
    private final String _url;
    private final String _username;
    private final String _password;

    private static DBManager _instance;

    private DBManager(String url, String user, String pass, String port, String dbName) {
        _url = "jdbc:postgresql://" + url + ":" + port + "/";
        _username = user;
        _password = pass;
        try {
            String _driverName = "org.postgresql.Driver";
            Class.forName(_driverName);
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur, impossible de charger le driver pour : " + _url);
        }
    }
    public static DBManager getInstance() {
        if (_instance == null) {
            _instance = new DBManager("localhost", "apiwebtoolUser", "RVomy#$@CE76#t!yNkPr", "ApiWebToolDB", "5432");
            try {
                _instance.createTable();
            } catch (SQLException e) {
                System.out.println("Impossible de créer l'instance : ");
                e.printStackTrace();
            }
        }
        return _instance;
    }

    private Connection connect() throws SQLException {
        Connection con = null;
        con = DriverManager.getConnection(_url, _username, _password);
        return con;
    }
    private void createTable() throws SQLException {
        Connection _con = _instance.connect();
        String request = "CREATE TABLE IF NOT EXISTS Communes (" +
                            "Id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                            "Nom VARCHAR(256)," +
                            "CodeCommune VARCHAR(5)," +
                            "CodeDepartement VARCHAR(256)," +
                            "CodeRegion VARCHAR(2)," +
                            "population BIGINT);";
        Statement _stmt = _con.createStatement();
        _stmt.executeUpdate(request);
    }

    @Override
    public void save(List<Commune> communes) throws SQLException {
        Connection _con = _instance.connect();
        Statement _stmt = _con.createStatement();
        Iterator<Commune> _iterator = communes.iterator();
        while(_iterator.hasNext()) {
            String request = "INSERT INTO Communes(Nom, CodeCommune, CodeDepartement, CodeRegion, population) VALUES (" +
                    _iterator.next().getNom() + ", " +
                    _iterator.next().getCodeCommune() + ", " +
                    _iterator.next().getCodeDepartement() + ", " +
                    _iterator.next().getCodeRegion() + ", " +
                    _iterator.next().getPopulation() + ");";
            _stmt.executeUpdate(request);
        }
    }

    @Override
    public List<Commune> getAll() throws SQLException {
        List<Commune> _communes = new ArrayList<Commune>();
        String request = "SELECT * " +
                            "FROM Communes;";

        Connection _con = _instance.connect();
        Statement _stmt = _con.createStatement();
        ResultSet results = _stmt.executeQuery(request);
        while(results.next()) {
            Commune.Builder _tempComBuilder = new Commune.Builder();
            _tempComBuilder.setNom(results.getString(1));
            _tempComBuilder.setCodeCommune(results.getString(2));
            _tempComBuilder.setCodeDepartement(results.getString(3));
            _tempComBuilder.setCodeRegion(results.getString(4));
            _tempComBuilder.setPopulation(results.getInt(5));
            Commune _tempCom = _tempComBuilder.build();
            _communes.add(_tempCom);
        }
        return _communes;
    }

    @Override
    public List<Commune> getByName() {
        return null;
    }

    @Override
    public List<Commune> getByCodeCommune() {
        return null;
    }
}