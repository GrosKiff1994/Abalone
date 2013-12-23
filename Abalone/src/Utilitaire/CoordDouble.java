package Utilitaire;

public class CoordDouble {
	private double x, y;

	public CoordDouble(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public boolean equals(Object o) {
		return (this.x == ((CoordDouble) o).getX() && this.y == ((CoordDouble) o).getY());
	}

	public Coord castCoord() {
		return new Coord((int) this.getX(), (int) this.getY());
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
		this.x = c.getX();
		this.y = c.getY();
	}

	public static CoordDouble difference(CoordDouble p, CoordDouble s) {
		return new CoordDouble(p.getX() - s.getX(), p.getY() - s.getY());
	}

	public static CoordDouble somme(CoordDouble p, CoordDouble s) {
		return new CoordDouble(p.getX() + s.getX(), p.getY() + s.getY());
	}
}
