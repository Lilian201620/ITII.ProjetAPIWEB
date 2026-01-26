package fr.itii.apiweb.ui;

import fr.itii.apiweb.api.APICaller;
import fr.itii.apiweb.model.Commune;
import fr.itii.apiweb.serializer.JSONSerializer;

import java.util.List;
import java.util.Scanner;

public class Terminal {

    private final APICaller apiCaller;
    private final JSONSerializer serializer;

    public Terminal(APICaller apiCaller, JSONSerializer serializer) {
        this.apiCaller = apiCaller;
        this.serializer = serializer;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Projet APIWeb ===");
            System.out.println("1. Rechercher une commune par nom");
            System.out.println("2. Rechercher une commune par code INSEE");
            System.out.println("3. Quitter");
            System.out.print("Votre choix : ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> searchByName(scanner);
                case "2" -> searchByInsee(scanner);
                case "3" -> {
                    running = false;
                    System.out.println("Fin du programme.");
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void searchByName(Scanner scanner) {
        System.out.print("Nom de la commune : ");
        String name = scanner.nextLine();

        String json = apiCaller.getCommuneByName(name);
        List<Commune> communes = serializer.serializeCityResult(json);

        display(communes);
    }

    private void searchByInsee(Scanner scanner) {
        System.out.print("Code INSEE : ");
        String insee = scanner.nextLine();

        String json = apiCaller.getCommuneByInsee(insee);
        List<Commune> communes = serializer.serializeCityResult(json);

        display(communes);
    }

    private void display(List<Commune> communes) {
        if (communes == null || communes.isEmpty()) {
            System.out.println("Aucun résultat.");
            return;
        }

        for (Commune c : communes) {
            System.out.println("--------------------");
            System.out.println("Nom : " + c.getNom());
            System.out.println("Code INSEE : " + c.getCodeInsee());
            System.out.println("Population : " + c.getPopulation());
            System.out.println("Centre : " + c.getCentre());
        }
    }
}
