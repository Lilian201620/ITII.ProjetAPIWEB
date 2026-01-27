package fr.itii.apiweb.ui;

import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.domain.models.Commune;
import fr.itii.apiweb.domain.tools.JSONSerializer;

import java.util.List;
import java.util.Scanner;

public class Terminal {

    // Styles ANSI
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";

    private final APICaller apiCaller;
    private final JSONSerializer serializer;
    private final DBManager dbManager;

    private List<Commune> lastResults = null;

    public Terminal(APICaller apiCaller, JSONSerializer serializer, DBManager dbManager) {
        this.apiCaller = apiCaller;
        this.serializer = serializer;
        this.dbManager = dbManager;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);

        header("Projet APIWeb");

        while (true) {
            System.out.println("\n" + BOLD + "1. Rechercher API" + RESET);
            System.out.println(BOLD + "2. Rechercher dans la DataBase" + RESET);
            System.out.println(BOLD + "3. Supprimer database" + RESET);
            System.out.println(BOLD + "4. Quitter" + RESET);
            System.out.print(prompt());

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> searchApi(sc);
                case "2" -> searchDatabase(sc);
                case "3" -> deleteDatabase();
                case "4" -> {
                    info("Fin.");
                    return;
                }
                default -> warn("Choix invalide.");
            }
        }
    }

    // ====== RECHERCHE API ======
    private void searchApi(Scanner sc) {
        System.out.print(CYAN + "Nom de la commune : " + RESET);
        String name = sc.nextLine().trim();

        JsonNode json = apiCaller.getCommunesByName(name);
        lastResults = serializer.toCommunes(json);

        afterResultsMenu(sc);
    }

    // ====== RECHERCHE DB ======
    private void searchDatabase(Scanner sc) {
        lastResults = dbManager.getAll();
        afterResultsMenu(sc);
    }

    // ====== MENU APRÈS RÉSULTATS ======
    private void afterResultsMenu(Scanner sc) {
        display(lastResults);

        if (lastResults == null || lastResults.isEmpty()) {
            return;
        }

        while (true) {
            System.out.println("\n" + BOLD + "1. Affiner recherche" + RESET);
            System.out.println(BOLD + "2. Sauvegarder" + RESET);
            System.out.println(BOLD + "3. Menu Principal" + RESET);
            System.out.println(BOLD + "4. Quitter" + RESET);
            System.out.print(prompt());

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    refine(sc);
                    return;
                }
                case "2" -> saveLastResults();
                case "3" -> {
                    return;
                }
                case "4" -> System.exit(0);
                default -> warn("Choix invalide.");
            }
        }
    }

    // ====== AFFINER ======
    private void refine(Scanner sc) {
        System.out.println(YELLOW + "Donnez un nom plus précis pour affiner la recherche :" + RESET);
        System.out.print(prompt());
        String refineName = sc.nextLine().trim();

        JsonNode json = apiCaller.getCommunesByName(refineName);
        lastResults = serializer.toCommunes(json);

        display(lastResults);

        if (lastResults == null || lastResults.isEmpty()) {
            return;
        }

        while (true) {
            System.out.println("\n" + BOLD + "1. Nouvelle recherche" + RESET);
            System.out.println(BOLD + "2. Sauvegarder" + RESET);
            System.out.println(BOLD + "3. Quitter" + RESET);
            System.out.print(prompt());

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    return;
                }
                case "2" -> saveLastResults();
                case "3" -> System.exit(0);
                default -> warn("Choix invalide.");
            }
        }
    }

    // ====== SAUVEGARDE ======
    private void saveLastResults() {
        if (lastResults == null || lastResults.isEmpty()) {
            warn("Rien à sauvegarder.");
            return;
        }
        System.out.println(YELLOW + "Sauvegarde ...." + RESET);
        dbManager.save(lastResults);

    }

    // ====== SUPPRESSION DB ======
    private void deleteDatabase() {
        dbManager.deleteAll();
        System.out.println(RED + "Base de données vidée." + RESET);
    }

    // ====== AFFICHAGE ======
    private void display(List<Commune> communes) {
        if (communes == null || communes.isEmpty()) {
            warn("Aucun résultat.");
            return;
        }

        System.out.println(CYAN + "\n--- Résultats (" + communes.size() + ") ---" + RESET);
        for (Commune c : communes) {
            System.out.println(c);
        }
    }

    // ====== OUTILS STYLE ======
    private void header(String title) {
        System.out.println(BOLD + CYAN + "========================================" + RESET);
        System.out.println(BOLD + CYAN + " " + title + RESET);
        System.out.println(BOLD + CYAN + "========================================" + RESET);
    }

    private String prompt() {
        return BOLD + "> " + RESET;
    }

    private void info(String msg) {
        System.out.println(CYAN + msg + RESET);
    }

    private void warn(String msg) {
        System.out.println(YELLOW + msg + RESET);
    }
}
