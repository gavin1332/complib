package google;


import graph.Dijkstra;
import graph.Edge;
import graph.AbstractGraph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;



public class SpaceshipDefence {
  
  public static void main(String[] args) throws IOException {
    final String filePrefix = "E-large-practice";
    
    Scanner scan = new Scanner(new File(filePrefix + ".in"));
    PrintWriter out = new PrintWriter(new FileWriter(filePrefix + ".out"));

    int caseNum = scan.nextInt();
    for (int i = 0; i < caseNum; ++i) {
      int count = scan.nextInt();
      String[] room = new String[count + 1];
      Map<String, Integer> rid = new HashMap<String, Integer>();
      for (int j = 1; j <= count; ++j) {
        room[j] = scan.next();
        rid.put(room[j], null);
      }
      
      int id = 0;
      for (Entry<String, Integer> e : rid.entrySet()) {
        e.setValue(id++);
      }
      
      AbstractGraph<Edge> g = new AbstractGraph<Edge>();
      for (int j = 0; j < id; ++j) {
        g.addNode(j);
      }
      count = scan.nextInt();
      for (int j = 0; j < count; ++j) {
        int from = rid.get(room[scan.nextInt()]);
        int to = rid.get(room[scan.nextInt()]);
        g.addEdge(new Edge(from, to, scan.nextInt()));
      }

      out.println("Case #" + (i + 1) + ": ");
      
      count = scan.nextInt();
      for (int j = 0; j < count; ++j) {
        int from = rid.get(room[scan.nextInt()]);
        int to = rid.get(room[scan.nextInt()]);
        List<Integer> path = new ArrayList<Integer>();
        int dist = Dijkstra.findShortestPath(g, from, to, path);
        out.println(dist);
      }
    }
    
    scan.close();
    out.close();
  }

}
