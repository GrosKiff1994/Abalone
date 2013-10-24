package core;

import objects.Plateau;
import display.BoutonRond;
import display.FenetreAbalone;

public class Partie {

	public enum Etat {
		SELECTION, DEPLACEMENT;
	}

	private static Joueur[] tabJoueurs = new Joueur[2];
	private static Joueur perdant;

	public Partie(FenetreAbalone fenetre, char[][] tab) {
		final Plateau plateauJeu = new Plateau();
		plateauJeu.chargerTab(tab);
		fenetre.setPlateau(plateauJeu);
		// gagnant = null;
		fenetre.getPanneau().visibiliteBoutonVide();
		BoutonRond.setEtat(Etat.SELECTION);

	}

	public static void verifierVictoire() {
		String chaine = "";

		for (Joueur j : tabJoueurs) {
			chaine += j.getNom() + " : " + j.getBoulesDuJoueurEjectees()
					+ " boule(s) éjectée(s); \n";
			if (j.getBoulesDuJoueurEjectees() >= 6) {
				perdant = j;
				System.out.println("Le joueur " + perdant + " a perdu !");
			}
		}

		System.out.print(chaine);
	}

	public void lancerPartie() {

		tabJoueurs[0] = new Joueur("joueurNOIR", Couleur.NOIR);
		tabJoueurs[1] = new Joueur("joueurBLANC", Couleur.BLANC);
	}

	public static Joueur[] getTabJoueurs() {
		return tabJoueurs;
	}

}
