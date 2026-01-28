package fr.itii.apiweb.data.local;

import fr.itii.apiweb.domain.models.Commune;
import fr.itii.apiweb.domain.models.db_models.Tables;
import fr.itii.apiweb.domain.tools.ExceptionsHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewDBManager {
    private final String _url = "jdbc:postgresql://localhost:5432/ApiWebToolDB";
    private static final String _username = "apiwebtoolUser";
    private static final String _password = "RVomy#$@CE76#t!yNkPr";

    private static NewDBManager _instance;

    /**
     * Constructeur privé pour réalisation d'un singleton
     */
    private NewDBManager() {
        try {
            String _driverName = "org.postgresql.Driver";
            Class.forName(_driverName);
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur, impossible de charger le driver pour : " + _url);
        }
    }

    /**
     * Récupérer l'instance du singleton DBManager
     */
    public static NewDBManager getInstance() {
        if (_instance == null) {
            _instance = new NewDBManager();
            _instance.initTables();
        }
        return _instance;
    }

    // - Méthodes publiques pour l'exécution des requêtes SQL
    // PUBLIC | Used everywhere across the project
    // ----------------------------------------------------------





    // — Méthodes privées pour l'exécution des méthodes publiques
    //  PRIVATE | Only used in NewDBManager
    // ----------------------------------------------------------

    /**
     * Méthode de connexion à la BDD.
     * @return Connexion à la base de données
     */
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(_url,_username,_password);
    }

    /**
     * Cette méthode coupe la connexion mentionnée à la BDD.
     * @param _con Connexion à fermer
     */
    private void disconnect(Connection _con) throws SQLException {
        if (_con != null) {
            _con.close();
        }
    }

    /**
     * Cette méthode instancie les tables SQL nécessaires si elles n'existent pas dans la BDD.
     */
    private void initTables() {
        try {
            Connection _con = _instance.connect();
            if (_con != null) {
                String _reqCommunes = "CREATE TABLE IF NOT EXISTS " + Tables.communes.toString() + "( Id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, Nom VARCHAR(256), CodeCommune VARCHAR(5), CodeDepartement VARCHAR(50), CodePostal VARCHAR(16), CodeRegion VARCHAR(2), population BIGINT);";
                String _reqEtablissements = "CREATE TABLE IF NOT EXISTS " + Tables.etablissements.toString() + "( Id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, Nom VARCHAR(256), Type VARCHAR(256), Mail VARCHAR(256), statut VARCHAR(6), codeCommune VARCHAR(5), nomCommune VARCHAR(256));";
                String _reqCommEtabl = "ALTER TABLE " + Tables.etablissements.toString() + " ADD CONSTRAINT fk_communes FOREIGN KEY (codeCommune) REFERENCES " + Tables.communes.toString() + "(CodeCommune);";
                Statement _stmt = _con.createStatement();
                _stmt.executeUpdate(_reqCommunes);
                _stmt.executeUpdate(_reqEtablissements);
                _stmt.executeUpdate(_reqCommEtabl);
                this.disconnect(_con);
            }
        } catch (Exception e) {
                ExceptionsHandler.handleException(new SQLException());
        }
    }

    /**
     * Méthode générale getter
     */
    private List<Commune> get(Tables Table, Enum col, String critere, boolean onlyExplicit)
    {
        String _req = "SELECT * FROM " + Tables.communes.toString() + " WHERE " + col.toString() + " ILIKE ?";
        List<Commune> _return = new ArrayList<>();
        try {
            Connection _con = _instance.connect();
            if (_con != null) {
                PreparedStatement _stmt = _con.prepareStatement(_req);
                if (onlyExplicit) {
                    _stmt.setString(1, critere);
                } else {
                    _stmt.setString(1, "%" + critere + "%");
                }
                ResultSet results = _stmt.executeQuery();
                this.disconnect(_con);
                _return = this.getListFromRs(results);
            }
        }  catch (Exception e) {
                ExceptionsHandler.handleException(new SQLException());
        }
        return _return;
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
