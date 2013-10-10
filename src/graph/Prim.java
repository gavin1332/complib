package graph;

import graph.AbstractGraph.Graph;

import java.util.ArrayList;
import java.util.List;

// Minimum spanning tree
public class Prim {

  public static int extract(AbstractGraph<?> graph, List<Edge> edges) {
    assert(graph != null && graph.getNodeNum() > 0);

    final int nodeNum = graph.getNodeNum();
    int[] value = new int[nodeNum];
    Edge[] previous = new Edge[nodeNum];
    for (int id = 0; id < nodeNum; ++id) {
      value[id] = Integer.MAX_VALUE;
      previous[id] = null;
    }
    
    value[0] = 0;
    int minSum = 0;
    final int visited = Integer.MIN_VALUE;
    for (int i = 0; i < nodeNum; ++i) {
      int min = Integer.MAX_VALUE;
      int minId = -1;
      for (int id = 0; id < nodeNum; ++id) {
        if (value[id] != visited && value[id] < min) {
          minId = id;
          min = value[id];
        }
      }
      minSum += min;
      for (Edge e : graph.getLinkOuts(minId)) {
        if (value[e.getTo()] != visited && e.getValue() < value[e.getTo()]) {
          value[e.getTo()] = e.getValue();
          previous[e.getTo()] = e;
        }
      }
      value[minId] = visited;
    }
    
    if (edges != null) {
      for (int i = 0; i < nodeNum; ++i) {
        if (previous[i] != null) {
          edges.add(previous[i]);
        }
      }
    }
    return minSum;
  }

  public static void main(String[] args) {
    GraphCreater creator = new GraphCreater();
    Graph g = creator.createDAG();
    List<Edge> edges = new ArrayList<Edge>();
    System.out.println(g);
    System.out.println(Prim.extract(g, edges));
    System.out.println(edges);
  }
  
}
