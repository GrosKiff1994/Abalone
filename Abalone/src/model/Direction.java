package model;

import utils.Vector;

public enum Direction {

  DIR_HD(new Vector(1, -1)), DIR_D(new Vector(1, 0)), DIR_BD(new Vector(0, 1)), DIR_BG(
      new Vector(-1, 1)), DIR_G(new Vector(-1, 0)), DIR_HG(new Vector(0, -1));

  public Vector vector;

  Direction(Vector offset) {
    this.vector = offset;
  }

  public static Direction toDirection(Vector delta) {
    if (delta.x < -1 || delta.x > 1 || delta.y < -1 || delta.y > 1) {
      try {
        throw new DirectionException("Direction invalide : " + delta);
      } catch (DirectionException e) {
        e.printStackTrace();
      }
    }
    for (Direction dir : values()) {
      if (dir.vector.equals(delta)) {
        return dir;
      }
    }
    return null;
  }

}
