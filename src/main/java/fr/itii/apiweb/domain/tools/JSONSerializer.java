package fr.itii.apiweb.domain.tools;
import fr.itii.apiweb.domain.models.*;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONSerializer {
    private ObjectMapper mapper = new ObjectMapper();

    public List<Commune> toCommunes(HttpResponse<String> response) {
        try {
            JsonNode json = mapper.readTree(response.body());
            return toCommunes(json);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Commune> toCommunes(JsonNode json) {
        ArrayList villes = new ArrayList();

        try {
            for(JsonNode node: json){
                Commune ville = mapper.treeToValue(node, Commune.class);
                villes.add(ville);
            }
            return villes;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Commune getCommuneById(List<Commune> communeList, Integer idCommune){
        return communeList.stream()
                .filter(c -> c.getId() == idCommune)
                .findFirst()
                .orElse(null);
    }

    public Commune getCommuneByNom(List<Commune> communeList, String nomCommune){
        return communeList.stream()
                .filter(c -> c.getNom() == nomCommune)
                .findFirst()
                .orElse(null);
    }
}
