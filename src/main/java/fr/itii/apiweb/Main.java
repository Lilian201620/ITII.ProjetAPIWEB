package fr.itii.apiweb.main;

import fr.itii.apiweb.api.APICaller;
import fr.itii.apiweb.db.DBManager;
import fr.itii.apiweb.serializer.JSONSerializer;
import fr.itii.apiweb.terminal.Terminal;

public class Main {

    public static void main(String[] args) {

        /* =====================
           Configuration
        ===================== */

        String apiUrl = "https://geo.api.gouv.fr/decoupage-administratif/communes";

        String dbUrl = "jdbc:postgresql://localhost";
        String dbUser = "user";
        String dbPass = "password";
        String dbPort = "5432";

        /* =====================
           Initialisation
        ===================== */

        APICaller apiCaller = new APICaller(apiUrl);
        JSONSerializer jsonSerializer = new JSONSerializer();
        DBManager dbManager = new DBManager(dbUrl, dbUser, dbPass, dbPort);

        /* =====================
           Lancement du terminal
        ===================== */

        Terminal terminal = new Terminal(
                apiCaller,
                jsonSerializer,
                dbManager
        );

        terminal.start();
    }
}
