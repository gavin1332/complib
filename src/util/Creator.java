package util;

import java.util.ArrayList;
import java.util.List;

public class Creator {
  
  public static <T> List<List<T>> create2DArray(int rows) {
    List<List<T>> array = new ArrayList<List<T>>();
    for (int i = 0; i < rows; ++i) {
      array.add(new ArrayList<T>());
    }
    return array;
  }
  
  public static int[][] create2DIntArray(int rows, int cols) {
    int[][] array = new int[rows][];
    for (int i = 0; i < rows; ++i) {
      array[i] = new int[cols];
    }
    return array;
  }

}
