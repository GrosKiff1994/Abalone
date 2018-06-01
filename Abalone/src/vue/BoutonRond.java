package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;

import Utilitaire.Coord;
import controleur.Etat;

public class BoutonRond extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Coord coord;

	boolean varDist;

	public static final Color couleurTransparent = new Color(0, 0, 0, 0);
	public static final Color couleurMouseOver = new Color(153, 251, 111, 100);
	public static final Color couleurLigne = new Color(75, 181, 193, 40);
	public static final Color couleurLateralDeplac = new Color(255, 160, 173, 40);
	public static final Color couleurLateralSelec = new Color(60, 160, 173, 40);
	public static final Color couleurSelec = new Color(75, 181, 193, 200);
	public static final Color couleurBords = new Color(0, 0, 0);

	private Color couleurActuelle;
	private FenetreAbalone fenetre;

	Shape shape;

	/* flags du bouton */
	private boolean mouseOver;
	private boolean cliquableDroit;
	private boolean cliquableGauche;

	// private boolean selectionne;

	public void reset() {
		this.mouseOver = false;
		this.cliquableDroit = false;
		this.cliquableGauche = false;
		this.setVisible(false);
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public boolean isCliquableDroit() {
		return cliquableDroit && isVisible();
	}

	public boolean isCliquableGauche() {
		return cliquableGauche && isVisible();
	}

	public boolean isSelectionne() {
		return this.getCoord() == fenetre.getController().getB1() || this.getCoord() == fenetre.getController().getB2()
				|| this.getCoord() == fenetre.getController().getB3();
	}

	public BoutonRond(int rayon, int i, int j, final FenetreAbalone fenetre) {
		this.cliquableDroit = false;
		this.cliquableGauche = false;
		this.setVisible(false);
		this.mouseOver = false;
		this.fenetre = fenetre;

		coord = new Coord(j, i);
		int x = j * PanneauJeu.DIMBOULE + i * PanneauJeu.DIMBOULE / 2 - 4;
		int y = i * (PanneauJeu.DIMBOULE - PanneauJeu.DIMBOULE / 8) - 4;

		setLocation(x, y);
		setSize(rayon, rayon);
		setContentAreaFilled(false);

		class listenerSelection extends MouseAdapter {

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				fenetre.getController().sourisRelachee(e);
			}
		}

		class listenerPassageSouris extends MouseAdapter {

			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				fenetre.getController().sourisEntree(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				fenetre.getController().sourisSortie(e);
			}
		}

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
		Graphics2D graphics2D = (Graphics2D)g;
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (getModel().isArmed()) {
			couleurActuelle = couleurSelec;
		} else if (mouseOver) {
			couleurActuelle = couleurMouseOver;
		} else if (isSelectionne()) {
			couleurActuelle = couleurSelec;
		} else if (cliquableDroit) {
			couleurActuelle = couleurLateralSelec;
		} else if (cliquableGauche && fenetre.getController().getEtat() == Etat.SELECTIONLIGNE) {
			couleurActuelle = couleurLigne;
		} else if (cliquableGauche) {
			couleurActuelle = couleurLateralDeplac;
		} else {
			couleurActuelle = couleurTransparent;
		}

		g.setColor(couleurActuelle);
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
		this.mouseOver = b;
	}

	public void mettreCliquableDroit() {
		this.cliquableDroit = true;
		this.setVisible(true);
	}

	public void mettreCliquableGauche() {
		this.cliquableGauche = true;
		this.setVisible(true);
	}

	@Override
	public String toString() {
		return "BoutonRond [coord=" + coord + ", couleurActuelle=" + couleurActuelle + ", mouseOver=" + mouseOver
				+ ", cliquableDroit=" + cliquableDroit + ", cliquableGauche=" + cliquableGauche
				+ ", isCliquableDroit()=" + isCliquableDroit() + ", isCliquableGauche()=" + isCliquableGauche()
				+ ", isSelectionne()=" + isSelectionne() + "]";
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
		BoutonRond other = (BoutonRond) obj;
		if (coord == null) {
			if (other.coord != null)
				return false;
		} else if (!coord.equals(other.coord))
			return false;
		return true;
	}

}