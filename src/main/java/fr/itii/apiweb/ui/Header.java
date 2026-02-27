package fr.itii.apiweb.ui;

public enum Header {

    COMMUNE(
            "%-40s %-30s %-30s %-30s %-10s",
            new String[]{
                    "Nom de la commune",
                    "Departement",
                    "Region",
                    "Codes Postaux",
                    "Population"
            }
    ),
    ETABLISSEMENT(
            "%-60s %-25s %-30s %-15s %-35s %-10s",
            new String[]{
                    "Nom de l'etablissement",
                    "Type",
                    "Nom de la commune",
                    "Code commune",
                    "mail",
                    "statut"
            }
    ),
    METEO(
            "%-40s %-10s",
            new String[]{"Nom de la commune", "Température"}
    ),
    ENTREPRISE(
            "%-50s %-40s",
            new String[]{"Nom de l'entreprise", "Adresse"}
    );

    public final String format;
    public final String[] titles;

    Header(String format, String[] titles) {
        this.format = format;
        this.titles = titles;
    }

    // Affiche l'en-tête formaté
    @Override
    public String toString() {
        return String.format(format, (Object[]) titles);
    }
}