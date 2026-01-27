package fr.itii.apiweb.ui;

import fr.itii.apiweb.domain.models.Commune;
import fr.itii.apiweb.domain.models.Etablissement;

import java.util.List;
import java.util.Scanner;

public class Affichage {

    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";

    Scanner sc = new Scanner(System.in);

    private final Integer PAGE_SIZE = 10;
    private Integer showIndex = 0;

    public String showMenu() {
        System.out.println("\n" + BOLD + RED + "==== Menu Principal ====" + RESET);
        System.out.println(BOLD + "1. " + CYAN + "Recherche API" + RESET);
        System.out.println(BOLD + "2. " + CYAN + "Recherche dans la DataBase" + RESET);
        System.out.println(BOLD + "3. " + CYAN + "Supprimer la database" + RESET);
        System.out.println(BOLD + "4. " + CYAN + "Quitter" + RESET);
        System.out.print(">");

        return sc.nextLine().trim();
    }

    public String showMenuSearchAPI() {
        System.out.println("\n" + BOLD + YELLOW + "==== Recherche API ====" + RESET);
        System.out.println(BOLD + "1. " + CYAN + "Commune par nom" + RESET);  //Page suivante
        System.out.println(BOLD + "2. " + CYAN + "Commune par code postal" + RESET);
        System.out.println(BOLD + "3. " + CYAN + "Commune par departement" + RESET);
        System.out.println(BOLD + "4. " + CYAN + "Etablissement par nom de commune" + RESET);
        System.out.println(BOLD + "5. " + CYAN + "Etablissement par code postal" + RESET);
        System.out.println(BOLD + "6. " + CYAN + "Etablissement par departement" + RESET);
        System.out.print(">");

        return sc.nextLine().trim();
    }

    public String showMenuAPI() {
        System.out.println("\n" + BOLD + GREEN + "==== Action API ====" + RESET);
        System.out.println(BOLD + "1. " + CYAN + "Page précédente" + RESET);  //Page suivante
        System.out.println(BOLD + "2. " + CYAN + "Page suivante" + RESET);
        System.out.println(BOLD + "3. " + CYAN + "Sauvegarder" + RESET);            //Save dans DB
        System.out.println(BOLD + "4. " + CYAN + "Nouvelle recherche" + RESET);     //Nouvelle API
        System.out.println(BOLD + "5. " + CYAN + "Retour" + RESET);                 //Retour au menu principal
        System.out.print(">");

        String c;
        do {
            c = sc.nextLine().trim();
        } while (c.isEmpty());
        return c;

    }

    public String showMenuSearchDB(){
        System.out.println("\n" + BOLD + "1. Commune par nom" + RESET);  //Page suivante
        System.out.println(BOLD + "2. Commune par code postal" + RESET);
        System.out.println(BOLD + "3. Commune par departement" + RESET);
        System.out.println(BOLD + "4. Etablissement par nom de commune" + RESET);
        System.out.println(BOLD + "5. Etablissement par code postal" + RESET);
        System.out.println(BOLD + "6. Etablissement par departement" + RESET);

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



    public <T> void showList(List<T> results) {
        showList(results, 0);
    }

    public <T> void showList(List<T> results, int page) {
        if (results == null || results.isEmpty()) {
            System.out.println("\n" + BOLD + GREEN + "==== Résultats ====" + RESET);
            System.out.println("Aucun résultat.");
            return;
        }

        showIndex = Math.max(0, page);
        int end = Math.min(showIndex + PAGE_SIZE, results.size());

        System.out.println("\n" + BOLD + GREEN + "==== Résultats " + (showIndex + 1) + " à " + end + " / " + results.size() + " ====" + RESET);
        for (int i = showIndex; i < end; i++) {
            System.out.println(String.format("#%-4s",(i + 1)) + " " + results.get(i));
        }
        showIndex = end;
    }

    public Integer getIndex(){
        return showIndex;
    }

}
