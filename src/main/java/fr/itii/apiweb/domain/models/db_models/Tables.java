package fr.itii.apiweb.domain.models.db_models;

public enum Tables {
    COMMUNES("communes"),
    ETABLISSEMENTS("etablissements");

    private final String _sql;

    Tables(String _sql) {
        this._sql = _sql;
    }
    @Override
    public String toString() {
        return _sql;
    }
}
