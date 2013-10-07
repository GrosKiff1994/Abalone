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

	public Case suivantCase(Direction dir, Coord coordCase)
			throws DeplacementException {
		int arX = coordCase.getX() + dir.getX();
		int arY = coordCase.getY() + dir.getY();

		if (arY < 0 || arY >= Plateau.WIDTH || arX < 0 || arX >= Plateau.HEIGHT) {
			throw new DeplacementException("suivant invalide");
		}

		return tab[arY][arX];
	}

	public void deplacerBouleDirection(Direction dir, Coord coordCase)
			throws DeplacementException {
		System.out.println("je deplace (" + coordCase.getX() + ";"
				+ coordCase.getY() + ") en ajoutant (" + dir.getX() + ";"
				+ dir.getY() + ").");

		if (coordCase.getX() < 0 || coordCase.getX() >= Plateau.WIDTH
				|| coordCase.getY() < 0 || coordCase.getY() >= Plateau.HEIGHT) {
			throw new DeplacementException("case debut invalide (<0 | >"
					+ Plateau.HEIGHT + ")");
		}

		Case caseActuelle = tab[coordCase.getY()][coordCase.getX()];
		Case caseSuivante = suivantCase(dir, coordCase);

		if (!caseActuelle.estOccupee()) {
			throw new DeplacementException("case debut non occupee");
		}
		if (caseSuivante.estOccupee()) {
			throw new DeplacementException("case arrivee occcupee");
		}
		caseSuivante.setBoule(caseActuelle.getBoule());
		caseActuelle.setBoule(null);
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
