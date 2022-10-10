package g56020.atlg4.stibride;

import g56020.atlg4.stibride.model.StibModel;
import g56020.atlg4.stibride.model.dto.FavoriteDto;
import g56020.atlg4.stibride.model.dto.StationDto;
import g56020.atlg4.stibride.model.dto.StopDto;
import g56020.atlg4.stibride.utils.observer.Observable;
import g56020.atlg4.stibride.utils.observer.Observer;
import g56020.atlg4.stibride.FXMLController.StationRow;
import javafx.application.Platform;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Presenter implements Observer {
    private final StibModel model;
    private final FXMLController view;

    public Presenter(StibModel model, FXMLController view) {
        this.model = model;
        this.view = view;
    }

    public void initialize() {
        model.initialize();
    }

    public void searchShortestPath(StationDto source, StationDto destination) {
        model.searchPath(source, destination);
    }

    public void addFavorite(StationDto source, StationDto destination, String name) {
        model.addFavorite(new Pair<>(source, destination), name);
    }

    public void removeFavorite(StationDto source, StationDto destination) {
        model.removeFavorite(new Pair<>(source, destination));
    }

    @Override
    public void update(Observable observable, Object arg) {
        Platform.runLater(() -> {
            if (arg instanceof LinkedList<?>) {
                view.clearStations();
                ((List<StationDto>) arg).forEach(station -> {
                    List<StopDto> stops = model.getStopsFromStation(station);
                    List<Integer> lines = new ArrayList<>();
                    for (StopDto stop : stops) {
                        lines.add(stop.getLine());
                    }
                    view.addStation(new StationRow(station, lines));
                });
            } else if (arg instanceof Pair<?, ?>) {
                Pair<List<StationDto>, List<FavoriteDto>> lists = (Pair<List<StationDto>, List<FavoriteDto>>) arg;
                view.initialize(lists.getKey(), lists.getValue());
            } else if (arg instanceof List<?>) {

                if (((List<?>) arg).get(0) instanceof FavoriteDto) {
                    List<FavoriteDto> favorites = (List<FavoriteDto>) arg;
                    view.refreshFavorites(favorites);
                }
            } else if (arg.equals("EMPTY_FAVORITES")) {
                view.refreshFavorites(null);
            }
        });

    }
}

