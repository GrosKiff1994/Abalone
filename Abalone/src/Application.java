import objects.Coord;
import objects.Couleur;
import objects.Joueur;
import objects.Plateau;
import display.FenetreAbalone;

public class Application {

	static Joueur gagnant = null;

	private static void verifierVictoire(Joueur joueurBlanc, Joueur joueurNoir) {
		if (joueurBlanc.getBoulesDuJoueurEjectees() >= 6)
			gagnant = joueurNoir;
		else if (joueurNoir.getBoulesDuJoueurEjectees() >= 6)
			gagnant = joueurBlanc;
	}

	public static void main(String[] args) {

		@SuppressWarnings("unused")
		char tabVide[][] = new char[][] {
				{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
				{ 'x', 'x', 'x', 'x', 'x', 'v', 'v', 'v', 'v', 'v', 'x' },
				{ 'x', 'x', 'x', 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'x' },
				{ 'x', 'x', 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'x' },
				{ 'x', 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'x' },
				{ 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'x' },
				{ 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'x', 'x' },
				{ 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'x', 'x', 'x' },
				{ 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'x', 'x', 'x', 'x' },
				{ 'x', 'v', 'v', 'v', 'v', 'v', 'x', 'x', 'x', 'x', 'x' },
				{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' } };

		char tabClassique[][] = new char[][] {
				{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
				{ 'x', 'x', 'x', 'x', 'x', 'b', 'b', 'b', 'b', 'b', 'x' },
				{ 'x', 'x', 'x', 'x', 'b', 'b', 'b', 'b', 'b', 'b', 'x' },
				{ 'x', 'x', 'x', 'v', 'v', 'b', 'b', 'b', 'v', 'v', 'x' },
				{ 'x', 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'x' },
				{ 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'x' },
				{ 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'x', 'x' },
				{ 'x', 'v', 'v', 'n', 'n', 'n', 'v', 'v', 'x', 'x', 'x' },
				{ 'x', 'n', 'n', 'n', 'n', 'n', 'n', 'x', 'x', 'x', 'x' },
				{ 'x', 'n', 'n', 'n', 'n', 'n', 'x', 'x', 'x', 'x', 'x' },
				{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' } };

		@SuppressWarnings("unused")
		char tabAtomouche[][] = new char[][] {
				{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
				{ 'x', 'x', 'x', 'x', 'x', 'n', 'b', 'v', 'v', 'v', 'x' },
				{ 'x', 'x', 'x', 'x', 'b', 'v', 'v', 'n', 'b', 'n', 'x' },
				{ 'x', 'x', 'x', 'v', 'v', 'n', 'v', 'v', 'v', 'v', 'x' },
				{ 'x', 'x', 'n', 'v', 'b', 'v', 'v', 'v', 'v', 'b', 'x' },
				{ 'x', 'b', 'v', 'v', 'n', 'v', 'b', 'v', 'v', 'n', 'x' },
				{ 'x', 'n', 'v', 'v', 'v', 'v', 'n', 'v', 'b', 'x', 'x' },
				{ 'x', 'v', 'v', 'v', 'v', 'b', 'v', 'v', 'x', 'x', 'x' },
				{ 'x', 'b', 'n', 'b', 'v', 'v', 'n', 'x', 'x', 'x', 'x' },
				{ 'x', 'v', 'v', 'v', 'n', 'b', 'x', 'x', 'x', 'x', 'x' },
				{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' } };

		@SuppressWarnings("unused")
		char tabCentrifugeuse[][] = new char[][] {
				{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
				{ 'x', 'x', 'x', 'x', 'x', 'v', 'b', 'b', 'b', 'v', 'x' },
				{ 'x', 'x', 'x', 'x', 'n', 'v', 'v', 'v', 'v', 'n', 'x' },
				{ 'x', 'x', 'x', 'n', 'n', 'v', 'b', 'v', 'n', 'n', 'x' },
				{ 'x', 'x', 'n', 'n', 'v', 'v', 'v', 'v', 'n', 'n', 'x' },
				{ 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'x' },
				{ 'x', 'b', 'b', 'v', 'v', 'v', 'v', 'b', 'b', 'x', 'x' },
				{ 'x', 'b', 'b', 'v', 'n', 'v', 'b', 'b', 'x', 'x', 'x' },
				{ 'x', 'b', 'v', 'v', 'v', 'v', 'b', 'x', 'x', 'x', 'x' },
				{ 'x', 'v', 'n', 'n', 'n', 'v', 'x', 'x', 'x', 'x', 'x' },
				{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' } };

		@SuppressWarnings("unused")
		char tabMargueriteBelge[][] = new char[][] {
				{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
				{ 'x', 'x', 'x', 'x', 'x', 'b', 'b', 'v', 'n', 'n', 'x' },
				{ 'x', 'x', 'x', 'x', 'b', 'b', 'b', 'n', 'n', 'n', 'x' },
				{ 'x', 'x', 'x', 'v', 'b', 'b', 'v', 'n', 'n', 'v', 'x' },
				{ 'x', 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'x' },
				{ 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'x' },
				{ 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'x', 'x' },
				{ 'x', 'v', 'n', 'n', 'v', 'b', 'b', 'v', 'x', 'x', 'x' },
				{ 'x', 'n', 'n', 'n', 'b', 'b', 'b', 'x', 'x', 'x', 'x' },
				{ 'x', 'n', 'n', 'v', 'b', 'b', 'x', 'x', 'x', 'x', 'x' },
				{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' } };

		final Plateau plateauJeu = new Plateau();

		plateauJeu.chargerTab(tabClassique);

		System.out.println(plateauJeu.toString());

		final FenetreAbalone fen = new FenetreAbalone(plateauJeu);

		Joueur joueurNoir = new Joueur("joueurNOIR", Couleur.NOIR);
		Joueur joueurBlanc = new Joueur("joueurBLANC", Couleur.BLANC);

		Coord caseDepart;
		Coord caseDestination;

		while (gagnant == null) {
			caseDepart = null;
			caseDestination = null;

			/* les actions */

			while (caseDepart == null) {

			}
			// caseDepart est selectionnée

			while (caseDestination == null) {

			}
			// caseDestination est selectionnée

			/* vérification victoire */
			verifierVictoire(joueurBlanc, joueurNoir);
		}

	}
}
