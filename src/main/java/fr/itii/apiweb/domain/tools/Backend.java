package fr.itii.apiweb.domain.tools;

import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.data.remote.EtablissementApiCaller;
import fr.itii.apiweb.domain.models.*;
import fr.itii.apiweb.domain.models.api_models.CommunesFieldsEnum;
import fr.itii.apiweb.domain.models.api_models.EtablissementsFieldsEnum;
import fr.itii.apiweb.domain.models.db_models.CommunesCol;
import fr.itii.apiweb.domain.models.db_models.EtablissementsCol;

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
        JsonNode json = api.getCommunes(CommunesFieldsEnum.NOM, value, "100");
        return serializer.toCommunes(json);
    }

    public List<Commune> searchCommuneFromAPIByCodePostal(String value){
        JsonNode json = api.getCommunes(CommunesFieldsEnum.CODE_POSTAL, value, "100");
        return serializer.toCommunes(json);
    }

    public List<Commune> searchCommuneFromAPIByDepartement(String value){
        JsonNode json = api.getCommunes(CommunesFieldsEnum.DEPARTEMENT, value, "100");
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
        db.saveCommunes(listeCommune);
    }

    public void deleteCommune() {
        db.deleteCommunes();
    }

    // ==========================================================
    //  Etablissement
    //  =========================================================

    public List<Etablissement> searchEtablissementFromAPIByNomCommune(String value){
        JsonNode json = etablissementApiCaller.getEtablissements(EtablissementsFieldsEnum.NOM_COMMUNE, value, "100");
        return serializer.toEtablissements(json);
    }

    public List<Etablissement> searchEtablissementFromAPIByCodeCommune(String value){
        JsonNode json = etablissementApiCaller.getEtablissements(EtablissementsFieldsEnum.CODE_COMMUNE, value, "100");
        return serializer.toEtablissements(json);
    }

    public List<Etablissement> searchEtablissementFromAPIByDepartement(String value){
        JsonNode json = etablissementApiCaller.getEtablissements(EtablissementsFieldsEnum.CODE_DEPARTEMENT, value, "100");
        return serializer.toEtablissements(json);
    }

    public List<Etablissement> searchEtablissementFromDBByNomCommune(String value){
        return db.getJoin(CommunesCol.NOM, value, true);
    }

    public List<Etablissement> searchEtablissementFromDBByCodeCommune(String value){
        return db.getEtablissements(EtablissementsCol.CODE_COMMUNE, value, false);
    }

    public  List<Etablissement> searchEtablissementFromDBByDepartement(String value){
        return db.getJoin(CommunesCol.CODE_DEPARTEMENT, value, true);
    }

    public void saveEtablissement(List<Etablissement> listeEtablissement) {
        db.saveEtablissements(listeEtablissement);
    }

    public void deleteEtablissement() {
        db.deleteEtablissements();
    }



}
