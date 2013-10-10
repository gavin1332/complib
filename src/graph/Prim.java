package graph;

import graph.AbstractGraph.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

// Minimum spanning tree
public class Prim {

  public static int extract(Graph graph, List<Edge> edges) {
    assert(graph != null && graph.getNodeNum() > 0);

    Map<Integer, Integer> value = new HashMap<Integer, Integer>();
    Map<Integer, Edge> previous = new HashMap<Integer, Edge>();
    Integer[] nodes = graph.getNodes();
    for (int id : nodes) {
      value.put(id, Integer.MAX_VALUE);
      previous.put(id, null);
    }
    
    value.put(nodes[0], 0);
    int minSum = 0;
    for (int i = 0; i < nodes.length; ++i) {
      int min = Integer.MAX_VALUE;
      int id = -1;
      for (Entry<Integer, Integer> entry : value.entrySet()) {
        if (entry.getValue() < min) {
          id = entry.getKey();
          min = entry.getValue();
        }
      }
      minSum += min;
      for (Edge e : graph.getLinkOuts(id)) {
        if (value.containsKey(e.getTo()) && e.getValue() < value.get(e.getTo())) {
          value.put(e.getTo(), e.getValue());
          previous.put(e.getTo(), e);
        }
      }
      value.remove(id);
    }
    
    if (edges != null) {
      for (Entry<Integer, Edge> entry : previous.entrySet()) {
        if (entry.getValue() != null) {
          edges.add(entry.getValue());
        }
      }
    }
    return minSum;
  }

  public static void main(String[] args) {
    Graph g = new Graph();
    g.addBiDirectedEdge(1, 2, 7);
    g.addBiDirectedEdge(1, 3, 8);
    g.addBiDirectedEdge(2, 3, 5);
    g.addBiDirectedEdge(2, 6, 2);
    g.addBiDirectedEdge(3, 6, 6);
    g.addBiDirectedEdge(2, 7, 3);
    g.addBiDirectedEdge(3, 7, 4);
    g.addBiDirectedEdge(6, 7, 9);

    List<Edge> edges = new ArrayList<Edge>();
    System.out.println(Prim.extract(g, edges));
    System.out.println(edges);
  }
  
}
