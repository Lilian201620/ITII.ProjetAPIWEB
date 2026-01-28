package fr.itii.apiweb.data.local;

import fr.itii.apiweb.domain.models.Commune;
import fr.itii.apiweb.domain.models.dbModels.CommunesCol;
import fr.itii.apiweb.domain.models.dbModels.EtablissementsCol;
import fr.itii.apiweb.domain.models.dbModels.Tables;

import java.sql.SQLException;
import java.util.List;

/**
 * Cette interface recense les méthodes nécessaires pour être un DataRepository
 * @author Nathan
 * @version 1.0.0
 */
public interface DataRepository {
    void save(List<Commune> communes);
    List<Commune> getAll();
    List<Commune> getCommune(CommunesCol col, String Name, boolean OnlyExplicitCaracters);
    List<Commune> getEtablissement(EtablissementsCol col, String Name, boolean OnlyExplicitCaracters);
    List<Commune> getByCodeCommune(String CodeCommune, boolean OnlyExplicitCaracters);
    void deleteByName(String Name);
    void deleteById(long id);
    void deleteByCodeCommune(String CodeCommune);
}
