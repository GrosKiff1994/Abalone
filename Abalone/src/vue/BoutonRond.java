package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;

import controlleur.listenerPassageSouris;
import controlleur.listenerSelection;
import controlleur.Partie.Etat;

import modele.Coord;


public class BoutonRond extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Coord coord;
	private static Etat etat;

	boolean varDist;

	public static final Color couleurMouseOver = new Color(153, 251, 111, 100);
	public static final Color couleurSelecTour = new Color(75, 181, 193, 40);
	public static final Color couleurSelec = new Color(75, 181, 193, 200);
	public static final Color couleurBords = new Color(0, 0, 0);

	private Color couleurActuelle;

	private PanneauJeu panneau;

	public BoutonRond(int rayon, int i, int j, final PanneauJeu panneau) {
		this.panneau = panneau;
		coord = new Coord(j, i);
		int x = j * PanneauJeu.DIMBOULE + i * PanneauJeu.DIMBOULE / 2 - 4;
		int y = i * (PanneauJeu.DIMBOULE - PanneauJeu.DIMBOULE / 8) - 4;

		setLocation(x, y);
		setSize(rayon, rayon);
		setContentAreaFilled(false);

		this.addMouseListener(new listenerPassageSouris());

		this.addMouseListener(new listenerSelection());
	}

	public int getCoordI() {
		return coord.getY();
	}

	public int getCoordJ() {
		return coord.getX();
	}

	public Color getCouleurActuelle() {
		return couleurActuelle;
	}

	public void setCouleurActuelle(Color couleurActuelle) {
		this.couleurActuelle = couleurActuelle;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (getModel().isArmed()) {
			couleurActuelle = couleurSelec;
		}

		if (couleurActuelle != null) {
			g.setColor(couleurActuelle);
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

	public static void setEtat(Etat etat) {
		BoutonRond.etat = etat;
	}

	public Etat getEtat() {
		return BoutonRond.etat;
	}

	public PanneauJeu getPanneauJeu() {
		return this.panneau;
	}

	public Coord getCoord() {
		return coord;
	}
}