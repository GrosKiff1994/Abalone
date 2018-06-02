package modele;

import java.util.ArrayList;
import java.util.List;

public class Game {
  private Board plateau;
  public List<Player> players = new ArrayList<>();
  private Player looser;

  public void verifierVictoire() {
    String chaine = "";

    for (Player j : players) {
      chaine += j.getNom() + " : " + j.getBoulesDuJoueurEjectees() + " boule(s) ejectee(s); \n";
      if (j.getBoulesDuJoueurEjectees() >= 6) {
        looser = j;
        System.out.println("Le joueur " + looser + " a perdu !");
        System.exit(0);
      }
    }

    System.out.print(chaine);
  }

  public Board getPlateau() {
    return this.plateau;
  }

  public void setBoard(Board plateau) {
    this.plateau = plateau;
  }

}
