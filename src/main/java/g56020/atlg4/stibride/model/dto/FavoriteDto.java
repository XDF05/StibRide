package g56020.atlg4.stibride.model.dto;

import javafx.util.Pair;

import java.util.Objects;

public class FavoriteDto extends Dto<Pair<StationDto, StationDto>> {
    private StationDto source;
    private StationDto destination;
    private String name;

    /**
     * Dto constructor with the key of the data.
     * Key = source
     * Value = destination
     *
     * @param key
     */
    public FavoriteDto(Pair<StationDto, StationDto> key, String name) {
        super(key);
        this.source = key.getKey();
        this.destination = key.getValue();
        this.name = name;
    }

    public StationDto getSource() {
        return source;
    }

    public StationDto getDestination() {
        return destination;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }
}