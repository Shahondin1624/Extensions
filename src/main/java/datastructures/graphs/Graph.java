package datastructures.graphs;

import java.util.List;
import java.util.function.Predicate;

public interface Graph<NodeData, EdgeData> {
    void addNode(NodeData data);

    void removeNode(Predicate<NodeData> matcher);

    void addEdge(Predicate<NodeData> firstNodeMatcher, Predicate<NodeData> secondNodeMatcher, EdgeData data);

    void removeEdge(Predicate<NodeData> firstNodeMatcher, Predicate<NodeData> secondNodeMatcher);

    List<Edge<EdgeData>> getEdges();

    List<Node<NodeData>> getNodes();
}
