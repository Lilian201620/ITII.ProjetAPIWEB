package fr.itii.apiweb.ui;

import java.util.List;
import java.util.Scanner;

public class Terminal {

    private final String RESET = "\u001B[0m";

    private final String BOLD = "\u001B[1m";
    private final String ITALIC = "\u001B[3m";
    private final String CYAN = "\u001B[36m";
    private final String GREEN = "\u001B[32m";
    private final String YELLOW = "\u001B[33m";
    private final String RED = "\u001B[31m";
    private final String GREY = "\u001B[90m";

    Scanner sc = new Scanner(System.in);

    private final Integer PAGE_SIZE = 10;
    private Integer showIndex = 0;

    public void clear(){
        for(int i = 0; i < 20; i++){
            System.out.println();
        }
    }

    public String scan(){
        return sc.nextLine().trim();
    }

    public String showMenu() {
        clear();
        System.out.println("\n" + BOLD + RED + "==== Menu Principal ====" + RESET);
        System.out.println("1. " + CYAN + "Appel API" + RESET);
        System.out.println("2. " + CYAN + "Lire la Database" + RESET);
        System.out.println("3. " + CYAN + "Supprimer une table" + RESET);
        System.out.println("0. " + CYAN + "Quitter" + RESET);
        System.out.print(">");

        return scan();
    }

    public String showMenuSearchAPI() {
        clear();
        System.out.println("\n" + BOLD + YELLOW + "==== Appel API ====" + RESET);
        System.out.println("1. " + CYAN + "Commune par nom" + RESET);  //Page suivante
        System.out.println("2. " + CYAN + "Commune par code postal" + RESET);
        System.out.println("3. " + CYAN + "Commune par departement" + RESET);
        System.out.println("4. " + CYAN + "Etablissement par nom de commune" + RESET);
        System.out.println("5. " + CYAN + "Etablissement par departement" + RESET);
        System.out.print(">");

        return scan();
    }

    public String showMenuAPI() {
        System.out.println("\n" + BOLD + GREEN + "==== Action API ====" + RESET);
        System.out.println("1. " + CYAN + "Page précédente" + RESET);
        System.out.println("2. " + CYAN + "Page suivante" + RESET);
        System.out.println("3. " + CYAN + "Sauvegarder par indice" + RESET);
        System.out.println("4. " + CYAN + "Sauvegarder tout" + RESET);
        System.out.println("5. " + CYAN + "Nouvelle recherche" + RESET);
        System.out.println("6. " + CYAN + "Retour" + RESET);
        System.out.print(">");

        return scan();
    }

    public String showMenuSearchDB(){
        clear();
        System.out.println("\n" + BOLD + YELLOW + "==== Lire la Database ====" + RESET);
        System.out.println("1. " + CYAN + "Commune par nom" + RESET);
        System.out.println("2. " + CYAN + "Commune par code postal" + RESET);
        System.out.println("3. " + CYAN + "Commune par departement" + RESET);
        System.out.println("4. " + CYAN + "Commune par region" + RESET);
        System.out.println("5. " + CYAN + "Etablissement par nom" + RESET);
        System.out.println("6. " + CYAN + "Etablissement par type" + RESET);
        System.out.println("7. " + CYAN + "Etablissement par nom de commune" + RESET);
        System.out.println("8. " + CYAN + "Etablissement par code postal" + RESET);
        System.out.println("9. " + CYAN + "Etablissement par departement" + RESET);
        System.out.println("10. " + CYAN + "Etablissement par region" + RESET);

        return scan();
    }

    public String showMenuDB() {
        System.out.println("\n" + BOLD + GREEN + "==== Action sur la DB ====" + RESET);
        System.out.println("1. " + CYAN + "Page précédente" + RESET);
        System.out.println("2. " + CYAN + "Page suivante" + RESET);
        System.out.println("3. " + CYAN + "Supprimer par indice" + RESET);
        System.out.println("4. " + CYAN + "Supprimer tout" + RESET);
        System.out.println("5. " + CYAN + "Nouvelle recherche" + RESET);
        System.out.println("6. " + CYAN + "Retour" + RESET);
        System.out.print(">");

        return scan();
    }

    public String showConfig(String title, String msg){
        clear();
        System.out.println("\n" + BOLD + YELLOW + "==== "+ title + " ====" + RESET);
        System.out.print(msg);

        return scan();
    }

    public String showAction(String title, String msg){
        System.out.println("\n" + BOLD + GREEN + "==== "+ title + " ====" + RESET);
        System.out.println(ITALIC + GREY + "Exemple de saisi: 1 4 7-10 => indice sauvegardé: 1,4,7,8,9,10" + RESET);
        System.out.print(msg);

        return sc.nextLine().trim();
    }

    public String showMenuDeleteDB(){
        clear();
        System.out.println("\n" + BOLD + YELLOW + "==== Table ====" + RESET);
        System.out.println("1. " + CYAN + "Supprimer commune" + RESET);
        System.out.println("2. " + CYAN + "Supprimer etablissement" + RESET);
        System.out.println("3. " + CYAN + "Retour" + RESET);

        return scan();
    }

    public <T> void showList(List<T> results) {
        showList(results, 0);
    }

    public <T> void showList(List<T> results, int index) {
        clear();
        if (results == null || results.isEmpty()) {
            System.out.println("\n" + BOLD + GREEN + "==== Résultats ====" + RESET);
            System.out.println("Aucun résultat.");
            return;
        }

        if(index > results.size()) {
            showList(results, index - PAGE_SIZE);
            return;
        }

        showIndex = Math.max(0, index);
        int end = Math.min(showIndex + PAGE_SIZE, results.size());

        System.out.println("\n" + BOLD + GREEN + "==== Résultats " + (showIndex + 1) + " à " + end + " / " + results.size() + " ====" + RESET);
        for (int i = showIndex; i < end; i++) {
            System.out.println(String.format("#%-4s",(i + 1)) + " " + results.get(i));
        }

        showIndex += PAGE_SIZE;
    }

    public Integer getIndex(){
        return showIndex;
    }

}
