package core;

import objects.Plateau;
import display.BoutonRond;
import display.FenetreAbalone;

public class Partie {

	public enum Etat {
		SELECTION, DEPLACEMENT;
	}

	// private Joueur gagnant;

	public Partie(FenetreAbalone fenetre, char[][] tab) {
		final Plateau plateauJeu = new Plateau();
		plateauJeu.chargerTab(tab);
		fenetre.setPlateau(plateauJeu);
		// gagnant = null;
		fenetre.getPanneau().visibiliteBoutonVide();
		BoutonRond.setEtat(Etat.SELECTION);

	}

	// private void verifierVictoire(Joueur joueurBlanc, Joueur joueurNoir) {
	// if (joueurBlanc.getBoulesDuJoueurEjectees() >= 6)
	// gagnant = joueurNoir;
	//
	// else if (joueurNoir.getBoulesDuJoueurEjectees() >= 6)
	// gagnant = joueurBlanc;
	// }

	public void lancerPartie() {

		// Joueur joueurNoir = new Joueur("joueurNOIR", Couleur.NOIR);
		// Joueur joueurBlanc = new Joueur("joueurBLANC", Couleur.BLANC);

	}

}