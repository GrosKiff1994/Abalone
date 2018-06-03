package controller;

import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import model.Board;
import model.Color;
import model.Direction;
import model.Game;
import model.Marble;
import model.Player;
import model.Space;
import utils.Coord;
import utils.CoordDouble;
import utils.Vector;
import view.GamePanel;
import view.RoundButton;
import view.Window;

public class GameController {

  public static final int LEFT_CLICK = MouseEvent.BUTTON1;
  public static final int RIGHT_CLICK = MouseEvent.BUTTON3;
  public static final int MAX_DEPLACEMENTS = 3;
  public static final int IMAGES_PER_SECOND = 35;
  public static final int TIME = 100;

  public Coord b1 = null;
  public Coord b2 = null;
  public Coord b3 = null;

  public Window window;
  public Game game;
  public State state;

  public GameController(Game game) {
    this.game = game;
  }

  public void afficherB1B2B3() {
    System.out.println("B1:" + b1 + "; B2:" + b2 + "; B3:" + b3 + ";");
  }

  public void cleanMarbles() {
    b1 = null;
    b2 = null;
    b3 = null;
    System.out.println("Cleaned marbles!");
    afficherB1B2B3();
  }

  public int colorCount(Direction delta, Coord start, Color color) {
    return this.colorCount(delta, start.y, start.x, color);
  }

  public int colorCount(Direction delta, int iStart, int jStart, Color color) {
    int colorCount = 0;
    Coord parcours = new Coord(jStart, iStart);
    Board board = game.board;
    while (board.getSpace(parcours) != null && board.getSpace(parcours).hasMarble()
        && board.getSpace(parcours).marble.color == color) {
      colorCount++;
      parcours.x = parcours.x + delta.vector.x;
      parcours.y = parcours.y + delta.vector.y;
    }
    return colorCount;
  }

  public void checkBalls() {
    Board board = game.board;
    // Check if a marble is out of the game
    for (int i = 0; i < board.height; i++) {
      for (int j = 0; j < board.width; j++) {
        if (board.getSpace(i, j).isBorder && board.getSpace(i, j).hasMarble()) {
          for (Player player : game.players) {
            if (board.getSpace(i, j).marble.color == player.color) {
              player.lostMarbles += 1;
            }
          }
          board.getSpace(i, j).marble = null;
          game.checkVictory();
        }
      }
    }
  }

  public void onMouseClickUp(MouseEvent e) {

    RoundButton button = (RoundButton) e.getSource();
    GamePanel panel = window.panel;
    Board board = game.board;

    System.out.println("Action:click(" + button.coord.x + ";" + button.coord.y + ")");

    Direction[] lesDir = Direction.values();

    switch (state) {
      case NORMAL:
        switch (e.getButton()) {
          case LEFT_CLICK:
            handleLeftClickInNormalState(button, panel, board, lesDir);
            break;
          case RIGHT_CLICK:
            handleRightClickInNormalState(button, panel, board, lesDir);
            break;
          default:
            break;
        }
        break;
      case FIRST_SELECTED_FOR_LINE:
        switch (e.getButton()) {
          case LEFT_CLICK:
            handleLeftClickInFirstSelectedForLineState(button, panel, board);
            break;
          default:
            break;
        }
        break;
      case FIRST_SELECTED_FOR_LATERAL:
        switch (e.getButton()) {
          case RIGHT_CLICK:
            handleRightClickInFirstSelectedForLateralState(button, panel, board);
            break;
          default:
            break;
        }
        break;
      case SECOND_SELECTED_FOR_LATERAL:
        switch (e.getButton()) {
          case LEFT_CLICK:
            handleLeftClickInsecondSelectedForLateralState(button, panel, board);
            break;
          case RIGHT_CLICK:
            handleRightClickInSecondSelectedForLateralState(button, board);
            break;
          default:
            break;
        }
        break;
      case THIRD_SELECTED_FOR_LATERAL:
        switch (e.getButton()) {
          case LEFT_CLICK:
            handleLeftClickInThirdSelectedfForLateralState();
            break;
          default:
            break;
        }
        break;
      default:
        break;

    }
  }

  private void handleLeftClickInThirdSelectedfForLateralState() {
    this.cleanMarbles();
    this.state = State.NORMAL;
  }

