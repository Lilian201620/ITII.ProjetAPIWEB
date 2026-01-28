//package fr.itii.apiweb.ui;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import fr.itii.apiweb.data.local.DBManager;
//import fr.itii.apiweb.data.remote.APICaller;
//import fr.itii.apiweb.domain.models.Commune;
//import fr.itii.apiweb.domain.tools.JSONSerializer;
//
//import java.util.List;
//import java.util.Scanner;
//
//public class Terminal {
//
//    private final Menu menu;                 // ✅ on UTILISE la classe Menu
//    private final APICaller apiCaller;
//    private final JSONSerializer serializer;
//    private final DBManager dbManager;
//
//    private final Scanner sc = new Scanner(System.in);
//
//    // lastResult general
//    private List<Commune> lastResults = null;
//
//    // pagination
//    private int showIndex = 0;
//    private static final int PAGE_SIZE = 10;
//
//    public Terminal(Menu menu, APICaller apiCaller, JSONSerializer serializer, DBManager dbManager) {
//        this.menu = menu;
//        this.apiCaller = apiCaller;
//        this.serializer = serializer;
//        this.dbManager = dbManager;
//    }
//
//    /* =========================================================
//       RUN PRINCIPAL : utilise Menu.showMenu()
//       ========================================================= */
//    public void run() {
//        while (true) {
//            String choice = menu.showMenu();
//
//            switch (choice) {
//                case "1" -> flowAPI();
//                case "2" -> flowDB();
//                case "3" -> deleteDatabase();
//                case "4" -> System.exit(0);
//                default -> { /* on relance */ }
//            }
//        }
//    }
//
//    /* =========================================================
//       FLOW API : API only + show + menu API
//       ========================================================= */
//    private void flowAPI() {
//        System.out.print("Nom de la commune : ");
//        String name = sc.nextLine().trim();
//
//        searchAPI(name);
//        show(); // affiche 0..10
//
//        while (true) {
//            String choice = menu.showMenuAPI();
//
//            switch (choice) {
//                case "1" -> show();               // élargir (+10)
//                case "2" -> saveLastResults();    // save
//                case "3" -> { return; }           // nouvelle recherche
//                case "4" -> { return; }           // retour menu principal
//                default -> { /* re-affiche */ }
//            }
//        }
//    }
//
//    /* =========================================================
//       FLOW DB : DB only + show + menu DB + affinage DB
//       ========================================================= */
//    private void flowDB() {
//        // Selon ton besoin : soit tout, soit recherche par nom.
//        // Là je fais une recherche par nom si l'utilisateur tape quelque chose, sinon getAll.
//        System.out.print("Nom de la commune (vide = tout) : ");
//        String name = sc.nextLine().trim();
//
//        if (name.isEmpty()) {
//            searchDatabaseAll();
//        } else {
//            searchDatabaseByName(name);
//        }
//
//        show(); // affiche 0..10
//
//        while (true) {
//            String choice = menu.showMenuDB();
//
//            switch (choice) {
//                case "1" -> saveLastResults();   // sauvegarder
//                case "2" -> { return; }          // nouvelle recherche
//                case "3" -> { return; }          // retour menu principal
//                default -> { /* re-affiche */ }
//            }
//        }
//    }
//
//    /* =========================================================
//       JUSTE API search  --> HTTP
//       ========================================================= */
//    private void searchAPI(String name) {
//        JsonNode json = apiCaller.getCommunesByName(name);
//        lastResults = serializer.toCommunes(json);
//        resetShow();
//    }
//
//    /* =========================================================
//       JUSTE DB search + affinage --> SQL
//       ========================================================= */
//    private void searchDatabaseAll() {
//        lastResults = dbManager.getAll();
//        resetShow();
//    }
//
//    private void searchDatabaseByName(String name) {
//        lastResults = dbManager.getByName(name, false);
//        resetShow();
//    }
//
//    // si plus tard tes menus DB demandent "affiner"
//    private void refineDatabaseByName(String morePreciseName) {
//        lastResults = dbManager.getByName(morePreciseName, false);
//        resetShow();
//    }
//
//    /* =========================================================
//       DELETE BD
//       ========================================================= */
//    private void deleteDatabase() {
//        dbManager.deleteAll();
//        lastResults = null;
//        resetShow();
//        System.out.println("Base de données vidée.");
//    }
//
//    /* =========================================================
//       SAVE lastResults
//       ========================================================= */
//    private void saveLastResults() {
//        if (lastResults == null || lastResults.isEmpty()) {
//            System.out.println("Rien à sauvegarder.");
//            return;
//        }
//        dbManager.save(lastResults);
//        System.out.println("Sauvegarde ....");
//    }
//
//    /* =========================================================
//       SHOW paginé : 0..10 puis +10 à chaque appel
//       ========================================================= */
//    private void show() {
//        if (lastResults == null || lastResults.isEmpty()) {
//            System.out.println("Aucun résultat.");
//            return;
//        }
//
//        int end = Math.min(showIndex + PAGE_SIZE, lastResults.size());
//
//        System.out.println("\n--- Résultats " + (showIndex + 1) + " à " + end + " / " + lastResults.size() + " ---");
//        for (int i = showIndex; i < end; i++) {
//            System.out.println("#" + (i + 1) + " " + lastResults.get(i));
//        }
//
//        showIndex = end;
//
//        if (showIndex >= lastResults.size()) {
//            System.out.println("Fin des résultats.");
//        }
//    }
//
//    private void resetShow() {
//        showIndex = 0;
//    }
//}
