package tree;

public class BST extends BT {

  public static void insert(Node root, int value) {
    if (root == null) {
      throw new IllegalArgumentException();
    }

    Node node = new Node(value);
    while (true) {
      if (value < root.value) {
        if (root.left == null) {
          root.left = node;
          break;
        }
        root = root.left;
      } else {
        if (root.right == null) {
          root.right = node;
          break;
        }
        root = root.right;
      }
    }
  }

  public static Node find(Node root, int value) {
    while (root != null) {
      if (root.value == value)
        return root;

      if (value < root.value) {
        root = root.left;
      } else {
        root = root.right;
      }
    }

    return null;
  }

}
