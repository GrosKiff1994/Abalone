package model;

import view.RoundButton;

import java.util.Optional;

public class Space {

  public Optional<Ball> ball = Optional.empty();
  public boolean isBorder;
  public RoundButton button;

  public Space() {
    isBorder = false;
  }

  public boolean hasBall() {
    return this.ball.isPresent();
  }

}
