package fr.itii.apiweb;

import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.domain.tools.JSONSerializer;
import fr.itii.apiweb.ui.NewTerminal;

public class Main {

    public static void main(String[] args) {
        APICaller apiCaller = new APICaller();
        JSONSerializer serializer = new JSONSerializer();
        DBManager dbManager = DBManager.getInstance();

        NewTerminal terminal = new NewTerminal();
       // NewTerminal.flow();
    }
}
