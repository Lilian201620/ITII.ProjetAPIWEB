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
            System.out.println("1 - Rechercher une commune par nom");
            System.out.println("2 - Sauvegarder la dernière recherche (.save)");
            System.out.println("3 - Quitter");
            System.out.print("Choix : ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> searchByNameFlow(scanner);
                case "2" -> saveLastResults();
                case "3" -> running = false;
                default -> System.out.println("Choix invalide.");
            }
        }

        System.out.println("Fin du programme.");
    }

    private void searchByNameFlow(Scanner scanner) {
        // 1ère recherche
        System.out.print("Nom de la commune : ");
        String name = scanner.nextLine().trim();

        List<Commune> communes = fetchCommunesByName(name);
        display(communes);

        // stocker pour .save
        lastResults = communes;

        // message demandé + possibilité d'affiner
        if (communes != null && communes.size() > 1) {
            System.out.println("\nDonnez un nom plus précise pour affiner la recherche (ou appuyez sur Entrée pour ignorer) :");
            String refine = scanner.nextLine().trim();

            if (!refine.isEmpty()) {
                List<Commune> refined = fetchCommunesByName(refine);
                display(refined);
                lastResults = refined;
            }
        }
    }

    private List<Commune> fetchCommunesByName(String name) {
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
            System.out.println("Aucune recherche à sauvegarder. Faites une recherche d'abord.");
            return;
        }

        // Décommenter quand Nathan confirme la méthode exacte
        // for (Commune c : lastResults) {
        //     dbManager.save(c);
        // }

        System.out.println("Sauvegarde demandée (.save). (À activer quand DBManager est prêt)");
    }
}
