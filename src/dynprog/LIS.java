package dynprog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LIS {

  public static <T extends Comparable<T>> int calculate(T[] arr) {
    List<T> maxV = new ArrayList<T>();
    maxV.add(arr[0]);
    
    int[] lis = new int[arr.length];
    for (int i = 0; i < arr.length; ++i) {
      lis[i] = 1;
    }
    int maxLen = 1;
    for (int i = 1; i < arr.length; ++i) {
      if (arr[i].compareTo(maxV.get(maxLen - 1)) >= 0) {
        maxV.add(arr[i]);
        ++maxLen;
        lis[i] = maxLen;
      } else {
        int pos = Collections.binarySearch(maxV, arr[i]);
        if (pos < 0) {
          pos = -1 - pos;
        }
        lis[i] = pos;
        maxV.set(pos, arr[i]);
      }
    }
    return maxLen;
  }
  
  public static void main(String[] argv) {
//    Integer[] data = {1, -1, 2, -3, 4, -5, 6, -7};
    String[] data = {"AA", "bb", "cc", "dd", "ee"};
    int size = calculate(data);
    System.out.println(size);
  }

}
