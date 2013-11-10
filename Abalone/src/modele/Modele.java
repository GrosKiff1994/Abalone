package modele;

import vue.FenetreAbalone;

public class Modele {
	private Plateau plateau;
	private static Joueur[] tabJoueurs = new Joueur[2];
	private static Joueur perdant;

	public Joueur[] getTabJoueurs() {
		return tabJoueurs;
	}

	public void verifierVictoire() {
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

	public void setVue(FenetreAbalone fenetre) {
	}

	public Plateau getPlateau() {
		return this.plateau;
	}

	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}

}
