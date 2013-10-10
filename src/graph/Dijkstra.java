package graph;

import graph.AbstractGraph.Graph;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

// Single-source shortest path
public class Dijkstra {

  public static int findShortestPath(AbstractGraph<?> graph, int from, int to,
      LinkedList<Integer> path) {
    assert(graph != null && graph.hasNode(from) && graph.hasNode(to));
    
    Map<Integer, Integer> dist = new HashMap<Integer, Integer>();
    Map<Integer, Integer> previous = new HashMap<Integer, Integer>();
    Map<Integer, Integer> reachableMap = new LinkedHashMap<Integer, Integer>();

    for (int nodeId : graph.getNodes()) {
      if (nodeId == from) {
        dist.put(from, 0);
        reachableMap.put(from, 0);
      } else {
        dist.put(nodeId, Integer.MAX_VALUE);
        reachableMap.put(nodeId, Integer.MAX_VALUE);
      }
      previous.put(nodeId, null);
    }

    while (!reachableMap.isEmpty()) {
      Integer endId = -1;
      int shortest = Integer.MAX_VALUE;
      for (Entry<Integer, Integer> entry : reachableMap.entrySet()) {
        if (entry.getValue() < shortest) {
          endId = entry.getKey();
          shortest = entry.getValue();
        }
      }
      if (endId == -1) break;
      
      reachableMap.remove(endId);
      
      if (endId == to) {
        if (path != null) {
          while (endId != null) {
            path.addFirst(endId);
            endId = previous.get(endId);
          }
        }
        return dist.get(to);
      }

      for (Edge e : graph.getLinkOuts(endId)) {
        int alt = dist.get(endId) + e.getValue();
        if (alt < dist.get(e.getTo())) {
          dist.put(e.getTo(), alt);
          reachableMap.put(e.getTo(), alt);
          previous.put(e.getTo(), e.getFrom());
        }
      }
    }

    return Integer.MAX_VALUE;
  }

  public static void main(String[] args) {
    Graph g = new Graph();
    g.addEdge(1, 2, 1);
    g.addEdge(1, 3, 3);
    g.addEdge(1, 4, 2);
    g.addEdge(4, 2, 3);
    g.addEdge(2, 5, 4);
    g.addEdge(4, 5, 5);
    g.addEdge(3, 4, 5);

    System.out.println(Dijkstra.findShortestPath(g, 1, 5, null));
  }
}