package fr.itii.apiweb.domain.models.objet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import fr.itii.apiweb.ui.Header;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Entreprise {
    @JsonProperty("nom_raison_sociale")
    private String nom;

    // Option A : L'adresse du siège social
    @JsonProperty("siege")
    private Siege siege;

    // Option B : La liste des établissements correspondants (votre recherche actuelle)
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

    // --- Méthodes pour récupérer l'adresse ---

    // Récupère l'adresse du premier établissement trouvé (ex: Gournay-en-Bray)
    public String getAdresseMatching() {
        if (matchingEtablissements != null && !matchingEtablissements.isEmpty()) {
            return matchingEtablissements.get(0).getAdresse();
        }
        return "Aucune adresse correspondante";
    }

    // Récupère l'adresse du siège social (ex: Paris 15)
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
