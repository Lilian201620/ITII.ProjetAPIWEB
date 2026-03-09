package fr.itii.apiweb.ui;

import fr.itii.apiweb.domain.models.objets.Commune;
import fr.itii.apiweb.domain.models.objets.Entreprise;
import fr.itii.apiweb.domain.models.objets.Etablissement;
import fr.itii.apiweb.domain.models.objets.Meteo;
import fr.itii.apiweb.domain.tools.Backend;
import fr.itii.apiweb.domain.tools.Queryable;

import java.util.List;

public class Controller {
    private final Terminal t = new Terminal();
    private final Backend b = new Backend();

    public void init() {
        t.init();
        this.open();
    }

    // =========================================================
    //  PRINCIPAL
    //  =========================================================

    private void open() {
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
                t.showList(res, Header.COMMUNE, 0);
                call(res, Header.COMMUNE);
            }



            //Commune par code postal
            case "2" -> {
                List<Commune> res = b.searchCommuneFromAPIByCodePostal(
                        t.showConfig("Recherche commune dans API", "Numero du code postal: ")
                );
                t.showList(res, Header.COMMUNE, 0);
                call(res, Header.COMMUNE);
            }

            //Commune par departement
            case "3" -> {
                List<Commune> res = b.searchCommuneFromAPIByDepartement(
                        t.showConfig("Recherche commune dans API", "Numero de departement: ")
                );
                t.showList(res, Header.COMMUNE, 0);
                call(res, Header.COMMUNE);
            }

            //Etablissement par code postal
            case "4" -> {
                List<Etablissement> res = b.searchEtablissementFromAPIByCodePostal(
                        t.showConfig("Recherche etablissement dans API", "Numero du code postal: ")
                );
                t.showList(res, Header.ETABLISSEMENT, 0);
                call(res, Header.ETABLISSEMENT);
            }

            //Etablissement par departement
            case "5" -> {
                List<Etablissement> res = b.searchEtablissementFromAPIByDepartement(
                        t.showConfig("Recherche etablissement dans API", "Numero de departement: ")
                );
                t.showList(res, Header.ETABLISSEMENT, 0);
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
                    b.saveList(liste, t.showSelect(
                            "Sauvegarde",
                            "Liste des indices: "
                    ));
                    t.showList(liste, header, 0);
                    call(liste, header);
                }

