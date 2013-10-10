package template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GoogleReader {
  
  private static String proceed(BufferedReader in) {
    return "";
  }
  
  private static void init() {}
  
  public static void main(String[] args) throws IOException {
    final String filePrefix = "A-small-practice";
    BufferedReader in = new BufferedReader(new FileReader(filePrefix + ".in"));
    PrintWriter out = new PrintWriter(new FileWriter(filePrefix + ".out"));
    init();
    
    int caseNum = Integer.parseInt(in.readLine());
    for (int i = 0; i < caseNum; ++i) {
      String strResult = proceed(in);
      out.println("Case #" + (i + 1) + ": " + strResult);
    }
    in.close();
    out.close();
  }
  
}
