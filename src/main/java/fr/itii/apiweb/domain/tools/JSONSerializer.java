package fr.itii.apiweb.domain.tools;

import java.lang.reflect.Array;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.itii.apiweb.models.*

public class JSONSerializer {
        //ObjectMapper mapper = new ObjectMapper();
        //JsonNode json = mapper.readTree(response.body());
        //System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));

        //JsonNode	    Manipuler un JSON comme un arbre
        //JsonParser	Lire un JSON en streaming (bas niveau)
        //ObjectMapper	Conversion JSON ↔ Java
        //ObjectReader	Lire du JSON optimisé et thread-safe
        //ObjectWriter	Écrire du JSON optimisé et thread-safe

        public List<Commune> convertJsonToList(HttpResponse<String> response) {
            try{
                ObjectMapper mapper = new ObjectMapper();
                JsonNode json = mapper.readTree(response.body());
                convertJsonToList(json)
            } catch (JsonProcessingException e) {
                System.err.println(e);
                return null;
            }
        }

        public List<Commune> convertJsonToList(JsonNode json) {
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
                String nom = json.path("nom").asText();
                String codeCommune = json.path("code").asText();
                String codeDepartement = json.path("codeDepartement").asText();
                String codeRegion = json.path("codeRegion").asText();
                String codePostal = json.path("codePostal").asText();
                Integer population = json.path("population").asInt();

                Commune ville = new Commune(nom, codeCommune, codeDepartement, codeRegion, codePostal, population);
                villes.add(ville);
            }
            return villes;
        }

        public Commune getCommuneById(List<Communes> communeList, Commune idCommune){
            for (int i = 0; i < communeList.size(); i++) {
                Commune ville = communeList.get(i);
                if(ville.getId() == idCommune){
                    return ville;
                }
            }
            return null;
        }
        public Commune getCommuneByName(List<Communes> communeList, String nomCommune){
            for (int i = 0; i < communeList.size(); i++) {
                Commune ville = communeList.get(i);
                if(ville.getNom() == nomCommune){
                    return ville;
                }
            }
            return null;
        }
    }

}
