package fr.itii.apiweb.domain.tools;

import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.data.remote.EtablissementApiCaller;
import fr.itii.apiweb.domain.models.*;
import fr.itii.apiweb.domain.models.api_models.CommunesFieldsEnum;
import fr.itii.apiweb.domain.models.api_models.EtablissementsFieldsEnum;
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
    private final EtablissementApiCaller etablissementApiCaller = new EtablissementApiCaller();
    private final DBManager db = DBManager.getInstance();
    private final JSONSerializer serializer = new JSONSerializer();

    // ==========================================================
    //  Commune
    //  =========================================================

    public List<Commune> searchCommuneFromAPIByNom(String value){
        JsonNode json = api.getCommunes(CommunesFieldsEnum.NOM, value, "1000");
        return serializer.toCommunes(json);
    }

    public List<Commune> searchCommuneFromAPIByCodePostal(String value){
        JsonNode json = api.getCommunes(CommunesFieldsEnum.CODE_POSTAL, value, "1000");
        return serializer.toCommunes(json);
    }

    public List<Commune> searchCommuneFromAPIByDepartement(String value){
        JsonNode json = api.getCommunes(CommunesFieldsEnum.DEPARTEMENT, value, "1000");
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
        JsonNode json = etablissementApiCaller.getEtablissements(EtablissementsFieldsEnum.NOM_COMMUNE, value, "1000");
        return serializer.toEtablissements(json);
    }

    public List<Etablissement> searchEtablissementFromAPIByCodeCommune(String value){
        JsonNode json = etablissementApiCaller.getEtablissements(EtablissementsFieldsEnum.CODE_COMMUNE, value, "1000");
        return serializer.toEtablissements(json);
    }

    public List<Etablissement> searchEtablissementFromAPIByDepartement(String value){
        JsonNode json = etablissementApiCaller.getEtablissements(EtablissementsFieldsEnum.CODE_DEPARTEMENT, value, "1000");
        return serializer.toEtablissements(json);
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
