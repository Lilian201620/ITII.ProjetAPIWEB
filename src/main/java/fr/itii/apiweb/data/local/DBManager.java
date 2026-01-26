package fr.itii.apiweb.data.local;

import fr.itii.apiweb.domain.models.Commune;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
    public DBManager getInstance(String url, String user, String pass, String dbName, String port) {
        if (_instance == null) {
            _instance = new DBManager(url, user, pass, port, dbName);
        }
        return _instance;
    }

    private Connection connect() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(_url, _username, _password);
        } catch (SQLException e) {
            System.out.println("Impossible de se connecter à la DB : " + e.getMessage());
        }
        return con;
    };
    //
    public boolean save(List<Commune> communes) {
        return false; // Pour l'instant
    }

    public List<Commune> getAll() {
        return null; // Pour l'instant
    }

    public List<Commune> getByName() {
        return null;
    }

    public List<Commune> getByInsee() {
        return null;
    }
}