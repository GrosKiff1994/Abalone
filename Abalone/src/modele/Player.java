package modele;

public class Player {

  private String nom;
  public Color color;
  private int boulesDuJoueurEjectees;

  public Player(String nom, Color couleur) {
    this.nom = nom;
    this.setCouleur(couleur);
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

  public void setCouleur(Color couleur) {
    this.color = couleur;
  }

  public String toString() {
    return this.getNom();
  }
}
