package fr.itii.apiweb.data.local;

import fr.itii.apiweb.domain.models.Commune;

import java.sql.SQLException;
import java.util.List;

public interface DataRepository {
    void save(List<Commune> communes) throws SQLException;
    List<Commune> getAll() throws SQLException;
    List<Commune> getByName(String Name, boolean OnlyExplicitCaracters) throws SQLException;
    List<Commune> getByCodeCommune(String CodeCommune, boolean OnlyExplicitCaracters) throws SQLException;
    void deleteByName(String Name) throws SQLException;
    void deleteByCodeCommune(String CodeCommune) throws SQLException;
}
