package objects;

public enum Direction {
	dirHD(new Coord(1, -1)), dirD(new Coord(1, 0)), dirBD(new Coord(0, 1)), dirBG(new Coord(-1, 1)), dirG(new Coord(-1, 0)), dirHG(new Coord(0, -1));

	private Coord dir;

	Direction(Coord offset) {
		this.dir = offset;
	}

	public int getX() {
		return dir.getX();
	}

	public int getY() {
		return dir.getY();
	}

	public Object getCoord() {
		return dir;
	}

	public static Direction toDirection(Coord delta) {
		for (Direction dir : values()) {
			if (dir.getCoord().equals(delta)) {
				return dir;
			}
		}
		return null;
	}
}