  private void handleRightClickInSecondSelectedForLateralState(RoundButton bouton, Board board) {
    if (bouton.isSelectionne())
      return;

    b3 = bouton.coord;

    this.reorder(b1, b2, b3);
    this.afficherB1B2B3();

    Space caseB1 = board.getSpace(b1);
    Space caseB2 = board.getSpace(b2);
    Space caseB3 = board.getSpace(b3);

    Vector twoMarblesDirection = new Vector(b1, b2);

    Space caseDecalDepart = board.getSpace(b1.add(twoMarblesDirection.getOpposite()));
    Space caseDecalArrivee = board.getSpace(b3.add(twoMarblesDirection));

    HashSet<Space> casesSpeciales = new HashSet<>();
    casesSpeciales.add(caseDecalDepart);
    casesSpeciales.add(caseDecalArrivee);
    casesSpeciales.add(caseB1);
    casesSpeciales.add(caseB3);
    casesSpeciales.add(caseB2);

    /* affichage des boutons lateraux */
    this.cercleBoutonsLateraux(casesSpeciales, caseB1);
    this.cercleBoutonsLateraux(casesSpeciales, caseB2);
    this.cercleBoutonsLateraux(casesSpeciales, caseB3);

    caseDecalDepart.button.reset();
    caseDecalArrivee.button.reset();

    this.eliminerForeverAlone();

    this.state = State.THIRD_SELECTED_FOR_LATERAL;
  }

  private void handleLeftClickInsecondSelectedForLateralState(RoundButton button, GamePanel panel,
      Board board) {
    Vector twoMarblesDirection = new Vector(b1, b2);

    Space caseProlonge = board.getSpace(button.coord.add(twoMarblesDirection));
    Space caseInverse = board.getSpace(button.coord.add(twoMarblesDirection.getOpposite()));

    if ((caseProlonge.button != null && !caseProlonge.isBorder && caseProlonge.button.isMouseOver
        && !caseProlonge.hasMarble())
        || (caseInverse.button != null && !caseInverse.isBorder && caseInverse.button.isMouseOver
            && !caseInverse.hasMarble() && caseInverse.button.isLeftClickable())) {

      Vector sensDeplac = new Vector(b1, button.coord);
      int nbMarbles = 2;
      int periode = TIME / nbMarbles / IMAGES_PER_SECOND;

      if (!caseProlonge.button.isLeftClickable()) {
        sensDeplac = sensDeplac.add(twoMarblesDirection.getOpposite());
      }
      panel.hideButtons();

      Optional<Direction> maybeDir = Direction.toDirection(sensDeplac);
      Direction dir = maybeDir.orElseThrow(() -> new NoSuchElementException("No direction found!"));

      this.moveMarbles(dir, b1, periode);
      this.moveMarbles(dir, b2, periode);

      this.cleanMarbles();
      panel.hideButtons();
      panel.updateClickables();

      this.state = State.NORMAL;
    }
  }

  private void handleRightClickInFirstSelectedForLateralState(RoundButton button, GamePanel panel,
      Board board) {
    if (button.isSelectionne())
      return;

    b2 = button.coord;
    Vector twoMarblesDirection = new Vector(b1, b2);

    panel.hideButtons();

    Space caseDepart = board.getSpace(b1);
    Space caseArrivee = board.getSpace(b2);
    Space caseDecalDepart = board.getSpace(b1.add(twoMarblesDirection.getOpposite()));
    Space caseDecalArrivee = board.getSpace(b2.add(twoMarblesDirection));

    HashSet<Space> listeCases = new HashSet<>();
    listeCases.add(caseDepart);
    listeCases.add(caseArrivee);
    listeCases.add(caseDecalDepart);
    listeCases.add(caseDecalArrivee);

    /* affichage des boutons lateraux */
    cercleBoutonsLateraux(listeCases, caseDepart);
    cercleBoutonsLateraux(listeCases, caseArrivee);

    /* affichage des boutons decal */

    if (caseDecalArrivee.hasMarble() && caseDecalArrivee.marble.color == caseArrivee.marble.color) {
      caseDecalArrivee.button.makeRightClickable();
    } else {
      caseDecalArrivee.button.reset();
    }

    if (caseDecalDepart.hasMarble() && caseDecalDepart.marble.color == caseDepart.marble.color) {
      caseDecalDepart.button.makeRightClickable();
    } else {
      caseDecalDepart.button.reset();
    }

    eliminerForeverAlone();

    this.state = State.SECOND_SELECTED_FOR_LATERAL;
  }

