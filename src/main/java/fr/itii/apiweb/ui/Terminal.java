package fr.itii.apiweb.ui;

import fr.itii.apiweb.data.local.DBManager;

import java.util.List;
import java.util.Scanner;

public class Terminal {

    Scanner sc = new Scanner(System.in);

    private final Integer PAGE_SIZE = 10;
    private Integer showIndex = 0;

    public void clear(){
        for(int i = 0; i < 1; i++){
            System.out.println();
        }
    }

    public String scan(){
        return sc.nextLine().trim();
    }


    public void init() {
        System.out.println(Font.PINK + "   _______                                     ___      ");
        System.out.println("  /  ____/   ________   ___  ___   ___  ___   /  /      ___   _______   _______    ");
        System.out.println(" /  / ___   /  __   /  /  / /  /  |  | /  /  /  /      /  /  /  ____/  /  __   \\  ");
        System.out.println("/  /_/  /  /  /_/  /  /  /_/  /   |  |/  /  /  /___   /  /  /  ____/  /  /  /  /  ");
        System.out.println("\\______/   \\______/   \\___,__/    |_____/  /______/  /__/  /______/  /__/  /__/   " + Font.RESET);
        System.out.println("\n" + Font.PINK + "     ========== SYSTEM INFORMATION ==========" + Font.RESET);
        System.out.println("-----------------------------------------------------");
        System.out.println("Version:                1.0.0-STABLE");
        System.out.println("Authors:                Enzo, Lilian, Nathan, Nicolas");
        System.out.println("Status API:             "+ Font.GREEN + "Connected [✓]"  + Font.RESET);
        System.out.println("Port:                   Listening on 80");
        System.out.println("Status Database:        " + (DBManager.getInstance().isConnected() ? Font.GREEN + "Connected [✓]" : Font.RED + "Not connected [x]") + Font.RESET);
        System.out.println("Port:                   Listening on 5432");
        System.out.println("OS:                     " + System.getProperty("os.name"));
        System.out.println("Java:                   " + System.getProperty("java.version"));
        System.out.println("-----------------------------------------------------");
    }


    public String showMenu() {
        clear();
        System.out.println("\n" + Font.BOLD + Font.YELLOW + "==== Menu Principal ====" + Font.RESET);
        System.out.println("1. " + Font.CYAN + "Appel API" + Font.RESET);
        System.out.println("2. " + Font.CYAN + "Lire la Database" + Font.RESET);
        System.out.println("3. " + Font.CYAN + "Supprimer une table" + Font.RESET);
        System.out.println("4. " + Font.CYAN + "Afficher la meteo" + Font.RESET);
        System.out.println("5. " + Font.CYAN + "Rechercher les entreprises d'une commune" + Font.RESET);
        System.out.println("0. " + Font.CYAN + "Quitter" + Font.RESET);
        System.out.print(">");

        return scan();
    }


    public String showMenuSearchAPI() {
        clear();
        System.out.println("\n" + Font.BOLD + Font.YELLOW + "==== Appel API ====" + Font.RESET);
        System.out.println("1. " + Font.CYAN + "Commune par nom" + Font.RESET);  //Page suivante
        System.out.println("2. " + Font.CYAN + "Commune par code postal" + Font.RESET);
        System.out.println("3. " + Font.CYAN + "Commune par departement" + Font.RESET);
        System.out.println("4. " + Font.CYAN + "Etablissement par code postal" + Font.RESET);
        System.out.println("5. " + Font.CYAN + "Etablissement par departement" + Font.RESET);
        System.out.print(">");

        return scan();
    }


    public String showMenuAPI() {
        clear();
        System.out.println("\n" + Font.BOLD + Font.GREEN + "==== Action API ====" + Font.RESET);
        System.out.println("1. " + Font.CYAN + "Page precedente" + Font.RESET);
        System.out.println("2. " + Font.CYAN + "Page suivante" + Font.RESET);
        System.out.println("3. " + Font.CYAN + "Sauvegarder par indice" + Font.RESET);
        System.out.println("4. " + Font.CYAN + "Sauvegarder tout" + Font.RESET);
        System.out.println("5. " + Font.CYAN + "Nouvelle recherche" + Font.RESET);
        System.out.println("6. " + Font.CYAN + "Retour" + Font.RESET);
        System.out.print(">");

        return scan();
    }


