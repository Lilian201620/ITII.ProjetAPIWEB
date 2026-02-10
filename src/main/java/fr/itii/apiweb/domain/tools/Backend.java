package fr.itii.apiweb.domain.tools;

import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.domain.models.enumlist.APIField;
import fr.itii.apiweb.domain.models.enumlist.DBTable;
import fr.itii.apiweb.domain.models.enumlist.Font;
import fr.itii.apiweb.domain.models.objet.Commune;
import fr.itii.apiweb.domain.models.objet.Entreprise;
import fr.itii.apiweb.domain.models.objet.Etablissement;
import fr.itii.apiweb.domain.models.objet.Meteo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Backend {

    private final APICaller api = new APICaller();
    private final DBManager db = DBManager.getInstance();
    private final JSONSerializer serializer = new JSONSerializer();

    public Function<String, List<Commune>> searchCommuneFromDBByNom = this::searchCommuneFromDBByNom;
    public Function<String, List<Commune>> searchCommuneFromDBByCodePostal =  this::searchCommuneFromDBByCodePostal;
    public Function<String, List<Commune>> searchCommuneFromDBByDepartement = this::searchCommuneFromDBByDepartement;
    public Function<String, List<Commune>> searchCommuneFromDBByRegion =  this::searchCommuneFromDBByRegion;
    public Function<String, List<Etablissement>> searchEtablissementFromDBByNom = this::searchEtablissementFromDBByNom;
    public Function<String, List<Etablissement>> searchEtablissementFromDBByType = this::searchEtablissementFromDBByType;
    public Function<String, List<Etablissement>> searchEtablissementFromDBByNomCommune =  this::searchEtablissementFromDBByNomCommune;
    public Function<String, List<Etablissement>> searchEtablissementFromAPIByCodePostal = this::searchEtablissementFromAPIByCodePostal;
    public Function<String, List<Etablissement>> searchEtablissementFromDBByDepartement = this::searchEtablissementFromDBByDepartement;
    public Function<String, List<Etablissement>> searchEtablissementFromDBByRegion = this::searchEtablissementFromDBByRegion;

    // ==================================================
    //  Commune call API
    // ==================================================

    public List<Commune> searchCommuneFromAPIByNom(String value){
        JsonNode json = api.getCommunes(APIField.Commune.NOM, value, 100);
        return serializer.toCommunes(json);
    }

    public List<Commune> searchCommuneFromAPIByCodePostal(String value){
        JsonNode json = api.getCommunes(APIField.Commune.CODE_POSTAL, value, 100);
        return serializer.toCommunes(json);
    }

    public List<Commune> searchCommuneFromAPIByDepartement(String value){
        JsonNode json = api.getCommunes(APIField.Commune.CODE_DEPARTEMENT, value, 1000);
        return serializer.toCommunes(json);
    }

    // ==================================================
    //  Commune read DB
    // ==================================================

    public List<Commune> searchCommuneFromDBByNom(String value){
        return db.getCommunes(DBTable.Commune.NOM, value, false);
    }

    public List<Commune> searchCommuneFromDBByCodePostal(String value){
        return db.getCommunes(DBTable.Commune.CODES_POSTAUX, value);
    }

    public  List<Commune> searchCommuneFromDBByDepartement(String value){
        List<Commune> liste1 = db.getCommunes(DBTable.Commune.CODE_DEPARTEMENT, value, true);
        List<Commune> liste2 = db.getCommunes(DBTable.Commune.NOM_DEPARTEMENT, value, false);
        liste1.addAll(liste2);
        return liste1;
    }

    public List<Commune> searchCommuneFromDBByRegion(String value){
        List<Commune> liste1 =  db.getCommunes(DBTable.Commune.CODE_REGION, value, true);
        List<Commune> liste2 = db.getCommunes(DBTable.Commune.NOM_REGION, value, false);
        liste1.addAll(liste2);
        return liste1;
    }

    // ==================================================
    //  Commune save DB
    // ==================================================

    public void saveCommune(List<Commune> listeCommune){
        db.saveCommunes(listeCommune);
    }

    public void saveCommune(List<Commune> listeCommune, String select){
        if (!listeCommune.isEmpty()){
            saveCommune(serializer.toLists(listeCommune, select));
        }
    }

    // ==================================================
    //  Commune delete DB
    // ==================================================

    public void deleteCommune(){
        db.deleteCommunes();
    }

    public void deleteCommune(List<Commune> listeCommune){
        for(Commune c : listeCommune){
            db.deleteCommunes(DBTable.Commune.CODE_COMMUNE, c.getCodeCommune(), true);
        }
    }

    public void deleteCommune(List<Commune> listeCommune, String select){
        if (!listeCommune.isEmpty()){
            deleteCommune(serializer.toLists(listeCommune, select));
        }
    }

    // ==================================================
    //  Etablissement call API
    // ==================================================

    public List<Etablissement> searchEtablissementFromAPIByDepartement(String value){
        JsonNode json = api.getEtablissements(APIField.Etablissement.CODE_DEPARTEMENT,  "0" + value, 100);
        return serializer.toEtablissements(json);
    }

    public List<Etablissement> searchEtablissementFromAPIByCodePostal(String value){
        JsonNode json = api.getEtablissements(APIField.Etablissement.CODE_POSTAL,  value, 100);
        return serializer.toEtablissements(json);
    }

    // ==================================================
    //  Etablissement read DB
    // ==================================================

    public List<Etablissement> searchEtablissementFromDBByNom(String value){
        return db.getEtablissements(DBTable.Etablissement.NOM, value, false);
    }

    public List<Etablissement> searchEtablissementFromDBByType(String value){
        return db.getEtablissements(DBTable.Etablissement.TYPE, value, false);
    }

    public List<Etablissement> searchEtablissementFromDBByNomCommune(String value){
        return db.getJoin(DBTable.Commune.NOM, value, false);
    }

    public List<Etablissement> searchEtablissementFromDBByCodePostal(String value){
        return db.getJoin(DBTable.Commune.CODE_POSTAL, value, true);
    }

    public  List<Etablissement> searchEtablissementFromDBByDepartement(String value){
        List<Etablissement> liste1 = db.getJoin(DBTable.Commune.CODE_DEPARTEMENT, value, true);
        List<Etablissement> liste2 = db.getJoin(DBTable.Commune.NOM_DEPARTEMENT, value, false);
        liste1.addAll(liste2);
        return liste1;
    }

    public List<Etablissement> searchEtablissementFromDBByRegion(String value){
        List<Etablissement> liste1 = db.getJoin(DBTable.Commune.CODE_REGION, value, true);
        List<Etablissement> liste2 = db.getJoin(DBTable.Commune.NOM_REGION, value, false);
        liste1.addAll(liste2);
        return liste1;
    }

    // ==================================================
    //  Etablissement save DB
    // ==================================================

    public void saveEtablissement(List<Etablissement> etablissements){
        List<String> _listCodeCommunes = new ArrayList<>();
        for(Etablissement _etbl : etablissements){
            String _commEtbl = _etbl.getCodeCommune();
            if(!_listCodeCommunes.contains(_commEtbl)){
                _listCodeCommunes.add(_commEtbl);
            }
        }
        List<Commune> _listCommune = new ArrayList<>();
        for(String _comm : _listCodeCommunes){
            JsonNode json = api.getCommunes(APIField.Commune.CODE_COMMUNE, _comm, 1);
            Commune _commune = serializer.toCommunes(json).getFirst();
            _listCommune.add(_commune);
            System.out.println(Font.ITALIC + "" + Font.GREY + "Ajout de la commune " + _commune.getNom() + " car un établissement est dedans.");
        }
        db.saveCommunes(_listCommune);
        db.saveEtablissements(etablissements);
    }

    public void saveEtablissement(List<Etablissement> listeEtablissement, String select){
        if (!listeEtablissement.isEmpty()){
            saveEtablissement(serializer.toLists(listeEtablissement, select));
        }
    }

    // ==================================================
    //  Etablissement delete DB
    // ==================================================

    public void deleteEtablissement(){
        db.deleteEtablissements();
    }

    public void deleteEtablissement(List<Etablissement> etablissements) {
        for (Etablissement etab : etablissements) {
            db.deleteEtablissements(DBTable.Etablissement.ID, etab.getId());
        }
    }

    public void deleteEtablissement(List<Etablissement> listeEtablissement, String select){
        deleteEtablissement(serializer.toLists(listeEtablissement, select));
    }


    // ==================================================
    //  Meteo
    // ==================================================

    public List<Meteo> searchMeteoFromAPIByDBNomCommune(String value){
        List<Commune> communes = db.getCommunes(DBTable.Commune.NOM, value, false);

        List<Meteo> meteos = new ArrayList<>();

        for(Commune c : communes){
            JsonNode node = api.getMeteo(c.getLongitude(), c.getLatitude());
            Meteo m = new Meteo(
                    c.getNom(),
                    node.path("current_weather").path("temperature").asDouble()
            );
            meteos.add(m);
        }
        return meteos;
    }

    // ==================================================
    //  Entreprise
    // ==================================================

    public List<Entreprise> searchEntrepriseFromAPIByDBNomCommune(String value){
        List<Commune> communes = db.getCommunes(DBTable.Commune.NOM, value, false);

        //System.out.println("communes: " + communes);

        List<Entreprise> entreprises = new ArrayList<>();

        for(Commune c : communes) {
            System.out.println(c.getCodeCommune());
            JsonNode json = api.getEntreprises(c.getCodeCommune());
            System.out.println(json);
            List<Entreprise> ent = serializer.toEntreprises(json);
            entreprises.addAll(ent);
        }

        for(Entreprise e : entreprises){
            System.out.println(e.getNom());
        }

        return entreprises;
    }

}
