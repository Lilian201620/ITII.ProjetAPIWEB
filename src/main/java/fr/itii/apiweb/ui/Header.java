package fr.itii.apiweb.ui;

public enum Header {
    COMMUNE(String.format("%-4s %-40s %-30s %-30s %-30s %-10s",
        "#",
        "Nom",
        "Departement",
        "Region",
        "Codes Postaux",
        "Population"
    )),

    ETABLISSEMENT(String.format("%-4s %-40s %-30s %-30s %-30s %-10s",
        "#",
        "Nom",
        "Type",
        "Commune",
        "",
        "Population"
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
