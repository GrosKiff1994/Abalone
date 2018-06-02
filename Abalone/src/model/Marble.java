package model;

import utils.CoordDouble;

public class Marble {

  public Color color;
  public CoordDouble coord;

  public Marble(CoordDouble coordDouble, Color color) {
    this.color = color;
    this.coord = coordDouble;
  }

}
