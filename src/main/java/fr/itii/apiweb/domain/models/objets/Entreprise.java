package fr.itii.apiweb.domain.models.objets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.itii.apiweb.ui.Header;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Entreprise {
    @JsonProperty("nom_raison_sociale")
    private String nom;


    @JsonProperty("siege")
    private Siege siege;


    @JsonProperty("matching_etablissements")
    private List<Etablissement> matchingEtablissements;

    public String getNom() {
        return nom;
    }

    public String getAdresse(){
        return matchingEtablissements.getFirst().getAdresse();
    }

    public Entreprise(){

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Siege {
        @JsonProperty("adresse")
        private String adresse;

        public Siege() {

        }

        public String getAdresse() { return adresse; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Etablissement {
        @JsonProperty("adresse")
        private String adresse;
        @JsonProperty("libelle_commune")
        private String ville;

        public String getAdresse() { return adresse; }

        public Etablissement(){

        }
    }


    public String getAdresseMatching() {
        if (matchingEtablissements != null && !matchingEtablissements.isEmpty()) {
            return matchingEtablissements.get(0).getAdresse();
        }
        return "Aucune adresse correspondante";
    }

    public String getAdresseSiege() {
        return (siege != null) ? siege.getAdresse() : "Siège non trouvé";
    }


    @Override
    public String toString() {
        return String.format(Header.ENTREPRISE.format,
                getNom(),
                getAdresse()
        );

    }
}
