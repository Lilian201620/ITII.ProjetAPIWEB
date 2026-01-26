package fr.itii.apiweb.data.local;

import fr.itii.apiweb.domain.models.Commune;

import java.sql.SQLException;
import java.util.List;

public interface DataRepository {
    void save(List<Commune> communes) throws SQLException;
    List<Commune> getAll();
    List<Commune> getByName();
    List<Commune> getByInsee();
}
