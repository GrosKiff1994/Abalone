package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import Utilitaire.CoordDouble;
import controleur.Etat;
import controleur.SuperController;
import modele.Case;
import modele.Plateau;

public class PanneauJeu extends JPanel {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public static final int DIMBOULE = 60;

  private FenetreAbalone fenetre;
  private BufferedImage fond;

  public void visibiliteBoutonVide() {
    for (int i = 0; i < Plateau.HEIGHT; i++) {
      for (int j = 0; j < Plateau.WIDTH; j++) {
        BoutonRond bout = fenetre.getModele().getPlateau().getCase(i, j).getBouton();
        if (bout != null) {
          if (fenetre.getModele().getPlateau().getCase(i, j).estOccupee()) {
            bout.setVisible(true);
          }
        }
      }
    }
  }

  public void cacherBoutons() {
    for (int i = 0; i < Plateau.HEIGHT; i++) {
      for (int j = 0; j < Plateau.WIDTH; j++) {
        BoutonRond bout = fenetre.getModele().getPlateau().getCase(i, j).getBouton();
        if (bout != null) {
          bout.reset();
        }
      }
    }
  }

  public PanneauJeu(final FenetreAbalone fenetre, final SuperController controller) {
    this.fenetre = fenetre;

    class listenerAnnuler extends MouseAdapter {

      @Override
      public void mouseReleased(java.awt.event.MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3 || e.getButton() == MouseEvent.BUTTON1) {
          cacherBoutons();
          visibiliteBoutonVide();
          fenetre.getController().viderB1B2B3();
          controller.setEtat(Etat.NORMAL);
          System.out.println("Etat : NORMAL");
        }
      }

    }

    this.addMouseListener(new listenerAnnuler());

    this.setLayout(null);

    for (int i = 0; i < Plateau.HEIGHT; i++) {
      for (int j = 0; j < Plateau.WIDTH; j++) {
        Case caseCourante = fenetre.getModele().getPlateau().getCase(i, j);
        if (caseCourante != null) {
          BoutonRond tmpBouton = new BoutonRond(DIMBOULE, i, j, fenetre);
          this.add(tmpBouton);
          fenetre.getModele().getPlateau().getCase(i, j).setBouton(tmpBouton);
        }

      }
    }

  }

  public void genererFond() {
    fond = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics g = fond.getGraphics();

    // parcours du tableau
    for (int i = 0; i < Plateau.HEIGHT; i++) {
      for (int j = 0; j < Plateau.WIDTH; j++) {
        Case caseCourante = fenetre.getModele().getPlateau().getCase(i, j);
        // case existe ?
        if (caseCourante != null && !caseCourante.getBord()) {
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
    for (int i = 0; i < Plateau.HEIGHT; i++) {
      for (int j = 0; j < Plateau.WIDTH; j++) {
        Case caseCourante = fenetre.getModele().getPlateau().getCase(i, j);
        // case existe ?
        if (caseCourante != null && caseCourante.estOccupee()) {
          CoordDouble coord = caseCourante.getBoule().getCoord();

          g.setColor(Color.BLACK);
          g.fillOval((int) (coord.getX() * DIMBOULE + coord.getY() * DIMBOULE / 2 - 2),
              (int) (coord.getY() * (DIMBOULE - DIMBOULE / 8) - 2), DIMBOULE, DIMBOULE);

          // selon la couleur
          switch (caseCourante.getBoule().getCouleur()) {
            case NOIR:
              g.setColor(Color.DARK_GRAY);
              break;
            case BLANC:
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
