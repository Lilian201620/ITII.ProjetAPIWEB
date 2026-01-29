package fr.itii.apiweb.ui;

import fr.itii.apiweb.domain.models.Commune;
import fr.itii.apiweb.domain.models.Etablissement;
import fr.itii.apiweb.domain.tools.Backend;

import java.util.List;
import java.util.Scanner;

public class Manager {
    private final Terminal t = new Terminal();
    private final Backend b = new Backend();
    private final Scanner sc = new Scanner(System.in);

    // =========================================================
    //  PRINCIPAL
    //  =========================================================

    public void open() {
        while (true) {
            switch (t.showMenu()) {
                //Recherche API
                case "1" -> {
                    call();
                }

                //Recherche DB
                case "2" -> {
                    read();
                }

                //Delete DB
                case "3" -> {
                    delete();
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

    private void call() {
        switch (t.showMenuSearchAPI()) {
            //Commune par nom
            case "1" -> {
                List<Commune> res = b.searchCommuneFromAPIByNom(
                        t.showConfig("Recherche commune dans API", "Nom de la commune: ")
                );
                t.showList(res);
                call(res);
            }

            //Commune par code postal
            case "2" -> {
                List<Commune> res = b.searchCommuneFromAPIByCodePostal(
                        t.showConfig("Recherche commune dans API", "Numero du code postal: ")
                );
                t.showList(res);
                call(res);
            }

            //Commune par departement
            case "3" -> {
                List<Commune> res = b.searchCommuneFromAPIByDepartement(
                        t.showConfig("Recherche commune dans API", "Numero de departement: ")
                );
                t.showList(res);
                call(res);
            }

            //Etablissement par nom de la commune
            case "4" -> {
                List<Etablissement> res = b.searchEtablissementFromAPIByNomCommune(
                        t.showConfig("Recherche etablissement dans API", "Nom de la commune: ")
                );
                t.showList(res);
                call(res);
            }

            //Etablissement par departement
            case "5" -> {
                List<Etablissement> res = b.searchEtablissementFromAPIByNomCommune(
                        t.showConfig("Recherche etablissement dans API", "Numero de departement: ")
                );
                t.showList(res);
                call(res);
            }

            //Retour
            default -> { open();}
        }
    }

    // ==========================================================
    //  API2
    //  =========================================================

    private <T> void call(List<T> liste) {
        while (true) {
            switch (t.showMenuAPI()) {
                //Page précédente
                case "1" -> {
                    t.showList(liste, t.getIndex() - 20);
                    call(liste);
                }

                //Page suivante
                case "2" -> {
                    t.showList(liste, t.getIndex());
                    call(liste);
                }

                //Save par indice
                case "3" -> {
                    String param = t.showAction(
                            "Sauvegarde",
                            "Liste des indices: "
                    );
                    if (!liste.isEmpty()) {
                        Object first = liste.getFirst();

                        if (first instanceof Commune) {
                            b.saveCommune((List<Commune>) liste, param);
                        } else if (first instanceof Etablissement) {
                            b.saveEtablissement((List<Etablissement>) liste, param);
                        }
                    }
                    t.showList(liste);
                    call(liste);
                }

                //Save tout
                case "4" -> {
                    if (!liste.isEmpty()) {
                        Object first = liste.getFirst();

                        if (first instanceof Commune) {
                            b.saveCommune((List<Commune>) liste);
                        } else if (first instanceof Etablissement) {
                            b.saveEtablissement((List<Etablissement>) liste);
                        }
                    }
                    open();
                }

                // nouvelle recherche
                case "5" -> {
                    call();
                }

                // retour menu principal
                case "6" -> {
                    open();
                }

                default -> {}
            }
        }
    }



    // ==========================================================
    //  DATABASE
    //  =========================================================

    private void read() {
        switch (t.showMenuSearchDB()) {
            //Commune par nom
            case "1" -> {
                List<Commune> res = b.searchCommuneFromDBByNom(
                        t.showConfig("Recherche commune dans DB", "Nom de la commune: ")
                );
                t.showList(res);
                read(res);
            }

            //Commune par code postal
            case "2" -> {
                List<Commune> res = b.searchCommuneFromDBByCodePostal(
                        t.showConfig("Recherche commune dans DB", "Numero du code postal: ")
                );
                t.showList(res);
                read(res);
            }
            //Commune par departement
            case "3" -> {
                List<Commune> res = b.searchCommuneFromDBByDepartement(
                        t.showConfig("Recherche commune dans DB", "Numero de departement: ")
                );
                t.showList(res);
                read(res);
            }

            //Commune par region
            case "4" -> {
                List<Commune> res = b.searchCommuneFromDBByRegion(
                        t.showConfig("Recherche commune dans DB", "Numero de region: ")
                );
                t.showList(res);
                read(res);
            }

            //Etablissement par nom
            case "5" -> {
                List<Etablissement> res = b.searchEtablissementFromDBByNom(
                        t.showConfig("Recherche etablissement dans DB", "Nom de l'etablissement: ")
                );
                t.showList(res);
                read(res);

            }
            //Etablissement par type
            case "6" -> {
                List<Etablissement> res = b.searchEtablissementFromDBByType(
                        t.showConfig("Recherche etablissement dans DB","Type d'etablissement: ")
                );
                t.showList(res);
                read(res);

            }
            //Etablissement par nom de commune
            case "7" -> {
                List<Etablissement> res = b.searchEtablissementFromDBByNomCommune(
                        t.showConfig("Recherche etablissement dans DB", "Nom de la commune: ")
                );
                t.showList(res);
                read(res);
            }

            //Etablissment par code postal
            case "8" -> {
                List<Etablissement> res = b.searchEtablissementFromAPIByNom(
                        t.showConfig("Recherche etablissement dans DB", "Numero du code postal: ")
                );
                t.showList(res);
                read(res);
            }

            //Etablissement par departement
            case "9" -> {
                List<Etablissement> res = b.searchEtablissementFromDBByDepartement(
                        t.showConfig("Recherche etablissement dans DB", "Numero de departement: ")
                );
                t.showList(res);
                read(res);
            }

            //Etablissement par region
            case "10" -> {
                List<Etablissement> res = b.searchEtablissementFromDBByRegion(
                        t.showConfig("Recherche etablissement dans DB", "Numero de region: ")
                );
                t.showList(res);
                read(res);
            }

            //Retour
            default -> open();
        }
    }

    // ==========================================================
    //  DATABASE
    //  =========================================================

    private <T> void read(List<T> liste) {
        while (true) {
            switch (t.showMenuDB()) {
                //Page précédente
                case "1" -> {
                    t.showList(liste, t.getIndex() - 20);
                    read(liste);
                }
                //Page suivante
                case "2" -> {
                    t.showList(liste, t.getIndex());
                    read(liste);
                }

                //Delete par indice
                case "3" -> {
                    String param = t.showAction(
                            "Supprimer",
                            "Liste des indices: "
                    );
                    if (!liste.isEmpty()) {
                        Object first = liste.get(0);

                        if (first instanceof Commune) {
                            b.deleteCommune((List<Commune>) liste, param);
                        } else if (first instanceof Etablissement) {
                            b.deleteEtablissement((List<Etablissement>) liste, param);
                        }
                    }
                    t.showList(liste);
                    read(liste);
                }

                //Delete tout
                case "4" -> {
                    if (!liste.isEmpty()) {
                        Object first = liste.getFirst();

                        if (first instanceof Commune) {
                            b.deleteCommune((List<Commune>) liste);
                        } else if (first instanceof Etablissement) {
                            b.deleteEtablissement((List<Etablissement>) liste);
                        }
                    }
                    open();
                }

                // nouvelle recherche
                case "5" -> {
                    read();
                }
                // retour menu principal
                case "6" -> {
                    open();
                }

                default -> {}
            }
        }
    }

    private void delete() {
        while (true) {
            switch (t.showMenuDeleteDB()) {
                //Supprimer commune
                case "1" -> {
                    b.deleteCommune();
                    delete();
                }

                //Supprimer etablissment
                case "2" -> {
                    b.deleteEtablissement();
                    delete();
                }

                //Retour
                case "3" -> {
                    open();
                }

                default -> {}
            }
        }
    }
}
