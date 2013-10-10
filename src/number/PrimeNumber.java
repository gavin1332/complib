package number;

import java.util.HashSet;
import java.util.Set;

public class PrimeNumber {

  public static Set<Integer> generate(int begin, int end) {
    assert (begin > 1 && end > begin && end < 10000000);

    boolean[] isPrime = new boolean[end];
    for (int i = 2; i < end; ++i) {
      isPrime[i] = true;
    }

    for (int i = 2; i < end; ++i) {
      if (!isPrime[i])
        continue;
      for (int j = i + i; j < end; j += i) {
        isPrime[j] = false;
      }
    }

    Set<Integer> prime = new HashSet<Integer>();
    for (int i = begin; i < end; ++i) {
      if (isPrime[i]) {
        prime.add(i);
      }
    }
    return prime;
  }

  public static boolean isPrime(int n) {
    for (int i = 2; (long) i * i <= n; ++i) {
      if (n % i == 0)
        return false;
    }
    return true;
  }

}
