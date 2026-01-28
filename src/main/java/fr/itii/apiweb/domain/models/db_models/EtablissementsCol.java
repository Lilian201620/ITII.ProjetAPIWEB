package fr.itii.apiweb.domain.models.db_models;

public enum EtablissementsCol {
    ID("id"),
    NOM("nom"),
    TYPE("type"),
    MAIL("mail"),
    STATUT("statut"),
    CODE_COMMUNE("codecommune"),
    NOM_COMMUNE("nomcommune");

    private final String _sql;

    EtablissementsCol(String _sql) {
        this._sql = _sql;
    }
    @Override
    public String toString() {
        return _sql;
    }
}
