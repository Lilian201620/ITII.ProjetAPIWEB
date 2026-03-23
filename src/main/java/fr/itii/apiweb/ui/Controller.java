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




    private void open() {
        while (true) {
            switch (t.showMenu()) {

                case "1" -> {
                    call();
                }


                case "2" -> {
                    read();
                }


                case "3" -> {
                    delete();
                }


                case "4" -> {
                    meteo();
                }


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


    private void call() {
        switch (t.showMenuSearchAPI()) {

            case "1" -> {
                List<Commune> res = b.searchCommuneFromAPIByNom(
                        t.showConfig("Recherche commune dans API", "Nom de la commune: ")
                );
                t.showList(res, Header.COMMUNE, 0);
                call(res, Header.COMMUNE);
            }




            case "2" -> {
                List<Commune> res = b.searchCommuneFromAPIByCodePostal(
                        t.showConfig("Recherche commune dans API", "Numero du code postal: ")
                );
                t.showList(res, Header.COMMUNE, 0);
                call(res, Header.COMMUNE);
            }


            case "3" -> {
                List<Commune> res = b.searchCommuneFromAPIByDepartement(
                        t.showConfig("Recherche commune dans API", "Numero de departement: ")
                );
                t.showList(res, Header.COMMUNE, 0);
                call(res, Header.COMMUNE);
            }


            case "4" -> {
                List<Etablissement> res = b.searchEtablissementFromAPIByCodePostal(
                        t.showConfig("Recherche etablissement dans API", "Numero du code postal: ")
                );
                t.showList(res, Header.ETABLISSEMENT, 0);
                call(res, Header.ETABLISSEMENT);
            }


            case "5" -> {
                List<Etablissement> res = b.searchEtablissementFromAPIByDepartement(
                        t.showConfig("Recherche etablissement dans API", "Numero de departement: ")
                );
                t.showList(res, Header.ETABLISSEMENT, 0);
                call(res, Header.ETABLISSEMENT);
            }


            default -> { open();}
        }
    }


    private <T> void call(List<T> liste, Header header) {
        while (true) {
            switch (t.showMenuAPI()) {

                case "1" -> {
                    t.showList(liste, header, t.getIndex() - 20);
                    call(liste, header);
                }


                case "2" -> {
                    t.showList(liste, header, t.getIndex());
                    call(liste, header);
                }


                case "3" -> {
                    b.saveList(liste, t.showSelect(
                            "Sauvegarde",
                            "Liste des indices: "
                    ));
                    t.showList(liste, header, 0);
                    call(liste, header);
                }


                case "4" -> {
                    b.saveList(liste);
                    t.showList(liste, header, 0);
                    call(liste, header);
                }


                case "5" -> {
                    call();
                }


                case "6" -> {
                    open();
                }

                default -> {}
            }
        }
    }




    private <T> void read() {
        switch (t.showMenuSearchDB()) {

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


            case "2" -> {
                String value = t.showConfig("Recherche commune dans DB", "Numero du code postal: ");

                Queryable q = new Queryable() {
                    @Override
                    public <T> List<T> search() {
                        return (List<T>) b.searchCommuneFromDBByCodePostal(value);
                    }

                    @Override
                    public Header add() {
                        return Header.COMMUNE;
                    }
                };

                t.showList(q.search(),  q.add(), 0);
                read(q);
            }


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


            default -> open();
        }
    }


    private <T> void read(Queryable q) {
        while (true) {
            switch (t.showMenuDB()) {

                case "1" -> {
                    t.showList(q.search(), q.add(), t.getIndex() - 20);
                    read(q);
                }

                case "2" -> {
                    t.showList(q.search(), q.add(), t.getIndex());
                    read(q);
                }


                case "3" -> {

                    b.deleteList(q.search(), t.showSelect(
                            "Supprimer",
                            "Liste des indices: "
                    ));
                    t.showList(q.search(), q.add(), 0);
                    read(q);
                }


                case "4" -> {
                    b.deleteList(q.search());
                    t.showList(q.search(), q.add(), 0);
                    read(q);
                }


                case "5" -> {
                    read();
                }

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

                case "1" -> {
                    b.deleteCommune();
                    delete();
                }


                case "2" -> {
                    b.deleteEtablissement();
                    delete();
                }


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
