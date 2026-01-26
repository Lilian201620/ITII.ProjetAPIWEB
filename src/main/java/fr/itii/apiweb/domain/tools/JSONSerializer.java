package fr.itii.apiweb.domain.tools;
import fr.itii.apiweb.domain.models.*;

import java.lang.reflect.Array;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONSerializer {

    public List<Commune> toCommunes(HttpResponse<String> response) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(response.body());
            //System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));
            return toCommunes(json);
        } catch (JsonProcessingException e) {
            System.err.println(e);
            return null;
        }
    }

    public List<Commune> toCommunes(JsonNode json) {
//          {
//              "nom" : "Beauvais",
//              "code" : "60057",
//              "codeDepartement" : "60",
//              "siren" : "216000562",
//              "codeEpci" : "200067999",
//              "codeRegion" : "32",
//              "codesPostaux" : [ "60000" ],
//              "population" : 55550
//          }

        ArrayList villes = new ArrayList();

        for (int i = 0; i < json.size(); i++) {
            JsonNode node = json.get(i);
            String nom = node.path("nom").asText();
            String codeCommune = node.path("code").asText();
            String codeDepartement = node.path("codeDepartement").asText();
            String siren = node.path("siren").asText();
            String codeEpci = node.path("codeEpci").asText();
            String codeRegion = node.path("codeRegion").asText();
            String codePostal = node.path("codesPostaux").getFirst().asText();
            Integer population = node.path("population").asInt();

            Commune ville = new Commune(nom, codeCommune, codeDepartement, siren, codeEpci, codeRegion, codePostal, population);
            villes.add(ville);
        }
        return villes;
    }

    public Commune getCommuneById(List<Commune> communeList, Integer idCommune){
        for (int i = 0; i < communeList.size(); i++) {
            Commune ville = communeList.get(i);
            if(ville.getId() == idCommune){
                return ville;
            }
        }
        return null;
    }

    public Commune getCommuneByNom(List<Commune> communeList, String nomCommune){
        for (int i = 0; i < communeList.size(); i++) {
            Commune ville = communeList.get(i);
            if(ville.getNom() == nomCommune){
                return ville;
            }
        }
        return null;
    }
}
