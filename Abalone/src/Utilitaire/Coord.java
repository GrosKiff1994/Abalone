package utilitaire;

public class Coord {
  private int x, y;

  public Coord(int x, int y) {
    super();
    this.x = x;
    this.y = y;
  }

  public Coord(Coord c) {
    this.x = c.getX();
    this.y = c.getY();
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public boolean equals(Object o) {
    return (this.x == ((Coord) o).getX() && this.y == ((Coord) o).getY());
  }

  public CoordDouble castCoordDouble() {
    return new CoordDouble(this.getX(), this.getY());
  }

  public void setCoord(int i, int j) {
    this.y = i;
    this.x = j;
  }

  @Override
  public String toString() {
    return "Coord [x=" + x + ", y=" + y + "]";
  }

  public void setCoord(Coord c) {
    this.x = c.getX();
    this.y = c.getY();
  }

  public static Coord difference(Coord p, Coord s) {
    return new Coord(p.getX() - s.getX(), p.getY() - s.getY());
  }

  public static Coord somme(Coord p, Coord s) {
    return new Coord(p.getX() + s.getX(), p.getY() + s.getY());
  }

}
