package model;

public class Player {

  public String name;
  public Color color;
  public int lostMarbles;

  public Player(String name, Color color) {
    this.name = name;
    this.color = color;
    this.lostMarbles = 0;
  }

  public String toString() {
    return this.name;
  }

}
