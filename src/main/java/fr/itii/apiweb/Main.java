package fr.itii.apiweb;

import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.domain.tools.JSONSerializer;
import fr.itii.apiweb.ui.Terminal;

public class Main {

    public static void main(String[] args) {
        APICaller apiCaller = new APICaller();
        JSONSerializer serializer = new JSONSerializer();
        DBManager dbManager = DBManager.getInstance();

        Terminal terminal = new Terminal(apiCaller, serializer, dbManager);
        terminal.start();
    }
}
