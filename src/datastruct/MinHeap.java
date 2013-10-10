package datastruct;

import java.util.Arrays;
import java.util.Comparator;

@SuppressWarnings("unchecked")
public class MinHeap<E> {

  private static final int DEFAULT_INITIAL_CAPACITY = 11;

  private Object[] data;

  private int size = 0;

  private final Comparator<? super E> comparator;

  public MinHeap() {
    this(DEFAULT_INITIAL_CAPACITY, null);
  }

  public MinHeap(int initialCapacity) {
    this(initialCapacity, null);
  }

  public MinHeap(int initialCapacity, Comparator<? super E> comparator) {
    // Note: This restriction of at least one is not actually needed,
    // but continues for 1.5 compatibility
    if (initialCapacity < 1)
      throw new IllegalArgumentException();
    this.data = new Object[initialCapacity];
    this.comparator = comparator;
  }

  public boolean add(E e) {
    if (e == null)
      throw new NullPointerException();
    int i = size;
    if (i >= data.length)
      grow(i + 1);
    size = i + 1;
    if (i == 0)
      data[0] = e;
    else
      siftUp(i, e);
    return true;
  }

  public E min() {
    if (size == 0)
      return null;
    return (E) data[0];
  }

  public int size() {
    return size;
  }
  
  public boolean isEmpty() {
    return size == 0;
  }

  public E poll() {
    if (size == 0)
      return null;
    int s = --size;
    E result = (E) data[0];
    E x = (E) data[s];
    data[s] = null;
    if (s != 0)
      siftDown(0, x);
    return result;
  }

  public void clear() {
    for (int i = 0; i < size; i++)
      data[i] = null;
    size = 0;
  }

  public E removeAt(int i) {
    assert i >= 0 && i < size;
    int s = --size;
    if (s == i) // removed last element
      data[i] = null;
    else {
      E moved = (E) data[s];
      data[s] = null;
      siftDown(i, moved);
      if (data[i] == moved) {
        siftUp(i, moved);
        if (data[i] != moved)
          return moved;
      }
    }
    return null;
  }
  
  public void decreaseKey(int k, E x) {
    assert k >= 0 && k < size;
    siftUp(k, x);
  }

  private void grow(int minCapacity) {
    if (minCapacity < 0) // overflow
      throw new OutOfMemoryError();
    int oldCapacity = data.length;
    // Double size if small; else grow by 50%
    int newCapacity = ((oldCapacity < 64) ? ((oldCapacity + 1) * 2)
        : ((oldCapacity / 2) * 3));
    if (newCapacity < 0) // overflow
      newCapacity = Integer.MAX_VALUE;
    if (newCapacity < minCapacity)
      newCapacity = minCapacity;
    data = Arrays.copyOf(data, newCapacity);
  }

  private void siftUp(int k, E x) {
    if (comparator != null)
      siftUpUsingComparator(k, x);
    else
      siftUpComparable(k, x);
  }

  private void siftUpComparable(int k, E x) {
    Comparable<? super E> key = (Comparable<? super E>) x;
    while (k > 0) {
      int parent = (k - 1) >>> 1;
      Object e = data[parent];
      if (key.compareTo((E) e) >= 0)
        break;
      data[k] = e;
      k = parent;
    }
    data[k] = key;
  }

  private void siftUpUsingComparator(int k, E x) {
    while (k > 0) {
      int parent = (k - 1) >>> 1;
      Object e = data[parent];
      if (comparator.compare(x, (E) e) >= 0)
        break;
      data[k] = e;
      k = parent;
    }
    data[k] = x;
  }

  private void siftDown(int k, E x) {
    if (comparator != null)
      siftDownUsingComparator(k, x);
    else
      siftDownComparable(k, x);
  }

  private void siftDownComparable(int k, E x) {
    Comparable<? super E> key = (Comparable<? super E>) x;
    int half = size >>> 1; // loop while a non-leaf
    while (k < half) {
      int child = (k << 1) + 1; // assume left child is least
      Object c = data[child];
      int right = child + 1;
      if (right < size
          && ((Comparable<? super E>) c).compareTo((E) data[right]) > 0)
        c = data[child = right];
      if (key.compareTo((E) c) <= 0)
        break;
      data[k] = c;
      k = child;
    }
    data[k] = key;
  }

  private void siftDownUsingComparator(int k, E x) {
    int half = size >>> 1;
    while (k < half) {
      int child = (k << 1) + 1;
      Object c = data[child];
      int right = child + 1;
      if (right < size && comparator.compare((E) c, (E) data[right]) > 0)
        c = data[child = right];
      if (comparator.compare(x, (E) c) <= 0)
        break;
      data[k] = c;
      k = child;
    }
    data[k] = x;
  }

//  private void heapify() {
//    for (int i = (size >>> 1) - 1; i >= 0; i--)
//      siftDown(i, (E) data[i]);
//  }

  public Comparator<? super E> comparator() {
    return comparator;
  }

}
