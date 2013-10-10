package aov;

import graph.Edge;
import graph.AbstractGraph;
import graph.AbstractGraph.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CriticalPath {

  public static int getCriticalPath(AbstractGraph<?> graph, List<Integer> path) {
    if (path == null)
      return -1;

    int[] order = TopologicalSort.sort(graph);
    if (order == null)
      return -1;

    Map<Integer, Integer> orderMap = new HashMap<Integer, Integer>();
    for (int i = 0; i < order.length; ++i) {
      orderMap.put(order[i], i);
    }

    int[] earliest = new int[order.length];
    earliest[0] = 0;
    for (int i = 1; i < order.length; ++i) {
      earliest[i] = 0;
      for (int fromId : graph.getLinkIns(order[i])) {
        int during = graph.getEdge(fromId, order[i]).getValue();
        int index = orderMap.get(fromId);
        if (earliest[index] + during > earliest[i]) {
          earliest[i] = earliest[index] + during;
        }
      }
    }

    int[] latest = new int[order.length];
    latest[order.length - 1] = earliest[order.length - 1];
    for (int i = order.length - 2; i >= 0; --i) {
      latest[i] = Integer.MAX_VALUE;
      for (Edge e : graph.getLinkOuts(order[i])) {
        int during = graph.getEdge(order[i], e.getTo()).getValue();
        int index = orderMap.get(e.getTo());
        if (latest[index] - during < latest[i]) {
          latest[i] = latest[index] - during;
        }
      }
    }

    for (int i = 0; i < order.length; ++i) {
      if (earliest[i] == latest[i]) {
        path.add(order[i]);
      }
    }

    return earliest[order.length - 1];
  }

  public static void main(String[] args) {
    Graph g = new Graph();
    g.addEdge(1, 2, 6);
    g.addEdge(1, 3, 4);
    g.addEdge(1, 4, 5);
    g.addEdge(2, 5, 1);
    g.addEdge(3, 5, 1);
    g.addEdge(4, 6, 2);
    g.addEdge(5, 7, 9);
    g.addEdge(5, 8, 7);
    g.addEdge(6, 8, 4);
    g.addEdge(7, 9, 2);
    g.addEdge(8, 9, 4);

    System.out.println(g);
    List<Integer> path = new ArrayList<Integer>();
    int time = CriticalPath.getCriticalPath(g, path);
    System.out.println(time);
    System.out.println(path);
  }

}
