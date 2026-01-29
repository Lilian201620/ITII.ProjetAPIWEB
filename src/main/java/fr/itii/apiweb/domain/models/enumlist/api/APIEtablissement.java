package fr.itii.apiweb.domain.models.enumlist.api;

public enum APIEtablissement {

    CODE_COMMUNE("code_commune"),
    NOM_COMMUNE("nom_commune"),
    CODE_DEPARTEMENT("code_departement");

    private final String field;

    APIEtablissement(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
