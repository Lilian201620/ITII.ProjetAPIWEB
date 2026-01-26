package fr.itii.apiweb.data.local;

import fr.itii.apiweb.domain.models.Commune;

import javax.swing.plaf.nimbus.State;
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
        _url = "jdbc:postgresql://" + url + ":" + port + "/" + dbName;
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
            _instance = new DBManager("localhost", "apiwebtoolUser", "RVomy#$@CE76#t!yNkPr", "5432", "ApiWebToolDB");
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
        return DriverManager.getConnection(_url, _username, _password);
    }

    private void disconnect(Connection _con) throws SQLException {
        _con.close();
    }

    private void createTable() throws SQLException {
        Connection _con = _instance.connect();
        String request = "CREATE TABLE IF NOT EXISTS Communes (" +
                            "Id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                            "Nom VARCHAR(256)," +
                            "CodeCommune VARCHAR(5)," +
                            "CodeDepartement VARCHAR(256)," +
                            "CodePostal VARCHAR(16)," +
                            "CodeRegion VARCHAR(2)," +
                            "population BIGINT);";
        Statement _stmt = _con.createStatement();
        _stmt.executeUpdate(request);
        this.disconnect(_con);
    }

    @Override
    public void save(List<Commune> communes) throws SQLException {
        Connection _con = _instance.connect();
        Statement _stmt = _con.createStatement();
        Iterator<Commune> _iterator = communes.iterator();
        while(_iterator.hasNext()) {
            Commune _commune =  _iterator.next();
            System.out.println(_commune.toString());
            String request = "INSERT INTO Communes(Nom, CodeCommune, CodeDepartement, CodePostal, CodeRegion, population) VALUES ('" +
                    _commune.getNom().replace("'", "''") + "', '" +
                    _commune.getCodeCommune().replace("'", "''") + "', '" +
                    _commune.getCodeDepartement().replace("'", "''") + "', '" +
                    _commune.getCodePostal().replace("'", "''") + "', '" +
                    _commune.getCodeRegion().replace("'", "''") + "', " +
                    _commune.getPopulation().toString() + ");";
            _stmt.executeUpdate(request);
        }
        this.disconnect(_con);
    }

    @Override
    public List<Commune> getAll() throws SQLException {

        String request = "SELECT * " +
                            "FROM communes;";

        Connection _con = _instance.connect();
        Statement _stmt = _con.createStatement();
        ResultSet results = _stmt.executeQuery(request);
        this.disconnect(_con);
        return this.getListFromRs(results);
    }

    @Override
    public List<Commune> getByName(String Name, boolean OnlyExplicitCaracters) throws SQLException{
        String request = "SELECT * FROM Communes WHERE nom ILIKE ?";
        Connection _con = _instance.connect();
        PreparedStatement _stmt =  _con.prepareStatement(request);
        if (OnlyExplicitCaracters) {
            _stmt.setString(1, Name);
        } else {
            _stmt.setString(1, "%" + Name + "%");
        }
        ResultSet results = _stmt.executeQuery();
        this.disconnect(_con);
        return this.getListFromRs(results);
    }

    @Override
    public List<Commune> getByCodeCommune(String CodeCommune, boolean OnlyExplicitCaracters) throws SQLException {
        String request = "SELECT * FROM Communes WHERE codecommune ILIKE ?";
        Connection _con = _instance.connect();
        PreparedStatement _stmt = _con.prepareStatement(request);
        if (OnlyExplicitCaracters) {
            _stmt.setString(1, CodeCommune);
        } else {
            _stmt.setString(1, "%" + CodeCommune + "%");
        }
        ResultSet results = _stmt.executeQuery();
        this.disconnect(_con);
        return this.getListFromRs(results);
    }

    @Override
    public void deleteByName(String Name) throws SQLException{
        Connection _con = _instance.connect();
        Statement _stmt = _con.createStatement();
        String request = "DELETE FROM Communes WHERE Nom='" + Name.replace("'", "''") + "';";
        _stmt.executeUpdate(request);
    }

    @Override
    public void deleteByCodeCommune(String CodeCommune) throws SQLException{
        Connection _con = _instance.connect();
        Statement _stmt = _con.createStatement();
        String request = "DELETE FROM Communes WHERE CodeCommune='" + CodeCommune.replace("'", "''") + "';";
        _stmt.executeUpdate(request);
    }

    private List<Commune> getListFromRs(ResultSet results) throws SQLException {
        List<Commune> _communes = new ArrayList<Commune>();
        while(results.next()) {
            Commune.Builder _tempComBuilder = new Commune.Builder();
            _tempComBuilder.setNom(results.getString(2));
            _tempComBuilder.setCodeCommune(results.getString(3));
            _tempComBuilder.setCodeDepartement(results.getString(4));
            _tempComBuilder.setCodePostal(results.getString(5));
            _tempComBuilder.setCodeRegion(results.getString(6));
            _tempComBuilder.setPopulation(results.getInt(7));
            Commune _tempCom = _tempComBuilder.build();
            _communes.add(_tempCom);
        }
        return _communes;
    }

}