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
import objects.Couleur;
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

	public BoutonRond(int rayon, int i, int j, final PanneauJeu panneau) {
		coordI = i;
		coordJ = j;
		int x = j * PanneauJeu.DIMBOULE + i * PanneauJeu.DIMBOULE / 2 - 4;
		int y = i * (PanneauJeu.DIMBOULE - PanneauJeu.DIMBOULE / 8) - 4;

		setLocation(x, y);
		setSize(rayon, rayon);
		setContentAreaFilled(false);

		class listenerSelection extends MouseAdapter {

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {

				if (e.getButton() != MouseEvent.BUTTON1)
					return;

				BoutonRond leBouton = ((BoutonRond) e.getSource());

				System.out.println("clic : ligne " + leBouton.getCoordI()
						+ ", colonne " + leBouton.getCoordJ());
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
						if (tmp != null
								&& !panneau.getPlateau().getCase(iDest, jDest)
										.getBord()) {
							tmp.couleurActuelle = couleurSelecTour;
							tmp.setVisible(true);
						}
					}

					// coordonnees depart
					depart.setY(((BoutonRond) e.getSource()).getCoordI());
					depart.setX(((BoutonRond) e.getSource()).getCoordJ());
					etat = Etat.DEPLACEMENT;
					System.out.println("Clic premier");
				} else {
					// deplacer la boule

					boolean deplacementPossible = true;
					Coord arrive = new Coord(leBouton.getCoordJ(),
							leBouton.getCoordI());
					Coord delta = new Coord(arrive.getX() - depart.getX(),
							arrive.getY() - depart.getY());
					int coeffDelta = 0;
					Direction tabDir[] = Direction.values();
					for (Direction dir : tabDir) {
						if (dir.getCoord().equals(delta)) {
							// compte le nombre de boules a deplacer
							int nbCouleurActuelle = 0;
							int nbCouleurOpposee = 0;

							Couleur couleurDep = panneau.getPlateau()
									.getCase(depart.getY(), depart.getX())
									.getBoule().getCouleur();

							while (panneau.getPlateau().getCase(
									(coeffDelta - 1) * delta.getY()
											+ arrive.getY(),
									(coeffDelta - 1) * delta.getX()
											+ arrive.getX()) != null
									&& panneau
											.getPlateau()
											.getCase(
													(coeffDelta - 1)
															* delta.getY()
															+ arrive.getY(),
													(coeffDelta - 1)
															* delta.getX()
															+ arrive.getX())
											.estOccupee()
									&& panneau
											.getPlateau()
											.getCase(
													(coeffDelta - 1)
															* delta.getY()
															+ arrive.getY(),
													(coeffDelta - 1)
															* delta.getX()
															+ arrive.getX())
											.getBoule().getCouleur() == couleurDep
									&& nbCouleurActuelle < 4) {

								nbCouleurActuelle++;
								coeffDelta++;
							}

							while (panneau.getPlateau().getCase(
									(coeffDelta - 1) * delta.getY()
											+ arrive.getY(),
									(coeffDelta - 1) * delta.getX()
											+ arrive.getX()) != null
									&& panneau
											.getPlateau()
											.getCase(
													(coeffDelta - 1)
															* delta.getY()
															+ arrive.getY(),
													(coeffDelta - 1)
															* delta.getX()
															+ arrive.getX())
											.estOccupee()
									&& panneau
											.getPlateau()
											.getCase(
													(coeffDelta - 1)
															* delta.getY()
															+ arrive.getY(),
													(coeffDelta - 1)
															* delta.getX()
															+ arrive.getX())
											.getBoule().getCouleur() != couleurDep
									&& nbCouleurOpposee < 3) {

								nbCouleurOpposee++;
								coeffDelta++;
							}

							while (panneau.getPlateau().getCase(
									(coeffDelta - 1) * delta.getY()
											+ arrive.getY(),
									(coeffDelta - 1) * delta.getX()
											+ arrive.getX()) != null
									&& panneau
											.getPlateau()
											.getCase(
													(coeffDelta - 1)
															* delta.getY()
															+ arrive.getY(),
													(coeffDelta - 1)
															* delta.getX()
															+ arrive.getX())
											.estOccupee()
									&& panneau
											.getPlateau()
											.getCase(
													(coeffDelta - 1)
															* delta.getY()
															+ arrive.getY(),
													(coeffDelta - 1)
															* delta.getX()
															+ arrive.getX())
											.getBoule().getCouleur() == couleurDep
									&& nbCouleurActuelle < 4) {

								nbCouleurActuelle++;
								coeffDelta++;
								deplacementPossible = false;
							}

							if (nbCouleurOpposee < nbCouleurActuelle
									&& nbCouleurActuelle < 4
									&& deplacementPossible) {

								System.out.println(coeffDelta
										+ " boule(s) a deplacer");
								// la derniere boule est la premiere deplacee
								depart.setX(depart.getX() + (coeffDelta - 1)
										* delta.getX());
								depart.setY(depart.getY() + (coeffDelta - 1)
										* delta.getY());
								while (coeffDelta > 0) {
									try {
										coeffDelta--;
										panneau.getPlateau()
												.deplacerBouleDirection(dir,
														depart);
										depart.setX(depart.getX()
												- delta.getX());
										depart.setY(depart.getY()
												- delta.getY());
									} catch (DeplacementException e1) {
										e1.printStackTrace();
									}
								}

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
				BoutonRond leBouton = ((BoutonRond) e.getSource());
				Case caseCourante = panneau.getPlateau().getCase(leBouton.getCoordI(), leBouton.getCoordJ());
				if (caseCourante.estOccupee()) {
					if (((BoutonRond) e.getSource()).couleurActuelle == null) {
						((BoutonRond) e.getSource()).couleurActuelle = couleurMouseOver;
					}
				}
				if (leBouton.couleurActuelle == couleurSelecTour) {
					leBouton.couleurActuelle = couleurMouseOver;
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				BoutonRond leBouton = ((BoutonRond) e.getSource());
				if (leBouton.couleurActuelle == couleurMouseOver) {
					if (etat == Etat.SELECTION) {
						leBouton.couleurActuelle = null;
					} else if (etat == Etat.DEPLACEMENT) {
						leBouton.couleurActuelle = couleurSelecTour;
					}

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