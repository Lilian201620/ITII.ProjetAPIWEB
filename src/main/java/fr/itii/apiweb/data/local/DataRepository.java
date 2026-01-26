package fr.itii.apiweb.data.local;

import fr.itii.apiweb.domain.models.Commune;

import java.util.List;

public interface DataRepository {
    boolean save(List<Commune> communes);
    List<Commune> getAll();
    List<Commune> getByName();
    List<Commune> getByInsee();
}
