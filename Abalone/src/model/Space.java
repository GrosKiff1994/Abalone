package model;

import view.RoundButton;

public class Space {

  public Ball ball;
  public boolean isBorder;
  public RoundButton bouton;

  public Space() {
    isBorder = false;
  }

  public boolean estOccupee() {
    return this.ball != null;
  }

}
