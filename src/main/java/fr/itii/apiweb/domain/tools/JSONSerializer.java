package fr.itii.apiweb.domain.tools;
import fr.itii.apiweb.domain.models.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONSerializer {

    public List<Commune> toCommunes(JsonNode json) {
        ObjectMapper mapper = new ObjectMapper();
        List<Commune> villes = new ArrayList<>();

        try {
            for(JsonNode node : json){
                Commune ville = mapper.treeToValue(node, Commune.class);
                villes.add(ville);
            }
            return villes;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Commune getCommuneByNom(List<Commune> communeList, String nomCommune){
        return communeList.stream()
                .filter(c -> c.getNom() == nomCommune)
                .findFirst()
                .orElse(null);
    }
}
