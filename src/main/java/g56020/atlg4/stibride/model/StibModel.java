package g56020.atlg4.stibride.model;

import g56020.atlg4.stibride.config.ConfigManager;
import g56020.atlg4.stibride.model.dijkstra.Dijkstra;
import g56020.atlg4.stibride.model.dijkstra.Graph;
import g56020.atlg4.stibride.model.dijkstra.Node;
import g56020.atlg4.stibride.model.dto.FavoriteDto;
import g56020.atlg4.stibride.model.dto.StationDto;
import g56020.atlg4.stibride.model.dto.StopDto;
import g56020.atlg4.stibride.model.repository.FavoriteRepository;
import g56020.atlg4.stibride.model.repository.RepositoryException;
import g56020.atlg4.stibride.model.repository.StationRepository;
import g56020.atlg4.stibride.model.repository.StopsRepository;
import g56020.atlg4.stibride.utils.observer.Observable;
import g56020.atlg4.stibride.utils.observer.Observer;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StibModel implements Observable {
    private Graph graph;

    private StopsRepository stopsRepository;
    private StationRepository stationRepository;
    private FavoriteRepository favoriteRepository;

    private List<Observer> observers;

    public StibModel() {
        observers = new ArrayList<>();
        try {
            ConfigManager.getInstance().load();
            this.stopsRepository = new StopsRepository();
            this.stationRepository = new StationRepository();
            favoriteRepository = new FavoriteRepository();
        } catch (RepositoryException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * retrieves every stop from the Repository
     *
     * @return list containing every stop inside the repository
     */
    public List<StopDto> getStops() {
        List<StopDto> stops = null;
        try {
            stops = stopsRepository.getAll();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return stops;
    }

    public void initialize() {
        List<StationDto> stations = getStations();
        List<FavoriteDto> favorites = getFavorites();
        notifyObservers(stations);
        notifyObservers(new Pair<>(stations, favorites));
    }

    public void initFavorites() {

    }

    /**
     * retrieves every station from the Repository
     *
     * @return list containing every station
     */
    private List<StationDto> getStations() {
        List<StationDto> stations = null;
        try {
            stations = stationRepository.getAll();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return stations;
    }


    public List<FavoriteDto> getFavorites() {
        List<FavoriteDto> favorites = null;
        try {
            favorites = favoriteRepository.getAll();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return favorites;
    }

    public int addFavorite(Pair<StationDto, StationDto> favorite, String name) {
        int result = 0;
        try {
            result = favoriteRepository.add(new FavoriteDto(favorite, name));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        notifyObservers(getFavorites());
        return result;
    }

    public int removeFavorite(Pair<StationDto, StationDto> favorite) {
        int result = 0;
        try {
            result = favoriteRepository.remove(favorite);
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        List<FavoriteDto> favorites = getFavorites();
        if (favorites.isEmpty()) {
            notifyObservers("EMPTY_FAVORITES");
        } else {
            notifyObservers(favorites);
        }

        return result;
    }

    /**
     * Initialises the graph with every station there is inside the Repository
     * Every node (station) has a list of adjacent nods (stations).
     * <p>
     * The distance between each adjacent node is set to 1
     */
    public void initGraph() {
        List<StationDto> stations = getStations();
        graph = new Graph();
        // add every station to the graph
        stations.forEach(stationDto -> {
            Node station = new Node(stationDto);
            graph.addNode(station);
        });
        // add every adjacent node for each node in the graph
        graph.getNodes().forEach(node -> {
            List<StationDto> adjacentStations = getAdjacentStations(node.getStation());
            // adds every adjacent node to the adjacency list of the current node
            adjacentStations.forEach(adj -> {
                Node adjacentNode = graph.getNode(adj);
                node.addDestination(adjacentNode, 1);
            });
        });
    }

    /**
     * Searches for the shortest path between a source and a destination station
     * Uses the dijkstra algorithm.
     *
     * @param source      source station
     * @param destination destination station
     */
    public void searchPath(StationDto source, StationDto destination) {
        new Thread(()->{
            initGraph();
            Node sourceNode = graph.getNode(source);
            Graph graphShortestPath = Dijkstra.calculateShortestPathFromSource(graph, sourceNode);
            Node destinationNode = graphShortestPath.getNode(destination);

            List<StationDto> shortestPath = new LinkedList<>();
            destinationNode.getShortestPath().forEach(node -> {
                shortestPath.add(node.getStation());
            });
            notifyObservers(shortestPath);
        }).start();

    }

    /**
     * retrieves the adjacent stations to a given station, without taking the line in consideration
     *
     * @param station given station
     * @return list of stations to which the given station is adjacentF
     */
    private List<StationDto> getAdjacentStations(StationDto station) {
        List<StationDto> adjacentStations = new ArrayList<>();
        List<StopDto> sameStations = getStopsFromStation(station);
        for (StopDto stop : sameStations) {
            for (StopDto adjStop : getAdjacentStops(stop)) {
                if (!adjacentStations.contains(adjStop.getStation())) {
                    adjacentStations.add(adjStop.getStation());
                }
            }
        }
        return adjacentStations;
    }

    /**
     * For a given station, retrieve each stop in which it appears
     *
     * @param station given station
     * @return list of stops where this station appears
     */
    public List<StopDto> getStopsFromStation(StationDto station) {
        List<StopDto> stops = new ArrayList<>();
        for (StopDto s : getStops()) {
            if (s.getStation().equals(station)) {
                stops.add(s);
            }
        }
        return stops;
    }

    /**
     * Retrieves every adjacent stop on the same line to the given stop in parameter
     *
     * @param stop given stop
     * @return list containing every adjacent stop on same line as the given stop
     */
    private List<StopDto> getAdjacentStops(StopDto stop) {
        List<StopDto> adjacentStops = new ArrayList<>();
        for (StopDto s : getStops()) {
            if (s.getLine() == stop.getLine()) {
                if ((s.getOrder() == stop.getOrder() + 1) ||
                        (s.getOrder() == stop.getOrder() - 1)) {
                    adjacentStops.add(s);
                }
            }
        }
        return adjacentStops;
    }

    /**
     * retrieve every line for a given station
     *
     * @param station given station
     * @return every line for a given station
     */
    private List<Integer> getLines(StationDto station) {
        List<StopDto> stops = getStops();
        List<Integer> lines = new ArrayList<>();

        stops.forEach(stopsDto -> {
            if (stopsDto.getStation().equals(station)) {
                lines.add(stopsDto.getLine());
            }
        });
        return lines;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Object args) {
        for (Observer observer : observers) {
            observer.update(this, args);
        }
    }
}
