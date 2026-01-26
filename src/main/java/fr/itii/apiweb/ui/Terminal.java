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

    private List<Commune> lastResults = null; // pour .save

    public Terminal(APICaller apiCaller, JSONSerializer serializer, DBManager dbManager) {
        this.apiCaller = apiCaller;
        this.serializer = serializer;
        this.dbManager = dbManager;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Projet APIWeb ===");
            System.out.println("1 - Rechercher par nom");
            System.out.println("2 - .save (sauvegarder la dernière recherche)");
            System.out.println("3 - Quitter");
            System.out.print("Choix : ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> searchByName(scanner);
                case "2" -> saveLastResults();
                case "3" -> running = false;
                default -> System.out.println("Choix invalide.");
            }
        }

        System.out.println("Fin.");
    }

    private void searchByName(Scanner scanner) {
        System.out.print("Nom de la commune : ");
        String name = scanner.nextLine().trim();

        JsonNode json = apiCaller.getCommunesByName(name);
        List<Commune> communes = serializer.toCommunes(json);

        if (communes == null || communes.isEmpty()) {
            System.out.println("Aucun résultat.");
            lastResults = null;
            return;
        }

        System.out.println("\n--- Résultats (" + communes.size() + ") ---");
        for (Commune c : communes) {
            System.out.println(c);
        }

        // On garde en mémoire la dernière recherche pour .save
        lastResults = communes;

        System.out.println("\nDonnez un nom plus précise pour affiner la recherche (Entrée pour ignorer) :");
        String refine = scanner.nextLine().trim();

        if (!refine.isEmpty()) {
            JsonNode json2 = apiCaller.getCommunesByName(refine);
            List<Commune> refined = serializer.toCommunes(json2);

            if (refined == null || refined.isEmpty()) {
                System.out.println("Aucun résultat.");
                lastResults = null;
                return;
            }

            System.out.println("\n--- Résultats (" + refined.size() + ") ---");
            for (Commune c : refined) {
                System.out.println(c);
            }

            lastResults = refined;
        }
    }

    private void saveLastResults() {
        if (lastResults == null || lastResults.isEmpty()) {
            System.out.println("Rien à sauvegarder. Faites une recherche avant.");
            return;
        }

        try {
            dbManager.save(lastResults);
            System.out.println("Sauvegarde OK.");
        } catch (Exception e) {
            System.out.println("Erreur pendant la sauvegarde : " + e.getMessage());
        }
    }
}
