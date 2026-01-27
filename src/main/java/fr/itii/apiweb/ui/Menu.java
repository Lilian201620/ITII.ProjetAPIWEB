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
        System.out.println("\n" + BOLD + "1. Rechercher API" + RESET);
        System.out.println(BOLD + "2. Rechercher dans la DataBase" + RESET);
        System.out.println(BOLD + "3. Supprimer la database" + RESET);
        System.out.println(BOLD + "4. Quitter" + RESET);
        System.out.print(">");

        return sc.nextLine().trim();
    }

    public String showMenuAPI() {
        System.out.println("\n" + BOLD + "1. Page précédente" + RESET);  //Page suivante
        System.out.println(BOLD + "2. Page suivante" + RESET);
        System.out.println(BOLD + "3. Sauvegarder" + RESET);            //Save dans DB
        System.out.println(BOLD + "4. Nouvelle recherche" + RESET);     //Nouvelle API
        System.out.println(BOLD + "5. Retour" + RESET);                 //Retour au menu principal
        System.out.print(">");

        return sc.nextLine().trim();
    }

    public String showMenuDB() {
        System.out.println("\n" + BOLD + "1. Page précédente" + RESET);  //Page suivante
        System.out.println(BOLD + "2. Page suivante" + RESET);
        System.out.println(BOLD + "3. Nouvelle recherche" + RESET);     //Nouvelle API
        System.out.println(BOLD + "4. Retour" + RESET);                 //Retour au menu principal
        System.out.print(">");

        return sc.nextLine().trim();
    }

}
