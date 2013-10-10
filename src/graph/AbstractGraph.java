package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Only support sequential ids starts with 0
public class AbstractGraph<E extends Edge> {

  public static final class Graph extends AbstractGraph<Edge> {
    public Graph(int nodeNum) {
      super(nodeNum);
    }

    public void addEdge(int from, int to, int value) {
      addEdge(new Edge(from, to, value));
    }

    public void addConnection(int from, int to) {
      addEdge(from, to, 1);
    }
    
    public void addBiDirectedEdge(int id1, int id2, int value) {
      addEdge(id1, id2, value);
      addEdge(id2, id1, value);
    }
  }
  
  private final int nodeNum;

  private final List<List<E>> adjacentMap;

  private List<List<E>> reversedMap;

  public AbstractGraph(int nodeNum) {
    this.nodeNum = nodeNum;
    adjacentMap = new ArrayList<List<E>>(nodeNum);
    for (int i = 0; i < nodeNum; ++i) {
      adjacentMap.add(new LinkedList<E>());
    }
  }

  public int getNodeNum() {
    return nodeNum;
  }

  public List<E> getLinkOuts(int from) {
    return adjacentMap.get(from);
  }

  private void reverseAjacentMap() {
    reversedMap = new ArrayList<List<E>>(nodeNum);
    for (int i = 0; i < nodeNum; ++i) {
      reversedMap.add(new LinkedList<E>());
    }
    for (E e : getAllEdges()) {
      reversedMap.get(e.getTo()).add(e);
    }
  }

  public List<E> getLinkIns(int to) {
    if (reversedMap == null) {
      reverseAjacentMap();
    }
    return reversedMap.get(to);
  }

  public void addEdge(E edge) {
    adjacentMap.get(edge.getFrom()).add(edge);
  }

  public E getEdge(int from, int to) {
    List<E> edges = adjacentMap.get(from);
    for (E e : edges) {
      if (e.getTo() == to) return e;
    }
    return null;
  }

  public final List<E> getAllEdges() {
    List<E> edges = new LinkedList<E>();
    for (List<E> elist : adjacentMap) {
      edges.addAll(elist);
    }
    return edges;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < nodeNum; ++i) {
      List<E> elist = adjacentMap.get(i);
      builder.append(i).append(": ");
      boolean isFirst = true;
      for (E e : elist) {
        if (isFirst) {
          builder.append(' ');
          isFirst = false;
        } else {
          builder.append(", ");
        }
        builder.append('(').append(e.getTo()).append(',').append(e.getValue()).append(')');
      }
      builder.append('\n');
    }
    return builder.toString();
  }

}