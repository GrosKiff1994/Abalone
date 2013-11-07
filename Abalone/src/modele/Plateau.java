package modele;

import java.util.HashSet;

import controleur.DeplacementException;

public class Plateau {

	public static final int HEIGHT = 11;
	public static final int WIDTH = 11;
	public static final int MAXDEPLACEMENT = 3;
	private static Case tab[][];
	private HashSet<Boule> setBoules;

	public Plateau() {

		Plateau.tab = new Case[Plateau.HEIGHT][Plateau.WIDTH];
		setBoules = new HashSet<Boule>();

	}

	public void chargerTab(char[][] carte) {
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				Boule bouleTmp;
				switch (carte[i][j]) {
				case 'v':
					tab[i][j] = new Case();
					break;
				case 'b':
					tab[i][j] = new Case();
					bouleTmp = new Boule(Couleur.BLANC);
					tab[i][j].setBoule(bouleTmp);
					bouleTmp.getCoord().setCoord((double) i, (double) j);
					setBoules.add(bouleTmp);
					break;
				case 'n':
					tab[i][j] = new Case();
					bouleTmp = new Boule(Couleur.NOIR);
					tab[i][j].setBoule(bouleTmp);
					bouleTmp.getCoord().setCoord((double) i, (double) j);
					setBoules.add(bouleTmp);
					break;
				case 'x':
					tab[i][j] = new Case();
					tab[i][j].setBord(true);
					break;
				default:
				}
			}
		}
	}

	public Case getCase(int i, int j) {
		return tab[i][j];
	}

	public Case getCase(Coord coord) {
		return getCase(coord.getY(), coord.getX());
	}

	public static Case[][] getTabCase() {
		return tab;
	}

	public Coord coordCaseSuivant(Direction dir, Coord coordCase) throws DeplacementException {
		int arX = coordCase.getX() + dir.getX();
		int arY = coordCase.getY() + dir.getY();

		if (arY < 0 || arY >= Plateau.WIDTH || arX < 0 || arX >= Plateau.HEIGHT) {
			throw new DeplacementException("suivant invalide");
		}

		return new Coord(arX, arY);
	}

	public void deplacerBouleDirection(Direction dir, Coord coordCase) throws DeplacementException {
		System.out.println("deplacement de (" + coordCase.getX() + ";" + coordCase.getY() + ") en direction (" + dir.getX() + ";" + dir.getY() + ")");

		if (coordCase.getX() < 0 || coordCase.getX() >= Plateau.WIDTH || coordCase.getY() < 0 || coordCase.getY() >= Plateau.HEIGHT) {
			throw new DeplacementException("case debut invalide (<0 | >" + Plateau.HEIGHT + ")");
		}

		Case caseActuelle = tab[coordCase.getY()][coordCase.getX()];
		Coord coordCaseSuivante = coordCaseSuivant(dir, coordCase);

		if (!caseActuelle.estOccupee()) {
			throw new DeplacementException("case debut non occupee");
		}
		if (this.getCase(coordCaseSuivante).estOccupee()) {
			throw new DeplacementException("case arrivee occcupee");
		}

		Boule bouleADeplacer = caseActuelle.getBoule();
		bouleADeplacer.getCoord().setCoord(coordCaseSuivante.getY(), coordCaseSuivante.getX());
		this.getCase(coordCaseSuivante).setBoule(caseActuelle.getBoule());
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
