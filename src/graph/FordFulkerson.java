package graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


// Maximum stream
public class FordFulkerson {
  
  public static class MSGraph extends AbstractGraph<MSEdge> {}
  
  public static class MSEdge extends Edge {
    private int curr;
    private int remain;

    public MSEdge(int from, int to, int capacity, int remain, int curr) {
      super(from, to, capacity);
      this.curr = curr;
      this.remain = remain;
    }

    public MSEdge(int from, int to, int capacity) {
      this(from, to, capacity, capacity, 0);
    }

    @Override
    public int hashCode() {
      final int prime = 97;
      int result = 1;
      result = prime * result + from;
      result = prime * result + to;
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      MSEdge other = (MSEdge) obj;
      if (from == other.from && to == other.to || from == other.to
          && to == other.from) {
        return true;
      }
      return false;
    }

    public int getRemain() {
      return remain;
    }

    public void setRemain(int remain) {
      this.remain = remain;
    }

    public int getCurr() {
      return curr;
    }

    public void setCurr(int curr) {
      this.curr = curr;
    }

    public int getCapacity() {
      return value;
    }
  }

  private AbstractGraph<MSEdge> graph;

  private int start;
  private int end;

  private Set<MSEdge> path = new HashSet<MSEdge>();

  public FordFulkerson(AbstractGraph<MSEdge> graph, int start, int end) {
    this.graph = graph;
    this.start = start;
    this.end = end;
  }

  public int getMaxStream() {
    while (hasAugmentPath(start, end)) {
      int minRemain = getMinRemain(path);
      for (MSEdge edge : path) {
        MSEdge reverseEdge = graph.getEdge(edge.getTo(), edge.getFrom());
        int reverseCurr = reverseEdge.getCurr();
        if (reverseCurr > 0) {
          if (minRemain > reverseCurr) {
            reverseEdge.setCurr(0);
            edge.setCurr(minRemain - reverseCurr);
          } else {
            reverseEdge.setCurr(reverseCurr - minRemain);
          }
        } else {
          edge.setCurr(edge.getCurr() + minRemain);
        }
        edge.setRemain(edge.getRemain() - minRemain);
        reverseEdge.setRemain(reverseEdge.getRemain() + minRemain);
      }
      path.clear();
    }
    return 0;
  }

  public void printStreamInfo() {
    List<MSEdge> edges = graph.getLinkOuts(start);
    int maxStream = 0;
    for (MSEdge edge : edges) {
      if (edge.getCapacity() != 0) {
        maxStream += edge.getCurr();
      }
    }
    System.out.println("网路最大流为：" + maxStream);
    System.out.println("流信息如下：");
    for (MSEdge edge : graph.getAllEdges()) {
      if (edge.getCapacity() != 0) {
        System.out.println(edge.getFrom() + "->" + edge.getTo() + "\t:\t"
            + edge.getCurr());
      }
    }
  }

  private boolean hasAugmentPath(int from, int to) {
    for (MSEdge edge : graph.getLinkOuts(from)) {
      if (path.contains(edge) || edge.getRemain() == 0)
        continue;

      path.add(edge);
      if (edge.getTo() == to) {
        return true;
      } else {// 深度递归
        if (hasAugmentPath(edge.getTo(), to)) {
          return true;
        } else {
          path.remove(edge);
          continue;
        }
      }
    }
    return false;
  }

  private int getMinRemain(Set<MSEdge> edges) {
    int res = Integer.MAX_VALUE;
    for (MSEdge edge : edges) {
      if (edge.getRemain() < res) {
        res = edge.getRemain();
      }
    }
    return res;
  }

  public static void main(String[] args) {
    MSGraph g = new MSGraph();
    g.addEdge(new MSEdge(1, 2, 16));
    g.addEdge(new MSEdge(1, 3, 13));
    g.addEdge(new MSEdge(2, 1, 0));
    g.addEdge(new MSEdge(2, 3, 10));
    g.addEdge(new MSEdge(2, 4, 12));
    g.addEdge(new MSEdge(3, 2, 4));
    g.addEdge(new MSEdge(3, 5, 14));
    g.addEdge(new MSEdge(3, 1, 0));
    g.addEdge(new MSEdge(3, 4, 0));
    g.addEdge(new MSEdge(4, 2, 0));
    g.addEdge(new MSEdge(4, 3, 9));
    g.addEdge(new MSEdge(4, 6, 20));
    g.addEdge(new MSEdge(4, 5, 0));
    g.addEdge(new MSEdge(5, 3, 0));
    g.addEdge(new MSEdge(5, 4, 7));
    g.addEdge(new MSEdge(5, 6, 4));
    g.addEdge(new MSEdge(6, 4, 0));
    g.addEdge(new MSEdge(6, 5, 0));

    FordFulkerson fordFulkerson = new FordFulkerson(g, 1, 6);
    fordFulkerson.getMaxStream();
    fordFulkerson.printStreamInfo();
  }
}

