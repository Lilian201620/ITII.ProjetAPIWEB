package fr.itii.apiweb.domain.tools;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.sql.SQLException;

public class ExceptionsHandler {
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";

    public static void handleException(Exception exception){
        switch (exception) {
            case IOException ioException ->
                    System.out.println(RED + BOLD + "Un problème est survenu lors de la requête à l'API Web. Vérifiez votre connexion et réessayez" + RESET);
            case InterruptedException interruptedException ->
                    System.out.println(RED + BOLD + "Un problème est survenu lors de la requête à l'API Web. Vérifiez votre connexion et réessayez" + RESET);
            case SQLException _ ->
                    System.out.println(RED + BOLD + "Un problème est survenu avec la base de données, vérifiez le serveur SQL et réessayez" + RESET);
            case null, default ->
                    System.out.println(RED + BOLD + "Un problème inconnu est survenu, veuillez réessayer" + RESET);
        }
    }

    public static void handleException(JsonProcessingException exception){
        System.out.println("Un problème est survenu lors du traitement de la réponse. Veuillez réessayer");
    }

}
