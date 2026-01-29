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

    //Commune
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


    //Etablissements
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

    //Listes
    public <T> List toLists(List<T> liste, String value){
        if(value.equals("*")){
            return liste;
        }

        String[] select = value.replaceAll("[^0-9\\- ]", "").split("\\s+");
        List<T> newList = new ArrayList<>();

        for (String s : select) {
            if (s.isEmpty()) continue;

            String[] intervalle = s.split("-");
            try {
                int start = Integer.parseInt(intervalle[0]);
                int end = (intervalle.length > 1) ? Integer.parseInt(intervalle[1]) : start;

                for (int j = Math.max(0, start - 1); j <= Math.min(end - 1, liste.size() - 1); j++) {
                    newList.add(liste.get(j));
                }
            } catch (NumberFormatException e) {

            }
        }
        return newList;
    }
}
