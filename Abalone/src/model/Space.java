package model;

import view.RoundButton;

public class Space {

  public Marble marble = null;
  public boolean isBorder;
  public RoundButton button;

  public Space() {
    isBorder = false;
  }

  public boolean hasMarble() {
    return this.marble != null;
  }

}
