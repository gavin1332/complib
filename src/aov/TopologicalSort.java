package aov;

import graph.AbstractGraph;
import graph.AbstractGraph.Graph;
import graph.Edge;
import graph.GraphCreater;

public class TopologicalSort {

  public static int[] sort(AbstractGraph<?> graph) {
    final int nodeNum = graph.getNodeNum();
    int[] into = new int[nodeNum];
    for (Edge e : graph.getAllEdges()) {
      ++into[e.getTo()];
    }

    int[] order = new int[nodeNum];
    for (int i = 0; i < nodeNum; ++i) {
      boolean found = false;
      for (int id = 0; id < nodeNum; ++id) {
        if (into[id] == 0) {
          order[i] = id;
          for (Edge e : graph.getLinkOuts(id)) {
            if (into[e.getTo()] > 0) {
              --into[e.getTo()];
            }
          }
          into[id] = -1;
          found = true;
          break;
        }
      }
      if (!found) return null;
    }
    return order;
  }

  public static void main(String[] args) {
    GraphCreater creator = new GraphCreater();
    Graph g = creator.createDAG();
    System.out.println(g);
    int[] order = TopologicalSort.sort(g);
    for (int i = 0; i < order.length; ++i) {
      System.out.print(order[i] + " ");
    }
  }
}
