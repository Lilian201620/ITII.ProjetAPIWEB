package fr.itii.apiweb.ui;

import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.domain.models.Commune;
import fr.itii.apiweb.domain.tools.JSONSerializer;

import java.util.List;
import java.util.Scanner;

public class Terminal {

    //couleurs ANSI
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
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
            // MENU 1
            menuTitle("Menu principal");
            System.out.println("1. Rechercher");
            System.out.println("2. Quitter");
            System.out.print(prompt());

            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                doSearch(sc);
            } else if (choice.equals("2")) {
                info("Fin.");
                return;
            } else {
                warn("Choix invalide.");
            }
        }
    }

    private void doSearch(Scanner sc) {
        line();
        System.out.print(CYAN + "Nom de la commune : " + RESET);
        String name = sc.nextLine().trim();

        lastResults = fetchByName(name);
        display(lastResults);

        if (lastResults == null || lastResults.isEmpty()) {
            return; // retour menu principal
        }

        // MENU 2
        while (true) {
            menuTitle("Après résultats");
            System.out.println("1. Affiner recherche");
            System.out.println("2. Sauvegarder");
            System.out.println("3. Nouvelle recherche");
            System.out.println("4. Quitter");
            System.out.print(prompt());

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    doRefine(sc); // mène au MENU 3
                    return;
                }
                case "2" -> saveLastResults();
                case "3" -> {
                    doSearch(sc);
                    return;
                }
                case "4" -> {
                    info("Fin.");
                    System.exit(0);
                }
                default -> warn("Choix invalide.");
            }
        }
    }

    private void doRefine(Scanner sc) {
        line();
        System.out.println(YELLOW + "Donnez un nom plus précis pour affiner la recherche :" + RESET);
        System.out.print(prompt());
        String refineName = sc.nextLine().trim();

        lastResults = fetchByName(refineName);
        display(lastResults);

        if (lastResults == null || lastResults.isEmpty()) {
            return;
        }

        // MENU 3
        while (true) {
            menuTitle("Après affinage");
            System.out.println("1. Nouvelle recherche");
            System.out.println("2. Sauvegarder");
            System.out.println("3. Quitter");
            System.out.print(prompt());

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    doSearch(sc);
                    return;
                }
                case "2" -> saveLastResults();
                case "3" -> {
                    info("Fin.");
                    System.exit(0);
                }
                default -> warn("Choix invalide.");
            }
        }
    }

    private List<Commune> fetchByName(String name) {
        JsonNode json = apiCaller.getCommunesByName(name);
        return serializer.toCommunes(json);
    }

    private void display(List<Commune> communes) {
        if (communes == null || communes.isEmpty()) {
            error("Aucun résultat.");
            return;
        }

        line();
        System.out.println(BOLD + GREEN + "Résultats (" + communes.size() + ")" + RESET);
        line();

        int i = 1;
        for (Commune c : communes) {
            System.out.println(CYAN + "#" + i + RESET + " " + c.toString());
            i++;
        }

        line();
    }
//.save
    private void saveLastResults() {
        if (lastResults == null || lastResults.isEmpty()) {
            warn("Rien à sauvegarder.");
            return;
        }
        dbManager.save(lastResults);
    }

    // Encadrement/style
    private void header(String title) {
        System.out.println(BOLD + CYAN + "========================================" + RESET);
        System.out.println(BOLD + CYAN + " " + title + RESET);
        System.out.println(BOLD + CYAN + "========================================" + RESET);
    }

    private void menuTitle(String title) {
        System.out.println("\n" + BOLD + "=== " + title + " ===" + RESET);
    }

    private void line() {
        System.out.println(CYAN + "----------------------------------------" + RESET);
    }

    private String prompt() {
        return BOLD + "> " + RESET;
    }

    private void ok(String msg) {
        System.out.println(GREEN + "✔ " + msg + RESET);
    }

    private void info(String msg) {
        System.out.println(CYAN + msg + RESET);
    }

    private void warn(String msg) {
        System.out.println(YELLOW + "⚠ " + msg + RESET);
    }

    private void error(String msg) {
        System.out.println(RED + "✖ " + msg + RESET);
    }
}
