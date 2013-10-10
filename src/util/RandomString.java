package util;

import java.util.Random;

public class RandomString {
  
  private static final char[] DIC = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
  
  private static final Random randGen = new Random(System.currentTimeMillis());
  
  public static int dicSize() {
    return DIC.length;
  }

  public static String generate(int length) {
    if (length < 1) {
      return null;
    }
    char[] buffer = new char[length];
    for (int i = 0; i < buffer.length; ++i) {
      buffer[i] = DIC[randGen.nextInt(DIC.length)];
    }
    return new String(buffer);
  }
}
