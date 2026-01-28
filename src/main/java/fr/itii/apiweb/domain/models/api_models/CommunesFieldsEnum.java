package fr.itii.apiweb.domain.models.api_models;

public enum CommunesFieldsEnum {
    NOM("Nom"),
    CODE_POSTAL("codePostal"),
    DEPARTEMENT("codeDepartement");

    private final String field;

    CommunesFieldsEnum(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
