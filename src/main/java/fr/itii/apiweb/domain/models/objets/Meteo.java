package fr.itii.apiweb.domain.models.objets;

import fr.itii.apiweb.ui.Header;

public class Meteo {


    private String nom;
    private Double temperature;


    public Meteo(String nom, double temperature) {
        this.nom = nom;
        this.temperature = temperature;
    }

    public Meteo() {}


    public String getNomCommune() {
        return this.nom;
    }

    public Double getTemperature() {
        return this.temperature;
    }



    @Override
    public String toString() {
    return String.format(Header.METEO.format,
                getNomCommune(),
                getTemperature() + " °C"
        );
    }
}
