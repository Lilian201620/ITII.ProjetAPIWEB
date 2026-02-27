package fr.itii.apiweb.domain.tools;

import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.domain.models.enums.APIField;
import fr.itii.apiweb.domain.models.enums.DBTable;
import fr.itii.apiweb.ui.Font;
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

    // ==================================================
    //  Commune call API
    // ==================================================

    public List<Commune> searchCommuneFromAPIByNom(String value){
        JsonNode json = api.getCommunes(APIField.Commune.NOM, value, 1000);
        return serializer.toCommunes(json);
    }

    public List<Commune> searchCommuneFromAPIByCodePostal(String value){
        JsonNode json = api.getCommunes(APIField.Commune.CODE_POSTAL, value, 1000);
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

    private void saveCommune(List<Commune> liste){
        db.saveCommunes(liste);
    }

    public <T> void saveList(List<T> liste) {
        if (!liste.isEmpty()) {
            Object first = liste.getFirst();

            if (first instanceof Commune) {
                saveCommune((List<Commune>) liste);
            } else if (first instanceof Etablissement) {
                saveEtablissement((List<Etablissement>) liste);
            }
        }
    }

    public <T> void saveList(List<T> liste, String select){
        if (!liste.isEmpty()) {
            Object first = liste.getFirst();

            if (first instanceof Commune) {
                saveCommune(serializer.toLists((List<Commune>) liste, select));
            } else if (first instanceof Etablissement) {
                saveEtablissement(serializer.toLists((List<Etablissement>)liste, select));
            }
        }
    }

    // ==================================================
    //  Commune delete DB
    // ==================================================

    public void deleteCommune(){
        db.deleteCommunes();
    }

    private void deleteCommune(List<Commune> liste){
        for(Commune c : liste){
            db.deleteCommunes(DBTable.Commune.CODE_COMMUNE, c.getCodeCommune(), true);
        }
    }

    public <T> void deleteList(List<T> liste){
        if (!liste.isEmpty()) {
            Object first = liste.getFirst();

            if (first instanceof Commune) {
                deleteCommune((List<Commune>) liste);
            } else if (first instanceof Etablissement) {
                deleteEtablissement((List<Etablissement>) liste);
            }
        }
    }

    public <T> void deleteList(List<T> liste, String select){
        if (!liste.isEmpty()) {
            Object first = liste.getFirst();

            if (first instanceof Commune) {
                deleteCommune(serializer.toLists((List<Commune>) liste, select));
            } else if (first instanceof Etablissement) {
                deleteEtablissement(serializer.toLists((List<Etablissement>)liste, select));
            }
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
            //System.out.println(c.getCodeCommune());
            JsonNode json = api.getEntreprises(c.getCodeCommune());
            //System.out.println(json);
            List<Entreprise> ent = serializer.toEntreprises(json);
            entreprises.addAll(ent);
        }

        for(Entreprise e : entreprises){
            //System.out.println(e.getNom());
        }

        return entreprises;
    }

}
