package fr.itii.apiweb.domain.models.enums;

public enum APIField {;

    public enum Commune {
        NOM("nom"),
        CODE_COMMUNE("code"),
        CODE_DEPARTEMENT("codeDepartement"),
        CODE_POSTAL("codePostal");

        private final String field;
        Commune(String field) {
            this.field = field;
        }
        @Override
        public String toString() {
            return field;
        }
    }

    public enum Etablissement {
        CODE_DEPARTEMENT("code_departement"),
        CODE_POSTAL("code_postal");

        private final String field;
        Etablissement(String field) {
            this.field = field;
        }
        @Override
        public String toString() {
            return field;
        }
    }
}
