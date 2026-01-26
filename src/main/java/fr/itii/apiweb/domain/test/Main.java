package fr.itii.apiweb.domain.test;

import fr.itii.apiweb.tools.*

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
//recuperer tout les cours des autre
//git pull

//envoyer le dossier domain
git push

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        //System.out.print("Latitude : ");
        //double latitude = scanner.nextDouble();

        //System.out.print("Longitude : ");
        //double longitude = scanner.nextDouble();


        String url = "https://geo.api.gouv.fr/departements/60/communes";

        //curl 'https://geo.api.gouv.fr/communes?codePostal=78000'


        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONSerializer json = new JSONSerializer(response);
        System.out.println(obj);


    }
}
