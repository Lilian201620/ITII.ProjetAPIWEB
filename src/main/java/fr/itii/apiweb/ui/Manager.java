package fr.itii.apiweb.ui;

import fr.itii.apiweb.domain.models.Commune;
import fr.itii.apiweb.domain.models.Etablissement;
import fr.itii.apiweb.domain.tools.Backend;

import java.util.List;
import java.util.Scanner;

public class Manager {
    private final Terminal a = new Terminal();
    private final Backend b = new Backend();
    private final Scanner sc = new Scanner(System.in);

    // =========================================================
    //  PRINCIPAL
    //  =========================================================

    public void flow() {
        while (true) {
            switch (a.showMenu()) {
                //Recherche API
                case "1" -> {
                    flowAPI();
                }

                //Recherche DB
                case "2" -> {
                    flowDB();
                }

                //Delete DB
                case "3" -> {
                    deleteDatabase();
                }

                //Quitter
                case "0" -> {
                    System.exit(0);
                }

                default -> {}
            }
        }

    }

    // ==========================================================
    //  API
    //  =========================================================

    private void flowAPI() {
        switch (a.showMenuSearchAPI()) {
            //Commune par nom
            case "1" -> {
                List<Commune> res = b.searchCommuneFromAPIByNom(
                        a.showConfig("Recherche commune dans API", "Nom de la commune: ")
                );
                a.showList(res);
                flowAPI(res);
            }

            //Commune par code postal
            case "2" -> {
                List<Commune> res = b.searchCommuneFromAPIByCodePostal(
                        a.showConfig("Recherche commune dans API", "Numéro du code postal: ")
                );
                a.showList(res);
                flowAPI(res);
            }

            //Commune par departement
            case "3" -> {
                List<Commune> res = b.searchCommuneFromAPIByDepartement(
                        a.showConfig("Recherche commune dans API", "Numéro de département: ")
                );
                a.showList(res);
                flowAPI(res);
            }

            //Etablissement par commune
            case "4" -> {
                List<Etablissement> res = b.searchEtablissementFromAPIByNomCommune(
                        a.showConfig("Recherche etablissement dans API", "Nom de la commune: ")
                );
                a.showList(res);
                flowAPI(res);
            }

            //Etablissement par code commune
            case "5" -> {
                List<Etablissement> res = b.searchEtablissementFromAPIByCodeCommune(
                        a.showConfig("Recherche etablissement dans API", "Numero de la commune: ")
                );
                a.showList(res);
                flowAPI(res);
            }

            //Etablissement par departement
            case "6" -> {
                List<Etablissement> res = b.searchEtablissementFromAPIByDepartement(
                        a.showConfig("Recherche etablissement dans API", "Numero de departement: ")
                );
                a.showList(res);
                flowAPI(res);
            }

            //Retour
            default -> { flow();}
        }
    }

    // ==========================================================
    //  API vers List
    //  =========================================================

    private <T> void flowAPI(List<T> liste) {
        while (true) {
            switch (a.showMenuAPI()) {
                //Page précédente
                case "1" -> {
                    a.showList(liste, a.getIndex() - 20);
                    flowAPI(liste);
                }

                //Page suivante
                case "2" -> {
                    a.showList(liste, a.getIndex());
                    flowAPI(liste);
                }

                //Save
                case "3" -> {
                    if (!liste.isEmpty()) {
                        Object first = liste.get(0);

                        if (first instanceof Commune) {
                            b.saveCommune((List<Commune>) liste);
                        } else if (first instanceof Etablissement) {
                            b.saveEtablissement((List<Etablissement>) liste);
                        }
                    }
                    flowAPI(liste);
                }

                // nouvelle recherche
                case "4" -> {
                    flowAPI();
                }

                // retour menu principal
                case "5" -> {
                    flow();
                }

                default -> {}
            }
        }
    }



    // ==========================================================
    //  DATABASE
    //  =========================================================

    private void flowDB() {
        switch (a.showMenuSearchDB()) {
            //Commune par nom
            case "1" -> {
                List<Commune> res = b.searchCommuneFromDBByNom(
                        a.showConfig("Recherche commune dans DB", "Nom de la commune: ")
                );
                a.showList(res);
                flowDB(res);
            }

            //Commune par code postal
            case "2" -> {
                List<Commune> res = b.searchCommuneFromDBByCodePostal(
                        a.showConfig("Recherche commune dans DB", "Numero du code postal: ")
                );
                a.showList(res);
                flowDB(res);
            }
            //Commune par departement
            case "3" -> {
                List<Commune> res = b.searchCommuneFromDBByDepartement(
                        a.showConfig("Recherche commune dans DB", "Numero de departement: ")
                );
                a.showList(res);
                flowDB(res);
            }

            //Etablissement par commune
            case "4" -> {
                List<Etablissement> res = b.searchEtablissementFromDBByNomCommune(
                        a.showConfig("Recherche etablissement dans DB", "Nom de la commune: ")
                );
                a.showList(res);
                flowDB(res);

            }
            //Etablissement par code commune
            case "5" -> {
                List<Etablissement> res = b.searchEtablissementFromDBByCodeCommune(
                        a.showConfig("Recherche etablissement dans DB","Numéro de la commune: ")
                );
                a.showList(res);
                flowDB(res);

            }
            //Etablissement par departement
            case "6" -> {
                List<Etablissement> res = b.searchEtablissementFromDBByDepartement(
                        a.showConfig("Recherche etablissement dans DB", "Numero de departement: ")
                );
                a.showList(res);
                flowDB(res);
            }

            //Retour
            default -> flow();
        }
    }

    // ==========================================================
    //  DATABASE vers List
    //  =========================================================

    private <T> void flowDB(List<T> liste) {
        while (true) {
            switch (a.showMenuDB()) {
                //Page précédente
                case "1" -> {
                    a.showList(liste, a.getIndex() - 20);
                    flowDB(liste);
                }
                //Page suivante
                case "2" -> {
                    a.showList(liste, a.getIndex());
                    flowDB(liste);
                }

                // nouvelle recherche
                case "3" -> {
                    flowDB();
                }
                // retour menu principal
                case "4" -> {
                    flow();
                }

                default -> {}
            }
        }
    }

    private void deleteDatabase() {
        flow();
    }
}
