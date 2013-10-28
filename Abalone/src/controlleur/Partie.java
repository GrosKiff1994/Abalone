package controlleur;

import modele.Couleur;
import modele.Joueur;
import modele.Plateau;
import vue.BoutonRond;
import vue.FenetreAbalone;

public class Partie {

	public enum Etat {
		SELECTION, SELECTION2, SELECTION3, DEPLACEMENTLIGNE, DEPLACEMENTLATERAL2, DEPLACEMENTLATERAL3;
	}

	private static Joueur[] tabJoueurs = new Joueur[2];
	private static Joueur perdant;
	private FenetreAbalone fenetre;

	public Partie(FenetreAbalone fenetre, char[][] tab) {
		this.fenetre = fenetre;
		final Plateau plateauJeu = new Plateau();
		plateauJeu.chargerTab(tab);
		this.fenetre.setPlateau(plateauJeu);
		// gagnant = null;
		fenetre.getPanneau().visibiliteBoutonVide();
		BoutonRond.setEtat(Etat.SELECTION);

	}

	public static void verifierVictoire() {
		String chaine = "";

		for (Joueur j : tabJoueurs) {
			chaine += j.getNom() + " : " + j.getBoulesDuJoueurEjectees() + " boule(s) ejectee(s); \n";
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
