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
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(_url, _username, _password);
    }

    /**
     * Privé. Termine la connexion passée en paramètre.
     * @param _con Connexion à fermer.
     */
    private void disconnect(Connection _con) throws SQLException {
        _con.close();
    }

    /**
     * Privé. Crée la table si elle n'existe pas.
     */
    private void createTable() {
        try {
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
        } catch (Exception e) {
            ExceptionsHandler.handleException(new SQLException());
        }

    }

    /**
     * Public. Permet de sauvegarder une liste de communes dans la BDD.
     * @param communes Liste de communes à sauvegarder.
     */
    @Override
    public void save(List<Commune> communes) {
        try {
            Connection _con = _instance.connect();
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
            System.out.println("Sauvegarde OK!");
        } catch (Exception e) {
            ExceptionsHandler.handleException(new SQLException());
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
        try {
        Connection _con = _instance.connect();
            Statement _stmt = _con.createStatement();
            ResultSet results = _stmt.executeQuery(request);
            this.disconnect(_con);
            return this.getListFromRs(results);
        } catch (Exception e) {
            ExceptionsHandler.handleException(new SQLException());
            return new ArrayList<>();
        }
    }

    /**
     *
     * @param col Colonne sur laquelle baser sa recherche.
     * @param critere Critere que doit respecter l'élément.
     * @param onlyExplicitCaracters S'il faut ajouter des jockers autour de l'entrée.
     * @return Liste des communes correspondents aux critères.
     */
    @Override
    public List<Commune> get(String col, String critere, boolean onlyExplicitCaracters) {
        String request = "SELECT * FROM Communes WHERE " + col + " ILIKE ?";
        try {
            Connection _con = _instance.connect();
            PreparedStatement _stmt = _con.prepareStatement(request);
            if (onlyExplicitCaracters) {
                _stmt.setString(1, critere);
            } else {
                _stmt.setString(1, "%" + critere + "%");
            }
            ResultSet results = _stmt.executeQuery();
            this.disconnect(_con);
            return this.getListFromRs(results);
        } catch (Exception e) {
            ExceptionsHandler.handleException(new SQLException());
            return new ArrayList<>();
        }
    }

    /**
     *
     * @param col Colonne sur laquelle baser la recherche.
     * @param critere Critère que doit respecter l'élément.
     * @return Liste de communes correspondant à la requête.
     */
    public List<Commune> get(String col, int critere) {
        String request = "SELECT * FROM Communes WHERE " + col + " = ?";
        try {
            Connection _con = _instance.connect();
            PreparedStatement _stmt = _con.prepareStatement(request);
            _stmt.setLong(1, critere);
            ResultSet results = _stmt.executeQuery();
            this.disconnect(_con);
            return this.getListFromRs(results);
        } catch (Exception e) {
            ExceptionsHandler.handleException(new SQLException());
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
        try {
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
        } catch (Exception e) {
            ExceptionsHandler.handleException(new SQLException());
            return new ArrayList<>();
        }
    }

    /**
     *
     * @param Name Nom de commune à supprimer dans la DB.
     */
    @Override
    public void deleteByName(String Name) {
        try {
            Connection _con = _instance.connect();
            Statement _stmt = _con.createStatement();
            String request = "DELETE FROM Communes WHERE Nom='" + Name.replace("'", "''") + "';";
            _stmt.executeUpdate(request);
        } catch (Exception e) {
            ExceptionsHandler.handleException(new SQLException());
        }
    }

    /**
     *
     * @param id ID de la base de données à supprimer
     */
    @Override
    public void deleteById(long id) {
        try {
            Connection _con = _instance.connect();
            Statement _stmt = _con.createStatement();
            String request = "DELETE FROM Communes WHERE id='" + id + "';";
            _stmt.executeUpdate(request);
            this.disconnect(_con);
        } catch (Exception e) {
            ExceptionsHandler.handleException(new SQLException());
        }
    }

    /**
     *
     * @param CodeCommune Code de la commune à supprimer dans la DB.
     */
    @Override
    public void deleteByCodeCommune(String CodeCommune) {
        try {
            Connection _con = _instance.connect();
            Statement _stmt = _con.createStatement();
            String request = "DELETE FROM Communes WHERE CodeCommune='" + CodeCommune.replace("'", "''") + "';";
            _stmt.executeUpdate(request);
        } catch (Exception e) {
            ExceptionsHandler.handleException(new SQLException());
        }
    }

    /**
     * Suppprime toute la BDD
     */
    public void deleteAll() {
        try {
            Connection _con = _instance.connect();
            Statement _stmt = _con.createStatement();
            String request = "DELETE FROM Communes;";
            _stmt.executeUpdate(request);
        } catch (Exception e) {
            ExceptionsHandler.handleException(new SQLException());
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
            _tempComBuilder.setId(results.getInt(1));
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