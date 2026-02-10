package fr.itii.apiweb.domain.models.objet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Commune {

    //Variable
    private Integer id;

    @JsonProperty("nom")
    private String nom;

    @JsonProperty("code")
    private String code;

    @JsonProperty("departement")
    private Ressource departement;

    @JsonProperty("region")
    private Ressource region;

    @JsonProperty("codesPostaux")
    private List<String> codesPostaux;

    @JsonProperty("population")
    private Integer population;

    @JsonProperty("centre")
    private Position coordonnees;

    //Constructeur
    public Commune() {}

    public Commune(
        Integer id,
        String nom,
        String code,
        Ressource departement,
        Ressource region,
        List<String> codesPostaux,
        Integer population,
        Position coordonnees
    ) {
        this.id = id;
        this.nom = nom;
        this.code = code;
        this.departement = departement;
        this.region = region;
        this.codesPostaux = codesPostaux;
        this.population = population;
        this.coordonnees = coordonnees;
    }

    public static class Builder {
        Integer id;
        String nom;
        String code;
        Ressource departement;
        Ressource region;
        List<String> codesPostaux;
        Integer population;
        Position coordonnees;

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setNom(String nom) {
            this.nom = nom;
            return this;
        }
        public Builder setCode(String code) {
            this.code = code;
            return this;
        }
        public Builder setDepartement(String code, String nom) {
            this.departement = new Ressource(code, nom);
            return this;
        }
        public Builder setRegion(String code, String nom) {
            this.region = new Ressource(code, nom);
            return this;
        }
        public Builder setCodesPostaux(List<String> codesPostaux) {
            this.codesPostaux = codesPostaux;
            return this;
        }
        public Builder setCodePostal(String codePostal) {
            this.codesPostaux.add(codePostal);
            return this;
        }
        public Builder setPopulation(Integer population) {
            this.population = population;
            return this;
        }
        public Builder setCoordonnees(double longitude, double latitude) {
            this.coordonnees = new Position(longitude, latitude);
            return this;
        }

        public Commune build(){
            return new Commune(id, nom, code, departement, region, codesPostaux, population, coordonnees);
        }
    }

    //Setters


    // Getters
    public Integer getId() {
        return this.id;
    }
    public String getNom(){
        return this.nom;
    }
    public String getCodeCommune(){
        return this.code;
    }
    public String getCodeDepartement(){
        return this.departement.code;
    }
    public String getNomDepartement(){
        return this.departement.nom;
    }
    public String getDepartement(){
        return this.departement.toString();
    }
    public String getNomRegion(){
        return region.nom;
    }
    public String getCodeRegion(){
        return region.code;
    }
    public String getRegion(){
        return region.toString();
    }
    public List<String> getCodesPostaux(){
        return this.codesPostaux;
    }
    public String getCodePostal(){
        return this.codesPostaux.get(0);
    }
    public Integer getPopulation(){
        return this.population;
    }
    public List<Double>  getCoordonnees(){
        return coordonnees.value;
    }
    public Double getLongitude(){
        return coordonnees.value.get(0);
    }
    public  Double getLatitude(){
        return coordonnees.value.get(1);
    }

    @Override
    public String toString() {
        return String.format("nom: %-40s departement: %-30s region: %-20s codes postaux: %-10s poupulation: %-10s",
                getNom(),
                getDepartement(),
                getNomRegion(),
                getCodesPostaux(),
                getPopulation()
        );
    }

    // Sous-Classes
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Ressource {
        @JsonProperty("code")
        public String code;
        @JsonProperty("nom")
        public String nom;

        public Ressource(){}

        public Ressource(String code, String nom) {
            this.code = code;
            this.nom = nom;
        }

        public String toString() {
            return code + " - " + nom;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Position {
        @JsonProperty("coordinates")
        public List<Double> value;

        public Position(){}

        public Position(double longitude, double latitude) {
            this.value = new ArrayList<>();
            this.value.add(longitude);
            this.value.add(latitude);
        }

        public String toString() {
            return value.toString();
        }
    }
}