package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;

import objects.Case;
import objects.Coord;
import objects.Direction;
import objects.Plateau;
import core.DeplacementException;
import core.Partie.Etat;

public class BoutonRond extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int coordI;
	private int coordJ;
	private static Etat etat;

	boolean varDist;

	public static final Color couleurMouseOver = new Color(153, 251, 111, 100);
	public static final Color couleurSelecTour = new Color(75, 181, 193, 40);
	public static final Color couleurSelec = new Color(75, 181, 193, 200);
	public static final Color couleurBords = new Color(0, 0, 0);

	private static Coord depart = new Coord(0, 0);

	private Color couleurActuelle;
	
	public BoutonRond(int rayon, int i, int j, final Plateau plateau, final PanneauJeu panneau) {
		coordI = i;
		coordJ = j;
		int x = j * PanneauJeu.DIMBOULE + i * PanneauJeu.DIMBOULE / 2;
		int y = i * (PanneauJeu.DIMBOULE - PanneauJeu.DIMBOULE / 8);

		setLocation(x, y);
		setSize(rayon, rayon);
		setContentAreaFilled(false);

		class listenerSelection extends MouseAdapter {

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {

				if (e.getButton() != MouseEvent.BUTTON1)
					return;

				System.out.println("clic sur ligne "
						+ ((BoutonRond) e.getSource()).getCoordI()
						+ ", colonne "
						+ ((BoutonRond) e.getSource()).getCoordJ());
				if (etat == Etat.SELECTION) {
					// afficher les cases entourant :
					
					// cacher
					for (int i = 0; i < Plateau.HEIGHT; i++) {
						for (int j = 0; j < Plateau.WIDTH; j++) {
							BoutonRond bout = panneau.getTabBouton()[i][j];
							if (bout != null) {
								bout.setVisible(false);
							}
						}
					}

					// afficher cercle
					Direction[] lesDir = Direction.values();
					for (Direction dir : lesDir) {
						int iDest = ((BoutonRond) e.getSource()).getCoordI()
								+ dir.getY();
						int jDest = ((BoutonRond) e.getSource()).getCoordJ()
								+ dir.getX();

						BoutonRond tmp = panneau.getTabBouton()[iDest][jDest];
						if (tmp != null) {
							tmp.couleurActuelle = couleurSelecTour;
							tmp.setVisible(true);
						}
					}
					
					// coordonnées départ
					depart.setY(((BoutonRond) e.getSource()).getCoordI());
					depart.setX(((BoutonRond) e.getSource()).getCoordJ());
					etat = Etat.DEPLACEMENT;
					System.out.println("Clic premier");
				} else {
					// deplacer la boule
					int deltaI = ((BoutonRond) e.getSource()).getCoordI()
							- depart.getY();
					int deltaJ = ((BoutonRond) e.getSource()).getCoordJ()
							- depart.getX();
					Direction tabDir[] = Direction.values();
					for (Direction dir : tabDir) {
						if (dir.getCoord().equals(new Coord(deltaJ, deltaI))) {
							try {
								Plateau.deplacerBouleDirection(dir, depart);
							} catch (DeplacementException e1) {
								e1.printStackTrace();
							}
							break;
						}
					}
					
					panneau.visibiliteBoutonVide();
					
					System.out.println("Clic second");
					etat = Etat.SELECTION;
				}

			}

		}
		;

		class listenerPassageSouris extends MouseAdapter {

			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				int i = ((BoutonRond) e.getSource()).getCoordI();
				int j = ((BoutonRond) e.getSource()).getCoordJ();
				Case caseCourante = plateau.getCase(i, j);
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

	public static void setEtat(Etat etat) {
		BoutonRond.etat = etat;
	}
}