package model;

import utils.Vector;

import java.util.Optional;

public enum Direction {

  DIR_HD(new Vector(1, -1)), DIR_D(new Vector(1, 0)), DIR_BD(new Vector(0, 1)), DIR_BG(
      new Vector(-1, 1)), DIR_G(new Vector(-1, 0)), DIR_HG(new Vector(0, -1));

  public Vector vector;

  Direction(Vector offset) {
    this.vector = offset;
  }

  public static Optional<Direction> toDirection(Vector delta) {
    for (Direction dir : values()) {
      if (dir.vector.equals(delta)) {
        return Optional.of(dir);
      }
    }
    // It can be impossible to map a vector to a direction
    System.err.println("Invalid direction : " + delta);
    return Optional.empty();
  }
}
