package graph;

import graph.AbstractGraph.Graph;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class ShortestPath {

  //Single-source shortest path
  public static int findByDijkstra(AbstractGraph<?> graph, int from, int to,
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

  //Shortest path between arbitrary two nodes
  public static int[][] findByFloydWarshall(final AbstractGraph<?> graph, Integer[] dic) {
    assert(graph != null && dic != null);
    
    final int nodeNum = graph.getNodeNum();
    Map<Integer, Integer> dicMap = new HashMap<Integer, Integer>();
    for (int i = 0; i < nodeNum; ++i) {
      dicMap.put(dic[i], i);
    }
    
    int[][] dist = new int[nodeNum][];
    for (int i = 0; i < nodeNum; ++i) {
      dist[i] = new int[nodeNum];
      for (int j = 0; j < nodeNum; ++j) {
        dist[i][j] = (i == j)? 0 : Integer.MAX_VALUE;
      }
    }
    for (Edge e : graph.getAllEdges()) {
      dist[dicMap.get(e.getFrom())][dicMap.get(e.getTo())] = e.getValue();
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
    Graph g = new Graph();
    g.addEdge(1, 2, 1);
    g.addEdge(1, 3, 3);
    g.addEdge(1, 4, 2);
    g.addEdge(4, 2, 3);
    g.addEdge(2, 5, 4);
    g.addEdge(4, 5, 5);
    g.addEdge(3, 4, 5);

    Integer[] dic = g.getNodes();
    int[][] dist = ShortestPath.findByFloydWarshall(g, dic);
    Map<Integer, Integer> dicMap = new HashMap<Integer, Integer>();
    for (int i = 0; i < dic.length; ++i) {
      dicMap.put(dic[i], i);
    }

    for (int i = 0; i < dic.length; ++i) {
      for (int j = 0; j < dic.length; ++j) {
        if (ShortestPath.findByDijkstra(g, dic[i], dic[j], null) 
            != dist[dicMap.get(dic[i])][dicMap.get(dic[j])]) {
          System.out.println(dic[i] + "->" + dic[j] + ": " + dist[dicMap.get(dic[i])][dicMap.get(dic[j])]);
        }
      }
    }
  }
}