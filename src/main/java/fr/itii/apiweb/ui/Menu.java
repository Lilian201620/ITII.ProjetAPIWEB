package fr.itii.apiweb.ui;

import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.domain.models.Commune;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";

    Scanner sc = new Scanner(System.in);

    public String showMenu() {
        System.out.println(BOLD + "1. Rechercher API" + RESET);
        System.out.println(BOLD + "2. Rechercher dans la DataBase" + RESET);
        System.out.println(BOLD + "3. Supprimer la database" + RESET);
        System.out.println(BOLD + "4. Quitter" + RESET);
        System.out.print(prompt());

        return sc.nextLine().trim();
    }

    public String showMenuAPI() {
        System.out.println(BOLD + "1. Elargir la recherche" + RESET);   //Plus de ligne
        System.out.println(BOLD + "2. Sauvegarder" + RESET);            //Save dans DB
        System.out.println(BOLD + "3. Nouvelle recherche" + RESET);     //Nouvelle API
        System.out.println(BOLD + "4. Retour" + RESET);                 //Retour au menu principal
        System.out.print(prompt());

        return sc.nextLine().trim();
    }

    public String showMenuDB() {
        System.out.println(BOLD + "1. Sauvegarder" + RESET);
        System.out.println(BOLD + "2. Nouvelle recherche" + RESET);
        System.out.println(BOLD + "3. Retour" + RESET);
        System.out.print(prompt());

        return sc.nextLine().trim();
    }

    public void switchPrincipal() {
        switch (showMenu()) {
            case "1" -> //searchApi();
                        //show(List, nombre);
                        //switchAPI
            case "2" -> //searchDatabase();
                        //show(List, nombre);
                        //switchDB
            case "3" -> //deleteDatabase();
            case "4" -> {
                info("Fin.");
                System.exit(0);
            }
            default -> switchPrincipal();
        }
    }

    public void switchAPI() {
        switch (showMenuAPI()) {
            case "1" -> {
                //searchAPI() -> showAPI()

            }
            case "2" -> //saveLastResults();
            case "3" -> {
                return;
            }
            case "4" -> System.exit(0);
            default -> //
        }
    }

    public switchDB() {
        switch (showMenuDB()) {
            case "1" -> {
                return;
            }
            case "2" ->
            case "3" -> System.exit(0);
            default -> //
        }
    }


}
}
