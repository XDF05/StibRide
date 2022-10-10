package g56020.atlg4.stibride.model.repository;

import g56020.atlg4.stibride.model.dto.Dto;

import java.util.List;

public interface Repository<K, T extends Dto<K>> {
    /**
     * Returns all the elements of the repository.
     *
     * @return all the elements of the repository.
     * @throws RepositoryException repository can't access to the elements.
     */
    List<T> getAll() throws RepositoryException;

    /**
     * Return the element of the repository with the given key.
     *
     * @param key key of the element.
     * @return element of the repository according to a given key.
     * @throws RepositoryException repository can't access to the element.
     */
    T get(K key) throws RepositoryException;

    /**
     * verifies whether an element exists in the repository
     * search is based on the element's key
     *
     * @param key given key of the element to look for
     * @return true if the element exist in the repository and false otherwise.
     * @throws RepositoryException repository can't access to the element.
     */
    boolean contains(K key) throws RepositoryException;

}
