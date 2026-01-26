package fr.itii.apiweb.data.local;

import fr.itii.apiweb.model.Commune;
import java.util.List;

public class DBManager {
    public DBManager(String url, String user, String pass) {
    }
//
    public boolean saveAll(List<Commune> communes) {
        return false; // Pour l'instant
    }

    public List<Commune> getAll() {
        return null; // Pour l'instant
    }
}
