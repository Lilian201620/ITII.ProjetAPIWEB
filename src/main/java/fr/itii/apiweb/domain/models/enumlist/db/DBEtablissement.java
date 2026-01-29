package fr.itii.apiweb.domain.models.enumlist.db;

public enum DBEtablissement {
    ID("id"),
    NOM("nom"),
    TYPE("type"),
    MAIL("mail"),
    STATUT("statut"),
    CODE_COMMUNE("codecommune"),
    NOM_COMMUNE("nomcommune");

    private final String sql;

    DBEtablissement(String sql) {
        this.sql = sql;
    }
    @Override
    public String toString() {
        return sql;
    }
}
