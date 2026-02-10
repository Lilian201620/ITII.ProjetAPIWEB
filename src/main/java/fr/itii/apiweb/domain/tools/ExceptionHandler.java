package fr.itii.apiweb.domain.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.itii.apiweb.ui.Font;

import java.io.IOException;
import java.sql.SQLException;

public class ExceptionHandler {

    public static void handleException(Exception exception){
        switch (exception) {
            case IOException _, InterruptedException _ ->
                    System.out.println(Font.RED + "" + Font.BOLD + "Un problème est survenu lors de la requête à l'API Web. Vérifiez votre connexion et réessayez" + Font.RESET);
            case SQLException _ -> {

                    System.out.println(Font.RED + "" + Font.BOLD + "Un problème est survenu avec la base de données, vérifiez le serveur SQL et réessayez" + Font.RESET);
                    exception.printStackTrace();
            }
            case null, default ->
                    System.out.println(Font.RED + "" + Font.BOLD + "Un problème inconnu est survenu, veuillez réessayer" + Font.RESET);
        }
    }

    public static void handleException(JsonProcessingException exception){
        System.out.println("Un problème est survenu lors du traitement de la réponse. Veuillez réessayer");
    }

}
