package utilitaire;

public class Vector {

  public int x;
  public int y;

  public Vector(Coord from, Coord to) {
    this(to.x - from.x, to.y - from.y);
  }

  public Vector(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Vector(Vector vector) {
    this.x = vector.x;
    this.y = vector.y;
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
    Vector other = (Vector) obj;
    if (x != other.x)
      return false;
    if (y != other.y)
      return false;
    return true;
  }

  public void setVector(Vector vector) {
    this.x = vector.x;
    this.y = vector.y;
  }

  public Vector getOpposite() {
    return new Vector(-x, -y);
  }

  public Vector add(Vector v) {
    return new Vector(x + v.x, y + v.y);
  }

}
