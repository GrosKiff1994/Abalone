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

  public static final Color transparentColor = new Color(0, 0, 0, 0);
  public static final Color mouseOverColor = new Color(153, 251, 111, 100);
  public static final Color lineColor = new Color(75, 181, 193, 40);
  public static final Color lateralMoveColor = new Color(255, 160, 173, 40);
  public static final Color lateralSelectionColor = new Color(60, 160, 173, 40);
  public static final Color selectionColor = new Color(75, 181, 193, 200);
  public static final Color borderColor = new Color(0, 0, 0);

  public Color currentColor;
  public Window window;

  private Shape shape;

  /* flags du bouton */
  public boolean isMouseOver;
  private boolean isRightClickable;
  private boolean isLeftClickable;

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
    return window.controller.b1 == this.coord || window.controller.b2 == this.coord
        || window.controller.b3 == this.coord;
  }

  public RoundButton(int rayon, int i, int j, final Window fenetre) {
    this.isRightClickable = false;
    this.isLeftClickable = false;
    this.setVisible(false);
    this.isMouseOver = false;
    this.window = fenetre;

    coord = new Coord(j, i);
    int x = j * GamePanel.MARBLE_SIZE + i * GamePanel.MARBLE_SIZE / 2 - 4;
    int y = i * (GamePanel.MARBLE_SIZE - GamePanel.MARBLE_SIZE / 8) - 4;

    setLocation(x, y);
    setSize(rayon, rayon);
    setContentAreaFilled(false);

    class SelectionListener extends MouseAdapter {

      @Override
      public void mouseReleased(java.awt.event.MouseEvent e) {
        fenetre.controller.onMouseClickUp(e);
      }
    }

    class MouseMovementListener extends MouseAdapter {

      @Override
      public void mouseEntered(java.awt.event.MouseEvent e) {
        fenetre.controller.onMouseOver(e);
      }

      @Override
      public void mouseExited(MouseEvent e) {
        fenetre.controller.onMouseOut(e);
      }
    }

    this.addMouseListener(new MouseMovementListener());
    this.addMouseListener(new SelectionListener());

  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graphics2D = (Graphics2D) g;
    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    if (getModel().isArmed()) {
      currentColor = selectionColor;
    } else if (isMouseOver) {
      currentColor = mouseOverColor;
    } else if (isSelectionne()) {
      currentColor = selectionColor;
    } else if (isRightClickable) {
      currentColor = lateralSelectionColor;
    } else if (isLeftClickable && window.controller.state == State.FIRST_SELECTED_FOR_LINE) {
      currentColor = lineColor;
    } else if (isLeftClickable) {
      currentColor = lateralMoveColor;
    } else {
      currentColor = transparentColor;
    }

    g.setColor(currentColor);
    g.fillOval(0, 0, getSize().width, getSize().height);

  }

  protected void paintBorder(Graphics g) {
    g.setColor(borderColor);
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

  public void makeRightClickable() {
    this.isRightClickable = true;
    this.setVisible(true);
  }

  public void makeLeftClickable() {
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
