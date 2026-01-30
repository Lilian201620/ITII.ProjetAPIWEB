package fr.itii.apiweb.domain.models.enumlist.db;

public enum DBCommune {
    ID("id"),
    NOM("nom"),
    CODE_COMMUNE("codecommune"),
    CODE_DEPARTEMENT("codedepartement"),
    CODE_POSTAL("codepostal"),
    CODE_REGION("coderegion"),
    POPULATION("population");

    private final String sql;

    DBCommune(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return sql;
    }

}