                //Save tout
                case "4" -> {
                    b.saveList(liste);
                    t.showList(liste, header, 0);
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

    private <T> void read() {
        switch (t.showMenuSearchDB()) {
            //Commune par nom
            case "1" -> {
                String value = t.showConfig("Recherche commune dans DB", "Nom de la commune: ");

                Queryable q = new Queryable() {
                    @Override
                    public <T> List<T> search() {
                        return (List<T>) b.searchCommuneFromDBByNom(value);
                    }

                    @Override
                    public Header add() {
                        return Header.COMMUNE;
                    }
                };

                t.showList(q.search(),  q.add(), 0);
                read(q);
            }

            //Commune par code postal
            case "2" -> {
                String value = t.showConfig("Recherche commune dans DB", "Numero du code postal: ");
                List<Commune> res = b.searchCommuneFromDBByCodePostal(value);

                Queryable q = new Queryable() {
                    @Override
                    public <T> List<T> search() {
                        return (List<T>) b.searchCommuneFromDBByNom(value);
                    }

                    @Override
                    public Header add() {
                        return Header.COMMUNE;
                    }
                };

                t.showList(q.search(),  q.add(), 0);
                read(q);

            }
            //Commune par departement
            case "3" -> {
                String value = t.showConfig("Recherche commune dans DB", "Numero ou nom du departement: ");

                Queryable q = new Queryable() {
                    @Override
                    public <T> List<T> search() {
                        return (List<T>) b.searchCommuneFromDBByDepartement(value);
                    }

                    @Override
                    public Header add() {
                        return Header.COMMUNE;
                    }
                };

                t.showList(q.search(),  q.add(), 0);
                read(q);

            }

            //Commune par region
            case "4" -> {
                String value = t.showConfig("Recherche commune dans DB", "Numero ou nom de la region: ");

                Queryable q = new Queryable() {
                    @Override
                    public <T> List<T> search() {
                        return (List<T>) b.searchCommuneFromDBByRegion(value);
                    }

                    @Override
                    public Header add() {
                        return Header.COMMUNE;
                    }
                };

                t.showList(q.search(),  q.add(), 0);
                read(q);

            }

            //Etablissement par nom
            case "5" -> {
                String value = t.showConfig("Recherche etablissement dans DB", "Nom de l'etablissement: ");

                Queryable q = new Queryable() {
                    @Override
                    public <T> List<T> search() {
                        return (List<T>) b.searchEtablissementFromDBByNom(value);
                    }

                    @Override
                    public Header add() {
                        return Header.ETABLISSEMENT;
                    }
                };

                t.showList(q.search(),  q.add(), 0);
                read(q);


            }
            //Etablissement par type
            case "6" -> {
                String value = t.showConfig("Recherche etablissement dans DB","Type d'etablissement: ");

                Queryable q = new Queryable() {
                    @Override
                    public <T> List<T> search() {
                        return (List<T>) b.searchEtablissementFromDBByType(value);
                    }

                    @Override
                    public Header add() {
                        return Header.ETABLISSEMENT;
                    }
                };

                t.showList(q.search(),  q.add(), 0);
                read(q);


            }
            //Etablissement par nom de commune
            case "7" -> {
                String value = t.showConfig("Recherche etablissement dans DB", "Nom de la commune: ");

                Queryable q = new Queryable() {
                    @Override
                    public <T> List<T> search() {
                        return (List<T>) b.searchEtablissementFromDBByNomCommune(value);
                    }

                    @Override
                    public Header add() {
                        return Header.ETABLISSEMENT;
                    }
                };

                t.showList(q.search(),  q.add(), 0);
                read(q);

            }

            //Etablissment par code postal
            case "8" -> {
                String value = t.showConfig("Recherche etablissement dans DB", "Numero du code postal: ");

                Queryable q = new Queryable() {
                    @Override
                    public <T> List<T> search() {
                        return (List<T>) b.searchEtablissementFromAPIByCodePostal(value);
                    }

                    @Override
                    public Header add() {
                        return Header.ETABLISSEMENT;
                    }
                };

                t.showList(q.search(),  q.add(), 0);
                read(q);

            }

            //Etablissement par departement
            case "9" -> {
                String value = t.showConfig("Recherche etablissement dans DB", "Numero de departement: ");

                Queryable q = new Queryable() {
                    @Override
                    public <T> List<T> search() {
                        return (List<T>) b.searchEtablissementFromDBByDepartement(value);
                    }

                    @Override
                    public Header add() {
                        return Header.ETABLISSEMENT;
                    }
                };

                t.showList(q.search(),  q.add(), 0);
                read(q);

            }

            //Etablissement par region
            case "10" -> {
                String value = t.showConfig("Recherche etablissement dans DB", "Numero de region: ");

                Queryable q = new Queryable() {
                    @Override
                    public <T> List<T> search() {
                        return (List<T>) b.searchEtablissementFromDBByRegion(value);
                    }

                    @Override
                    public Header add() {
                        return Header.ETABLISSEMENT;
                    }
                };

                t.showList(q.search(),  q.add(), 0);
                read(q);

            }

            //Retour
            default -> open();
        }
    }

    // ==========================================================
    //  DATABASE
    //  =========================================================

    private <T> void read(Queryable q) {
        while (true) {
            switch (t.showMenuDB()) {
                //Page précédente
                case "1" -> {
                    t.showList(q.search(), q.add(), t.getIndex() - 20);
                    read(q);
                }
                //Page suivante
                case "2" -> {
                    t.showList(q.search(), q.add(), t.getIndex());
                    read(q);
                }

                //Delete par indice
                case "3" -> {

                    b.deleteList(q.search(), t.showSelect(
                            "Supprimer",
                            "Liste des indices: "
                    ));
                    t.showList(q.search(), q.add(), 0);
                    read(q);
                }

                //Delete tout
                case "4" -> {
                    b.deleteList(q.search());
                    t.showList(q.search(), q.add(), 0);
                    read(q);
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
