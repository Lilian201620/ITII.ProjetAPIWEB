package option2;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Array;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class DataObject {

    private JsonNode json;
    private ObjectMapper mapper;

    //ObjectMapper mapper = new ObjectMapper();
    //JsonNode json = mapper.readTree(response.body());
    //System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));

    //JsonNode	    Manipuler un JSON comme un arbre
    //JsonParser	Lire un JSON en streaming (bas niveau)
    //ObjectMapper	Conversion JSON ↔ Java
    //ObjectReader	Lire du JSON optimisé et thread-safe
    //ObjectWriter	Écrire du JSON optimisé et thread-safe


    List<Commune> villes = new ArrayList<>();

    public DataObject(HttpResponse<String> response) {
        try{
            mapper = new ObjectMapper();
            json = mapper.readTree(response.body());

        } catch (JsonProcessingException e) {
            System.err.println(e);
        }
    }

    public DataObject(JsonNode json) {
        this.json = json;
    }

    @Override
    public String toString() {
        try {
            if(villes.size() == 0){
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < villes.size(); i++) {
                    sb.append(villes.get(i).toString()).append("\n");
                }
                return sb.toString();
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void setJson(JsonNode json){
        this.json = json;
    }
    public JsonNode getJson(){
        return this.json;
    }

    public void generateCommune(){
        for (int i = 0; i < getJson().size(); i++) {
//          {
//              "nom" : "Beauvais",
//              "code" : "60057",
//              "codeDepartement" : "60",
//              "siren" : "216000562",
//              "codeEpci" : "200067999",
//              "codeRegion" : "32",
//              "codesPostaux" : [ "60000" ],
//              "population" : 55550
//            }

            JsonNode node = getJson().get(i);

            String nom = node.path("nom").asText();
            String codeCommune = node.path("code").asText();
            String codeDepartement = node.path("codeDepartement").asText();
            String codeRegion = node.path("codeRegion").asText();
            String codePostal = node.path("codePostal").asText();
            Integer population = node.path("population").asInt();

            Commune ville = new Commune(nom, codeCommune, codeDepartement, codeRegion, codePostal, population);
            villes.add(ville);
        }
    }

    public ArrayList<Commune> getAll(){
        return (ArrayList<Commune>) villes;
    }

    public Commune getCommuneByList(Integer idList){
        return villes.get(idList);
    }

    public Commune getCommuneById(Integer idCommune){
        for (int i = 0; i < villes.size(); i++) {
            Commune ville = villes.get(i);
            if(ville.getId() == idCommune){
                return ville;
            }
        }
        return null;
    }
    public Commune getCommuneByName(String nomCommune){
        for (int i = 0; i < villes.size(); i++) {
            Commune ville = villes.get(i);
            if(ville.getNom() == nomCommune){
                return ville;
            }
        }
        return null;
    }
}
