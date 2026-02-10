package fr.itii.apiweb.ui;

public enum Header {

    COMMUNE(String.format("%-4s %-40s %-30s %-30s %-30s %-10s",
        "#",
        "Nom de la commune",
        "Departement",
        "Region",
        "Codes Postaux",
        "Population"
    )),

    ETABLISSEMENT(String.format("%-4s %-50s %-10s %-25s %-10s %-30s %-10s",
        "#",
        "Nom de l'etablissement",
        "Type",
        "Nom de la Commune",
        "Code de la commune",
        "mail",
        "statut"
    )),

    METEO(String.format("%-4s %-40s %-30s",
        "#",
        "Nom de la commune",
        "Temperature"
    )),

    ENTREPRISE(String.format("%-4s %-50s %-40s",
        "#",
        "Nom de l'entreprise",
        "Adresse"
    ));

    public final String header;

    Header(String header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return header;
    }
}
