package tree;

import java.util.Stack;

public class BT {

  protected static class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
      this.value = value;
    }
  }

  public static void visit(Node node) {
    System.out.print(node.value + " ");
  }

  public static void preOrderTraverse(Node root) {
    Stack<Node> stack = new Stack<Node>();
    while (root != null || !stack.empty()) {
      while (root != null) {
        visit(root);
        stack.push(root);
        root = root.left;
      }
      if (!stack.empty()) {
        root = stack.pop();
        root = root.right;
      }
    }
  }

  public static void inOrderTraverse(Node root) {
    Stack<Node> stack = new Stack<Node>();
    while (root != null || !stack.empty()) {
      while (root != null) {
        stack.push(root);
        root = root.left;
      }
      if (!stack.empty()) {
        root = stack.pop();
        visit(root);
        root = root.right;
      }
    }
  }

  public static void postOrderTraverse(Node root) {
    Stack<Node> stack = new Stack<Node>();
    stack.push(root);
    Node pre = null;
    while (!stack.empty()) {
      root = stack.pop();
      if (root.left == null && root.right == null || pre != null
          && (pre == root.left || pre == root.right)) {
        visit(root);
        pre = root;
      } else {
        stack.push(root);

        if (root.right != null) {
          stack.push(root.right);
        }
        if (root.left != null) {
          stack.push(root.left);
        }
      }
    }
  }

  public static void main(String[] argv) {
    final int count = 20;
    Node[] node = new Node[count];
    for (int i = 0; i < count; ++i) {
      node[i] = new Node(i);
    }
    node[0].left = node[1];
    node[0].right = node[2];
    node[1].left = node[3];
    node[1].right = node[4];
    node[2].left = node[5];
    node[2].right = node[6];
    node[3].left = node[7];
    node[3].right = node[8];
    node[4].left = node[9];
    node[4].right = node[10];

    Node root = node[0];
    // preOrderTraverse(root);
    // inOrderTraverse(root);
    postOrderTraverse(root);
  }

}