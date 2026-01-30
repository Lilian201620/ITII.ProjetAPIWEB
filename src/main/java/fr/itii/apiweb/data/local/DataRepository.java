package fr.itii.apiweb.data.local;

import fr.itii.apiweb.domain.models.objet.Commune;
import fr.itii.apiweb.domain.models.enumlist.db.DBCommune;
import fr.itii.apiweb.domain.models.enumlist.db.DBEtablissement;
import fr.itii.apiweb.domain.models.objet.Etablissement;

import java.util.List;

/**
 * Cette interface recense les méthodes nécessaires pour être un DataRepository
 * @author Nathan
 * @version 1.0.0
 */
public interface DataRepository {
    // savers
    void saveCommunes(List<Commune> communes);
    void saveEtablissements(List<Etablissement> Etablissements);

    // getters
    List<Commune> getCommunes(DBCommune col, String Name, boolean OnlyExplicitCaracters);
    List<Commune> getCommunes(DBCommune col, int critere);
    List<Commune> getCommunes();
    List<Etablissement> getEtablissements(DBEtablissement col, String Name, boolean OnlyExplicitCaracters);
    List<Etablissement> getEtablissements(DBEtablissement col, int critere);
    List<Etablissement> getEtablissements();

    // deleters
    void deleteCommunes(DBCommune col, String critere, boolean explicitCaractersOnly);
    void deleteCommunes(DBCommune col, int critere);
    void deleteCommunes();
    void deleteEtablissements(DBEtablissement col, String critere, boolean explicitCaractersOnly);
    void deleteEtablissements(DBEtablissement col, int critere);
    void deleteEtablissements();

    // getter joined tables
    List<Etablissement> getJoin(DBCommune col, String critere, boolean onlyExplicit);
}
