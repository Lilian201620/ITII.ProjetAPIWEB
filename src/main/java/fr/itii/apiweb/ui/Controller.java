package fr.itii.apiweb.ui;

import fr.itii.apiweb.domain.models.objet.Commune;
import fr.itii.apiweb.domain.models.objet.Entreprise;
import fr.itii.apiweb.domain.models.objet.Etablissement;
import fr.itii.apiweb.domain.models.objet.Meteo;
import fr.itii.apiweb.domain.tools.Backend;

import java.util.List;
import java.util.function.Function;

public class Controller {
    private final Terminal t = new Terminal();
    private final Backend b = new Backend();

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

                //Meteo
                case "4" -> {
                    meteo();
                }

                //Entreprise
                case "5" -> {
                    entreprise();
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
                t.showList(res, Header.COMMUNE);
                call(res, Header.COMMUNE);
            }



            //Commune par code postal
            case "2" -> {
                List<Commune> res = b.searchCommuneFromAPIByCodePostal(
                        t.showConfig("Recherche commune dans API", "Numero du code postal: ")
                );
                t.showList(res, Header.COMMUNE);
                call(res, Header.COMMUNE);
            }

            //Commune par departement
            case "3" -> {
                List<Commune> res = b.searchCommuneFromAPIByDepartement(
                        t.showConfig("Recherche commune dans API", "Numero de departement: ")
                );
                t.showList(res, Header.COMMUNE);
                call(res, Header.COMMUNE);
            }

            //Etablissement par code postal
            case "4" -> {
                List<Etablissement> res = b.searchEtablissementFromAPIByCodePostal(
                        t.showConfig("Recherche etablissement dans API", "Numero du code postal: ")
                );
                t.showList(res, Header.ETABLISSEMENT);
                call(res, Header.ETABLISSEMENT);
            }

            //Etablissement par departement
            case "5" -> {
                List<Etablissement> res = b.searchEtablissementFromAPIByDepartement(
                        t.showConfig("Recherche etablissement dans API", "Numero de departement: ")
                );
                t.showList(res, Header.ETABLISSEMENT);
                call(res, Header.ETABLISSEMENT);
            }

