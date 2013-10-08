package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;

public class BoutonRond extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int coordI;
	private int coordJ;

	boolean varDist;

	private static final Color couleurSelec = new Color(75, 181, 193, 200);
	private static final Color couleurBords = new Color(0, 0, 0);

	public BoutonRond(int rayon, int i, int j) {

		coordI = i;
		coordJ = j;
		int x = j * PanneauJeu.DIMBOULE + i * PanneauJeu.DIMBOULE / 2;
		int y = i * (PanneauJeu.DIMBOULE - PanneauJeu.DIMBOULE / 8);

		setLocation(x, y);
		setSize(rayon, rayon);
		setContentAreaFilled(false);

		ActionListener monListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("clic sur ligne "
						+ ((BoutonRond) e.getSource()).getCoordI()
						+ ", colonne "
						+ ((BoutonRond) e.getSource()).getCoordJ());
				((BoutonRond) e.getSource()).envoyerSignal();
				// ((BoutonRond) e.getSource()).setVisible(false);
			}

		};

		this.addActionListener(monListener);
	}

	protected void envoyerSignal() {

	}

	public int getCoordI() {
		return coordI;
	}

	public int getCoordJ() {
		return coordJ;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (getModel().isArmed()) {
			g.setColor(couleurSelec);
			g.fillOval(0, 0, getSize().width, getSize().height);
		}

	}

	protected void paintBorder(Graphics g) {
		g.setColor(couleurBords);
		g.drawOval(0, 0, getSize().width, getSize().height);
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