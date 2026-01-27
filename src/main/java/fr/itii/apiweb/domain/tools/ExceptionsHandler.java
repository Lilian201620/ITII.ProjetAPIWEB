package fr.itii.apiweb.domain.tools;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.sql.SQLException;

public class ExceptionsHandler {

    public static void handleException(Exception exception){
        if (exception instanceof IOException) {
            System.out.println("Un problème est survenu lors de la requête. Verifiez votre connexion et réessayez");
        }else if (exception instanceof InterruptedException){
            System.out.println("Un problème est survenu lors de la requête. Verifiez votre connexion et réessayez");
        } else if (exception instanceof SQLException){
            System.out.println("Un problème est survenu avec la base de donnée, veuillez réessayer");
        }else {
            System.out.println("Un problème inconnu est survenu, veuillez réessayer");
        }
    }

    public static void handleException(JsonProcessingException exception){
        System.out.println("Un problème est survenu lors du traitement de la réponse. Veuillez réessayer");
    }

}
