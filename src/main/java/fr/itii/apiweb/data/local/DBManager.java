package fr.itii.apiweb.data.local;

import fr.itii.apiweb.domain.models.Commune;
import fr.itii.apiweb.domain.tools.ExceptionsHandler;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Cette classe gère la connexion à une BDD PostGreSQL. De plus, il s'agit d'un singleton.
 * @author Nathan
 * @version 1.0.0
 *
 */
public class DBManager implements DataRepository {
    private final String _url;
    private final String _username;
    private final String _password;

    private static DBManager _instance;

    /**
     * @param url URL de la base PostgreSQL (sans formatage JDBC)
     * @param user Nom d'utilisateur de la BDD.
     * @param pass Mot de passe de la BDD.
     * @param port Port de la BDD.
     * @param dbName Nom de la BDD à utiliser.
     */
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

    /**
     * Permet d'initialiser ou récupérer l'instance de DBManager.
     * @return Instance de DBManager (singleton).
     */
    public static DBManager getInstance() {
        if (_instance == null) {
            _instance = new DBManager("localhost", "apiwebtoolUser", "RVomy#$@CE76#t!yNkPr", "5432", "ApiWebToolDB");
            _instance.createTable();
        }
        return _instance;
    }

    /**
     * Privé. Permet de se connecter à la base SQL et de renvoyer une connexion.
     * @return Connection à la base SQL.
     */
    private Connection connect() {
        try {
            return DriverManager.getConnection(_url, _username, _password);
        } catch (SQLException e) {
            ExceptionsHandler.handleException(e);
            return null;
        }
    }

    /**
     * Privé. Termine la connexion passée en paramètre.
     * @param _con Connexion à fermer.
     */
    private void disconnect(Connection _con) {
        try {
            _con.close();
        }
        catch (SQLException e) {
            ExceptionsHandler.handleException(e);
        }
    }

    /**
     * Privé. Crée la table si elle n'existe pas.
     */
    private void createTable() {
        Connection _con = _instance.connect();
        String request = "CREATE TABLE IF NOT EXISTS Communes (" +
                            "Id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                            "Nom VARCHAR(256)," +
                            "CodeCommune VARCHAR(5)," +
                            "CodeDepartement VARCHAR(256)," +
                            "CodePostal VARCHAR(16)," +
                            "CodeRegion VARCHAR(2)," +
                            "population BIGINT);";
        try {
            Statement _stmt = _con.createStatement();
            _stmt.executeUpdate(request);
            this.disconnect(_con);
        } catch (SQLException e) {
            ExceptionsHandler.handleException(e);
        }

    }

    /**
     * Public. Permet de sauvegarder une liste de communes dans la BDD.
     * @param communes Liste de communes à sauvegarder.
     */
    @Override
    public void save(List<Commune> communes) {
        Connection _con = _instance.connect();
        try {
            Statement _stmt = _con.createStatement();
            Iterator<Commune> _iterator = communes.iterator();
            while (_iterator.hasNext()) {
                Commune _commune = _iterator.next();
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
        } catch (SQLException e) {
            ExceptionsHandler.handleException(e);
        }
    }

    /**
     * Public. Permet de récupérer toutes les communes de la base SQL.
     * @return Liste de communes dans la base.
     */
    @Override
    public List<Commune> getAll() {

        String request = "SELECT * " +
                            "FROM communes;";

        Connection _con = _instance.connect();
        try {
            Statement _stmt = _con.createStatement();
            ResultSet results = _stmt.executeQuery(request);
            this.disconnect(_con);
            return this.getListFromRs(results);
        } catch (SQLException e) {
            ExceptionsHandler.handleException(e);
            return new ArrayList<>();
        }
    }

    /**
     *
     * @param Name Nom de commune à rechercher.
     * @param OnlyExplicitCaracters S'il faut ajouter des jockers autour de l'entrée.
     * @return Liste des communes correspondents aux critères.
     * @throws SQLException Erreur lors de la requête SQL.
     */
    @Override
    public List<Commune> getByName(String Name, boolean OnlyExplicitCaracters) {
        String request = "SELECT * FROM Communes WHERE nom ILIKE ?";
        Connection _con = _instance.connect();
        try {
            PreparedStatement _stmt = _con.prepareStatement(request);
            if (OnlyExplicitCaracters) {
                _stmt.setString(1, Name);
            } else {
                _stmt.setString(1, "%" + Name + "%");
            }
            ResultSet results = _stmt.executeQuery();
            this.disconnect(_con);
            return this.getListFromRs(results);
        } catch (SQLException e) {
            ExceptionsHandler.handleException(e);
            return new ArrayList<>();
        }
    }

    /**
     *
     * @param CodeCommune Code de la commune à rechercher.
     * @param OnlyExplicitCaracters S'il faut ajouter des jockers autour de l'entrée.
     * @return Liste des communes correspondents aux critères.
     */
    @Override
    public List<Commune> getByCodeCommune(String CodeCommune, boolean OnlyExplicitCaracters) {
        String request = "SELECT * FROM Communes WHERE codecommune ILIKE ?";
        Connection _con = _instance.connect();
        try {
            PreparedStatement _stmt = _con.prepareStatement(request);
            if (OnlyExplicitCaracters) {
                _stmt.setString(1, CodeCommune);
            } else {
                _stmt.setString(1, "%" + CodeCommune + "%");
            }
            ResultSet results = _stmt.executeQuery();
            this.disconnect(_con);
            return this.getListFromRs(results);
        } catch (SQLException e) {
            ExceptionsHandler.handleException(e);
            return new ArrayList<>();
        }
    }

    /**
     *
     * @param Name Nom de commune à supprimer dans la DB.
     */
    @Override
    public void deleteByName(String Name) {
        Connection _con = _instance.connect();
        try {
            Statement _stmt = _con.createStatement();
            String request = "DELETE FROM Communes WHERE Nom='" + Name.replace("'", "''") + "';";
            _stmt.executeUpdate(request);
        } catch (SQLException e) {
            ExceptionsHandler.handleException(e);
        }
    }

    /**
     *
     * @param CodeCommune Code de la commune à supprimer dans la DB.
     */
    @Override
    public void deleteByCodeCommune(String CodeCommune) {
        Connection _con = _instance.connect();
        try {
            Statement _stmt = _con.createStatement();
            String request = "DELETE FROM Communes WHERE CodeCommune='" + CodeCommune.replace("'", "''") + "';";
            _stmt.executeUpdate(request);
        } catch (SQLException e) {
            ExceptionsHandler.handleException(e);
        }
    }

    /**
     * Suppprime toute la BDD
     */
    public void deleteAll() {
        Connection _con = _instance.connect();
        try {
            Statement _stmt = _con.createStatement();
            String request = "DELETE FROM Communes;";
            _stmt.executeUpdate(request);
        } catch (SQLException e) {
            ExceptionsHandler.handleException(e);
        }
    }

    /**
     *
     * @param results ResultSet de la DB.
     * @return Liste des communes dans le RS.
     * @throws SQLException Erreur lors de la requête SQL.
     */
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