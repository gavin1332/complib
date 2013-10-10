package graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AbstractGraph<E extends Edge> {

  public static final class Graph extends AbstractGraph<Edge> {
    public void addEdge(int from, int to, int value) {
      addEdge(new Edge(from, to, value));
    }

    public void addConnection(int from, int to) {
      addEdge(from, to, 1);
    }
    
    public void addBiDirectedEdge(int id1, int id2, int value) {
      addEdge(new Edge(id1, id2, value));
      addEdge(new Edge(id2, id1, value));
    }
  }

  private final Map<Integer, List<E>> adjacentMap;

  private Map<Integer, List<E>> reversedMap;

  public AbstractGraph() {
    adjacentMap = new HashMap<Integer, List<E>>();
  }

  public int getNodeNum() {
    return adjacentMap.size();
  }

  public List<E> getLinkOuts(int from) {
    List<E> edges = adjacentMap.get(from);
    if (edges == null) {
      edges = new LinkedList<E>();
      adjacentMap.put(from, edges);
    }
    return edges;
  }

  private void reverseAjacentMap() {
    reversedMap = new HashMap<Integer, List<E>>();
    for (int id : getNodes()) {
      reversedMap.put(id, new LinkedList<E>());
    }
    for (E e : getAllEdges()) {
      List<E> edges = reversedMap.get(e.getTo());
      edges.add(e);
    }
  }

  public List<E> getLinkIns(int to) {
    if (reversedMap == null) {
      reverseAjacentMap();
    }
    return reversedMap.get(to);
  }

  public void addNode(int id) {
    adjacentMap.put(id, new LinkedList<E>());
  }

  public boolean hasNode(int id) {
    return adjacentMap.containsKey(id);
  }

  public void addEdge(E edge) {
    List<E> edges = adjacentMap.get(edge.getFrom());
    if (edges == null) {
      edges = new LinkedList<E>();
      adjacentMap.put(edge.getFrom(), edges);
    }
    edges.add(edge);

    if (!hasNode(edge.getTo())) {
      addNode(edge.getTo());
    }
  }

  public void addBiDirectedEdge(E edge) {
    addEdge(edge);
  }

  public E getEdge(int from, int to) {
    List<E> edges = adjacentMap.get(from);
    if (edges == null)
      return null;
    for (E e : edges) {
      if (e.getTo() == to)
        return e;
    }
    return null;
  }

  public final Map<Integer, List<E>> getAdjacentMap() {
    return adjacentMap;
  }

  public final Integer[] getNodes() {
    Integer[] ids = new Integer[adjacentMap.size()];
    adjacentMap.keySet().toArray(ids);
    return ids;
  }

  public final List<E> getAllEdges() {
    List<E> edges = new LinkedList<E>();
    for (List<E> elist : adjacentMap.values()) {
      edges.addAll(elist);
    }
    return edges;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (Entry<Integer, List<E>> entry : adjacentMap.entrySet()) {
      builder.append(entry.getKey()).append(": ");
      for (E e : entry.getValue()) {
        if (e.getValue() != 1) {
          builder.append('(').append(e.getTo()).append(',')
              .append(e.getValue()).append(')').append(", ");
        } else {
          builder.append(e.getTo()).append(", ");
        }
      }
      builder.append('\n');
    }
    return builder.toString();
  }

  public String toAlphaString() {
    StringBuilder builder = new StringBuilder();
    for (Entry<Integer, List<E>> entry : adjacentMap.entrySet()) {
      builder.append((char) entry.getKey().intValue()).append(": ");
      for (E e : entry.getValue()) {
        if (e.getValue() != 1) {
          builder.append('(').append((char) e.getTo()).append(',')
              .append(e.getValue()).append(')').append(", ");
        } else {
          builder.append((char) e.getTo()).append(", ");
        }
      }
      builder.append('\n');
    }
    return builder.toString();
  }

}