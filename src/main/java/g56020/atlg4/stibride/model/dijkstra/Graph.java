package g56020.atlg4.stibride.model.dijkstra;

import g56020.atlg4.stibride.model.dto.StationDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * source: https://www.baeldung.com/java-dijkstra
 */
public class Graph {
    private final Set<Node> nodes = new HashSet<>();

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public Node getNode(StationDto station) {
        for (Node node : nodes) {
            if (node.getStation().equals(station)) {
                return node;
            }
        }
        throw new IllegalArgumentException("Node doesn't exist in the graph!");
    }
}
