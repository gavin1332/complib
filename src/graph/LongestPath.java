package graph;

import graph.AbstractGraph.Graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import aov.TopologicalSort;

// Only applied for DAG (Directed Acyclic Graph)
public class LongestPath {
  
  // find longest path in the graph
  public static int findByTopologicalSort(final Graph graph, LinkedList<Integer> path) {
    final int nodeNum = graph.getNodeNum();
    int[] order = TopologicalSort.sort(graph);
    Map<Integer, Integer> distMap = new HashMap<Integer, Integer>();
    Map<Integer, Integer> previous = new HashMap<Integer, Integer>();
    int longest = 0;
    Integer endId = -1;
    for (int i = 0; i < nodeNum; ++i) {
      if (graph.getLinkIns(order[i]).isEmpty()) {
        distMap.put(order[i], 0);
        continue;
      }
      
      int maxTmp = 0;
      for (Edge e : graph.getLinkIns(order[i])) {
        int dist = distMap.get(e.getFrom()) + e.getValue();
        if (dist > maxTmp) {
          maxTmp = dist;
          distMap.put(e.getTo(), maxTmp);
          if (dist > longest) {
            longest = dist;
            endId = e.getTo();
            previous.put(e.getTo(), e.getFrom());
          }
        }
      }
    }
    
    if (path != null) {
      while (endId != null) {
        path.addFirst(endId);
        endId = previous.get(endId);
      }
    }
    return longest;
  }

  // find longest path between arbitrary two nodes
  public static int findByFloydWarshall(final AbstractGraph<?> graph,
      Integer[] dic, int[][] dist) {
    assert (graph != null && dic != null);

    final int nodeNum = graph.getNodeNum();
    Map<Integer, Integer> dicMap = new HashMap<Integer, Integer>();
    for (int i = 0; i < nodeNum; ++i) {
      dicMap.put(dic[i], i);
    }

    if (dist == null) {
      dist = new int[nodeNum][];
    }
    for (int i = 0; i < nodeNum; ++i) {
      dist[i] = new int[nodeNum];
      for (int j = 0; j < nodeNum; ++j) {
        dist[i][j] = Integer.MIN_VALUE;
      }
    }
    for (Edge e : graph.getAllEdges()) {
      dist[dicMap.get(e.getFrom())][dicMap.get(e.getTo())] = e.getValue();
    }

    int max = 0;
    for (int k = 0; k < nodeNum; ++k) {
      for (int i = 0; i < nodeNum; ++i) {
        for (int j = 0; j < nodeNum; ++j) {
          int distSum;
          if (dist[i][k] == Integer.MIN_VALUE
              || dist[k][j] == Integer.MIN_VALUE) {
            distSum = Integer.MIN_VALUE;
          } else {
            distSum = dist[i][k] + dist[k][j];
          }
          dist[i][j] = Math.max(dist[i][j], distSum);
          if (dist[i][j] > max) {
            max = dist[i][j];
          }
        }
      }
    }

    return max;
  }

  public static void main(String[] argv) {
    Graph g = new Graph();
    g.addEdge(1, 2, 1);
    g.addEdge(1, 3, 3);
    g.addEdge(1, 4, 2);
    g.addEdge(4, 2, 3);
    g.addEdge(2, 5, 4);
    g.addEdge(4, 5, 5);
    g.addEdge(3, 4, 5);

    Integer[] dic = g.getNodes();
    int max1 = LongestPath.findByFloydWarshall(g, dic, null);
    int max2 = LongestPath.findByTopologicalSort(g, null);
    System.out.println(max1 + " " + max2);
  }

}
