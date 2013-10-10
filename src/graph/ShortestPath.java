package graph;

import graph.AbstractGraph.Graph;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

// The longest path in DAG (Directed Acyclic Graph) can be found by changing 
// path weights from positive to negative and do shortest path search
public class ShortestPath {

  //Single-source shortest path
  public static int findByDijkstra(final AbstractGraph<?> graph, int from,
      int to, LinkedList<Integer> path) {
    final int nodeNum = graph.getNodeNum();
    assert(graph != null && from < nodeNum && to < nodeNum);
    
    int[] dist = new int[nodeNum];
    int[] previous = new int[nodeNum];
    Map<Integer, Integer> reachableMap = new LinkedHashMap<Integer, Integer>();

    for (int id = 0; id < nodeNum; ++id) {
      if (id == from) {
        dist[from] = 0;
        reachableMap.put(from, 0);
      } else {
        dist[id] = Integer.MAX_VALUE;
        reachableMap.put(id, Integer.MAX_VALUE);
      }
      previous[id] = -1;
    }

    while (!reachableMap.isEmpty()) {
      int endId = -1;
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
          while (endId != -1) {
            path.addFirst(endId);
            endId = previous[endId];
          }
        }
        return dist[to];
      }

      for (Edge e : graph.getLinkOuts(endId)) {
        int alt = dist[endId] + e.getValue();
        if (alt < dist[e.getTo()]) {
          dist[e.getTo()] = alt;
          reachableMap.put(e.getTo(), alt);
          previous[e.getTo()] = e.getFrom();
        }
      }
    }

    return Integer.MAX_VALUE;
  }

  //Shortest path between arbitrary two nodes
  public static int[][] findByFloydWarshall(final AbstractGraph<?> graph) {
    assert(graph != null);
    
    final int nodeNum = graph.getNodeNum();
    int[][] dist = new int[nodeNum][];
    for (int i = 0; i < nodeNum; ++i) {
      dist[i] = new int[nodeNum];
      for (int j = 0; j < nodeNum; ++j) {
        dist[i][j] = (i == j)? 0 : Integer.MAX_VALUE;
      }
    }
    for (Edge e : graph.getAllEdges()) {
      dist[e.getFrom()][e.getTo()] = e.getValue();
    }

    for (int k = 0; k < nodeNum; ++k) {
      for (int i = 0; i < nodeNum; ++i) {
        for (int j = 0; j < nodeNum; ++j) {
          int distSum = 0;
          if (dist[i][k] == Integer.MAX_VALUE
              || dist[k][j] == Integer.MAX_VALUE) {
            distSum = Integer.MAX_VALUE;
          } else {
            distSum = dist[i][k] + dist[k][j];
          }
          dist[i][j] = Math.min(dist[i][j], distSum);
        }
      }
    }
    
    return dist;
  }

  public static void main(String[] args) {
    GraphCreater creator = new GraphCreater();
    Graph g = creator.createDAG();
    final int nodeNum = g.getNodeNum();
    int[][] dist = ShortestPath.findByFloydWarshall(g);
    for (int i = 0; i < nodeNum; ++i) {
      for (int j = 0; j < nodeNum; ++j) {
        if (ShortestPath.findByDijkstra(g, i, j, null) != dist[i][j]) {
          System.out.println(i + "->" + j + ": " + dist[i][j]);
        }
      }
    }
  }
}