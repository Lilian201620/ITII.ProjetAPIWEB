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

    private List<Commune> lastResults = null;

    public Terminal(APICaller apiCaller, JSONSerializer serializer, DBManager dbManager) {
        this.apiCaller = apiCaller;
        this.serializer = serializer;
        this.dbManager = dbManager;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            // MENU 1
            System.out.println("\n1. Rechercher");
            System.out.println("2. Quitter");
            System.out.print("> ");

            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                doSearch(sc);
            } else if (choice.equals("2")) {
                System.out.println("Fin.");
                return;
            } else {
                System.out.println("Choix invalide.");
            }
        }
    }

    private void doSearch(Scanner sc) {
        System.out.print("Nom de la commune : ");
        String name = sc.nextLine().trim();

        lastResults = fetchByName(name);
        display(lastResults);

        if (lastResults == null || lastResults.isEmpty()) {
            return; // retour menu principal
        }

        // MENU 2 (après résultats)
        while (true) {
            System.out.println("\n1. Affiner recherche");
            System.out.println("2. Sauvegarder");
            System.out.println("3. Nouvelle recherche");
            System.out.println("4. Quitter");
            System.out.print("> ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    doRefine(sc); // va ensuite au MENU 3
                    return;       // revient au menu principal après MENU 3
                }
                case "2" -> saveLastResults();
                case "3" -> {
                    doSearch(sc); // nouvelle recherche directe
                    return;
                }
                case "4" -> {
                    System.out.println("Fin.");
                    System.exit(0);
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void doRefine(Scanner sc) {
        System.out.println("Donnez un nom plus précis pour affiner la recherche :");
        System.out.print("> ");
        String refineName = sc.nextLine().trim();

        lastResults = fetchByName(refineName);
        display(lastResults);

        if (lastResults == null || lastResults.isEmpty()) {
            return; // retour menu principal
        }

        // MENU 3 (après affinage)
        while (true) {
            System.out.println("\n1. Nouvelle recherche");
            System.out.println("2. Sauvegarder");
            System.out.println("3. Quitter");
            System.out.print("> ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    doSearch(sc);
                    return;
                }
                case "2" -> saveLastResults();
                case "3" -> {
                    System.out.println("Fin.");
                    System.exit(0);
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private List<Commune> fetchByName(String name) {
        JsonNode json = apiCaller.getCommunesByName(name);
        return serializer.toCommunes(json);
    }

    private void display(List<Commune> communes) {
        if (communes == null || communes.isEmpty()) {
            System.out.println("Aucun résultat.");
            return;
        }

        System.out.println("\n--- Résultats (" + communes.size() + ") ---");
        for (Commune c : communes) {
            System.out.println(c.toString());
        }
    }

    private void saveLastResults() {
        if (lastResults == null || lastResults.isEmpty()) {
            System.out.println("Rien à sauvegarder.");
            return;
        }

        try {
            dbManager.save(lastResults);
            System.out.println("Sauvegarde OK.");
        } catch (Exception e) {
            System.out.println("Erreur sauvegarde : " + e.getMessage());
        }
    }
}
