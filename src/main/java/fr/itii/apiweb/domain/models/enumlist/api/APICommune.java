package fr.itii.apiweb.domain.models.enumlist.api;

public enum APICommune {
    NOM("nom"),
    CODE_POSTAL("codePostal"),
    DEPARTEMENT("codeDepartement"),
    CODE_COMMUNE("code"),
    REGION("codeRegion");

    private final String field;

    APICommune(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
