package aov;

import graph.Edge;
import graph.AbstractGraph;
import graph.AbstractGraph.Graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TopologicalSort {

  public static int[] sort(AbstractGraph<?> graph) {
    final int nodeNum = graph.getNodeNum();
    Map<Integer, Integer> into = new HashMap<Integer, Integer>();
    for (Integer id : graph.getNodes()) {
      into.put(id, 0);
    }
    for (Edge e : graph.getAllEdges()) {
      int linkIn = e.getTo();
      into.put(linkIn, into.get(linkIn) + 1);
    }

    int[] order = new int[nodeNum];
    for (int i = 0; i < nodeNum; ++i) {
      boolean found = false;
      for (Entry<Integer, Integer> entry : into.entrySet()) {
        if (entry.getValue() == 0) {
          int id = entry.getKey();
          order[i] = id;
          for (Edge e : graph.getLinkOuts(id)) {
            int linkIn = e.getTo();
            into.put(linkIn, into.get(linkIn) - 1);
          }
          into.remove(id);
          found = true;
          break;
        }
      }
      if (!found)
        return null;
    }
    return order;
  }

  public static void main(String[] args) {
    Graph g = new Graph();
    g.addConnection('a', 'c');
    g.addConnection('c', 'd');
    g.addConnection('a', 'g');
    g.addConnection('d', 'g');
    g.addConnection('d', 'e');
    g.addConnection('g', 'f');
    g.addConnection('f', 'e');
    g.addConnection('b', 'g');
    g.addConnection('b', 'h');
    g.addConnection('h', 'f');

    System.out.println(g);
    int[] order = TopologicalSort.sort(g);
    for (int i = 0; i < order.length; ++i) {
      System.out.println(order[i]);
    }
  }
}
