package fr.itii.apiweb.domain.models.enumlist.db;

public enum DBTable {
    COMMUNES("communes"),
    ETABLISSEMENTS("etablissements");

    private final String sql;

    DBTable(String sql) {
        this.sql = sql;
    }
    @Override
    public String toString() {
        return sql;
    }
}
