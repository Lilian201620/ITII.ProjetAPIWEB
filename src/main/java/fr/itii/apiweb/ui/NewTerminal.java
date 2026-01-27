package fr.itii.apiweb.ui;

import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.domain.models.Commune;
import fr.itii.apiweb.domain.tools.JSONSerializer;

import java.util.List;
import java.util.Scanner;

public class NewTerminal {


    private final APICaller api = new APICaller();
    private final JSONSerializer serializer = new JSONSerializer();
    private final DBManager db = DBManager.getInstance();
    private final Menu menu = new Menu();// ✅ on UTILISE la classe Menu

    private final Scanner sc = new Scanner(System.in);

    // lastResult general
    private List<Commune> lastResults = null;


    // pagination
    private int showIndex = 0;
    private static final int PAGE_SIZE = 10;


    /* =========================================================
       RUN PRINCIPAL : utilise Menu.showMenu()
       ========================================================= */
    public void run() {
        while (true) {
            switch (menu.showMenu()) {
                case "1" -> flowAPI();
                case "2" -> flowDB();
                case "3" -> deleteDatabase();
                case "4" -> System.exit(0);
                default -> { /* on relance */ }
            }
        }
    }

    /* =========================================================
       FLOW API : API only + show + menu API
       ========================================================= */
    private void flowAPI() {
        System.out.print("Nom de la commune : ");
        String name = sc.nextLine().trim();

        searchAPI(name);
        // affiche 0..10

        while (true) {
            String choice = menu.showMenuAPI();

            switch (choice) {
                case "1" -> show(lastResults, 10);               // élargir (+10)
                case "2" -> saveLastResults();    // save
                case "3" -> { return; }           // nouvelle recherche
                case "4" -> { return; }           // retour menu principal
                default -> { /* re-affiche */ }
            }
        }
    }

    /* =========================================================
       FLOW DB : DB only + show + menu DB + affinage DB
       ========================================================= */
    private void flowDB() {
        // Selon ton besoin : soit tout, soit recherche par nom.
        // Là je fais une recherche par nom si l'utilisateur tape quelque chose, sinon getAll.
        System.out.print("Nom de la commune (vide = tout) : ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            searchDatabaseAll();
        } else {
            searchDatabaseByName(name);
        }
        // affiche 0..10

        while (true) {
            String choice = menu.showMenuDB();

            switch (choice) {
                case "1" -> saveLastResults();   // sauvegarder
                case "2" -> { return; }          // nouvelle recherche
                case "3" -> { return; }          // retour menu principal
                default -> { /* re-affiche */ }
            }
        }
    }

    /* =========================================================
       JUSTE API search  --> HTTP
       ========================================================= */
    private void searchAPI(String name) {
        JsonNode json = api.getCommunesByName(name, "50");
        lastResults = serializer.toCommunes(json);
        show(lastResults, 0);
    }

    /* =========================================================
       JUSTE DB search + affinage --> SQL
       ========================================================= */
    private void searchDatabaseAll() {
        lastResults = db.getAll();
        show(lastResults, 0);
    }

    private void searchDatabaseByName(String name) {
        lastResults = db.get("nom", name,false);
        show(lastResults, 0);
    }

    /* =========================================================
       DELETE BD
       ========================================================= */
    private void deleteDatabase() {
        db.deleteAll();
        lastResults = null;
        System.out.println("Base de données vidée.");
    }

    /* =========================================================
       SAVE lastResults
       ========================================================= */
    private void saveLastResults() {
        if (lastResults == null || lastResults.isEmpty()) {
            System.out.println("Rien à sauvegarder.");
            return;
        }
        db.save(lastResults);
        System.out.println("Sauvegarde ....");
    }

    /* =========================================================
       SHOW par page
       ========================================================= */
    private void show(List<Commune> results, int page) {
        if (results == null || results.isEmpty()) {
            System.out.println("Aucun résultat.");
            return;
        }

        showIndex = page;

        int end = Math.min(showIndex + PAGE_SIZE, results.size());

        System.out.println("\n--- Résultats " + (showIndex + 1) + " à " + end + " / " + results.size() + " ---");
        for (int i = showIndex; i < end; i++) {
            System.out.println("#" + (i + 1) + " " + results.get(i));
        }

        showIndex = end;

        if (showIndex >= results.size()) {
            System.out.println("Fin des résultats.");
            showIndex = 0;
        }
    }
}
