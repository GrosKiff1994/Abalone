package model;

import utils.CoordDouble;

public class Ball {

  public Color color;
  public CoordDouble coord;

  public Ball(Color color) {
    this.color = color;
    this.coord = new CoordDouble(0, 0);
  }

}
