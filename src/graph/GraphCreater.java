package graph;

import graph.AbstractGraph.Graph;

import java.util.HashSet;
import java.util.Set;

import util.RandomString;

public class GraphCreater {

  private int nodeNum;
  
  private String[] dic;

  public GraphCreater() {
    nodeNum = 5;
    dic = new String[nodeNum];
  }
  
  public Graph createDAG() {
    dic = new String[nodeNum];
    int strLen = (int) Math.ceil(Math.log(10 * nodeNum) / Math.log(RandomString.dicSize()));
    Set<String> idSet = new HashSet<String>();
    for (int i = 0; i < nodeNum; ++i) {
      String id = RandomString.generate(strLen);
      while (idSet.contains(id)) {
        id = RandomString.generate(strLen);;
      }
      dic[i] = id;
    }

    Graph g = new Graph(nodeNum);
    g.addEdge(0, 1, 1);
    g.addEdge(0, 2, 3);
    g.addEdge(0, 3, 2);
    g.addEdge(3, 1, 3);
    g.addEdge(1, 4, 4);
    g.addEdge(3, 4, 5);
    g.addEdge(2, 3, 5);
    return g;
  }
  
  public int getNodeNum() {
    return nodeNum;
  }
  
  public String[] getDic() {
    return dic;
  }

}
