package fr.itii.apiweb.ui;

import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;

import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.domain.models.Commune;
import fr.itii.apiweb.domain.tools.JSONSerializer;
import fr.itii.apiweb.data.local.DBManager;

public class Terminal {

    private final APICaller apiCaller;
    private final JSONSerializer serializer;
    private final DBManager dbManager;

    public Terminal(APICaller apiCaller,
                    JSONSerializer serializer,
                    DBManager dbManager) {
        this.apiCaller = apiCaller;
        this.serializer = serializer;
        this.dbManager = dbManager;
    }

    /**
     * Lancement du terminal (interface utilisateur)
     */
    public void start() {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {

            System.out.println("\n=== Projet APIWeb ===");
            System.out.println("1 - Rechercher une commune par nom");
            System.out.println("2 - Quitter");
            System.out.print("Choix : ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> searchByName(scanner);
                case "2" -> {
                    running = false;
                    System.out.println("Fin du programme.");
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    /**
     * Recherche par nom de commune
     */
    private void searchByName(Scanner scanner) {

        System.out.print("Nom de la commune : ");
        String name = scanner.nextLine().trim();

        // 1. Appel API
        JsonNode json = apiCaller.getCommunesByName(name);

        // 2. JSON -> Objets Commune
        List<Commune> communes = serializer.toCommunes(json);


        // 3. Affichage (output)
        display(communes);
    }

    /**
     * Affichage des communes
     */
    private void display(List<Commune> communes) {

        if (communes == null || communes.isEmpty()) {
            System.out.println("Aucune commune trouvée.");
            return;
        }

        System.out.println("\n--- Résultats (" + communes.size() + ") ---");
        for (Commune commune : communes) {
            System.out.println(commune.toString());
        }
    }
}
