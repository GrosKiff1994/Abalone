package model;

import view.RoundButton;

public class Space {

  public Ball ball;
  public boolean isBorder;
  public RoundButton button;

  public Space() {
    isBorder = false;
  }

  public boolean hasBall() {
    return this.ball != null;
  }

}
