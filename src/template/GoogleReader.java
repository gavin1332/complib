package template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GoogleReader {
  
  public GoogleReader() {}
  
  private String proceed(BufferedReader in) {
    return "";
  }
  
  public static void main(String[] args) throws IOException {
    final String filePrefix = "A-small-practice";
    BufferedReader in = new BufferedReader(new FileReader(filePrefix + ".in"));
    PrintWriter out = new PrintWriter(new FileWriter(filePrefix + ".out"));
    
    GoogleReader google = new GoogleReader();
    int caseNum = Integer.parseInt(in.readLine());
    for (int i = 0; i < caseNum; ++i) {
      String strResult = google.proceed(in);
      out.println("Case #" + (i + 1) + ": " + strResult);
    }
    in.close();
    out.close();
  }
  
}
