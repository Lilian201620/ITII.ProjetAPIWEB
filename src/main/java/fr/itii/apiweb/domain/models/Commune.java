package fr.itii.apiweb.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
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

    private static Integer countId = 0;

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

    public Commune(
            String nom,
            String codeCommune,
            String codeDepartement,
            String siren,
            String codeEpci,
            String codeRegion,
            String codePostal,
            Integer population
    ) {
        this(nom, codeCommune, codeDepartement, siren, codeEpci, codeRegion, codePostal, population, 0);
    }

    public Commune(){}

    //Builder
    public static class Builder {
        private String nom;
        private String codeCommune;
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
            this.codeCommune = codeCommune;
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
            return new Commune(nom, codeCommune, codeDepartement, siren, codeEpci, codeRegion, codePostal, population, id);
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
    public void setCodePostal(ArrayList<String> codePostal) {
        this.codePostal = codePostal.get(0).toString();
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
        return this.codePostal ==  null ? this.codePostal : this.codesPostaux.get(0);
    }
    public Integer getPopulation() {
        return population;
    }
    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return  "nom= " + nom +
                ",\t commune= " + code +
                ",\t departement= " + codeDepartement +
                ",\t region= " + codeRegion +
                ",\t postal= " + getCodePostal() +
                ",\t population= " + population;
    }
}
