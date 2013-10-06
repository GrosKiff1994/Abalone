package abalone;

public class Plateau {

	public static final int HEIGHT = 11;
	public static final int WIDTH = 11;
	private Case tab[][];

	public Plateau() {

		this.tab = new Case[Plateau.HEIGHT][Plateau.WIDTH];

	}

	public void chargerTab(char tabChar[][]) {
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				switch (tabChar[i][j]) {
				case 'v':
					tab[i][j] = new Case();
					break;
				case 'b':
					tab[i][j] = new Case();
					tab[i][j].setBoule(new Boule(Couleur.BLANC));
					break;
				case 'n':
					tab[i][j] = new Case();
					tab[i][j].setBoule(new Boule(Couleur.NOIR));
					break;
				case 'x':
					break;
				default:
				}
			}
		}
	}

	public Case getCase(int i, int j) {
		return tab[i][j];
	}

	public void deplacerBouleDirection(Direction dir, Coord coordBoule) {
		System.out.println("je deplace (" + coordBoule.getX() + ";"
				+ coordBoule.getY() + ") en ajoutant (" + dir.getX() + ";"
				+ dir.getY() + ").");

		tab[coordBoule.getY() + dir.getY()][coordBoule.getX() + dir.getX()]
				.setBoule(tab[coordBoule.getY()][coordBoule.getX()].getBoule());

		tab[coordBoule.getY()][coordBoule.getX()] = new Case();
	}

	public String toString() {
		String res = "";
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if (tab[i][j] != null) {
					if (tab[i][j].estOccupee()) {
						switch (tab[i][j].getBoule().getCouleur()) {
						case NOIR:
							res += "N";
							break;
						case BLANC:
							res += "B";
							break;
						default:
						}
					} else {
						res += "O";
					}
				} else {
					res += "X";
				}
			}
			res += '\n';
		}
		return res;
	}
}
