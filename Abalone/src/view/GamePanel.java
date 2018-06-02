package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import controller.GameController;
import controller.State;
import model.Ball;
import model.Space;
import utils.CoordDouble;

import static model.Color.BLACK;
import static model.Color.WHITE;

public class GamePanel extends JPanel {

  private static final long serialVersionUID = 1L;
  public static final int DIMBOULE = 60;

  private Window window;
  private BufferedImage background;
  private GameController controller;

  public void updateClickables() {
    for (int i = 0; i < controller.game.board.height; i++) {
      for (int j = 0; j < controller.game.board.width; j++) {
        RoundButton bout = controller.game.board.getSpace(i, j).button;
        if (bout != null) {
          if (controller.game.board.getSpace(i, j).hasBall()) {
            bout.setVisible(true);
          }
        }
      }
    }
  }

  public void hideButtons() {
    for (int i = 0; i < controller.game.board.height; i++) {
      for (int j = 0; j < controller.game.board.width; j++) {
        RoundButton button = window.game.board.getSpace(i, j).button;
        if (button != null) {
          button.reset();
        }
      }
    }
  }

  public GamePanel(final Window fenetre, final GameController controller) {
    this.window = fenetre;
    this.controller = controller;

    class listenerAnnuler extends MouseAdapter {

      @Override
      public void mouseReleased(java.awt.event.MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3 || e.getButton() == MouseEvent.BUTTON1) {
          hideButtons();
          updateClickables();
          fenetre.controller.cleanBalls();
          controller.state = State.NORMAL;
          System.out.println("Etat : NORMAL");
        }
      }

    }

    this.addMouseListener(new listenerAnnuler());

    this.setLayout(null);

    for (int i = 0; i < controller.game.board.height; i++) {
      for (int j = 0; j < controller.game.board.width; j++) {
        Space caseCourante = fenetre.game.board.getSpace(i, j);
        if (caseCourante != null) {
          RoundButton tmpBouton = new RoundButton(DIMBOULE, i, j, fenetre);
          this.add(tmpBouton);
          fenetre.game.board.getSpace(i, j).button = tmpBouton;
        }

      }
    }

  }

  public void drawBackground() {
    background = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics g = background.getGraphics();

    // parcours du tableau
    for (int i = 0; i < controller.game.board.height; i++) {
      for (int j = 0; j < controller.game.board.width; j++) {
        Space caseCourante = window.game.board.getSpace(i, j);
        // case existe ?
        if (caseCourante != null && !caseCourante.isBorder) {
          g.setColor(Color.LIGHT_GRAY);
          g.fillOval(j * DIMBOULE + i * DIMBOULE / 2 - 2, i * (DIMBOULE - DIMBOULE / 8) - 2,
              DIMBOULE, DIMBOULE);
        }

      }

    }
  }

  public void paintComponent(Graphics g) {

    Graphics2D graphics2D = (Graphics2D) g;
    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    super.paintComponent(g);
    g.drawImage(background, 0, 0, null);

    // parcours du tableau
    for (int i = 0; i < controller.game.board.height; i++) {
      for (int j = 0; j < controller.game.board.width; j++) {
        Space caseCourante = window.game.board.getSpace(i, j);
        // case existe ?
        if (caseCourante != null && caseCourante.hasBall()) {
          Ball ball = caseCourante.ball;
            CoordDouble coord = ball.coord;

          g.setColor(Color.BLACK);
          g.fillOval((int) (coord.x * DIMBOULE + coord.y * DIMBOULE / 2 - 2),
              (int) (coord.y * (DIMBOULE - DIMBOULE / 8) - 2), DIMBOULE, DIMBOULE);

            // selon la couleur
            switch (ball.color) {
              case BLACK:
                g.setColor(Color.DARK_GRAY);
                break;
              case WHITE:
                g.setColor(Color.WHITE);
                break;
              default:
            }
            g.fillOval((int) (coord.x * DIMBOULE + coord.y * DIMBOULE / 2 - 4),
                    (int) (coord.y * (DIMBOULE - DIMBOULE / 8) - 4), DIMBOULE, DIMBOULE);
          }
        }

      } // fin case existe

      /*
       * else { g.setColor(Color.DARK_GRAY); }
       */

    }
  }
