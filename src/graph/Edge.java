package graph;

public class Edge implements Comparable<Edge> {

  protected int from;
  protected int to;
  protected int value;

  public Edge(int from, int to, int value) {
    super();
    this.from = from;
    this.to = to;
    this.value = value;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + from;
    result = prime * result + to;
    result = prime * result + value;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Edge other = (Edge) obj;
    if (from != other.from)
      return false;
    if (getTo() != other.getTo())
      return false;
    if (getValue() != other.getValue())
      return false;
    return true;
  }

  @Override
  public String toString() {
    return from + "->" + getTo() + ": " + getValue();
  }

  @Override
  public int compareTo(Edge e) {
    return value - e.value;
  }

  public int getFrom() {
    return from;
  }

  public void setFrom(int from) {
    this.from = from;
  }

  public int getTo() {
    return to;
  }

  public void setTo(int to) {
    this.to = to;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

}
