package fr.itii.apiweb.ui;

import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.domain.models.Commune;
import fr.itii.apiweb.domain.tools.JSONSerializer;

import java.util.List;
import java.util.Scanner;

public class Terminal {

    private final APICaller apiCaller;
    private final JSONSerializer serializer;
    private final DBManager dbManager;

    private List<Commune> lastResults = null; // dernière liste affichée

    public Terminal(APICaller apiCaller, JSONSerializer serializer, DBManager dbManager) {
        this.apiCaller = apiCaller;
        this.serializer = serializer;
        this.dbManager = dbManager;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            // Menu 1
            System.out.println("\n1. Rechercher");
            System.out.println("2. Quitter");
            System.out.print("> ");

            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                menuSource(sc);
            } else if (choice.equals("2")) {
                System.out.println("Fin.");
                return;
            } else {
                System.out.println("Choix invalide.");
            }
        }
    }

    private void menuSource(Scanner sc) {
        // Menu 2
        System.out.println("\n1. Rechercher API");
        System.out.println("2. Rechercher dans la DataBase");
        System.out.print("> ");

        String choice = sc.nextLine().trim();

        if (choice.equals("1")) {
            searchApi(sc);
        } else if (choice.equals("2")) {
            searchDatabase(sc);
        } else {
            System.out.println("Choix invalide.");
        }
    }

    private void searchApi(Scanner sc) {
        System.out.print("Nom de la commune : ");
        String name = sc.nextLine().trim();

        JsonNode json = apiCaller.getCommunesByName(name);
        lastResults = serializer.toCommunes(json);

        menuAfterResults(sc);
    }

    private void searchDatabase(Scanner sc) {
        lastResults = dbManager.getAll();
        menuAfterResults(sc);
    }

    private void menuAfterResults(Scanner sc) {
        display(lastResults);

        if (lastResults == null || lastResults.isEmpty()) {
            return;
        }

        while (true) {
            // Menu 3
            System.out.println("\n1. Affiner recherche");
            System.out.println("2. Sauvegarder");
            System.out.println("3. Nouvelle recherche");
            System.out.println("4. Quitter");
            System.out.print("> ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    refine(sc);
                    return;
                }
                case "2" -> saveLastResults();
                case "3" -> {
                    return; // retour au Menu 1
                }
                case "4" -> System.exit(0);
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void refine(Scanner sc) {
        System.out.println("Donnez un nom plus précis pour affiner la recherche :");
        System.out.print("> ");
        String refineName = sc.nextLine().trim();

        JsonNode json = apiCaller.getCommunesByName(refineName);
        lastResults = serializer.toCommunes(json);

        display(lastResults);

        if (lastResults == null || lastResults.isEmpty()) {
            return;
        }

        while (true) {
            // Menu 4
            System.out.println("\n1. Nouvelle recherche");
            System.out.println("2. Sauvegarder");
            System.out.println("3. Quitter");
            System.out.print("> ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    return; // retour Menu 1
                }
                case "2" -> saveLastResults();
                case "3" -> System.exit(0);
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void saveLastResults() {
        if (lastResults == null || lastResults.isEmpty()) {
            System.out.println("Rien à sauvegarder.");
            return;
        }

        dbManager.save(lastResults);
        System.out.println("Sauvegarde OK.");
    }

    private void display(List<Commune> communes) {
        if (communes == null || communes.isEmpty()) {
            System.out.println("Aucun résultat.");
            return;
        }

        System.out.println("\n--- Résultats (" + communes.size() + ") ---");
        for (Commune c : communes) {
            System.out.println(c);
        }
    }
}
