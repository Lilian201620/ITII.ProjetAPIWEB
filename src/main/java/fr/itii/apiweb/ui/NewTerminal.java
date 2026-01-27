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
        switch (menu.showMenu()) {
            //Recherche API
            case "1" -> flowAPI();
            //Recherche DB
            case "2" -> flowDB();
            //Delete DB
            case "3" -> deleteDatabase();
            //Quitter
            case "4" -> System.exit(0);
            default -> run();
        }
    }

    /* =========================================================
       FLOW API : API only + show + menu API
       ========================================================= */
    private void flowAPI() {
        showIndex = 0;
        System.out.print("Nom de la commune : ");
        String name = sc.nextLine().trim();

        searchAPI(name);
        show(lastResults);

        while(true){
            switch (menu.showMenuAPI()) {
                //Page précédente
                case "1" -> show(lastResults, showIndex-20);
                //Page suivante
                case "2" -> show(lastResults);
                //Save
                case "3" -> saveLastResults();
                // nouvelle recherche
                case "4" -> flowAPI();
                // retour menu principal
                case "5" -> {run();}

                default -> {}
            }
        }
    }

    /* =========================================================
       FLOW DB : DB only + show + menu DB + affinage DB
       ========================================================= */
    private void flowDB() {
        showIndex = 0;
        // Selon ton besoin : soit tout, soit recherche par nom.
        // Là je fais une recherche par nom si l'utilisateur tape quelque chose, sinon getAll.
        System.out.print("Nom de la commune (vide = tout) : ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            searchDatabaseAll();
        } else {
            searchDatabaseByName(name);
        }

        while(true){
            switch (menu.showMenuDB()) {
                //Page précédente
                case "1" -> show(lastResults, showIndex-20);
                //Page suivante
                case "2" -> show(lastResults);
                // nouvelle recherche
                case "3" -> flowAPI();
                // retour menu principal
                case "4" -> {run();}

                default -> {}
            }
        }
    }

    /* =========================================================
       JUSTE API search  --> HTTP
       ========================================================= */
    private void searchAPI(String name) {
        JsonNode json = api.getCommunesByName(name, "50");
        lastResults = serializer.toCommunes(json);
    }

    /* =========================================================
       JUSTE DB search + affinage --> SQL
       ========================================================= */
    private void searchDatabaseAll() {
        lastResults = db.getAll();
    }

    private void searchDatabaseByName(String name) {
        lastResults = db.get("nom", name,false);
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
        System.out.println("Sauvegarde ...");
    }

    /* =========================================================
       SHOW par page
       ========================================================= */

    private void show(List<Commune> results){
        show(results, showIndex);
    }

    private void show(List<Commune> results, int page) {
        if (results == null || results.isEmpty()) {
            System.out.println("Aucun résultat.");
            return;
        }

        showIndex = Math.max(0, page);

        int end = Math.min(showIndex + PAGE_SIZE, results.size());

        System.out.println("\n--- Résultats " + (showIndex + 1) + " à " + end + " / " + results.size() + " ---");
        for (int i = showIndex; i < end; i++) {
            System.out.println("#" + (i + 1) + " " + results.get(i));
        }

        showIndex = end;

        if (showIndex >= results.size()) {
            System.out.println("Fin des résultats.");
        }
    }
}
