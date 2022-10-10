package g56020.atlg4.stibride.model.dto;

import java.util.Objects;

public class Dto<K> {

    /**
     * Key of the data.
     */

    protected K key;

    /**
     * Dto constructor with the key of the data.
     *
     * @param key
     */
    protected Dto(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Missing key");
        }
        this.key = key;
    }

    /**
     * Key getter for the data.
     *
     * @return key of the data.
     */
    public K getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dto<?> dto = (Dto<?>) o;
        return Objects.equals(key, dto.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
