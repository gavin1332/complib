package graph;

import graph.AbstractGraph.Graph;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;


// Single-source shortest path
public class Dijkstra {

  public static int findShortestPath(AbstractGraph<?> graph, int from, int to,
      List<Integer> path) {
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
      int currId = -1;
      int min = Integer.MAX_VALUE;
      for (Entry<Integer, Integer> entry : reachableMap.entrySet()) {
        if (entry.getValue() < min) {
          currId = entry.getKey();
          min = entry.getValue();
        }
      }
      if (currId == -1) break;
      
      reachableMap.remove(currId);
      
      if (currId == to) {
        if (path != null) {
          Stack<Integer> pathStack = new Stack<Integer>();
          int id = currId;
          while (previous.get(id) != null) {
            pathStack.add(id);
            id = previous.get(id);
          }
          pathStack.add(id);
          
          while(!pathStack.empty()) {
            path.add(pathStack.pop());
          }
        }
        return dist.get(to);
      }

      for (Edge e : graph.getLinkOuts(currId)) {
        int alt = dist.get(currId) + e.getValue();
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