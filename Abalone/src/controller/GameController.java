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

  public static final int CLICGAUCHE = MouseEvent.BUTTON1;
  public static final int CLICDROIT = MouseEvent.BUTTON3;
  public static final int MAX_DEPLACEMENTS = 3;
  public static final int ips = 35;
  public static final int temps = 100;

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
    System.out.println("b1 : " + b1 + " b2 : " + b2 + " b3 : " + b3);
  }

  public void cleanMarbles() {
    b1 = null;
    b2 = null;
    b3 = null;
    System.out.println("dans viderB1B2B3");
    afficherB1B2B3();
  }

  public int compteCouleur(Direction delta, Coord depart, Color couleur) {
    return this.compteCouleur(delta, depart.y, depart.x, couleur);
  }

  public int compteCouleur(Direction delta, int iDep, int jDep, Color couleur) {
    int nbCoul = 0;
    Coord parcours = new Coord(jDep, iDep);
    Board board = game.board;
    while (board.getSpace(parcours) != null && board.getSpace(parcours).hasMarble()
        && board.getSpace(parcours).marble.color == couleur) {
      nbCoul++;
      parcours.x = parcours.x + delta.vector.x;
      parcours.y = parcours.y + delta.vector.y;
    }
    return nbCoul;
  }

  public void verifierBoules() {
    Board board = game.board;
    // verification boule hors jeu
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

  public void sourisRelachee(MouseEvent e) {

    RoundButton button = ((RoundButton) e.getSource());
    GamePanel panel = window.panel;
    Board board = game.board;

    System.out.println("clic : ligne " + button.getCoordI() + ", colonne " + button.getCoordJ());

    Direction[] lesDir = Direction.values();

    // BoutonRond tmp; /* tous les boutons temporaires de parcours
    // circulaire */

    switch (state) {
      case NORMAL:
        switch (e.getButton()) {
          case CLICGAUCHE:
            handleLeftClickInNormalState(button, panel, board, lesDir);
            break;
          case CLICDROIT:
            handleRightClickInNormalState(button, panel, board, lesDir);
            break;
          default:
            break;
        }
        break;
      case FIRST_SELECTED_FOR_LINE:
        switch (e.getButton()) {
          case CLICGAUCHE:
            handleLeftClickInFirstSelectedForLineState(button, panel, board);
            break;
          default:
            break;
        }
        break;
      case FIRST_SELECTED_FOR_LATERAL:
        switch (e.getButton()) {
          case CLICDROIT:
            handleRightClickInFirstSelectedForLateralState(button, panel, board);
            break;
          default:
            break;
        }
        break;
      case SECOND_SELECTED_FOR_LATERAL:
        switch (e.getButton()) {
          case CLICGAUCHE:
            handleLeftClickInsecondSelectedForLateralState(button, panel, board);
            break;
          case CLICDROIT:
            handleRightClickInSecondSelectedForLateralState(button, board);
            break;
          default:
            break;
        }
        break;
      case THIRD_SELECTED_FOR_LATERAL:
        switch (e.getButton()) {
          case CLICGAUCHE:
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

    reordonne(b1, b2, b3);
    afficherB1B2B3();

    Space caseB1 = board.getSpace(b1);
    Space caseB2 = board.getSpace(b2);
    Space caseB3 = board.getSpace(b3);

    Vector sensDeuxBoules = new Vector(b1, b2);

    Space caseDecalDepart = board.getSpace(b1.add(sensDeuxBoules.getOpposite()));
    Space caseDecalArrivee = board.getSpace(b3.add(sensDeuxBoules));

    HashSet<Space> casesSpeciales = new HashSet<>();
    casesSpeciales.add(caseDecalDepart);
    casesSpeciales.add(caseDecalArrivee);
    casesSpeciales.add(caseB1);
    casesSpeciales.add(caseB3);
    casesSpeciales.add(caseB2);

    /* affichage des boutons lateraux */
    cercleBoutonsLateraux(casesSpeciales, caseB1);
    cercleBoutonsLateraux(casesSpeciales, caseB2);
    cercleBoutonsLateraux(casesSpeciales, caseB3);

    caseDecalDepart.button.reset();
    caseDecalArrivee.button.reset();

    eliminerForeverAlone();

    this.state = State.THIRD_SELECTED_FOR_LATERAL;
  }

  private void handleLeftClickInsecondSelectedForLateralState(RoundButton bouton, GamePanel panneau,
      Board board) {
    Vector sensDeuxBoules = new Vector(b1, b2);

    Space caseProlonge = board.getSpace(bouton.coord.add(sensDeuxBoules));
    Space caseInverse = board.getSpace(bouton.coord.add(sensDeuxBoules.getOpposite()));

    if ((caseProlonge.button != null && !caseProlonge.isBorder && caseProlonge.button.isMouseOver
        && !caseProlonge.hasMarble())
        || (caseInverse.button != null && !caseInverse.isBorder && caseInverse.button.isMouseOver
            && !caseInverse.hasMarble() && caseInverse.button.isLeftClickable())) {

      Vector sensDeplac = new Vector(b1, bouton.coord);
      int nbBoules = 2;
      int periode = temps / nbBoules / ips;

      if (!caseProlonge.button.isLeftClickable()) {
        sensDeplac = sensDeplac.add(sensDeuxBoules.getOpposite());
      }
      panneau.hideButtons();

      Optional<Direction> maybeDir = Direction.toDirection(sensDeplac);
      Direction dir = maybeDir.orElseThrow(() -> new NoSuchElementException("No direction found!"));

      deplacerBouleDirection(dir, b1, periode);
      deplacerBouleDirection(dir, b2, periode);

      this.cleanMarbles();
      panneau.hideButtons();
      panneau.updateClickables();

      this.state = State.NORMAL;
    }
  }

  private void handleRightClickInFirstSelectedForLateralState(RoundButton bouton, GamePanel panneau,
      Board board) {
    if (bouton.isSelectionne())
      return;

    b2 = bouton.getCoord();
    Vector sensDeuxBoules = new Vector(b1, b2);

    panneau.hideButtons();

    Space caseDepart = board.getSpace(b1);
    Space caseArrivee = board.getSpace(b2);
    Space caseDecalDepart = board.getSpace(b1.add(sensDeuxBoules.getOpposite()));
    Space caseDecalArrivee = board.getSpace(b2.add(sensDeuxBoules));

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
      caseDecalArrivee.button.mettreCliquableDroit();
    } else {
      caseDecalArrivee.button.reset();
    }

    if (caseDecalDepart.hasMarble() && caseDecalDepart.marble.color == caseDepart.marble.color) {
      caseDecalDepart.button.mettreCliquableDroit();
    } else {
      caseDecalDepart.button.reset();
    }

    eliminerForeverAlone();

    this.state = State.SECOND_SELECTED_FOR_LATERAL;
  }

  private void handleLeftClickInFirstSelectedForLineState(RoundButton bouton, GamePanel panneau,
      Board board) {
    b2 = bouton.getCoord();

    Vector sensDeuxBoules = new Vector(b1, b2);

    Optional<Direction> maybeDir = Direction.toDirection(sensDeuxBoules);
    Direction dir = maybeDir.orElseThrow(() -> new NoSuchElementException("No direction found!"));

    /* premiere ligne de boules */
    Color couleurDepart = board.getSpace(b1).marble.color;
    int nbCouleurActuelle = this.compteCouleur(dir, b1, couleurDepart);

    Space caseDeFinCouleurActuelle = board.getSpace(b1.y + nbCouleurActuelle * sensDeuxBoules.y,
        b1.x + nbCouleurActuelle * sensDeuxBoules.x);

    /* seconde ligne de boules */

    int nbCouleurOpposee =
        caseDeFinCouleurActuelle.marble != null
            ? compteCouleur(dir, b1.y + nbCouleurActuelle * sensDeuxBoules.y,
                b1.x + nbCouleurActuelle * sensDeuxBoules.x, caseDeFinCouleurActuelle.marble.color)
            : 0;
    int nbBoulesDeplac = nbCouleurActuelle + nbCouleurOpposee;
    boolean deplacementPossible = true;

    // verification de la case qui suit la derniere
    Space caseFinale = board.getSpace(b1.y + nbBoulesDeplac * sensDeuxBoules.y,
        b1.x + nbBoulesDeplac * sensDeuxBoules.x);
    if (caseFinale != null && caseFinale.hasMarble()) {
      deplacementPossible = false;
    }

    System.out.println(
        "nbCouleurAcuelle = " + nbCouleurActuelle + ", nbCouleurOpposee = " + nbCouleurOpposee);

    // deplacement reel
    if (nbCouleurActuelle <= MAX_DEPLACEMENTS && nbCouleurOpposee < nbCouleurActuelle
        && deplacementPossible) {
      panneau.hideButtons();
      this.deplacerLigneBoules(nbBoulesDeplac, sensDeuxBoules);
      verifierBoules();
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

    b1 = bouton.getCoord();

    System.out.println("dans 1er clic droit");
    afficherB1B2B3();
    System.out.println(bouton);

    panneau.hideButtons();

    RoundButton boutonDep = board.getSpace(b1).button;
    boutonDep.setVisible(true);

    // afficher cercle voisins
    for (Direction dir : lesDir) {
      Space spaceDest =
          board.getSpace(bouton.getCoordI() + dir.vector.y, bouton.getCoordJ() + dir.vector.x);

      tmp = spaceDest.button;
      if (tmp != null && !spaceDest.isBorder && spaceDest.marble != null
          && spaceDest.marble.color == board.getSpace(b1).marble.color) {
        tmp.mettreCliquableDroit();
      }
    }

    this.state = State.FIRST_SELECTED_FOR_LATERAL;
  }

  private void handleLeftClickInNormalState(RoundButton bouton, GamePanel panneau, Board board,
      Direction[] lesDir) {
    RoundButton tmp;// coordonnees depart
    b1 = bouton.getCoord();
    this.state = State.FIRST_SELECTED_FOR_LINE;

    // cacher
    panneau.hideButtons();

    // afficher cercle voisins
    for (Direction dir : lesDir) {
      Coord dest = new Coord(bouton.getCoordJ() + dir.vector.x, bouton.getCoordI() + dir.vector.y);

      tmp = board.getSpace(dest).button;
      if (tmp != null && !board.getSpace(dest).isBorder) {
        tmp.mettreCliquableGauche();
      }
    }
  }

  private void reordonne(Coord p, Coord s, Coord t) {
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
      Space caseDest = board.getSpace(centre.button.getCoordI() + dir.vector.y,
          centre.button.getCoordJ() + dir.vector.x);
      RoundButton tmp = caseDest.button;
      if (tmp != null && !caseDest.isBorder && !caseDest.hasMarble()) {
        for (Space caseTmp : blackList) {
          if (caseDest.button.equals(caseTmp.button))
            break;
        }
        tmp.mettreCliquableGauche();
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

  private void deplacerLigneBoules(int nbBoules, Vector delta) {
    int periode = temps / nbBoules / ips;

    System.out.println(nbBoules + " boule(s) a deplacer");
    // la derniere boule est la premiere deplacee
    Coord coordDepla = new Coord(b1.x + (nbBoules - 1) * delta.x, b1.y + (nbBoules - 1) * delta.y);
    while (nbBoules > 0) {
      nbBoules--;


      Optional<Direction> maybeDir = Direction.toDirection(delta);
      Direction dir = maybeDir.orElseThrow(() -> new NoSuchElementException("No direction found!"));

      deplacerBouleDirection(dir, coordDepla, periode);

      coordDepla.x = coordDepla.x - delta.x;
      coordDepla.y = coordDepla.y - delta.y;
    }
  }

  public void sourisEntree(MouseEvent e) {
    RoundButton bouton = ((RoundButton) e.getSource());
    bouton.setMouseOver(true);
    if (state == State.SECOND_SELECTED_FOR_LATERAL) {
      Vector sensDeuxBoules = new Vector(b1, b2);
      Coord coordDepla = bouton.coord.add(sensDeuxBoules);
      if (!game.board.getSpace(coordDepla).button.isVisible()) {
        coordDepla = bouton.coord.add(sensDeuxBoules.getOpposite());
      }
      game.board.getSpace(coordDepla).button.setMouseOver(true);
      window.repaint();
    } else if (state == State.THIRD_SELECTED_FOR_LATERAL) {
      // TODO On verra pus tard...
    }
  }

  public void sourisSortie(MouseEvent e) {
    RoundButton bouton = ((RoundButton) e.getSource());
    bouton.setMouseOver(false);
    if (state == State.SECOND_SELECTED_FOR_LATERAL) {
      Vector sensDeuxBoules = new Vector(b1, b2);
      Coord coordDepla = bouton.coord.add(sensDeuxBoules);
      if (!game.board.getSpace(coordDepla).button.isVisible()) {
        coordDepla = bouton.coord.add(sensDeuxBoules.getOpposite());
      }
      game.board.getSpace(coordDepla).button.setMouseOver(false);
      window.repaint();
    } else if (state == State.THIRD_SELECTED_FOR_LATERAL) {
      // TODO On verra pus tard...
    }
  }

  public void setWindow(Window fenetre) {
    this.window = fenetre;
  }

  public void deplacerBouleDirection(Direction dir, Coord coordCase, int tempsPeriode)
      throws MovementException {

    System.out.println("deplacement de (" + coordCase.x + ";" + coordCase.y + ") en direction ("
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

    Marble bouleADeplacer = caseActuelle.marble;

    CoordDouble delta =
        new CoordDouble((double) (dir.vector.x) / ips, (double) (dir.vector.y) / ips);

    for (int i = 0; i < ips; i++) {
      long debut = System.currentTimeMillis();
      bouleADeplacer.coord.setCoord(CoordDouble.somme(bouleADeplacer.coord, delta));

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

    bouleADeplacer.coord.setCoord(coordCaseSuivante.y, coordCaseSuivante.x);
    game.board.getSpace(coordCaseSuivante).marble = caseActuelle.marble;
    caseActuelle.marble = null;

  }
}
