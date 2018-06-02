package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import javax.swing.JButton;
import controller.State;
import utils.Coord;

public class RoundButton extends JButton {

  private static final long serialVersionUID = 1L;

  public Coord coord;

  boolean varDist;

  public static final Color couleurTransparent = new Color(0, 0, 0, 0);
  public static final Color couleurMouseOver = new Color(153, 251, 111, 100);
  public static final Color couleurLigne = new Color(75, 181, 193, 40);
  public static final Color couleurLateralDeplac = new Color(255, 160, 173, 40);
  public static final Color couleurLateralSelec = new Color(60, 160, 173, 40);
  public static final Color couleurSelec = new Color(75, 181, 193, 200);
  public static final Color couleurBords = new Color(0, 0, 0);

  public Color currentColor;
  public Window window;

  private Shape shape;

  /* flags du bouton */
  public boolean isMouseOver;
  private boolean isRightClickable;
  private boolean isLeftClickable;

  // private boolean selectionne;

  public void reset() {
    this.isMouseOver = false;
    this.isRightClickable = false;
    this.isLeftClickable = false;
    this.setVisible(false);
  }

  public boolean isRightClickable() {
    return isRightClickable && isVisible();
  }

  public boolean isLeftClickable() {
    return isLeftClickable && isVisible();
  }

  public boolean isSelectionne() {
    return window.controller.maybeB1.map(c -> c == this.getCoord()).orElse(false)
        || window.controller.maybeB2.map(c -> c == this.getCoord()).orElse(false)
        || window.controller.maybeB3.map(c -> c == this.getCoord()).orElse(false);
  }

  public RoundButton(int rayon, int i, int j, final Window fenetre) {
    this.isRightClickable = false;
    this.isLeftClickable = false;
    this.setVisible(false);
    this.isMouseOver = false;
    this.window = fenetre;

    coord = new Coord(j, i);
    int x = j * GamePanel.DIMBOULE + i * GamePanel.DIMBOULE / 2 - 4;
    int y = i * (GamePanel.DIMBOULE - GamePanel.DIMBOULE / 8) - 4;

    setLocation(x, y);
    setSize(rayon, rayon);
    setContentAreaFilled(false);

    class listenerSelection extends MouseAdapter {

      @Override
      public void mouseReleased(java.awt.event.MouseEvent e) {
        fenetre.controller.sourisRelachee(e);
      }
    }

    class listenerPassageSouris extends MouseAdapter {

      @Override
      public void mouseEntered(java.awt.event.MouseEvent e) {
        fenetre.controller.sourisEntree(e);
      }

      @Override
      public void mouseExited(MouseEvent e) {
        fenetre.controller.sourisSortie(e);
      }
    }

    this.addMouseListener(new listenerPassageSouris());

    this.addMouseListener(new listenerSelection());
  }

  public int getCoordI() {
    return coord.y;
  }

  public int getCoordJ() {
    return coord.x;
  }

  public Color getCouleurActuelle() {
    return currentColor;
  }

  public void setCouleurActuelle(Color couleurActuelle) {
    this.currentColor = couleurActuelle;
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graphics2D = (Graphics2D) g;
    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    if (getModel().isArmed()) {
      currentColor = couleurSelec;
    } else if (isMouseOver) {
      currentColor = couleurMouseOver;
    } else if (isSelectionne()) {
      currentColor = couleurSelec;
    } else if (isRightClickable) {
      currentColor = couleurLateralSelec;
    } else if (isLeftClickable && window.controller.state == State.FIRST_SELECTED_FOR_LINE) {
      currentColor = couleurLigne;
    } else if (isLeftClickable) {
      currentColor = couleurLateralDeplac;
    } else {
      currentColor = couleurTransparent;
    }

    g.setColor(currentColor);
    g.fillOval(0, 0, getSize().width, getSize().height);

  }

  protected void paintBorder(Graphics g) {
    g.setColor(couleurBords);
    g.drawOval(0, 0, getSize().width, getSize().height);
  }

  public boolean contains(int x, int y) {
    // If the button has changed size,
    // make a new shape object.
    if (shape == null || !shape.getBounds().equals(getBounds())) {
      shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
    }
    return shape.contains(x, y);
  }

  public Coord getCoord() {
    return coord;
  }

  public void setMouseOver(boolean b) {
    this.isMouseOver = b;
  }

  public void mettreCliquableDroit() {
    this.isRightClickable = true;
    this.setVisible(true);
  }

  public void mettreCliquableGauche() {
    this.isLeftClickable = true;
    this.setVisible(true);
  }

  @Override
  public String toString() {
    return "BoutonRond [coord=" + coord + ", couleurActuelle=" + currentColor + ", mouseOver="
        + isMouseOver + ", cliquableDroit=" + isRightClickable + ", cliquableGauche="
        + isLeftClickable + ", isCliquableDroit()=" + isRightClickable() + ", isCliquableGauche()="
        + isLeftClickable() + ", isSelectionne()=" + isSelectionne() + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((coord == null) ? 0 : coord.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    RoundButton other = (RoundButton) obj;
    if (coord == null) {
      if (other.coord != null)
        return false;
    } else if (!coord.equals(other.coord))
      return false;
    return true;
  }

}
