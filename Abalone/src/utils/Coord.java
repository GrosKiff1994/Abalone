package utils;

public class Coord {
  public int x;
  public int y;

  public Coord(int x, int y) {
    super();
    this.x = x;
    this.y = y;
  }

  public Coord(Coord c) {
    this.x = c.x;
    this.y = c.y;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + x;
    result = prime * result + y;
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
    Coord other = (Coord) obj;
    if (x != other.x)
      return false;
    if (y != other.y)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Coord [x=" + x + ", y=" + y + "]";
  }

  public void setCoord(Coord coord) {
    this.x = coord.x;
    this.y = coord.y;
  }

  public Coord add(Vector v) {
    return new Coord(x + v.x, y + v.y);
  }

}
