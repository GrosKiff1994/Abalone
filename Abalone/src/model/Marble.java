package model;

import utils.CoordDouble;

public class Marble {

  public Color color;
  public CoordDouble coord;

  public Marble(Color color) {
    this.color = color;
    this.coord = new CoordDouble(0, 0);
  }

}
