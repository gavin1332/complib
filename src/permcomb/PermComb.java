package permcomb;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class PermComb {
  
  interface PermVisitor<T> {
    void visit(T[] arr);
  }
  
  interface CombVisitor<T> {
    void visit(List<T> arr);
  }
  
  public static <T> void swap(T[] arr, int i1, int i2) {
    T temp = arr[i1];
    arr[i1] = arr[i2];
    arr[i2] = temp;
  }
  
  public static <T> void permutate(T[] arr, PermVisitor<T> visitor) {
    assert(arr.length > 0);
    
    Stack<Integer> stack = new Stack<Integer>();
    out: while (true) {
      if (stack.size() < arr.length) {
        stack.push(stack.size());
      } else {
        visitor.visit(arr);
        
        int index = stack.pop();
        while (index == arr.length - 1) {
          swap(arr, index, stack.size());
          if (stack.empty()) {
            break out;
          }
          index = stack.pop();
        }
        
        swap(arr, index, stack.size());
        ++index;
        swap(arr, index, stack.size());
        stack.push(index);
      }
    }
  }
  
  public static <T> void combine(T[] arr, CombVisitor<T> visitor) {
    assert(arr.length > 0);
    
    Stack<Boolean> stack = new Stack<Boolean>();
    LinkedList<T> set = new LinkedList<T>();
    while (true) {
      if (stack.size() < arr.length) {
        stack.push(false);
      } else {
        visitor.visit(set);
        
        boolean contains = stack.pop();
        while (!stack.empty() && contains) {
          set.removeLast();
          contains = stack.pop();
        }
        if (contains == true) break;
        stack.push(true);
        set.add(arr[stack.size() - 1]);
      }
    }
  }
  
  public static void main(String[] argv) {
    final int N = 4;
    Integer[] arr = new Integer[N];
    for (int i = 0; i < N; ++i) {
      arr[i] = i;
    }
    permutate(arr, new PermVisitor<Integer>() {
      @Override
      public void visit(Integer[] arr) {
        for (int i = 0; i < N; ++i) {
          System.out.print(arr[i] + " ");
        }
        System.out.println();
      }
    });
    combine(arr, new CombVisitor<Integer>() {
      @Override
      public void visit(List<Integer> arr) {
        for (int i : arr) {
          System.out.print(i + " ");
        }
        System.out.println();
      }
    });
  }

}
