package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;

import objects.Case;
import objects.Direction;
import objects.Plateau;

public class BoutonRond extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int coordI;
	private int coordJ;

	boolean varDist;

	public static final Color couleurMouseOver = new Color(153, 251, 111, 150);
	public static final Color couleurSelecTour = new Color(75, 181, 193, 150);
	public static final Color couleurSelec = new Color(75, 181, 193, 200);
	public static final Color couleurBords = new Color(0, 0, 0);
	private Color couleurActuelle;

	public BoutonRond(int rayon, int i, int j) {

		coordI = i;
		coordJ = j;
		int x = j * PanneauJeu.DIMBOULE + i * PanneauJeu.DIMBOULE / 2;
		int y = i * (PanneauJeu.DIMBOULE - PanneauJeu.DIMBOULE / 8);

		setLocation(x, y);
		setSize(rayon, rayon);
		setContentAreaFilled(false);

		class listenerSelection extends MouseAdapter {

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {

				if (e.getButton() != MouseEvent.BUTTON1)
					return;

				System.out.println("clic sur ligne "
						+ ((BoutonRond) e.getSource()).getCoordI()
						+ ", colonne "
						+ ((BoutonRond) e.getSource()).getCoordJ());

				for (int i = 0; i < Plateau.HEIGHT; i++) {
					for (int j = 0; j < Plateau.WIDTH; j++) {
						BoutonRond bout = PanneauJeu.tableauBoutons[i][j];
						if (bout != null) {
							bout.setVisible(false);
						}
					}
				}

				Direction[] lesDir = Direction.values();
				for (Direction dir : lesDir) {
					int iDest = ((BoutonRond) e.getSource()).getCoordI()
							+ dir.getY();
					int jDest = ((BoutonRond) e.getSource()).getCoordJ()
							+ dir.getX();

					BoutonRond tmp = PanneauJeu.tableauBoutons[iDest][jDest];
					if (tmp != null) {
						tmp.couleurActuelle = couleurSelecTour;
						tmp.setVisible(true);
					}
				}
			}

		}
		;

		class listenerPassageSouris extends MouseAdapter {

			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				int i = ((BoutonRond) e.getSource()).getCoordI();
				int j = ((BoutonRond) e.getSource()).getCoordJ();
				Case caseCourante = PanneauJeu.getPlateau().getCase(i, j);
				if (caseCourante.estOccupee()) {
					if (((BoutonRond) e.getSource()).couleurActuelle == null) {
						((BoutonRond) e.getSource()).couleurActuelle = couleurMouseOver;
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (((BoutonRond) e.getSource()).couleurActuelle == couleurMouseOver) {
					((BoutonRond) e.getSource()).couleurActuelle = null;
				}
			}
		}

		this.addMouseListener(new listenerPassageSouris());

		this.addMouseListener(new listenerSelection());
	}

	public int getCoordI() {
		return coordI;
	}

	public int getCoordJ() {
		return coordJ;
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
}