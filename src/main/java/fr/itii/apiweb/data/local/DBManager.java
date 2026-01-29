package fr.itii.apiweb.data.local;

import fr.itii.apiweb.domain.models.objet.Commune;
import fr.itii.apiweb.domain.models.objet.Etablissement;
import fr.itii.apiweb.domain.models.enumlist.db.DBCommune;
import fr.itii.apiweb.domain.models.enumlist.db.DBEtablissement;
import fr.itii.apiweb.domain.models.enumlist.db.DBTable;
import fr.itii.apiweb.domain.models.enumlist.font.Font;
import fr.itii.apiweb.domain.tools.ExceptionHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private final String _url = "jdbc:postgresql://localhost:5432/ApiWebToolDB";
    private static final String _username = "apiwebtoolUser";
    private static final String _password = "RVomy#$@CE76#t!yNkPr";

    private static DBManager _instance;
    private static boolean _isInit = false;

    /**
     * Constructeur privé pour réalisation d'un singleton
     */
    private DBManager() {
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
    public static DBManager getInstance() {
        if (_instance == null) {
            _instance = new DBManager();
            _instance.initTables();
        }
        return _instance;
    }

    // - Méthodes publiques pour l'exécution des requêtes SQL
    // PUBLIC | Used everywhere across the project
    // ----------------------------------------------------------
    public List<Commune> getCommunes(DBCommune col, String critere, boolean explicitCaractersOnly) {
        return getString(DBTable.COMMUNES, col, critere, explicitCaractersOnly);
    }
    public List<Commune> getCommunes(DBCommune col, int critere) {
        return getInt(DBTable.COMMUNES, col, critere);
    }
    public List<Commune> getCommunes() {
        return getString(DBTable.COMMUNES, DBCommune.ID, "", false);
    }
    public List<Etablissement> getEtablissements(DBEtablissement col, String critere, boolean explicitCaractersOnly) {
        return getString(DBTable.ETABLISSEMENTS, col, critere, explicitCaractersOnly);
    }
    public List<Etablissement> getEtablissements(DBEtablissement col, int critere) {
        return getInt(DBTable.ETABLISSEMENTS, col, critere);
    }
    public List<Etablissement> getEtablissements() {
        return getString(DBTable.ETABLISSEMENTS, DBEtablissement.ID, "", false);
    }


    public void deleteCommunes(DBCommune col, String critere, boolean explicitCaractersOnly) {
        deleteString(DBTable.COMMUNES, col, critere, explicitCaractersOnly);
    }
    public void deleteCommunes(DBCommune col, int critere) {
        deleteInt(DBTable.COMMUNES, col, critere);
    }
    public void deleteCommunes() {
        deleteString(DBTable.COMMUNES, DBCommune.ID, "", false);
    }
    public void deleteEtablissements(DBEtablissement col, String critere, boolean explicitCaractersOnly) {
        deleteString(DBTable.ETABLISSEMENTS, col, critere, explicitCaractersOnly);
    }
    public void deleteEtablissements(DBEtablissement col, int critere) {
        deleteInt(DBTable.ETABLISSEMENTS, col, critere);
    }
    public void deleteEtablissements() {
        deleteString(DBTable.ETABLISSEMENTS, DBEtablissement.ID, "", false);
    }

    public void saveCommunes(List<Commune> communes) {
        try {
            Connection _con = _instance.connect();
            PreparedStatement _statement;
            for (Commune _com : communes) {
                if(getCommunes(DBCommune.CODE_COMMUNE, _com.getCodeCommune(), true).isEmpty()) {
                    String _req = "INSERT INTO " + DBTable.COMMUNES.toString() + String.format("(%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?)", DBCommune.NOM.toString(), DBCommune.CODE_COMMUNE.toString(), DBCommune.CODE_DEPARTEMENT.toString(), DBCommune.CODE_POSTAL.toString(), DBCommune.CODE_REGION.toString(), DBCommune.POPULATION.toString());
                    _statement = _con.prepareStatement(_req);
                    _statement.setString(1, _com.getNom());
                    _statement.setString(2, _com.getCodeCommune());
                    _statement.setString(3, _com.getCodeDepartement());
                    _statement.setString(4, _com.getCodePostal());
                    _statement.setString(5, _com.getCodeRegion());
                    _statement.setInt(6, _com.getPopulation());
                    _statement.execute();
                } else {
                    System.out.println(Font.ITALIC.toString() + "" +  Font.GREY.toString() + "Entrée " + _com.getNom() + " déjà présente dans la BDD." + Font.RESET.toString());
                }

            }
            _con.close();
        } catch (Exception e) {
            ExceptionHandler.handleException(new SQLException());
        }
    }
    public void saveEtablissements(List<Etablissement> Etablissements) {
        try {
            Connection _con = _instance.connect();
            PreparedStatement _stmt;
            for (Etablissement _etbl : Etablissements) {
                String _req = "INSERT INTO " + DBTable.ETABLISSEMENTS.toString() + String.format("(%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?)", DBEtablissement.NOM.toString(), DBEtablissement.TYPE.toString(), DBEtablissement.MAIL.toString(), DBEtablissement.STATUT.toString(), DBEtablissement.CODE_COMMUNE.toString(), DBEtablissement.NOM_COMMUNE.toString());
                _stmt = _con.prepareStatement(_req);
                _stmt.setString(1, _etbl.getNomEtablissement());
                _stmt.setString(2, _etbl.getTypeEtablissement());
                _stmt.setString(3, _etbl.getMail());
                _stmt.setString(4, _etbl.getStatutPublicPrive());
                _stmt.setString(5, _etbl.getCodeCommune());
                _stmt.setString(6, _etbl.getNomCommune());
                _stmt.execute();
            }
            this.disconnect(_con);

        } catch (Exception e) {
            ExceptionHandler.handleException(new SQLException());
        }
    }

    public List<Etablissement> getJoin(DBCommune col, String critere, boolean onlyExplicit)
    {
        List<Etablissement> _return = new ArrayList<>();
        String _req = "SELECT * FROM " + DBTable.ETABLISSEMENTS.toString() + " a INNER JOIN " + DBTable.COMMUNES.toString() + " b ON a.codecommune = b.codecommune WHERE b." + col.toString() + " = ?";
        try {
            Connection _con = _instance.connect();
            if (_con != null) {
                PreparedStatement _stmt = _con.prepareStatement(_req);
                if(onlyExplicit) {
                    _stmt.setString(1, critere);
                } else {
                    _stmt.setString(1, "%" + critere + "%");
                }
                ResultSet results = _stmt.executeQuery();
                this.disconnect(_con);
                _return = this.getEtablissementListFromRs(results);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(new SQLException());
        }
        return _return;
    }

    // — Méthodes privées pour l'exécution des méthodes publiques
    //  PRIVATE | Only used in DBManager
    // ----------------------------------------------------------

    /**
     * Méthode de connexion à la BDD.
     * @return Connexion à la base de données
     */
    private Connection connect() throws SQLException {
        if (!_isInit) {
            this.initTables();
        }
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
                String _reqCommunes = "CREATE TABLE IF NOT EXISTS " + DBTable.COMMUNES.toString() + "( Id BIGINT GENERATED ALWAYS AS IDENTITY, Nom VARCHAR(256), CodeCommune VARCHAR(5) PRIMARY KEY, CodeDepartement VARCHAR(50), CodePostal VARCHAR(16), CodeRegion VARCHAR(2), population BIGINT);";
                String _reqEtablissements = "CREATE TABLE IF NOT EXISTS " + DBTable.ETABLISSEMENTS.toString() + "( Id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, Nom VARCHAR(256), Type VARCHAR(256), Mail VARCHAR(256), statut VARCHAR(6), codeCommune VARCHAR(5), nomCommune VARCHAR(256));";
                String _reqCommEtabl = "DO $$ BEGIN IF NOT EXISTS ( SELECT 1 FROM pg_constraint WHERE conname = 'fk_communes' ) THEN ALTER TABLE " + DBTable.ETABLISSEMENTS.toString() + " ADD CONSTRAINT fk_communes FOREIGN KEY (codeCommune) REFERENCES " + DBTable.COMMUNES.toString() + "(CodeCommune) ON DELETE CASCADE; END IF; END $$;";
                Statement _stmt = _con.createStatement();
                _stmt.executeUpdate(_reqCommunes);
                _stmt.executeUpdate(_reqEtablissements);
                _stmt.executeUpdate(_reqCommEtabl);
                this.disconnect(_con);
                _isInit = true;
            }
        } catch (Exception e) {
                ExceptionHandler.handleException(new SQLException());
        }
    }

    /**
     * Méthode générale getter
     */
    private List getString(DBTable Table, Enum col, String critere, boolean onlyExplicit)
    {
        String _req = "SELECT * FROM " + Table.toString() + " WHERE " + col.toString() + " ILIKE ?";
        List _return = new ArrayList<>();
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
                if(col instanceof DBCommune) {
                    _return = this.getCommunesListFromRs(results);
                } else {
                    _return = this.getEtablissementListFromRs(results);
                }
            }
        }  catch (Exception e) {
                ExceptionHandler.handleException(new SQLException());
        }
        return _return;
    }
    private List getInt(DBTable Table, Enum col, int critere)
    {
        String _req = "SELECT * FROM " + Table.toString() + " WHERE " + col.toString() + " = ?";
        List _return = new ArrayList<>();
        try {
            Connection _con = _instance.connect();
            if (_con != null) {
                PreparedStatement _stmt = _con.prepareStatement(_req);
                _stmt.setInt(1, critere);
                ResultSet results = _stmt.executeQuery();
                this.disconnect(_con);
                if(col instanceof DBCommune) {
                    _return = this.getCommunesListFromRs(results);
                } else {
                    _return = this.getEtablissementListFromRs(results);
                }
            }
        }  catch (Exception e) {
            ExceptionHandler.handleException(new SQLException());
        }
        return _return;
    }

    /**
     * Deleters
     */
    private void deleteString(DBTable Table, Enum col, String critere, boolean onlyExplicit)
    {
        String _req = "DELETE FROM " + Table.toString() + " WHERE " + col.toString() + " ILIKE ?";
        try {
            Connection _con = _instance.connect();
            if (_con != null) {
                PreparedStatement _stmt = _con.prepareStatement(_req);
                if (critere.isEmpty()) {
                    _stmt = _con.prepareStatement("DELETE FROM " + Table.toString() + ";");
                }
                else if (onlyExplicit) {
                    _stmt.setString(1, critere);
                } else {
                    _stmt.setString(1, "%" + critere + "%");
                }
                _stmt.executeUpdate();
                this.disconnect(_con);
            }
        }  catch (Exception e) {
            ExceptionHandler.handleException(new SQLException());
        }
    }
    private void deleteInt(DBTable Table, Enum col, int critere)
    {
        String _req = "DELETE FROM " + Table.toString() + " WHERE " + col.toString() + " = ?";
        try {
            Connection _con = _instance.connect();
            if (_con != null) {
                PreparedStatement _stmt = _con.prepareStatement(_req);
                _stmt.setInt(1, critere);
                _stmt.executeUpdate();
                this.disconnect(_con);
            }
        }  catch (Exception e) {
            ExceptionHandler.handleException(new SQLException());
        }
    }
    /**
     *
     * @param results ResultSet de la DB.
     * @return Liste des  / Etablissements dans le RS.
     * @throws SQLException Erreur lors de la requête SQL.
     */
    private List<Commune> getCommunesListFromRs(ResultSet results) throws SQLException {
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
    private List<Etablissement> getEtablissementListFromRs(ResultSet results) throws SQLException {
        List<Etablissement> _etablissement = new ArrayList<>();
        while(results.next()) {
            Etablissement.Builder _tempEtablissement = new Etablissement.Builder();
            _tempEtablissement.setId(results.getInt(1));
            _tempEtablissement.setNomEtablissement(results.getString(2));
            _tempEtablissement.setTypeEtablissement(results.getString(3));
            _tempEtablissement.setMail(results.getString(4));
            _tempEtablissement.setStatutPublicPrive(results.getString(5));
            _tempEtablissement.setCodeCommune(results.getString(6));
            _tempEtablissement.setNomCommune(results.getString(7));
            _etablissement.add(_tempEtablissement.build());
        }
        return _etablissement;
    }
}
