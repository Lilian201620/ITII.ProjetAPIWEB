package fr.itii.apiweb.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Commune {
//    {
//        "nom" : "Beauvais",
//        "code" : "60057",
//        "codeDepartement" : "60",
//        "siren" : "216000562",
//        "codeEpci" : "200067999",
//        "codeRegion" : "32",
//        "codesPostaux" : [ "60000" ],
//        "population" : 55550
//    }

    //Variable JSON
    private String nom;
    @JsonProperty("code")
    private String code;
    private String codeDepartement;
    private String siren;
    private String codeEpci;
    private String codeRegion;
    @JsonProperty("codesPostaux")
    private List<String> codesPostaux;
    private Integer population;

    //Autre variable
    private String codePostal = null;
    private Integer id;

    //Constructeur
    public Commune(
            String nom,
            String codeCommune,
            String codeDepartement,
            String siren,
            String codeEpci,
            String codeRegion,
            String codePostal,
            Integer population,
            Integer id
    ) {
        this.nom = nom;
        this.code = codeCommune;
        this.codeDepartement = codeDepartement;
        this.siren = siren;
        this.codeEpci = codeEpci;
        this.codeRegion = codeRegion;
        this.codePostal = codePostal;
        this.population = population;
        this.id = id;
    }

    public Commune(){

    }

    //Builder
    public static class Builder {
        private String nom;
        private String code;
        private String codeDepartement;
        private String siren;
        private String codeEpci;
        private String codeRegion;
        private String codePostal;
        private Integer population;
        private Integer id;

        public Builder setNom(String nom) {
            this.nom = nom;
            return this;
        }
        public Builder setCodeCommune(String codeCommune) {
            this.code = codeCommune;
            return this;
        }
        public Builder setCodeDepartement(String codeDepartement) {
            this.codeDepartement = codeDepartement;
            return this;
        }
        public Builder setSiren(String siren) {
            this.siren = siren;
            return this;
        }
        public  Builder setCodeEpci(String codeEpci) {
            this.codeEpci = codeEpci;
            return this;
        }
        public Builder setCodeRegion(String codeRegion) {
            this.codeRegion = codeRegion;
            return this;
        }
        public Builder setCodePostal(String codePostal) {
            this.codePostal = codePostal;
            return this;
        }
        public Builder setPopulation(Integer population) {
            this.population = population;
            return this;
        }
        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }
        public Commune build() {
            return new Commune(nom, code, codeDepartement, siren, codeEpci, codeRegion, codePostal, population, id);
        }
    }

    //Setters
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setCodeCommune(String codeCommune) {
        this.code = codeCommune;
    }
    public void setCodeDepartement(String codeDepartement) {
        this.codeDepartement = codeDepartement;
    }
    public void setSiren(String siren) {
        this.siren = siren;
    }
    public void setCodeEpci(String codeEpci) {
        this.codeEpci = codeEpci;
    }
    public void setCodeRegion(String codeRegion) {
        this.codeRegion = codeRegion;
    }
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }
    public void setPopulation(Integer population) {
        this.population = population;
    }

    //Getters
    public String getNom() {
        return this.nom;
    }
    public String getCodeCommune() {
        return this.code;
    }
    public String getCodeDepartement() {
        return this.codeDepartement;
    }
    public String getSiren() {
        return this.siren;
    }
    public String getCodeEpci() {
        return this.codeEpci;
    }
    public String getCodeRegion() {
        return this.codeRegion;
    }
    public String getCodePostal() {
        if (this.codePostal != null) {
            return codePostal;
        } else if(this.codesPostaux != null){
            return this.codesPostaux.getFirst();
        }
        return null;
    }
    public Integer getPopulation() {
        return this.population;
    }
    public Integer getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return String.format(
                "nom: %-25s commune: %-10s departement: %-10s region: %-10s postal: %-10s population: %5d",
                getNom(), getCodeCommune(), getCodeDepartement(), getCodeRegion(), getCodePostal(), getPopulation()
        );
    /*
        return  "nom: " + getNom() +
                ",\t commune: " + getCodeCommune() +
                ",\t departement: " + getCodeDepartement() +
                ",\t region: " + getCodeRegion() +
                ",\t postal: " + getCodePostal() +
                ",\t population: " + getPopulation();
                */
    }
}
