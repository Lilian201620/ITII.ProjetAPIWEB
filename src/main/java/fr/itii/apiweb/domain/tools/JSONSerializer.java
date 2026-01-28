package fr.itii.apiweb.domain.tools;

import fr.itii.apiweb.domain.models.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONSerializer {

    private final ObjectMapper mapper = new ObjectMapper();

   //communes
    public List<Commune> toCommunes(JsonNode json) {
        List<Commune> communes = new ArrayList<>();

        try {
            for (JsonNode node : json) {
                Commune commune = mapper.treeToValue(node, Commune.class);
                communes.add(commune);
            }
            return communes;

        } catch (JsonProcessingException e) {
            ExceptionsHandler.handleException(e);
            return Collections.emptyList();
        }
    }

    public Commune getCommuneByNom(List<Commune> communeList, String nomCommune) {
        return communeList.stream()
                .filter(c -> c.getNom().equalsIgnoreCase(nomCommune))
                .findFirst()
                .orElse(null);
    }

 //etablissements
    public List<Etablissement> toEtablissements(JsonNode json) {
        List<Etablissement> etablissements = new ArrayList<>();

        try {
            for (JsonNode node : json) {
                Etablissement etablissement = mapper.treeToValue(node, Etablissement.class);
                etablissements.add(etablissement);
            }
            return etablissements;

        } catch (JsonProcessingException e) {
            ExceptionsHandler.handleException(e);
            return Collections.emptyList();
        }
    }

    public Etablissement getEtablissementByNom(
            List<Etablissement> etablissementList,
            String nomEtablissement
    ) {
        return etablissementList.stream()
                .filter(e -> e.getNomEtablissement().equalsIgnoreCase(nomEtablissement))
                .findFirst()
                .orElse(null);
    }
}