    public String showMenuSearchDB(){
        clear();
        System.out.println("\n" + Font.BOLD + Font.YELLOW + "==== Lire la Database ====" + Font.RESET);
        System.out.println("1. " + Font.CYAN + "Commune par nom" + Font.RESET);
        System.out.println("2. " + Font.CYAN + "Commune par code postal" + Font.RESET);
        System.out.println("3. " + Font.CYAN + "Commune par departement" + Font.RESET);
        System.out.println("4. " + Font.CYAN + "Commune par region" + Font.RESET);
        System.out.println("5. " + Font.CYAN + "Etablissement par nom" + Font.RESET);
        System.out.println("6. " + Font.CYAN + "Etablissement par type" + Font.RESET);
        System.out.println("7. " + Font.CYAN + "Etablissement par nom de commune" + Font.RESET);
        System.out.println("8. " + Font.CYAN + "Etablissement par code postal" + Font.RESET);
        System.out.println("9. " + Font.CYAN + "Etablissement par departement" + Font.RESET);
        System.out.println("10. " + Font.CYAN + "Etablissement par region" + Font.RESET);

        return scan();
    }


    public String showMenuDB() {
        System.out.println("\n" + Font.BOLD + Font.GREEN + "==== Action sur la DB ====" + Font.RESET);
        System.out.println("1. " + Font.CYAN + "Page precedente" + Font.RESET);
        System.out.println("2. " + Font.CYAN + "Page suivante" + Font.RESET);
        System.out.println("3. " + Font.CYAN + "Supprimer par indice" + Font.RESET);
        System.out.println("4. " + Font.CYAN + "Supprimer tout" + Font.RESET);
        System.out.println("5. " + Font.CYAN + "Nouvelle recherche" + Font.RESET);
        System.out.println("6. " + Font.CYAN + "Retour" + Font.RESET);
        System.out.println(Font.ITALIC + "" + Font.GREY + "Supprimer une commune, supprimera ses etablissements..." + Font.RESET);
        System.out.print(">");

        return scan();
    }


    public String showConfig(String title, String msg){
        clear();
        System.out.println("\n" + Font.BOLD + Font.YELLOW + "==== "+ title + " ====" + Font.RESET);
        System.out.print(msg);

        return scan();
    }


    public String showSelect (String title, String msg){
        System.out.println("\n" + Font.BOLD + Font.GREEN + "==== "+ title + " ====" + Font.RESET);
        System.out.println(Font.ITALIC + "" + Font.GREY + "Exemple de saisi: 1 4 7-10 => indices selectionnes: 1,4,7,8,9,10" + Font.RESET);
        System.out.print(msg);

        return sc.nextLine().trim();
    }


    public String showMenuDeleteDB(){
        clear();
        System.out.println("\n" + Font.BOLD + Font.YELLOW + "==== Table ====" + Font.RESET);
        System.out.println("1. " + Font.CYAN + "Supprimer communes et etablissements" + Font.RESET);
        System.out.println("2. " + Font.CYAN + "Supprimer etablissements" + Font.RESET);
        System.out.println("3. " + Font.CYAN + "Retour" + Font.RESET);

        return scan();
    }


    public <T> void showList(List<T> results, Header header) {
        clear();
        if (results == null || results.isEmpty()) {
            System.out.println("\n" + Font.BOLD + Font.GREEN + "==== Resultats ====" + Font.RESET);
            System.out.println("Aucun resultat...");
            return;
        }

        System.out.println("\n" + Font.BOLD + Font.GREEN + "==== Resultats 1 à " + results.size() + " ====" + Font.RESET);
        System.out.println(Font.CYAN + String.format("%-6s","#") + header.toString() + Font.RESET);
        for (int i = 0; i < results.size(); i++) {
            System.out.println(String.format("%-6s",(i + 1)) + results.get(i));
        }
    }


    public <T> void showList(List<T> results, Header header, int index) {
        clear();
        if (results == null || results.isEmpty()) {
            System.out.println("\n" + Font.BOLD + Font.GREEN + "==== Resultats ====" + Font.RESET);
            System.out.println("Aucun resultat...");
            return;
        }

        if(index >= results.size()) {
            showList(results, header, index - PAGE_SIZE);
            return;
        }

        showIndex = Math.max(0, index);
        int end = Math.min(showIndex + PAGE_SIZE, results.size());

        System.out.println("\n" + Font.BOLD + Font.GREEN + "==== Resultats " + (showIndex + 1) + " à " + end + " / " + results.size() + " ====" + Font.RESET);
        System.out.println(Font.CYAN + String.format("%-6s ","#") + header.toString() + Font.RESET);
        for (int i = showIndex; i < end; i++) {
            System.out.println(String.format("%-6s ",(i + 1)) + results.get(i));
        }

        showIndex += PAGE_SIZE;
    }

    public Integer getIndex(){
        return showIndex;
    }

}
