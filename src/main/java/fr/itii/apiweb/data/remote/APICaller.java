package fr.itii.apiweb.data.remote;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.itii.apiweb.domain.models.enums.APIField;
import fr.itii.apiweb.domain.tools.ExceptionHandler;

public class APICaller {

    HttpClient client = HttpClient.newHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();

    public APICaller() {}


    private JsonNode sendRequest(String url){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response;

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readTree(response.body());
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
            return objectMapper.createArrayNode();
        }
    }


    public JsonNode getCommunes(APIField.Commune field, String query, Integer limit){
        String encodedField = URLEncoder.encode(field.toString(), StandardCharsets.UTF_8);
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);


        String url =
                "https://geo.api.gouv.fr/communes?" +
                "fields=nom,code,departement,region,codesPostaux,population,centre" +
                "&limit=" + limit +
                "&" + encodedField + "=" + encodedQuery;

        return sendRequest(url);
    }


    public JsonNode getEtablissements(APIField.Etablissement field, String query, Integer limit){
        String encodedField = URLEncoder.encode(field.toString(), StandardCharsets.UTF_8);
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);


        String url =
                "https://data.education.gouv.fr/api/explore/v2.1/catalog/datasets/fr-en-annuaire-education/records?" +
                "select=nom_etablissement,type_etablissement,code_commune,nom_commune,mail,statut_public_prive" +
                "&limit=" + limit +
                "&where=" + encodedField + "='" + encodedQuery + "'";

        return sendRequest(url);
    }


    public JsonNode getMeteo(double longitude, double latitude){
        String url = "https://api.open-meteo.com/v1/forecast"
                + "?latitude=" + latitude
                + "&longitude=" + longitude
                + "&current_weather=true";

        return sendRequest(url);
    }


    public JsonNode getEntreprises(String code){
        String url = "https://recherche-entreprises.api.gouv.fr/search?"
                + "&per_page=25"
                + "&code_commune=" + code;

        return sendRequest(url);
    }

}


