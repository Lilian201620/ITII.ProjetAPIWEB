package fr.itii.apiweb.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Etablissement {

    /*
    Exemple JSON attendu :
    {
        "nom_etablissement": "Lycée Jean Moulin",
        "type_etablissement": "Lycée",
        "code_commune": "60057",
        "nom_commune": "Beauvais",
        "mail": "contact@lycee.fr",
        "statut_public_prive": "Public"
    }
    */

    // ===== Variables JSON =====
    @JsonProperty("nom_etablissement")
    private String nomEtablissement;

    @JsonProperty("type_etablissement")
    private String typeEtablissement;

    @JsonProperty("code_commune")
    private String codeCommune;

    @JsonProperty("nom_commune")
    private String nomCommune;

    @JsonProperty("mail")
    private String mail;

    @JsonProperty("statut_public_prive")
    private String statutPublicPrive;

    // ===== Autres variables =====
    private Integer id;

    // ===== Constructeurs =====
    public Etablissement(
            String nomEtablissement,
            String typeEtablissement,
            String codeCommune,
            String nomCommune,
            String mail,
            String statutPublicPrive,
            Integer id
    ) {
        this.nomEtablissement = nomEtablissement;
        this.typeEtablissement = typeEtablissement;
        this.codeCommune = codeCommune;
        this.nomCommune = nomCommune;
        this.mail = mail;
        this.statutPublicPrive = statutPublicPrive;
        this.id = id;
    }

    public Etablissement() {
    }

    // ===== Builder =====
    public static class Builder {
        private String nomEtablissement;
        private String typeEtablissement;
        private String codeCommune;
        private String nomCommune;
        private String mail;
        private String statutPublicPrive;
        private Integer id;

        public Builder setNomEtablissement(String nomEtablissement) {
            this.nomEtablissement = nomEtablissement;
            return this;
        }

        public Builder setTypeEtablissement(String typeEtablissement) {
            this.typeEtablissement = typeEtablissement;
            return this;
        }

        public Builder setCodeCommune(String codeCommune) {
            this.codeCommune = codeCommune;
            return this;
        }

        public Builder setNomCommune(String nomCommune) {
            this.nomCommune = nomCommune;
            return this;
        }

        public Builder setMail(String mail) {
            this.mail = mail;
            return this;
        }

        public Builder setStatutPublicPrive(String statutPublicPrive) {
            this.statutPublicPrive = statutPublicPrive;
            return this;
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Etablissement build() {
            return new Etablissement(
                    nomEtablissement,
                    typeEtablissement,
                    codeCommune,
                    nomCommune,
                    mail,
                    statutPublicPrive,
                    id
            );
        }
    }

    // ===== Setters =====
    public void setNomEtablissement(String nomEtablissement) {
        this.nomEtablissement = nomEtablissement;
    }

    public void setTypeEtablissement(String typeEtablissement) {
        this.typeEtablissement = typeEtablissement;
    }

    public void setCodeCommune(String codeCommune) {
        this.codeCommune = codeCommune;
    }

    public void setNomCommune(String nomCommune) {
        this.nomCommune = nomCommune;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setStatutPublicPrive(String statutPublicPrive) {
        this.statutPublicPrive = statutPublicPrive;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // ===== Getters =====
    public String getNomEtablissement() {
        return nomEtablissement;
    }

    public String getTypeEtablissement() {
        return typeEtablissement;
    }

    public String getCodeCommune() {
        return codeCommune;
    }

    public String getNomCommune() {
        return nomCommune;
    }

    public String getMail() {
        return mail;
    }

    public String getStatutPublicPrive() {
        return statutPublicPrive;
    }

    public Integer getId() {
        return id;
    }

    // ===== Affichage =====
    @Override
    public String toString() {
        return String.format(
                "établissement: %-30s type: %-15s commune: %-20s code: %-6s statut: %-10s mail: %s",
                getNomEtablissement(),
                getTypeEtablissement(),
                getNomCommune(),
                getCodeCommune(),
                getStatutPublicPrive(),
                getMail()
        );
    }
}
