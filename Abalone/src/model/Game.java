package model;

import java.util.ArrayList;
import java.util.List;

public class Game {

  public Board board;
  public List<Player> players = new ArrayList<>();
  public Player looser;

  public void checkVictory() {
    String print = "";
    for (Player player : players) {
      print += player.name + " : " + player.lostBalls + " boule(s) ejectee(s); \n";
      if (player.lostBalls >= 6) {
        looser = player;
        System.out.println("Le joueur " + looser + " a perdu !");
        System.exit(0);
      }
    }
    System.out.print(print);
  }

}
