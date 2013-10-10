package util;

public class Util {

  private static void swap(int[] data, int from, int to) {
    int temp = data[from];
    data[from] = data[to];
    data[to] = temp;
  }

  public static int partition(int[] data, int begin, int end) {
    if (end - begin <= 1)
      return -1;

    int pivotIdx = (begin + end) / 2;
    int pivot = data[pivotIdx];
    int tail = end - 1;
    swap(data, pivotIdx, tail);

    int small = begin - 1;
    for (int i = begin; i < tail; ++i) {
      if (data[i] < pivot) {
        ++small;
        if (small != i) {
          swap(data, small, i);
        }
      }
    }
    ++small;
    swap(data, small, tail);

    return small;
  }

  public static int partition2(int[] data, int begin, int end) {
    if (end - begin <= 1)
      return -1;

    int pivotIdx = (begin + end) / 2;
    int pivot = data[pivotIdx];
    swap(data, pivotIdx, end - 1);

    int l = begin;
    int r = end - 2;
    while (true) {
      for (; data[l] < pivot; ++l) {
      }
      for (; r >= l && data[r] >= pivot; --r) {
      }
      if (l > r)
        break;

      swap(data, l, r);
    }
    swap(data, l, end - 1);

    return l;
  }

}
