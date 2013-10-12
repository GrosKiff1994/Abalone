package core;

import objects.Carte;
import objects.Couleur;
import objects.Joueur;
import objects.Plateau;
import display.FenetreAbalone;

public class Partie {

	public enum Etat {SELECTION,DEPLACEMENT;}
	
	private Joueur gagnant;
	private Etat etat;

	public Partie(FenetreAbalone fenetre) {
		final Plateau plateauJeu = new Plateau();
		plateauJeu.chargerTab(Carte.tabMargueriteBelge);
		fenetre.setPlateau(plateauJeu);
		gagnant = null;
	}

	private void verifierVictoire(Joueur joueurBlanc, Joueur joueurNoir) {
		if (joueurBlanc.getBoulesDuJoueurEjectees() >= 6)
			gagnant = joueurNoir;

		else if (joueurNoir.getBoulesDuJoueurEjectees() >= 6)
			gagnant = joueurBlanc;
	}

	public void lancerPartie() {

		etat = Etat.SELECTION;
		Joueur joueurNoir = new Joueur("joueurNOIR", Couleur.NOIR);
		Joueur joueurBlanc = new Joueur("joueurBLANC", Couleur.BLANC);

		while (gagnant == null) {
			verifierVictoire(joueurBlanc, joueurNoir);
		}
	}

}
