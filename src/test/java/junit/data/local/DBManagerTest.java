package junit.data.local;

import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.domain.models.enums.DBTable;
import fr.itii.apiweb.domain.models.objets.Commune;
import fr.itii.apiweb.domain.models.objets.Etablissement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.parallel.ResourceLock;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.SAME_THREAD)
@ResourceLock("postgres-db")
public class DBManagerTest {

    private static final String DB_HOST = System.getenv().getOrDefault("DB_HOST", "localhost");
    private static final String DB_URL = "jdbc:postgresql://" + DB_HOST + ":5432/ApiWebToolDB";
    private static final String DB_USER = "apiwebtoolUser";
    private static final String DB_PASSWORD = "RVomy#$@CE76#t!yNkPr";

    private static DBManager dbManager;

    @BeforeAll
    static void beforeAll() {
        Assumptions.assumeTrue(canConnectToDatabase(),
                "Base PostgreSQL indisponible. Lance Docker avant d'executer les tests.");
        dbManager = DBManager.getInstance();
    }

    @BeforeEach
    void cleanBeforeTest() throws SQLException {
        cleanDatabase();
    }

    @AfterEach
    void cleanAfterTest() throws SQLException {
        cleanDatabase();
    }

    @Test
    void saveCommunes_savesOneCommuneInDatabase() throws Exception {
        Commune commune = createCommune("Beauvais Test", "60057", List.of("60000", "60001"));

        dbManager.saveCommunes(List.of(commune));
        Thread.sleep(1000);

        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT nom, codecommune, codespostaux FROM communes WHERE codecommune = ?")) {

            statement.setString(1, "60057");

            try (ResultSet resultSet = statement.executeQuery()) {
                assertTrue(resultSet.next());
                assertEquals("Beauvais Test", resultSet.getString("nom"));
                assertEquals("60057", resultSet.getString("codecommune"));

                Array array = resultSet.getArray("codespostaux");
                assertNotNull(array);
                String[] codesPostaux = (String[]) array.getArray();
                assertEquals(2, codesPostaux.length);
                assertEquals("60000", codesPostaux[0]);
                assertEquals("60001", codesPostaux[1]);
            }
        }
    }

    @Test
    void getCommunes_returnsTheSavedCommune() throws Exception {
        insertCommuneInDatabase("Compiegne Test", "60200");

        List<Commune> communes = dbManager.getCommunes(DBTable.Commune.CODE_COMMUNE, "60200", true);

        assertEquals(1, communes.size());
        assertEquals("Compiegne Test", communes.getFirst().getNom());
        assertEquals("60200", communes.getFirst().getCodeCommune());
    }

    @Test
    void deleteCommunes_deletesTheSavedCommune() throws Exception {
        insertCommuneInDatabase("Senlis Test", "60300");

        dbManager.deleteCommunes(DBTable.Commune.CODE_COMMUNE, "60300", true);
        Thread.sleep(1000);

        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT COUNT(*) FROM communes WHERE codecommune = ?")) {

            statement.setString(1, "60300");

            try (ResultSet resultSet = statement.executeQuery()) {
                assertTrue(resultSet.next());
                assertEquals(0, resultSet.getInt(1));
            }
        }
    }

    @Test
    void saveEtablissements_savesOneEtablissement() throws Exception {
        insertCommuneInDatabase("Noyon Test", "60400");

        Etablissement etablissement = new Etablissement.Builder()
                .setNomEtablissement("Lycee Test")
                .setTypeEtablissement("Lycee")
                .setMail("contact@lycee-test.fr")
                .setStatutPublicPrive("Public")
                .setCodeCommune("60400")
                .setNomCommune("Noyon Test")
                .build();

        dbManager.saveEtablissements(List.of(etablissement));

        boolean lineFound = false;
        int attempts = 0;

        while (!lineFound && attempts < 20) {
            try (Connection connection = openConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "SELECT COUNT(*) FROM etablissements WHERE nom = ?")) {

                statement.setString(1, "Lycee Test");

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) == 1) {
                        lineFound = true;
                    }
                }
            }

            if (!lineFound) {
                Thread.sleep(200);
            }
            attempts++;
        }

        assertTrue(lineFound);

        List<Etablissement> etablissements = dbManager.getEtablissements(DBTable.Etablissement.NOM, "Lycee Test", true);

        assertEquals(1, etablissements.size());
        assertEquals("Lycee Test", etablissements.getFirst().getNomEtablissement());
        assertEquals("60400", etablissements.getFirst().getCodeCommune());
        assertEquals("Noyon Test", etablissements.getFirst().getNomCommune());
    }

    @Test
    void getJoin_returnsLinkedEtablissement() throws Exception {
        insertCommuneInDatabase("Clermont Test", "60600");
        insertEtablissementInDatabase("College Test", "60600", "Clermont Test");

        List<Etablissement> etablissements = dbManager.getJoin(DBTable.Commune.CODE_COMMUNE, "60600", true);

        assertFalse(etablissements.isEmpty());
        assertEquals("College Test", etablissements.getFirst().getNomEtablissement());
        assertEquals("60600", etablissements.getFirst().getCodeCommune());
    }

    private static boolean canConnectToDatabase() {
        try (Connection connection = openConnection()) {
            return connection != null;
        } catch (SQLException e) {
            return false;
        }
    }

    private static Connection openConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private void cleanDatabase() throws SQLException {
        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "TRUNCATE TABLE etablissements, communes RESTART IDENTITY CASCADE")) {
            statement.executeUpdate();
        }
    }

    private Commune createCommune(String nom, String codeCommune, List<String> codesPostaux) {
        return new Commune.Builder()
                .setNom(nom)
                .setCodeCommune(codeCommune)
                .setDepartement("60", "Oise")
                .setRegion("32", "Hauts-de-France")
                .setCodesPostaux(codesPostaux)
                .setPopulation(50000)
                .setCoordonnees(2.08, 49.43)
                .build();
    }

    private void insertCommuneInDatabase(String nom, String codeCommune) throws SQLException {
        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO communes (nom, codecommune, codedepartement, nomdepartement, coderegion, nomregion, codespostaux, population, longitude, latitude) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            statement.setString(1, nom);
            statement.setString(2, codeCommune);
            statement.setString(3, "60");
            statement.setString(4, "Oise");
            statement.setString(5, "32");
            statement.setString(6, "Hauts-de-France");
            statement.setArray(7, connection.createArrayOf("varchar", new String[]{codeCommune}));
            statement.setInt(8, 50000);
            statement.setDouble(9, 2.08);
            statement.setDouble(10, 49.43);
            statement.executeUpdate();
        }
    }

    private void insertEtablissementInDatabase(String nom, String codeCommune, String nomCommune) throws SQLException {
        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO etablissements (nom, type, mail, statut, codecommune, nomcommune) VALUES (?, ?, ?, ?, ?, ?)")) {

            statement.setString(1, nom);
            statement.setString(2, "College");
            statement.setString(3, "contact@test.fr");
            statement.setString(4, "Public");
            statement.setString(5, codeCommune);
            statement.setString(6, nomCommune);
            statement.executeUpdate();
        }
    }
}
