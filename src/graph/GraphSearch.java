package graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class GraphSearch {

  static interface Visitor {
    void visit(int id);

    void remove(int id);

    void touch(int id);
  }

  static class VisitorAdapter implements Visitor {
    @Override
    public void visit(int id) {
    }

    @Override
    public void remove(int id) {
    }

    @Override
    public void touch(int id) {
    }
  }

  private final class Node {
    private int id;
    // last bit: touched, second last bit: visited
    private int state;

    Node(int id) {
      this.id = id;
      state = 0;
    }

    int getId() {
      return id;
    }

    void setTouched() {
      state |= 1;
    }

    boolean isTouched() {
      return (state & 1) > 0;
    }

    void setVisited() {
      state |= 2;
    }

    boolean isVisited() {
      return (state & 2) > 0;
    }
  }

  private final class NodePool {
    Map<Integer, Node> pool = new HashMap<Integer, Node>();

    public Node getOrCreate(int id) {
      Node node = pool.get(id);
      if (node == null) {
        node = new Node(id);
        pool.put(id, node);
      }
      return node;
    }
  }

  private AbstractGraph<?> graph;

  private NodePool pool;

  public GraphSearch(AbstractGraph<?> graph) {
    this.graph = graph;
    pool = new NodePool();
  }

  public void DFS(Visitor visitor) {
    if (graph == null) {
      throw new IllegalArgumentException();
    }
    for (Integer id : graph.getNodes()) {
      Node node = pool.getOrCreate(id);
      if (node.isTouched())
        continue;
      DFS(visitor, id);
    }
  }

  public void DFS(Visitor visitor, int startId) {
    if (graph == null) {
      throw new IllegalArgumentException();
    }

    Stack<Node> stack = new Stack<Node>();
    Node node = pool.getOrCreate(startId);
    stack.add(node);
    while (!stack.empty()) {
      Node record = stack.peek();
      if (record.isVisited()) {
        visitor.remove(record.getId());
        stack.pop();
        continue;
      }

      visitor.visit(record.getId());
      record.setVisited();
      for (Edge e : graph.getLinkOuts(record.getId())) {
        Node neighbor = pool.getOrCreate(e.getTo());
        if (!neighbor.isTouched()) {
          stack.push(neighbor);
          visitor.touch(record.getId());
          neighbor.setTouched();
        }
      }
    }
  }

  public void BFS(Visitor visitor) {
    if (graph == null) {
      throw new IllegalArgumentException();
    }
    for (Integer id : graph.getNodes()) {
      Node node = pool.getOrCreate(id);
      if (node.isTouched())
        continue;
      BFS(visitor, id);
    }
  }

  public void BFS(Visitor visitor, int startId) {
    if (graph == null) {
      throw new IllegalArgumentException();
    }

    LinkedList<Node> queue = new LinkedList<Node>();
    Node node = pool.getOrCreate(startId);
    queue.offer(node);
    while (!queue.isEmpty()) {
      Node record = queue.poll();
      visitor.visit(record.getId());
      record.setVisited();
      visitor.remove(record.getId());
      for (Edge e : graph.getLinkOuts(record.getId())) {
        Node neighbor = pool.getOrCreate(e.getTo());
        if (!neighbor.isTouched()) {
          queue.offer(neighbor);
          visitor.touch(record.getId());
          neighbor.setTouched();
        }
      }
    }
  }

}
