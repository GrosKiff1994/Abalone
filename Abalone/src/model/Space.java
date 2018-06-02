package model;

import view.BoutonRond;

public class Space {

  public Ball ball;
  public boolean isBorder;
  public BoutonRond bouton;

  public Space() {
    isBorder = false;
  }

  public boolean estOccupee() {
    return this.ball != null;
  }

}
