package template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GoogleScanner {
  
  private String proceed(Scanner scan) {
    return "";
  }
  
  public static void main(String[] args) throws IOException {
    final String filePrefix = "A-small-practice";
    Scanner scan = new Scanner(new File(filePrefix + ".in"));
    PrintWriter out = new PrintWriter(new FileWriter(filePrefix + ".out"));

    GoogleScanner google = new GoogleScanner();
    int caseNum = scan.nextInt();
    for (int i = 0; i < caseNum; ++i) {
      String strResult = google.proceed(scan);
      out.println("Case #" + (i + 1) + ": " + strResult);
    }
    scan.close();
    out.close();
  }
  
}
