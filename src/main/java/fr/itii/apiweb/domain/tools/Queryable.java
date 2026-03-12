package fr.itii.apiweb.domain.tools;

import fr.itii.apiweb.ui.Header;

import java.util.List;

public interface Queryable {

    public <T> List<T> search();

    public Header add();
}
