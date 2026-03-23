package fr.itii.apiweb.data.local;

import fr.itii.apiweb.domain.models.enums.DBTable;
import fr.itii.apiweb.domain.models.objets.Commune;
import fr.itii.apiweb.domain.models.objets.Etablissement;
import fr.itii.apiweb.ui.Font;
import fr.itii.apiweb.domain.tools.ExceptionHandler;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DBManager implements DataRepository {
    private final String _dbHost = System.getenv().getOrDefault("DB_HOST", "localhost");
    private final String _url = "jdbc:postgresql://" + _dbHost + ":5432/ApiWebToolDB";
    private static final String _username = "apiwebtoolUser";
    private static final String _password = "RVomy#$@CE76#t!yNkPr";
    ExecutorService dbExecutor = Executors.newSingleThreadExecutor();

    private static DBManager _instance;
    private static boolean _isInit = false;


    private DBManager() {
        try {
            String driverName = "org.postgresql.Driver";
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur, impossible de charger le driver pour : " + _url);
        }
    }


    public static DBManager getInstance() {
        if (_instance == null) {
            _instance = new DBManager();
            _instance.initTables();
        }
        return _instance;
    }


    private Connection connect() throws SQLException {
        if (!_isInit) {
            this.initTables();
        }
        return DriverManager.getConnection(_url, _username, _password);
    }


    private void disconnect(Connection con) throws SQLException {
        if (con != null) {
            con.close();
        }
    }

    public boolean isConnected() {
        return _isInit;
    }


    private ResultSet getString(DBTable table, Enum col, String critere, boolean onlyExplicit) {
        String req = "SELECT * FROM " + table.toString() + " WHERE " + col.toString() + " ILIKE ?";
        try {
            Connection con = _instance.connect();
            if (con != null) {
                PreparedStatement stmt = con.prepareStatement(req);
                if (onlyExplicit) {
                    stmt.setString(1, critere);
                } else {
                    stmt.setString(1, "%" + critere + "%");
                }
                return stmt.executeQuery();
            }
        } catch (Exception e) {
            // ExceptionHandler.handleException(new SQLException());
            throw new RuntimeException("Erreur sql");
        }
        return null;
    }

    private ResultSet getInt(DBTable table, Enum col, int critere) {
        String req = "SELECT * FROM " + table.toString() + " WHERE " + col.toString() + " = ?";
        try {
            Connection con = _instance.connect();
            if (con != null) {
                PreparedStatement stmt = con.prepareStatement(req);
                stmt.setInt(1, critere);
                return stmt.executeQuery();
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(new SQLException());
        }
        return null;
    }

    private ResultSet getString(DBTable table, Enum col, String critere) {
        String req = "SELECT * FROM " + table.toString() + " WHERE ? = ANY(" + col.toString() + ")";

        try {
            Connection con = _instance.connect();
            if (con != null) {
                PreparedStatement stmt = con.prepareStatement(req);
                stmt.setString(1, critere);

                return stmt.executeQuery();
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(new SQLException());
        }
        return null;
    }


    private void deleteString(DBTable table, Enum col, String critere, boolean onlyExplicit) {
        dbExecutor.submit(() -> {
            String req = "DELETE FROM " + table.toString() + " WHERE " + col.toString() + " ILIKE ?";
            try {
                Connection con = _instance.connect();
                if (con != null) {
                    PreparedStatement stmt = con.prepareStatement(req);
                    if (critere.isEmpty()) {
                        stmt = con.prepareStatement("DELETE FROM " + table + ";");
                    } else if (onlyExplicit) {
                        stmt.setString(1, critere);
                    } else {
                        stmt.setString(1, "%" + critere + "%");
                    }
                    stmt.executeUpdate();
                    this.disconnect(con);
                }
            } catch (Exception e) {
                ExceptionHandler.handleException(new SQLException());
            }
        });
    }

    private void deleteInt(DBTable table, Enum col, int critere) {
        dbExecutor.submit(() -> {
            String req = "DELETE FROM " + table.toString() + " WHERE " + col.toString() + " = ?";
            try {
                Connection con = _instance.connect();
                if (con != null) {
                    PreparedStatement stmt = con.prepareStatement(req);
                    stmt.setInt(1, critere);
                    stmt.executeUpdate();
                    this.disconnect(con);
                }
            } catch (Exception e) {
                ExceptionHandler.handleException(new SQLException());
            }
        });
    }

    private void deleteString(DBTable table, Enum col, String critere) {
        dbExecutor.submit(() -> {
            String req = "DELETE FROM " + table.toString() + " WHERE ? = ANY(" + col.toString() + ")";

            try (Connection con = _instance.connect()) {
                if (con != null) {
                    try (PreparedStatement stmt = con.prepareStatement(req)) {
                        if (critere != null && !critere.isEmpty()) {
                            // On injecte le nombre en String ("75000")
                            stmt.setString(1, critere);
                        }

                        int rowsDeleted = stmt.executeUpdate();
                        System.out.println(rowsDeleted + " ligne(s) supprimée(s).");
                    }
                    this.disconnect(con);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    private void initTables() {
        try {
            Connection con = DriverManager.getConnection(_url, _username, _password);
            if (con != null) {
                String reqCommune = "CREATE TABLE IF NOT EXISTS " +
                    DBTable.COMMUNES + "(" +
                    DBTable.Commune.ID + " BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, " +
                    DBTable.Commune.NOM + " VARCHAR(256), " +
                    DBTable.Commune.CODE_COMMUNE + " VARCHAR(5) UNIQUE NOT NULL, " +
                    DBTable.Commune.CODE_DEPARTEMENT + " VARCHAR(3), " +
                    DBTable.Commune.NOM_DEPARTEMENT + " VARCHAR(256), " +
                    DBTable.Commune.CODE_REGION + " VARCHAR(2), " +
                    DBTable.Commune.NOM_REGION + " VARCHAR(256), " +
                    DBTable.Commune.CODES_POSTAUX + " VARCHAR(5)[], " +
                    DBTable.Commune.CODE_POSTAL + " VARCHAR(5), " +
                    DBTable.Commune.POPULATION + " BIGINT, " +
                    DBTable.Commune.LONGITUDE + " FLOAT8, " +
                    DBTable.Commune.LATITUDE + " FLOAT8" +
                ");";

                String reqEtablissement = "CREATE TABLE IF NOT EXISTS " +
                    DBTable.ETABLISSEMENTS + "( " +
                    DBTable.Etablissement.ID + " BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    DBTable.Etablissement.NOM +  " VARCHAR(256), " +
                    DBTable.Etablissement.TYPE + " VARCHAR(256), " +
                    DBTable.Etablissement.MAIL + " VARCHAR(256), " +
                    DBTable.Etablissement.STATUT + " VARCHAR(6), " +
                    DBTable.Etablissement.CODE_COMMUNE + " VARCHAR(5), " +
                    DBTable.Etablissement.NOM_COMMUNE + " VARCHAR(256) " +
                ");";

                String reqComEtab =
                    "DO $$ BEGIN IF NOT EXISTS ( SELECT 1 FROM pg_constraint WHERE conname = 'fk_communes' ) THEN " +
                    "ALTER TABLE " + DBTable.ETABLISSEMENTS + " " +
                    "ADD CONSTRAINT fk_communes FOREIGN KEY ("+ DBTable.Etablissement.CODE_COMMUNE + ") " +
                    "REFERENCES " + DBTable.COMMUNES + "( " + DBTable.Commune.CODE_COMMUNE + ") " +
                    "ON DELETE CASCADE; END IF; END $$;";

                Statement stmt = con.createStatement();
                stmt.executeUpdate(reqCommune);
                stmt.executeUpdate(reqEtablissement);
                stmt.executeUpdate(reqComEtab);
                this.disconnect(con);
                _isInit = true;
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(new SQLException());
        }
    }


    @Override
    public void saveCommunes(List<Commune> communes) {
        dbExecutor.submit(() -> {
            try {
                Connection con = _instance.connect();
                PreparedStatement stmt;
                for (Commune c : communes) {
                    try {
                        String req = "INSERT INTO " + DBTable.COMMUNES + String.format("(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?,?,?,?,?)",
                                DBTable.Commune.NOM,
                                DBTable.Commune.CODE_COMMUNE,
                                DBTable.Commune.CODE_DEPARTEMENT,
                                DBTable.Commune.NOM_DEPARTEMENT,
                                DBTable.Commune.CODE_REGION,
                                DBTable.Commune.NOM_REGION,
                                DBTable.Commune.CODES_POSTAUX,
                                DBTable.Commune.POPULATION,
                                DBTable.Commune.LONGITUDE,
                                DBTable.Commune.LATITUDE
                        );

                        stmt = con.prepareStatement(req);
                        stmt.setString(1, c.getNom());
                        stmt.setString(2, c.getCodeCommune());
                        stmt.setString(3, c.getCodeDepartement());
                        stmt.setString(4, c.getNomDepartement());
                        stmt.setString(5, c.getCodeRegion());
                        stmt.setString(6, c.getNomRegion());
                        stmt.setArray(7, con.createArrayOf("varchar", c.getCodesPostaux().toArray()));
                        stmt.setInt(8, c.getPopulation());
                        stmt.setDouble(9, c.getLongitude());
                        stmt.setDouble(10, c.getLatitude());
                        stmt.execute();

                    } catch (Exception e) {
                        System.out.println(Font.ITALIC + "" + Font.GREY + c.getNom() + " est déjà présente dans la base de donnée." + Font.RESET);
                    }
                }
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                ExceptionHandler.handleException(new SQLException());
            }
        });
    }

    private List<Commune> getCommunes(ResultSet results) {
        List<Commune> communes = new ArrayList<Commune>();
        try {
            while(results.next()) {
                Array sqlArray = results.getArray(DBTable.Commune.CODES_POSTAUX.toString());
                List<String> codes = (sqlArray != null) ? Arrays.asList((String[]) sqlArray.getArray()) : new ArrayList<>();

                Commune c = new Commune.Builder()
                    .setId(results.getInt(DBTable.Commune.ID.toString()))
                    .setNom(results.getString(DBTable.Commune.NOM.toString()))
                    .setCodeCommune(results.getString(DBTable.Commune.CODE_COMMUNE.toString()))
                    .setDepartement(
                            results.getString(DBTable.Commune.CODE_DEPARTEMENT.toString()),
                            results.getString(DBTable.Commune.NOM_DEPARTEMENT.toString())
                    )
                    .setRegion(
                            results.getString(DBTable.Commune.CODE_REGION.toString()),
                            results.getString(DBTable.Commune.NOM_REGION.toString())
                    )
                    .setCodesPostaux(codes)
                    .setPopulation(results.getInt(DBTable.Commune.POPULATION.toString()))
                    .setCoordonnees(
                            results.getDouble(DBTable.Commune.LONGITUDE.toString()),
                            results.getDouble(DBTable.Commune.LATITUDE.toString())
                    )
                    .build();
                communes.add(c);
            }
            return communes;
        } catch (Exception e) {
            ExceptionHandler.handleException(new SQLException());
        }
        return null;
    }

    @Override
    public List<Commune> getCommunes(DBTable.Commune col, String critere, boolean explicit) {
        ResultSet results = getString(DBTable.COMMUNES, col, critere, explicit);
        return getCommunes(results);
    }

    @Override
    public List<Commune> getCommunes(DBTable.Commune col, int critere) {
        ResultSet results = getInt(DBTable.COMMUNES, col, critere);
        return getCommunes(results);
    }

    @Override
    public List<Commune> getCommunes() {
        return null;
    }

    public List<Commune> getCommunes(DBTable.Commune col, String critere) {
        ResultSet results = getString(DBTable.COMMUNES, col, critere);
        return getCommunes(results);
    }

    @Override
    public void deleteCommunes(DBTable.Commune col, String critere, boolean explicit) {
        deleteString(DBTable.COMMUNES, col, critere, explicit);
    }

    @Override
    public void deleteCommunes(DBTable.Commune col, int critere) {
        deleteInt(DBTable.COMMUNES, col, critere);
    }

    @Override
    public void deleteCommunes() {
        deleteString(DBTable.COMMUNES, DBTable.Commune.ID, "", false);
    }


    @Override
    public void saveEtablissements(List<Etablissement> Etablissements) {
        dbExecutor.submit(() -> {
            try {
                Connection con = _instance.connect();
                PreparedStatement stmt;
                for (Etablissement e : Etablissements) {
                    String req = "INSERT INTO " + DBTable.ETABLISSEMENTS+ String.format("(%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?)",
                            DBTable.Etablissement.NOM,
                            DBTable.Etablissement.TYPE,
                            DBTable.Etablissement.MAIL,
                            DBTable.Etablissement.STATUT,
                            DBTable.Etablissement.CODE_COMMUNE,
                            DBTable.Etablissement.NOM_COMMUNE
                    );
                    stmt = con.prepareStatement(req);
                    stmt.setString(1, e.getNomEtablissement());
                    stmt.setString(2, e.getTypeEtablissement());
                    stmt.setString(3, e.getMail());
                    stmt.setString(4, e.getStatutPublicPrive());
                    stmt.setString(5, e.getCodeCommune());
                    stmt.setString(6, e.getNomCommune());
                    stmt.execute();
                }
                this.disconnect(con);

            } catch (Exception e) {
                ExceptionHandler.handleException(new SQLException());
            }
        });
    }

    private List<Etablissement> getEtablissements(ResultSet results) {
        List<Etablissement> etablissements = new ArrayList<>();
        try {
            while (results.next()) {
                Etablissement e = new Etablissement.Builder()
                        .setId(results.getInt(1))
                        .setNomEtablissement(results.getString(2))
                        .setTypeEtablissement(results.getString(3))
                        .setMail(results.getString(4))
                        .setStatutPublicPrive(results.getString(5))
                        .setCodeCommune(results.getString(6))
                        .setNomCommune(results.getString(7))
                        .build();
                etablissements.add(e);
            }
            return etablissements;
        } catch (Exception e) {
            ExceptionHandler.handleException(new SQLException());
        }
        return null;
    }

    @Override
    public List<Etablissement> getEtablissements(DBTable.Etablissement col, String critere, boolean explicit) {
        ResultSet results = getString(DBTable.ETABLISSEMENTS, col, critere, explicit);
        return getEtablissements(results);
    }

    @Override
    public List<Etablissement> getEtablissements(DBTable.Etablissement col, int critere) {
        ResultSet results = getInt(DBTable.ETABLISSEMENTS, col, critere);
        return getEtablissements(results);
    }

    @Override
    public List<Etablissement> getEtablissements() {
        return null;
    }

    @Override
    public void deleteEtablissements(DBTable.Etablissement col, String critere, boolean onlyExplicit) {
        deleteString(DBTable.ETABLISSEMENTS, col, critere, onlyExplicit);
    }

    @Override
    public void deleteEtablissements(DBTable.Etablissement col, int critere) {
        deleteInt(DBTable.ETABLISSEMENTS, col, critere);
    }

    @Override
    public void deleteEtablissements() {
        deleteString(DBTable.ETABLISSEMENTS, DBTable.Etablissement.ID, "", false);
    }

    public List<Etablissement> getJoin(DBTable.Commune col, String critere, boolean onlyExplicit) {
        List<Etablissement> etablissements = new ArrayList<>();
        String req =
                "SELECT * FROM " + DBTable.ETABLISSEMENTS + " e " +
                "INNER JOIN " + DBTable.COMMUNES + " c " +
                "ON e." + DBTable.Etablissement.CODE_COMMUNE + " = c." + DBTable.Commune.CODE_COMMUNE +
                " WHERE c." + col + " = ?";

        try {
            Connection con = DBManager._instance.connect();
            if (con != null) {
                PreparedStatement stmt = con.prepareStatement(req);
                if (onlyExplicit) {
                    stmt.setString(1, critere);
                } else {
                    stmt.setString(1, "%" + critere + "%");
                }
                ResultSet results = stmt.executeQuery();
                return getEtablissements(results);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(new SQLException());
        }
        return Collections.emptyList();
    }
}