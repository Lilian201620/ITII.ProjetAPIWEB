package fr.itii.apiweb.ui;

import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.domain.models.*;
import fr.itii.apiweb.domain.tools.JSONSerializer;

import java.util.List;

public class Backend {

    //Commune (unique)
        //Code postal
        //code department
        //nom de ville

    //Etablissement (unique)
        //code commune
        //nom de la commune
        //code departement

    private final APICaller api = new APICaller();
    private final DBManager db = DBManager.getInstance();
    private final JSONSerializer serializer = new JSONSerializer();

    public List<Commune> searchCommuneFromAPI(String key, String value){
        JsonNode json = api.getCommunesByName(value, "1000");
        return serializer.toCommunes(json);
    }

    public List<Commune> searchCommuneFromDB(String key, String value){
        return db.get(key, value, false);
    }

    public List<Etablissement> searchEtablissementFromAPI(String key, String value){
        //JsonNode json = api.getEByName(value, "1000");
        return null;
    }

    public List<Etablissement> searchEtablissementFromDB(String key, String value){
        return null;
    }

    public void saveCommune(List<Commune> results) {
        db.save(results);
    }

    public void saveEtablissement(List<Etablissement> results) {
        //db.save(results)
    }

    public void deleteCommuneDB() {
        db.deleteAll();
    }

    public void deleteEtablissementDB() {
        db.deleteAll();
    }
}
