package modele;

import Utilitaire.CoordDouble;

public class Boule {

	private Couleur couleur;
	private CoordDouble coord;

	public Boule(Couleur couleur) {
		this.couleur = couleur;
		this.coord = new CoordDouble(0, 0);
	}

	public Couleur getCouleur() {
		return this.couleur;
	}

	public CoordDouble getCoord() {
		return coord;
	}

}