  private void handleLeftClickInFirstSelectedForLineState(RoundButton bouton, GamePanel panneau,
      Board board) {
    b2 = bouton.coord;

    Vector twoMarblesDirection = new Vector(b1, b2);

    Optional<Direction> maybeDir = Direction.toDirection(twoMarblesDirection);
    Direction dir = maybeDir.orElseThrow(() -> new NoSuchElementException("No direction found!"));

    // First line of marbles
    Color couleurDepart = board.getSpace(b1).marble.color;
    int nbCouleurActuelle = this.colorCount(dir, b1, couleurDepart);

    Space caseDeFinCouleurActuelle =
        board.getSpace(b1.y + nbCouleurActuelle * twoMarblesDirection.y,
            b1.x + nbCouleurActuelle * twoMarblesDirection.x);

    // Second line of marbles
    int nbCouleurOpposee = caseDeFinCouleurActuelle.marble != null
        ? colorCount(dir, b1.y + nbCouleurActuelle * twoMarblesDirection.y,
            b1.x + nbCouleurActuelle * twoMarblesDirection.x, caseDeFinCouleurActuelle.marble.color)
        : 0;
    int nbMovedMarbles = nbCouleurActuelle + nbCouleurOpposee;
    boolean deplacementPossible = true;

    // verification de la case qui suit la derniere
    Space caseFinale = board.getSpace(b1.y + nbMovedMarbles * twoMarblesDirection.y,
        b1.x + nbMovedMarbles * twoMarblesDirection.x);
    if (caseFinale != null && caseFinale.hasMarble()) {
      deplacementPossible = false;
    }

    System.out.println(
        "CurrentColorNb : " + nbCouleurActuelle + ", OppositeColorNb : " + nbCouleurOpposee);

    // deplacement reel
    if (nbCouleurActuelle <= MAX_DEPLACEMENTS && nbCouleurOpposee < nbCouleurActuelle
        && deplacementPossible) {
      panneau.hideButtons();
      this.moveMarbleLine(nbMovedMarbles, twoMarblesDirection);
      checkBalls();
    }

    /* nettoyage et reaffichage */
    this.cleanMarbles();
    panneau.hideButtons();
    panneau.updateClickables();

    this.state = State.NORMAL;
  }

  private void handleRightClickInNormalState(RoundButton bouton, GamePanel panneau, Board board,
      Direction[] lesDir) {
    RoundButton tmp;// coordonnees depart

    b1 = bouton.coord;

    System.out.println("dans 1er clic droit");
    afficherB1B2B3();
    System.out.println(bouton);

    panneau.hideButtons();

    RoundButton boutonDep = board.getSpace(b1).button;
    boutonDep.setVisible(true);

    // afficher cercle voisins
    for (Direction dir : lesDir) {
      Space spaceDest =
          board.getSpace(bouton.coord.y + dir.vector.y, bouton.coord.x + dir.vector.x);

      tmp = spaceDest.button;
      if (tmp != null && !spaceDest.isBorder && spaceDest.marble != null
          && spaceDest.marble.color == board.getSpace(b1).marble.color) {
        tmp.makeRightClickable();
      }
    }

    this.state = State.FIRST_SELECTED_FOR_LATERAL;
  }

  private void handleLeftClickInNormalState(RoundButton bouton, GamePanel panneau, Board board,
      Direction[] lesDir) {
    RoundButton tmp;// coordonnees depart
    b1 = bouton.coord;
    this.state = State.FIRST_SELECTED_FOR_LINE;

    // cacher
    panneau.hideButtons();

    // afficher cercle voisins
    for (Direction dir : lesDir) {
      Coord dest = new Coord(bouton.coord.x + dir.vector.x, bouton.coord.y + dir.vector.y);

      tmp = board.getSpace(dest).button;
      if (tmp != null && !board.getSpace(dest).isBorder) {
        tmp.makeLeftClickable();
      }
    }
  }

  private void reorder(Coord p, Coord s, Coord t) {
    Vector sToT = new Vector(s, t);
    if (Math.abs(sToT.x) > 1 || Math.abs(sToT.y) > 1) {
      Coord pSave = new Coord(p);
      Coord sSave = new Coord(s);
      Coord tSave = new Coord(t);
      p.setCoord(tSave);
      s.setCoord(pSave);
      t.setCoord(sSave);
    }
  }


  // Met en cliquable gauche les boutons qui ne sont pas dans listeCases et qui entourent centre
  private void cercleBoutonsLateraux(HashSet<Space> blackList, Space centre) {
    Board board = this.game.board;
    Direction[] lesDir = Direction.values();
    for (Direction dir : lesDir) {
      Space caseDest = board.getSpace(centre.button.coord.y + dir.vector.y,
          centre.button.coord.x + dir.vector.x);
      RoundButton tmp = caseDest.button;
      if (tmp != null && !caseDest.isBorder && !caseDest.hasMarble()) {
        for (Space caseTmp : blackList) {
          if (caseDest.button.equals(caseTmp.button))
            break;
        }
        tmp.makeLeftClickable();
      }
    }
  }

