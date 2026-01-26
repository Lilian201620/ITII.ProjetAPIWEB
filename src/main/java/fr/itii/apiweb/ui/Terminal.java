package fr.itii.apiweb.ui;

import fr.itii.apiweb.model.Commune;
import java.util.List;

public class Terminal {
    public static void display(List<Commune> communes) {
        System.out.println("Affichage du terminal :");
        System.out.println(communes); // Affiche null pour l'instant
    }
    //
}
