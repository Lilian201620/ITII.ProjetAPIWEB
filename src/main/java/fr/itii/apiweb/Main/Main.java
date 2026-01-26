package fr.itii.apiweb.Main;

import fr.itii.apiweb.api.APICaller;
import fr.itii.apiweb.db.DBManager;
import fr.itii.apiweb.serializer.JSONSerializer;
import fr.itii.apiweb.model.Commune;
import fr.itii.apiweb.terminal.Terminal;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Configuration
        String apiUrl = "https://geo.api.gouv.fr/decoupage-administratif/communes";
        String dbUrl = "jdbc:postgresql://localhost:5432/maBase";
        String dbUser = "user";
        String dbPass = "password";

        // Initialisation des classes
        APICaller apiCaller = new APICaller(apiUrl);
        JSONSerializer serializer = new JSONSerializer();
        DBManager dbManager = new DBManager(dbUrl, dbUser, dbPass);

        // Appel API
        String jsonResult = apiCaller.request();

        // JSON -> Objets Java
        List<Commune> communes = serializer.serializeCityResult(jsonResult);

        // Sauvegarde DB
        dbManager.saveAll(communes);

        // Lecture DB
        List<Commune> communesFromDb = dbManager.getAll();

        // Affichage terminal
        Terminal.display(communesFromDb);

        System.out.println("Programme terminé !");
    }
}
