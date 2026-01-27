package fr.itii.apiweb.data.remote;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class EtablissementApiCaller {
    private final String API_URL = "https://data.education.gouv.fr/api/explore/v2.1/catalog/datasets/fr-en-annuaire-education/records?select=nom_etablissement%2Ctype_etablissement%2Ccode_commune%2Cnom_commune%2Cmail%2Cstatut_public_prive&where=";

    HttpClient client = HttpClient.newHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();


    public EtablissementApiCaller() {}

    public JsonNode getEtablissementsByCodeCommune(String codeCommune, String limit){
        String encodedCodeCommune = URLEncoder.encode(codeCommune, StandardCharsets.UTF_8);
        String url = API_URL
                + "code_commune=" + "'" + encodedCodeCommune + "'"
                + "&limit=" + limit ;
        return sendRequest(url);
    }

    public JsonNode getEtablissementsByNomCommune(String nomCommune, String limit){
        String encodedCommune = URLEncoder.encode(nomCommune, StandardCharsets.UTF_8);
        String url = API_URL
                + "nom_commune=" + "'" + encodedCommune + "'"
                + "&limit=" + limit ;
        return sendRequest(url);
    }

    public JsonNode getEtablissementsByCodeDepartement(String codeDepartement, String limit){
        String trueCode = "";
        if (codeDepartement.length()<3){
            trueCode = "0" + codeDepartement;
        } else {
            trueCode = codeDepartement;
        }
        String encodedCodeDepartement = URLEncoder.encode(trueCode, StandardCharsets.UTF_8);
        String url = API_URL
                + "code_departement=" + "'" + encodedCodeDepartement + "'"
                + "&limit=" + limit ;
        return sendRequest(url);
    }

    // Envoi de la requête a l'api
    private JsonNode sendRequest(String url){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode responseNode = objectMapper.readTree(response.body());

            return responseNode.get("results");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