            //Retour
            default -> { open();}
        }
    }

    // ==========================================================
    //  API2
    //  =========================================================

    private <T> void call(List<T> liste, Header header) {
        while (true) {
            switch (t.showMenuAPI()) {
                //Page précédente
                case "1" -> {
                    t.showList(liste, header, t.getIndex() - 20);
                    call(liste, header);
                }

                //Page suivante
                case "2" -> {
                    t.showList(liste, header, t.getIndex());
                    call(liste, header);
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
                    t.showList(liste, header);
                    call(liste, header);
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
                    t.showList(liste, header);
                    call(liste, header);
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
                String value = t.showConfig("Recherche commune dans DB", "Nom de la commune: ");
                List<Commune> res = b.searchCommuneFromDBByNom(value);
                t.showList(res,  Header.COMMUNE);
                read(res,Header.COMMUNE, b.searchCommuneFromDBByNom, value);
            }

            //Commune par code postal
            case "2" -> {
                String value = t.showConfig("Recherche commune dans DB", "Numero du code postal: ");
                List<Commune> res = b.searchCommuneFromDBByCodePostal(value);
                t.showList(res,   Header.COMMUNE);
                read(res, Header.COMMUNE, b.searchCommuneFromDBByCodePostal, value);
            }
            //Commune par departement
            case "3" -> {
                String value = t.showConfig("Recherche commune dans DB", "Numero ou nom du departement: ");
                List<Commune> res = b.searchCommuneFromDBByDepartement(value);
                t.showList(res, Header.COMMUNE);
                read(res, Header.COMMUNE, b.searchCommuneFromDBByDepartement, value);
            }

            //Commune par region
            case "4" -> {
                String value = t.showConfig("Recherche commune dans DB", "Numero ou nom de la region: ");
                List<Commune> res = b.searchCommuneFromDBByRegion(value);
                t.showList(res, Header.ETABLISSEMENT);
                read(res, Header.ETABLISSEMENT, b.searchCommuneFromDBByRegion, value);
            }

            //Etablissement par nom
            case "5" -> {
                String value = t.showConfig("Recherche etablissement dans DB", "Nom de l'etablissement: ");
                List<Etablissement> res = b.searchEtablissementFromDBByNom(value);
                t.showList(res, Header.ETABLISSEMENT);
                read(res, Header.ETABLISSEMENT, b.searchEtablissementFromDBByNom,  value);

            }
            //Etablissement par type
            case "6" -> {
                String value = t.showConfig("Recherche etablissement dans DB","Type d'etablissement: ");
                List<Etablissement> res = b.searchEtablissementFromDBByType(value);
                t.showList(res, Header.ETABLISSEMENT);
                read(res, Header.ETABLISSEMENT, b.searchEtablissementFromDBByType, value);

            }
            //Etablissement par nom de commune
            case "7" -> {
                String value = t.showConfig("Recherche etablissement dans DB", "Nom de la commune: ");
                List<Etablissement> res = b.searchEtablissementFromDBByNomCommune(value);
                t.showList(res, Header.ETABLISSEMENT);
                read(res, Header.ETABLISSEMENT, b.searchEtablissementFromDBByNomCommune, value);
            }

            //Etablissment par code postal
            case "8" -> {
                String value = t.showConfig("Recherche etablissement dans DB", "Numero du code postal: ");
                List<Etablissement> res = b.searchEtablissementFromAPIByCodePostal(value);
                t.showList(res, Header.ETABLISSEMENT);
                read(res, Header.ETABLISSEMENT, b.searchEtablissementFromAPIByCodePostal, value);
            }

            //Etablissement par departement
            case "9" -> {
                String value = t.showConfig("Recherche etablissement dans DB", "Numero de departement: ");
                List<Etablissement> res = b.searchEtablissementFromDBByDepartement(value);
                t.showList(res, Header.ETABLISSEMENT);
                read(res, Header.ETABLISSEMENT, b.searchEtablissementFromDBByDepartement, value);
            }

            //Etablissement par region
            case "10" -> {
                String value = t.showConfig("Recherche etablissement dans DB", "Numero de region: ");
                List<Etablissement> res = b.searchEtablissementFromDBByRegion(value);
                t.showList(res, Header.ETABLISSEMENT);
                read(res, Header.ETABLISSEMENT, b.searchEtablissementFromDBByRegion, value);
            }

            //Retour
            default -> open();
        }
    }

    // ==========================================================
    //  DATABASE
    //  =========================================================

    private <T> void read(List<T> liste, Header header, Function<String, List<T>> callback, String value) {
        while (true) {
            switch (t.showMenuDB()) {
                //Page précédente
                case "1" -> {
                    t.showList(liste, header, t.getIndex() - 20);
                    read(liste, header, callback, value);
                }
                //Page suivante
                case "2" -> {
                    t.showList(liste, header, t.getIndex());
                    read(liste, header, callback, value);
                }

                //Delete par indice
                case "3" -> {
                    String index = t.showAction(
                            "Supprimer",
                            "Liste des indices: "
                    );
                    if (!liste.isEmpty()) {
                        Object first = liste.getFirst();

                        if (first instanceof Commune) {
                            b.deleteCommune((List<Commune>) liste, index);
                        } else if (first instanceof Etablissement) {
                            b.deleteEtablissement((List<Etablissement>) liste, index);
                        }

                        List<T> newListe = callback.apply(value);
                        t.showList(newListe, header);
                        read(newListe, header, callback, value);

                    } else {
                        read(liste, header, callback, value);
                    }
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
                    List<T> newListe = callback.apply(value);
                    t.showList(newListe, header);
                    read(newListe, header, callback, value);
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

    private void meteo(){
        String value = t.showConfig("Meteo", "Nom de la commune inscrite dans la DB: ");
        List<Meteo> liste = b.searchMeteoFromAPIByDBNomCommune(value);
        t.showList(liste, Header.METEO);
        t.scan();
        open();
    }

    private void entreprise(){
        String value = t.showConfig("Entreprise", "Nom de la commune inscrite dans la DB: ");
        List<Entreprise> liste = b.searchEntrepriseFromAPIByDBNomCommune(value);
        t.showList(liste, Header.ENTREPRISE);
        t.scan();
        open();
    }
}
