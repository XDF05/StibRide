package g56020.atlg4.stibride.model.dao;

import g56020.atlg4.stibride.model.dto.Dto;
import g56020.atlg4.stibride.model.repository.RepositoryException;

import java.util.List;

/**
 * Data Access Object for a resource file
 *
 * @param <K> Key of an item
 * @param <T> item inside the resource
 */
public interface Dao<K, T extends Dto<K>> {
    /**
     * Returns an element based on a given key.
     *
     * @param key key of the element to select.
     * @return element based on the given key.
     * @throws RepositoryException resources cannot be accessed.
     */
    T select(K key) throws RepositoryException;

    List<T> selectAll() throws RepositoryException;
}
