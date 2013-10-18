package core;

import objects.Couleur;
import objects.Joueur;
import objects.Plateau;
import display.BoutonRond;
import display.FenetreAbalone;

public class Partie {

	public enum Etat {
		SELECTION, DEPLACEMENT;
	}

	private Joueur[] joueurs = new Joueur[2];
	private Joueur perdant;

	public Partie(FenetreAbalone fenetre, char[][] tab) {
		final Plateau plateauJeu = new Plateau();
		plateauJeu.chargerTab(tab);
		fenetre.setPlateau(plateauJeu);
		perdant = null;
		fenetre.getPanneau().visibiliteBoutonVide();
		BoutonRond.setEtat(Etat.SELECTION);

	}

	public void verifierVictoire() {
		String chaine = "";

		for (Joueur j : joueurs) {
			chaine += j.getNom() + " : " + j.getBoulesDuJoueurEjectees() + " boules éjectées; ";
			if (j.getBoulesDuJoueurEjectees() >= 6)
				perdant = j;
		}
	}

	public void lancerPartie() {

		joueurs[0] = new Joueur("joueurNOIR", Couleur.NOIR);
		joueurs[1] = new Joueur("joueurBLANC", Couleur.BLANC);
	}

}
