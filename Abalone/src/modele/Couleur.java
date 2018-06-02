package modele;

public enum Couleur {
  BLANC("Couleur blanche"), NOIR("Couleur noire");

  private String name = "";

  Couleur(String name) {
    this.name = name;
  }

  public String toString() {
    return name;
  }
}
