package model;

import java.util.NoSuchElementException;
import utils.Coord;

public class Board {

  private static final char BORDER = 'x';
  private static final char BLACK = 'n';
  private static final char WHITE = 'b';
  private static final char EMPTY = 'v';
  private static final int DEFAULT_HEIGHT = 11;
  private static final int DEFAULT_WIDTH = 11;

  public int width;
  public int height;
  private Space grid[][];

  public Board(int height, int width, char[][] map) {
    this.width = width;
    this.height = height;
    this.grid = new Space[this.height][this.width];
    this.load(map);
  }

  public Board(char[][] map) {
    this(DEFAULT_HEIGHT, DEFAULT_WIDTH, map);
  }

  private void load(char[][] map) {
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        Ball ball;
        switch (map[i][j]) {
          case EMPTY:
            grid[i][j] = new Space();
            break;
          case WHITE:
            grid[i][j] = new Space();
            ball = new Ball(Color.WHITE);
            grid[i][j].ball = ball;
            ball.coord.setCoord((double) i, (double) j);
            break;
          case BLACK:
            grid[i][j] = new Space();
            ball = new Ball(Color.BLACK);
            grid[i][j].ball = ball;
            ball.coord.setCoord((double) i, (double) j);
            break;
          case BORDER:
            grid[i][j] = new Space();
            grid[i][j].isBorder = true;
            break;
          default:
        }
      }
    }
  }

  public Space getSpace(int i, int j) {
    return grid[i][j];
  }

  public Space getSpace(Coord coord) {
    return getSpace(coord.y, coord.x);
  }

  public Coord getNeighbor(Coord from, Direction direction) {
    Coord coord = from.add(direction.vector);
    if (!contains(coord))
      throw new NoSuchElementException("X or Y out of board.");
    return coord;
  }

  private boolean contains(Coord coord) {
    return !(coord.y < 0 || coord.y >= this.width || coord.x < 0 || coord.x >= this.height);
  }

  public String toString() {
    String res = "";
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        if (grid[i][j] != null) {
          if (grid[i][j].hasBall()) {
            switch (grid[i][j].ball.color) {
              case BLACK:
                res += "N";
                break;
              case WHITE:
                res += "B";
                break;
              default:
            }
          } else {
            res += "O";
          }
        } else {
          res += "X";
        }
      }
      res += '\n';
    }
    return res;
  }

}
