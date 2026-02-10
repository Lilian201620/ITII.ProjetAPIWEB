package fr.itii.apiweb.data.local;

import fr.itii.apiweb.domain.models.enums.DBTable;
import fr.itii.apiweb.domain.models.objet.Commune;
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
    List<Commune> getCommunes(DBTable.Commune col, String Name, boolean OnlyExplicitCaracters);
    List<Commune> getCommunes(DBTable.Commune col, int critere);
    List<Commune> getCommunes();
    List<Etablissement> getEtablissements(DBTable.Etablissement col, String Name, boolean OnlyExplicitCaracters);
    List<Etablissement> getEtablissements(DBTable.Etablissement col, int critere);
    List<Etablissement> getEtablissements();

    // deleters
    void deleteCommunes(DBTable.Commune col, String critere, boolean explicitCaractersOnly);
    void deleteCommunes(DBTable.Commune col, int critere);
    void deleteCommunes();
    void deleteEtablissements(DBTable.Etablissement col, String critere, boolean explicitCaractersOnly);
    void deleteEtablissements(DBTable.Etablissement col, int critere);
    void deleteEtablissements();

    // getter joined tables
    List<Etablissement> getJoin(DBTable.Commune col, String critere, boolean onlyExplicit);
}