  private void eliminerForeverAlone() {
    Board board = this.game.board;
    Direction[] lesDir = Direction.values();
    // cache les boutons isolés
    for (int i = 0; i < this.game.board.height; i++) {
      for (int j = 0; j < this.game.board.width; j++) {
        if (board.getSpace(i, j).button.isLeftClickable()) {
          boolean aVoisin = false;
          for (Direction dir : lesDir) {
            if (board.getSpace(i + dir.vector.y, j + dir.vector.x).button.isLeftClickable()) {
              aVoisin = true;
              break;
            }
          }
          if (!aVoisin) {
            board.getSpace(i, j).button.reset();
          }
        }
      }
    }
  }

  private void moveMarbleLine(int nbMarbles, Vector delta) {
    int periode = TIME / nbMarbles / IMAGES_PER_SECOND;
    System.out.println(nbMarbles + " marbles to move.");
    // The first marble is the first oen to be moved
    Coord coordDepla =
        new Coord(b1.x + (nbMarbles - 1) * delta.x, b1.y + (nbMarbles - 1) * delta.y);
    while (nbMarbles > 0) {
      nbMarbles--;
      Optional<Direction> maybeDir = Direction.toDirection(delta);
      Direction dir = maybeDir.orElseThrow(() -> new NoSuchElementException("No direction found!"));
      moveMarbles(dir, coordDepla, periode);
      coordDepla.x = coordDepla.x - delta.x;
      coordDepla.y = coordDepla.y - delta.y;
    }
  }

  public void onMouseOver(MouseEvent e) {
    RoundButton button = ((RoundButton) e.getSource());
    button.isMouseOver = true;
    if (state == State.SECOND_SELECTED_FOR_LATERAL) {
      Vector twoMarblesDirection = new Vector(b1, b2);
      Coord coordDepla = button.coord.add(twoMarblesDirection);
      if (!game.board.getSpace(coordDepla).button.isVisible()) {
        coordDepla = button.coord.add(twoMarblesDirection.getOpposite());
      }
      game.board.getSpace(coordDepla).button.isMouseOver = true;
      window.repaint();
    } else if (state == State.THIRD_SELECTED_FOR_LATERAL) {
      // TODO On verra pus tard...
    }
  }

  public void onMouseOut(MouseEvent e) {
    RoundButton button = ((RoundButton) e.getSource());
    button.isMouseOver = false;
    if (state == State.SECOND_SELECTED_FOR_LATERAL) {
      Vector twoMarblesDirection = new Vector(b1, b2);
      Coord coordDepla = button.coord.add(twoMarblesDirection);
      if (!game.board.getSpace(coordDepla).button.isVisible()) {
        coordDepla = button.coord.add(twoMarblesDirection.getOpposite());
      }
      game.board.getSpace(coordDepla).button.isMouseOver = false;
      window.repaint();
    } else if (state == State.THIRD_SELECTED_FOR_LATERAL) {
      // TODO On verra pus tard...
    }
  }

  public void moveMarbles(Direction dir, Coord coordCase, int tempsPeriode)
      throws MovementException {

    System.out.println("Moving marble(" + coordCase.x + ";" + coordCase.y + ") with direction ("
        + dir.vector.x + ";" + dir.vector.y + ")");

    if (coordCase.x < 0 || coordCase.x >= game.board.width || coordCase.y < 0
        || coordCase.y >= game.board.height) {
      throw new MovementException("case debut invalide (<0 | >" + game.board.height + ")");
    }

    Space caseActuelle = game.board.getSpace(coordCase);
    Coord coordCaseSuivante = game.board.getNeighbor(coordCase, dir);

    if (game.board.getSpace(coordCaseSuivante).hasMarble()) {
      throw new MovementException("case arrivee occcupee");
    }

    Marble marbleToMove = caseActuelle.marble;

    CoordDouble delta = new CoordDouble((double) (dir.vector.x) / IMAGES_PER_SECOND,
        (double) (dir.vector.y) / IMAGES_PER_SECOND);

    for (int i = 0; i < IMAGES_PER_SECOND; i++) {
      long debut = System.currentTimeMillis();
      marbleToMove.coord.setCoord(CoordDouble.somme(marbleToMove.coord, delta));

      window.repaint();

      window.panel.paintImmediately(0, 0, window.panel.getWidth(), window.panel.getHeight());

      try {
        long tpsRestant = tempsPeriode - (System.currentTimeMillis() - debut);
        if (tpsRestant > 0)
          Thread.sleep(tpsRestant);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }

    marbleToMove.coord.setCoord(coordCaseSuivante.y, coordCaseSuivante.x);
    game.board.getSpace(coordCaseSuivante).marble = caseActuelle.marble;
    caseActuelle.marble = null;

  }
}
