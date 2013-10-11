package tree;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

public class Trie {
  
  interface Visitor {
    void visit(Node node);
  }
  
  public static class Node {
    private char value;
    private boolean touched;
    private Map<Character, Node> children;

    public Node(char value) {
      this.value = value;
      touched = false;
      children = new LinkedHashMap<Character, Node>();
    }

    public Node addChild(char value) {
      Node node = children.get(value);
      if (node == null) {
        node = new Node(value);
        children.put(value, node);
      }
      return node;
    }

    public char getVaue() {
      return value;
    }
    
    public boolean isTouched() {
      return touched;
    }
    
    public void setTouched() {
      touched = true;
    }

    public Map<Character, Node> getChildren() {
      return children;
    }
  }
  
  private Node root;
  
  public Trie() {
    root = new Node('\0');
  }
  
  public void addString(String str) {
    Node node = root;
    for (char ch : str.toCharArray()) {
      node = node.addChild(ch);
    }
  }
  
  public Node getRoot() {
    return root;
  }
  
  public void traverse(Visitor visitor) {
    Stack<Node> stack = new Stack<Node>();
    stack.push(root);
    while (!stack.empty()) {
      Node node = stack.pop();
      if (node.isTouched()) {
        visitor.visit(node);
        continue;
      }
      for (Node child : node.getChildren().values()) {
        stack.push(child);
      }
      node.setTouched();
    }
  }
}
