package g56020.atlg4.stibride.model.dto;

import javafx.util.Pair;

public class StopDto extends Dto<Pair<Integer, StationDto>> {

    private final int line;
    private final StationDto station;
    private final int order;


    /**
     * StopDto constructor
     * line and station are the key for a stop
     * <p>
     * pair.key() = line
     * pair.value() = station
     *
     * @param line    given line
     * @param station given station
     * @param order   given order
     */
    public StopDto(int line, StationDto station, int order) {
        super(new Pair<>(line, station));
        this.line = line;
        this.station = station;
        this.order = order;

    }

    public int getLine() {
        return line;
    }

    public StationDto getStation() {
        return station;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "StopDto{" +
                "line=" + line +
                ", station=" + station +
                ", order=" + order +
                '}';
    }
}
