package objects;

public class Joueur {

	private String nom;
	private Couleur couleur;
	private int boulesDuJoueurEjectees;

	public Joueur(String nom, Couleur couleur) {
		this.nom = nom;
		this.couleur = couleur;
		boulesDuJoueurEjectees = 0;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getBoulesDuJoueurEjectees() {
		return boulesDuJoueurEjectees;
	}

	public void setBoulesDuJoueurEjectees(int boulesDuJoueurEjectees) {
		this.boulesDuJoueurEjectees = boulesDuJoueurEjectees;
	}

}
