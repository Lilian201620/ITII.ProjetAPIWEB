package fr.itii.apiweb.domain.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.itii.apiweb.domain.models.objets.Commune;
import fr.itii.apiweb.domain.models.objets.Entreprise;
import fr.itii.apiweb.domain.models.objets.Etablissement;

public class JSONSerializer {

    private final ObjectMapper mapper = new ObjectMapper();


    public List<Commune> toCommunes(JsonNode json) {
        List<Commune> communes = new ArrayList<>();

        try {
            for (JsonNode node : json) {

                Commune commune = mapper.treeToValue(node, Commune.class);
                communes.add(commune);
            }
            return communes;

        } catch (JsonProcessingException e) {

            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public String toString(JsonNode json)  {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (JsonProcessingException e) {
            ExceptionHandler.handleException(e);
            return "";
        }
    }



    public List<Etablissement> toEtablissements(JsonNode json) {
        List<Etablissement> etablissements = new ArrayList<>();

        try {
            for (JsonNode node : json.path("results")) {
                Etablissement etablissement = mapper.treeToValue(node, Etablissement.class);
                etablissements.add(etablissement);
            }
            return etablissements;

        } catch (JsonProcessingException e) {
            ExceptionHandler.handleException(e);
            return Collections.emptyList();
        }
    }


    public <T> List toLists(List<T> liste, String indexs){
        if(indexs.equals("*")){
            return liste;
        }

        String[] select = indexs.replaceAll("[^0-9\\- ]", "").split("\\s+");
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


    public List<Entreprise> toEntreprises(JsonNode json) {
        List<Entreprise> entreprises = new ArrayList<>();

        try {
            for (JsonNode node : json.path("results")) {
                Entreprise e = mapper.treeToValue(node, Entreprise.class);
                entreprises.add(e);
            }
            return entreprises;

        } catch (JsonProcessingException e) {
            ExceptionHandler.handleException(e);
            return Collections.emptyList();
        }
    }

}
