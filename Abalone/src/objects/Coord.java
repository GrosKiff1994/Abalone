package objects;

public class Coord {
	public Coord(int x, int y) {
		super();
		this.x = x;
		this.y = y;
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

	private int x, y;
}
