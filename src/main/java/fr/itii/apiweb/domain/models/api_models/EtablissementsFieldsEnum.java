package fr.itii.apiweb.domain.models.api_models;

public enum EtablissementsFieldsEnum {

    CODE_COMMUNE("code_commune"),
    NOM_COMMUNE("nom_commune"),
    CODE_DEPARTEMENT("code_departement");

    private final String field;

    EtablissementsFieldsEnum(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
