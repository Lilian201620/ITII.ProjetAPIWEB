package fr.itii.apiweb.domain.models.db_models;

public enum CommunesCol {
    ID("id"),
    NOM("nom"),
    CODE_COMMUNE("codecommune"),
    CODE_DEPARTEMENT("codedepartement"),
    CODE_POSTAL("codepostal"),
    CODE_REGION("coderegion"),
    POPULATION("population");

    private final String _sql;

    CommunesCol(String _sql) {
        this._sql = _sql;
    }
    @Override
    public String toString() {
        return _sql;
    }
}
