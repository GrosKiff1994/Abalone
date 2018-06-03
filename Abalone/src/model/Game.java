package model;

import java.util.ArrayList;
import java.util.List;

public class Game {

  public Board board;
  public List<Player> players = new ArrayList<>();
  public Player looser;

  public Game(Board board) {
    this.board = board;
  }

  public void checkVictory() {
    String print = "";
    for (Player player : players) {
      print += player.name + " : " + player.lostMarbles + " lost marbles.\n";
      if (player.lostMarbles >= 6) {
        looser = player;
        System.out.println("Player " + looser + " lost!");
        System.exit(0);
      }
    }
    System.out.print(print);
  }

}
