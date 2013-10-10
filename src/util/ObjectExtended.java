package util;

public class ObjectExtended {

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    // ObjectExtended other = (ObjectExtended) obj;
    // if(false) {
    // return true;
    // }
    return false;
  }

  @Override
  public String toString() {
    return "";
  }

}
