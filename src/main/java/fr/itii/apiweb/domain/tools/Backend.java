package fr.itii.apiweb.domain.tools;

import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.domain.models.*;

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
        //Nicolas à remplir
        return null;
    }

    public List<Commune> searchCommuneFromAPIByCodePostal(String value){
        //Nicolas à remplir
        return null;
    }

    public List<Commune> searchCommuneFromAPIByDepartement(String value){
        //Nicolas à remplir
        return null;
    }

    public List<Commune> searchCommuneFromDBByNom(String value){
        //Nathan à remplir
        return null;
    }

    public List<Commune> searchCommuneFromDBByCodePostal(String value){
        //Nathan à remplir
        return null;
    }

    public  List<Commune> searchCommuneFromDBByDepartement(String value){
        //Nathan à remplir
        return null;
    }

    public void saveCommune(List<Commune> listeCommune) {
        //Nathan à modifier
        db.save(listeCommune);
    }

    public void deleteCommune() {
        //Nathan à modifier
        db.deleteAll();
    }

    // ==========================================================
    //  Etablissement
    //  =========================================================

    public List<Etablissement> searchEtablissementFromAPIByNomCommune(String value){
        //Lilian à remplir
        return null;
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
