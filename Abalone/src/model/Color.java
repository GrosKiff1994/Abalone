package model;

public enum Color {
  WHITE("Couleur blanche"), BLACK("Couleur noire");

  private String name = "";

  Color(String name) {
    this.name = name;
  }

  public String toString() {
    return name;
  }
}
