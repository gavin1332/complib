package graph;

import graph.AbstractGraph.Graph;

import java.util.HashMap;
import java.util.Map;

// Longest path between arbitrary two nodes
public class LongestFloydWarshall {

  public static int findLongestPath(final AbstractGraph<?> graph,
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
    int max = LongestFloydWarshall.findLongestPath(g, dic, null);
    Map<Integer, Integer> dicMap = new HashMap<Integer, Integer>();
    for (int i = 0; i < dic.length; ++i) {
      dicMap.put(dic[i], i);
    }

    System.out.println(max);
  }

}
