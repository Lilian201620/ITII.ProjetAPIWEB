package fr.itii.apiweb;

import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.domain.tools.JSONSerializer;
import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.ui.Terminal;

public class Main {

    public static void main(String[] args) {

        // Initialisation des briques
        APICaller apiCaller = new APICaller();
        JSONSerializer serializer = new JSONSerializer();

        // DBManager en singleton (Nathan)
        DBManager dbManager = DBManager.getInstance();

        // Lancement du terminal (interface utilisateur)
        Terminal terminal = new Terminal(apiCaller, serializer, dbManager);
        terminal.start();
    }
}
