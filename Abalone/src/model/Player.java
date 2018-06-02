package model;

public class Player {

  public String name;
  public Color color;
  public int lostBalls;

  public Player(String name, Color color) {
    this.name = name;
    this.color = color;
    this.lostBalls = 0;
  }

  public String toString() {
    return this.name;
  }

}
