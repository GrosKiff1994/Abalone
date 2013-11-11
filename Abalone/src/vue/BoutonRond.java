package vue;

import java.awt.Color;
import java.awt.Graphics;
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
	private boolean selectionne;

	public boolean isMouseOver() {
		return mouseOver;
	}

	public boolean isCliquableDroit() {
		return cliquableDroit;
	}

	public boolean isCliquableGauche() {
		return cliquableGauche;
	}

	public boolean isSelectionne() {
		return selectionne;
	}

	public BoutonRond(int rayon, int i, int j, final FenetreAbalone fenetre) {
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

		if (getModel().isArmed()) {
			couleurActuelle = couleurSelec;
		} else if (mouseOver) {
			couleurActuelle = couleurMouseOver;
		} else if (selectionne) {
			couleurActuelle = couleurSelec;
		} else if (cliquableDroit) {
			couleurActuelle = couleurLateralSelec;
		} else if (cliquableGauche && fenetre.getController().getEtat() == Etat.SELECTIONLIGNE) {
			couleurActuelle = couleurLigne;
		} else if (cliquableGauche) {
			couleurActuelle = couleurLateralDeplac;
		} else if (isVisible() && fenetre.getController().getEtat() != Etat.NORMAL) {
			couleurActuelle = couleurLigne;
		} else {
			couleurActuelle = couleurTransparent;
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

	public void setCliquableDroit(boolean b) {
		this.cliquableDroit = b;
	}

	public void setCliquableGauche(boolean b) {
		this.cliquableGauche = b;
	}

	public void setSelectionne(boolean b) {
		this.selectionne = b;
	}
}