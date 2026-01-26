package option2;

import java.util.ArrayList;

public class Commune {
    private String nom;
    private String codeCommune;
    private String codeDepartement;
    private String codeRegion;
    private String codePostal;
    private Integer population;
    private Integer id;

    private static Integer countId = 0;

    //Constructeur
    public Commune(String nom, String codeCommune, String codeDepartement, String codeRegion, String codePostal, Integer population) {
        this.nom = nom;
        this.codeCommune = codeCommune;
        this.codeDepartement = codeDepartement;
        this.codeRegion = codeRegion;
        this.codePostal = codePostal;
        this.population = population;

        this.id = countId;
        countId++;
    }

    //Builder
    public static class Builder{
        private String nom;
        private String codeCommune;
        private String codePostal;
        private Integer population;
        private String codeRegion;
        private String codeDepartement;

        public Builder setNom(String nom){
            this.nom = nom;
            return this;
        }
        public Builder setCodeCommune(String codeCommune){
            this.codeCommune = codeCommune;
            return this;
        }
        public Builder setCodeDepartement(String codeDepartement){
            this.codeDepartement = codeDepartement;
            return this;
        }
        public Builder setCodeRegion(String codeRegion){
            this.codeRegion = codeRegion;
            return this;
        }
        public Builder setCodePostal(String codePostal){
            this.codePostal = codePostal;
            return this;
        }
        public Builder setPopulation(Integer population){
            this.population = population;
            return this;
        }
        public Commune build(){
            return new Commune(nom, codeCommune, codeDepartement, codeRegion, codePostal, population);
        }

    }

    //Setters
    public void setNom(String nom){
        this.nom = nom;
    }
    public void setCodeCommune(String codeCommune){
        this.codeCommune = codeCommune;
    }
    public void setCodeDepartement(String codeDepartement){
        this.codeDepartement = codeDepartement;
    }
    public void setCodeRegion(String codeRegion){
        this.codeRegion = codeRegion;
    }

    public void setCodePostal(String codePostal){
        this.codePostal = codePostal;
    }
    public void setCodePostal(ArrayList<String> codePostal){
        this.codePostal = codePostal.getFirst().toString();
    }
    public void setPopulation(Integer population){
        this.population = population;
    }

    //Getters
    public String getNom() {
        return nom;
    }
    public String getCodeCommune() {
        return codeCommune;
    }
    public String getCodeDepartement() {
        return codeDepartement;
    }
    public String getCodeRegion() {
        return codeRegion;
    }
    public String getCodePostal() {
        return codePostal;
    }
    public Integer getPopulation() {
        return population;
    }
    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "nom='" + nom + '\'' +
                ", codeCommune='" + codeCommune + '\'' +
                ", codeDepartement='" + codeDepartement + '\'' +
                ", codeRegion='" + codeRegion + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", population=" + population +
                '}';
    }
}
