package graph;

import graph.AbstractGraph.Graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import aov.TopologicalSort;

public class LongestTopological {
  
  public static int findLongestPath(final Graph graph, LinkedList<Integer> path) {
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

  public static void main(String[] argv) {
    Graph g = new Graph();
    g.addEdge(1, 2, 1);
    g.addEdge(1, 3, 3);
    g.addEdge(1, 4, 2);
    g.addEdge(4, 2, 3);
    g.addEdge(2, 5, 4);
    g.addEdge(4, 5, 5);
    g.addEdge(3, 4, 5);

    int max = LongestTopological.findLongestPath(g, null);
    System.out.println(max);
  }

}
