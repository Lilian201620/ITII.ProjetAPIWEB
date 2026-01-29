package fr.itii.apiweb.domain.tools;

import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.data.local.DBManager;
import fr.itii.apiweb.data.remote.APICaller;
import fr.itii.apiweb.data.remote.EtablissementApiCaller;
import fr.itii.apiweb.domain.models.enumlist.api.APICommune;
import fr.itii.apiweb.domain.models.enumlist.api.APIEtablissement;
import fr.itii.apiweb.domain.models.enumlist.db.DBCommune;
import fr.itii.apiweb.domain.models.enumlist.db.DBEtablissement;
import fr.itii.apiweb.domain.models.objet.Commune;
import fr.itii.apiweb.domain.models.objet.Etablissement;

import java.util.ArrayList;
import java.util.List;

public class Backend {

    private final APICaller api = new APICaller();
    private final EtablissementApiCaller etablissementApiCaller = new EtablissementApiCaller();
    private final DBManager db = DBManager.getInstance();
    private final JSONSerializer serializer = new JSONSerializer();

    // ==================================================
    //  Commune call API
    // ==================================================

    public List<Commune> searchCommuneFromAPIByNom(String value){
        JsonNode json = api.getCommunes(APICommune.NOM, value, 100);
        return serializer.toCommunes(json);
    }

    public List<Commune> searchCommuneFromAPIByCodePostal(String value){
        JsonNode json = api.getCommunes(APICommune.CODE_POSTAL, value, 100);
        return serializer.toCommunes(json);
    }

    public List<Commune> searchCommuneFromAPIByDepartement(String value){
        JsonNode json = api.getCommunes(APICommune.DEPARTEMENT, value, 100);
        return serializer.toCommunes(json);
    }

    // ==================================================
    //  Commune read DB
    // ==================================================

    public List<Commune> searchCommuneFromDBByNom(String value){
        return db.getCommunes(DBCommune.NOM, value, false);
    }

    public List<Commune> searchCommuneFromDBByCodePostal(String value){
        return db.getCommunes(DBCommune.CODE_POSTAL, value, false);
    }

    public  List<Commune> searchCommuneFromDBByDepartement(String value){
        return db.getCommunes(DBCommune.CODE_DEPARTEMENT, value, false);
    }

    public List<Commune> searchCommuneFromDBByRegion(String value){
        return db.getCommunes(DBCommune.CODE_REGION, value, false);
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
            db.deleteCommunes(DBCommune.CODE_COMMUNE, c.getCodeCommune(), false);
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

    public List<Etablissement> searchEtablissementFromAPIByNom(String value){
        return null;
    }

    public List<Etablissement> searchEtablissementFromAPIByType(String value){
        return null;
    }

    public List<Etablissement> searchEtablissementFromAPIByNomCommune(String value){
        JsonNode json = etablissementApiCaller.getEtablissements(APIEtablissement.NOM_COMMUNE, value, 100);
        return serializer.toEtablissements(json);
    }

    public List<Etablissement> searchEtablissementFromAPIByCodePostal(String value){
        return null;
    }

    public List<Etablissement> searchEtablissementFromAPIByDepartement(String value){
        JsonNode json = etablissementApiCaller.getEtablissements(APIEtablissement.CODE_DEPARTEMENT, value, 100);
        return serializer.toEtablissements(json);
    }

    // ==================================================
    //  Etablissement read DB
    // ==================================================

    public List<Etablissement> searchEtablissementFromDBByNom(String value){
        return db.getEtablissements(DBEtablissement.NOM, value, false);
    }

    public List<Etablissement> searchEtablissementFromDBByType(String value){
        return db.getEtablissements(DBEtablissement.TYPE, value, false);
    }

    public List<Etablissement> searchEtablissementFromDBByNomCommune(String value){
        return db.getJoin(DBCommune.NOM, value, true);
    }

    public List<Etablissement> searchEtablissementFromDBByCodePostal(String value){
        return db.getJoin(DBCommune.CODE_POSTAL, value, true);
    }

    public  List<Etablissement> searchEtablissementFromDBByDepartement(String value){
        return db.getJoin(DBCommune.CODE_DEPARTEMENT, value, true);
    }

    public List<Etablissement> searchEtablissementFromDBByRegion(String value){
        return db.getJoin(DBCommune.CODE_REGION, value, true);
    }

    // ==================================================
    //  Etablissement save DB
    // ==================================================

    public void saveEtablissement(List<Etablissement> listeEtablissement){
        List<String> _listCodeCommunes = new ArrayList<>();
        for(Etablissement _etbl : listeEtablissement){
            String _commEtbl = _etbl.getCodeCommune();
            if(!_listCodeCommunes.contains(_commEtbl)){
                _listCodeCommunes.add(_commEtbl);
            }
        }
        List<Commune> _listCommune = new ArrayList<>();
        for(String _comm : _listCodeCommunes){
            JsonNode json = api.getCommunes(APICommune.CODE_COMMUNE, _comm, "1");
            _listCommune.add(serializer.toCommunes(json).getFirst());
        }
        db.saveCommunes(_listCommune);
        db.saveEtablissements(listeEtablissement);
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

    public void deleteEtablissement(List<Etablissement> listeEtablissement) {
        for (Etablissement et : listeEtablissement) {
            db.deleteEtablissements(DBEtablissement.NOM, et.getNomEtablissement(), true);
        }
    }

    public void deleteEtablissement(List<Etablissement> listeEtablissement, String select){
        deleteEtablissement(serializer.toLists(listeEtablissement, select));
    }

}
