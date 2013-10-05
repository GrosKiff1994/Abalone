public class Plateau {

	public static final int height = 11;
	public static final int width = 11;
	private Case tab[][];

	public Plateau() {

		this.tab = new Case[Plateau.height][Plateau.width];

	}

	public String toString() {

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (tab[i][j].estOccupee()) {
					if (tab[i][j].getBoule().getCouleur() == Couleur.NOIR) {
						System.out.print("N");
					} else {
						System.out.print("B");
					}
				} else {
					if (j == 0 || i == 0 || j == width - 1 || i == height - 1) {
						System.out.print("X");
					} else {
						System.out.print("O");
					}
				}
			}
			System.out.println("");
		}
		return null;
	}

	/*
	 * public String toString2() { switch(famille){ case "pique": do(); break;
	 * case "carreau": do2(); break; default: do3();}}
	 */

}
