package tree;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

public class Trie<T> {
  
  interface Visitor<T> {
    void visit(Node<T> node);
  }
  
  public static class Node<T> {
    private T value;
    private boolean touched;
    private Map<T, Node<T>> children;

    public Node(T value) {
      this.value = value;
      touched = false;
      children = new LinkedHashMap<T, Node<T>>();
    }
    
    public boolean hasChild(T value) {
      return children.containsKey(value);
    }

    public Node<T> addChild(T value) {
      Node<T> node = children.get(value);
      if (node == null) {
        node = new Node<T>(value);
        children.put(value, node);
      }
      return node;
    }

    public T getVaue() {
      return value;
    }
    
    public boolean isTouched() {
      return touched;
    }
    
    public void setTouched() {
      touched = true;
    }

    public Map<T, Node<T>> getChildren() {
      return children;
    }
  }
  
  private Node<T> root;
  
  public Trie(Node<T> root) {
    this.root = root;
  }
  
  public int addSequence(T[] seq) {
    Node<T> node = root;
    int newNodeNum = 0;
    for (T t : seq) {
      if (!node.hasChild(t)) {
        ++newNodeNum;
      }
      node = node.addChild(t);
    }
    return newNodeNum;
  }
  
  public Node<T> getRoot() {
    return root;
  }
  
  public void traverse(Visitor<T> visitor) {
    Stack<Node<T>> stack = new Stack<Node<T>>();
    stack.push(root);
    while (!stack.empty()) {
      Node<T> node = stack.pop();
      if (node.isTouched()) {
        visitor.visit(node);
        continue;
      }
      for (Node<T> child : node.getChildren().values()) {
        stack.push(child);
      }
      node.setTouched();
    }
  }
}
