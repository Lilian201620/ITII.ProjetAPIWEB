package fr.itii.apiweb.data.remote;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APICaller {


    private final String API_URL = "https://geo.api.gouv.fr/communes";

    HttpClient client = HttpClient.newHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();

    public APICaller() {
    }

    // Construction de la requête de recherche par nom
    public JsonNode getCommunesByName(String commune){
        String encodedCommune = URLEncoder.encode(commune, StandardCharsets.UTF_8);
        String url = API_URL
                + "?nom=" + encodedCommune
                + "&fields=nom,code,codeDepartement,codeRegion,codesPostaux,population"
                + "&limit=10";
        return sendRequest(url);
    }

    // Construction de la requête de recherche par code Postal
    public JsonNode getCommunesByCodePostal(String codePostal){
        String encodedCodePostal = URLEncoder.encode(codePostal, StandardCharsets.UTF_8);
        String url = API_URL
                + "?codePostal=" + encodedCodePostal
                + "&fields=nom,code,codeDepartement,codeRegion,codesPostaux,population"
                + "&limit=10";
        return sendRequest(url);
    }

    // Construction de la requête de recherche par code Postal
    public JsonNode getCommunesByDepartement(String departement){
        String encodedDepartement = URLEncoder.encode(departement, StandardCharsets.UTF_8);
        String url = API_URL
                + "?codeDepartement=" + encodedDepartement
                + "&fields=nom,code,codeDepartement,codeRegion,codesPostaux,population"
                + "&limit=10";
        return sendRequest(url);
    }

    // Envoi de la requête a l'api
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
            throw new RuntimeException(e);
        }
    }

}
