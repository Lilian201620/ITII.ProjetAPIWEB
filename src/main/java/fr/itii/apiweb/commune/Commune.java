package fr.itii.apiweb.commune;

public class Commune {
    private String nom;
    private String codeInsee;
    private int population;
    private String centre;

    // Constructeur vide
    public Commune() {}

    // Constructeur complet
    public Commune(String nom, String codeInsee, int population, String centre) {
        this.nom = nom;
        this.codeInsee = codeInsee;
        this.population = population;
        this.centre = centre;
    }

    @Override
    public String toString() {
        return nom + " (" + codeInsee + ")";
    }
}
