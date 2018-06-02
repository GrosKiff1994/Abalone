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
import model.Space;
import utils.CoordDouble;

public class GamePanel extends JPanel {

  private static final long serialVersionUID = 1L;
  public static final int DIMBOULE = 60;

  private Window fenetre;
  private BufferedImage fond;
  private GameController controller;

  public void updateClickables() {
    for (int i = 0; i < controller.modele.getPlateau().height; i++) {
      for (int j = 0; j < controller.modele.getPlateau().width; j++) {
        RoundButton bout = controller.modele.getPlateau().getSpace(i, j).bouton;
        if (bout != null) {
          if (controller.modele.getPlateau().getSpace(i, j).estOccupee()) {
            bout.setVisible(true);
          }
        }
      }
    }
  }

  public void hideButtons() {
    for (int i = 0; i < controller.modele.getPlateau().height; i++) {
      for (int j = 0; j < controller.modele.getPlateau().width; j++) {
        RoundButton bout = fenetre.getModele().getPlateau().getSpace(i, j).bouton;
        if (bout != null) {
          bout.reset();
        }
      }
    }
  }

  public GamePanel(final Window fenetre, final GameController controller) {
    this.fenetre = fenetre;
    this.controller = controller;

    class listenerAnnuler extends MouseAdapter {

      @Override
      public void mouseReleased(java.awt.event.MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3 || e.getButton() == MouseEvent.BUTTON1) {
          hideButtons();
          updateClickables();
          fenetre.getController().cleanBalls();
          controller.setState(State.NORMAL);
          System.out.println("Etat : NORMAL");
        }
      }

    }

    this.addMouseListener(new listenerAnnuler());

    this.setLayout(null);

    for (int i = 0; i < controller.modele.getPlateau().height; i++) {
      for (int j = 0; j < controller.modele.getPlateau().width; j++) {
        Space caseCourante = fenetre.getModele().getPlateau().getSpace(i, j);
        if (caseCourante != null) {
          RoundButton tmpBouton = new RoundButton(DIMBOULE, i, j, fenetre);
          this.add(tmpBouton);
          fenetre.getModele().getPlateau().getSpace(i, j).bouton = tmpBouton;
        }

      }
    }

  }

  public void drawBackground() {
    fond = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics g = fond.getGraphics();

    // parcours du tableau
    for (int i = 0; i < controller.modele.getPlateau().height; i++) {
      for (int j = 0; j < controller.modele.getPlateau().width; j++) {
        Space caseCourante = fenetre.getModele().getPlateau().getSpace(i, j);
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
    g.drawImage(fond, 0, 0, null);

    // parcours du tableau
    for (int i = 0; i < controller.modele.getPlateau().height; i++) {
      for (int j = 0; j < controller.modele.getPlateau().width; j++) {
        Space caseCourante = fenetre.getModele().getPlateau().getSpace(i, j);
        // case existe ?
        if (caseCourante != null && caseCourante.estOccupee()) {
          CoordDouble coord = caseCourante.ball.coord;

          g.setColor(Color.BLACK);
          g.fillOval((int) (coord.getX() * DIMBOULE + coord.getY() * DIMBOULE / 2 - 2),
              (int) (coord.getY() * (DIMBOULE - DIMBOULE / 8) - 2), DIMBOULE, DIMBOULE);

          // selon la couleur
          switch (caseCourante.ball.color) {
            case BLACK:
              g.setColor(Color.DARK_GRAY);
              break;
            case WHITE:
              g.setColor(Color.WHITE);
              break;
            default:
          }
          g.fillOval((int) (coord.getX() * DIMBOULE + coord.getY() * DIMBOULE / 2 - 4),
              (int) (coord.getY() * (DIMBOULE - DIMBOULE / 8) - 4), DIMBOULE, DIMBOULE);
        }

      } // fin case existe

      /*
       * else { g.setColor(Color.DARK_GRAY); }
       */

    }
  }
}
