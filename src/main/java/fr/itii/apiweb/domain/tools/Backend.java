package fr.itii.apiweb.domain.tools;

import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.domain.models.*;
import fr.itii.apiweb.domain.models.db_models.CommunesCol;

import java.util.List;

public class Backend {

    //Commune (unique)
        //nom de ville
        //Code postal
        //code department


    //Etablissement (unique)
        //code commune
        //nom de la commune
        //code departement

    private final APICaller api = new APICaller();
    private final DBManager db = DBManager.getInstance();
    private final JSONSerializer serializer = new JSONSerializer();

    // ==========================================================
    //  Commune
    //  =========================================================

    public List<Commune> searchCommuneFromAPIByNom(String value){
        JsonNode json = api.getCommunesByName(value, "1000");
        return serializer.toCommunes(json);
    }

    public List<Commune> searchCommuneFromAPIByCodePostal(String value){
        JsonNode json = api.getCommunesByCodePostal(value, "1000");
        return serializer.toCommunes(json);
    }

    public List<Commune> searchCommuneFromAPIByDepartement(String value){
        JsonNode json = api.getCommunesByDepartement(value, "1000");
        return serializer.toCommunes(json);
    }

    public List<Commune> searchCommuneFromDBByNom(String value){
        return db.getCommunes(CommunesCol.NOM, value, false);
    }

    public List<Commune> searchCommuneFromDBByCodePostal(String value){
        return db.getCommunes(CommunesCol.CODE_POSTAL, value, false);
    }

    public  List<Commune> searchCommuneFromDBByDepartement(String value){
        return db.getCommunes(CommunesCol.CODE_DEPARTEMENT, value, false);
    }

    public void saveCommune(List<Commune> listeCommune) {
        //Nathan à modifier
        db.saveCommunes(listeCommune);
    }

    public void deleteCommune() {
        //Nathan à modifier
        db.deleteCommunes();
    }

    // ==========================================================
    //  Etablissement
    //  =========================================================

    public List<Etablissement> searchEtablissementFromAPIByNomCommune(String value){
        //Lilian à remplir
        return  null;
    }

    public List<Etablissement> searchEtablissementFromAPIByCodeCommune(String value){
        //Lilian à remplir
        return null;
    }

    public List<Etablissement> searchEtablissementFromAPIByDepartement(String value){
        //Lilian à remplir
        return null;
    }

    public List<Etablissement> searchEtablissementFromDBByNomCommune(String value){
        //Lilian à remplir
        return null;
    }

    public List<Etablissement> searchEtablissementFromDBByCodeCommune(String value){
        //Lilian à remplir
        return null;
    }

    public  List<Etablissement> searchEtablissementFromDBByDepartement(String value){
        //Lilian à remplir
        return null;
    }

    public void saveEtablissement(List<Etablissement> listeEtablissement) {
        //Lilian à remplir
    }

    public void deleteEtablissement() {
        //Lilian a remplir
    }



}
