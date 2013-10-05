package Abalone;
public class Plateau {

	public static final int WEIGHT = 11;
	public static final int WIDTH = 11;
	private Case tab[][];

	public Plateau() {

		this.tab = new Case[Plateau.WEIGHT][Plateau.WIDTH];

	}

	public void chargerTab(char tabChar[][]) {
		for (int i = 0; i < WEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				switch (tabChar[i][j]) {
				case 'v':
					tab[i][j] = new Case();
					break;
				case 'b':
					tab[i][j] = new Case();
					tab[i][j].setOccupee(true);
					tab[i][j].setBoule(new Boule(Couleur.BLANC));
					break;
				case 'n':
					tab[i][j] = new Case();
					tab[i][j].setOccupee(true);
					tab[i][j].setBoule(new Boule(Couleur.NOIR));
					break;
				case 'x':
					break;
				default:
				}
			}
		}
	}

	public String toString() {
		String res = "";
		for (int i = 0; i < WEIGHT; i++) {
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
