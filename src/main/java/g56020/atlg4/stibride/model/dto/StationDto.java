package g56020.atlg4.stibride.model.dto;

import java.util.Objects;

public class StationDto extends Dto<Integer> {
    private final String name;

    /**
     * Dto constructor with the key of the data.
     *
     * @param station
     * @param name
     */
    public StationDto(int station, String name) {
        super(station);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name.toUpperCase();
    }
}
