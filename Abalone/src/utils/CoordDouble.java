package utils;

public class CoordDouble {

  public double x;
  public double y;

  public CoordDouble(double x, double y) {
    super();
    this.x = x;
    this.y = y;
  }

  public boolean equals(Object o) {
    return (this.x == ((CoordDouble) o).x && this.y == ((CoordDouble) o).y);
  }

  public Coord castCoord() {
    return new Coord((int) this.x, (int) this.y);
  }

  public void setCoord(double i, double j) {
    this.y = i;
    this.x = j;
  }

  @Override
  public String toString() {
    return "CoordDouble [x=" + x + ", y=" + y + "]";
  }

  public void setCoord(CoordDouble c) {
    this.x = c.x;
    this.y = c.y;
  }

  public static CoordDouble difference(CoordDouble p, CoordDouble s) {
    return new CoordDouble(p.x - s.x, p.y - s.y);
  }

  public static CoordDouble somme(CoordDouble p, CoordDouble s) {
    return new CoordDouble(p.x + s.x, p.y + s.y);
  }

}
