package abalone;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;

public class BoutonRond extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color couleur;

	public BoutonRond(int rayon, int x, int y) {

		setLocation(x, y);
		setSize(rayon, rayon);
		setContentAreaFilled(false);
	}

	public void setColor(Color c) {
		couleur = c;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (getModel().isArmed()) {
			g.setColor(Color.RED);
		} else {
			g.setColor(couleur);
		}
		g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

	}

	protected void paintBorder(Graphics g) {

	}

	Shape shape;

	public boolean contains(int x, int y) {
		// If the button has changed size,
		// make a new shape object.
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		}
		return shape.contains(x, y);
	}
}