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
    public APICaller() {
    }

    public JsonNode getCommunesByName(String commune){
        ObjectMapper objectMapper = new ObjectMapper();
        HttpClient client = HttpClient.newHttpClient();
        String encodedCommune = URLEncoder.encode(commune, StandardCharsets.UTF_8);
        String url = API_URL
                + "?nom=" + encodedCommune
                + "&fields=nom,code,codeDepartement,codeRegion,codesPostaux,population"
                + "&limit=10";

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
