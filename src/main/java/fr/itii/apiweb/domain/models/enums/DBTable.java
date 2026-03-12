package fr.itii.apiweb.domain.models.enums;

public enum DBTable {
    COMMUNES("communes"),
    ETABLISSEMENTS("etablissements"),
    ENTREPRISES("entreprises");

    private String sql;

    private DBTable(String sql){
        this.sql = sql;
    }

    public String toString(){
        return this.sql;
    }

    public enum Commune {
        ID("id"),
        NOM("nom"),
        CODE_COMMUNE("codecommune"),
        CODE_DEPARTEMENT("codedepartement"),
        NOM_DEPARTEMENT("nomdepartement"),
        CODE_REGION("coderegion"),
        NOM_REGION("nomregion"),
        CODE_POSTAL("codepostal"),
        CODES_POSTAUX("codespostaux"),
        POPULATION("population"),
        LONGITUDE("longitude"),
        LATITUDE("latitude");

        private final String sql;

        Commune(String sql) {
            this.sql = sql;
        }

        @Override
        public String toString() {
            return sql;
        }

    }

    public enum Etablissement {
        ID("id"),
        NOM("nom"),
        TYPE("type"),
        MAIL("mail"),
        STATUT("statut"),
        CODE_COMMUNE("codecommune"),
        NOM_COMMUNE("nomcommune");

        private final String sql;

        Etablissement(String sql) {
            this.sql = sql;
        }

        @Override
        public String toString() {
            return sql;
        }
    }
}
