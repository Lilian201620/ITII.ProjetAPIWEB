package fr.itii.apiweb.domain.models.api_models;

public enum CommunesFieldsEnum {
    NOM("nom"),
    CODE_POSTAL("codePostal"),
    DEPARTEMENT("codeDepartement"),
    CODE_COMMUNE("code"),
    REGION("codeRegion");

    private final String field;

    CommunesFieldsEnum(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
