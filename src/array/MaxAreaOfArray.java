package array;

import java.util.Stack;

public class MaxAreaOfArray {
  
  public static int calcMaxAreaOfArray(int[] data) {
    Stack<Integer> s1 = new Stack<Integer>();
    Stack<Integer> s2 = new Stack<Integer>();
    s1.push(data[0]);
    s2.push(0);
    int max = 0;
    int index = 1;
    for (index = 1; index < data.length; ++index) {
      if (s1.peek() <= data[index]) {
        s1.push(data[index]);
        s2.push(index);
        continue;
      }
      
      int lastIndex = -1;
      while (s1.peek() > data[index]) {
        lastIndex = s2.pop();
        int area = s1.pop() * (index - lastIndex);
        if (area > max) {
          max = area;
        }
      }
      s1.push(data[index]);
      if (s2.empty()) {
        s2.push(0);
      } else {
        s2.push(lastIndex);
      }
    }
    while (!s1.empty()) {
      int area = s1.pop() * (index - s2.pop());
      if (area > max) {
        max = area;
      }
    }
    
    return max;
  }
  
  public static void main(String[] argv) {
    int[] data = new int[10];
    for (int i = 0; i < 10; ++i) {
      data[i] = i + 1;
    }
    System.out.println(calcMaxAreaOfArray(data));
  }

}
