package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import modele.Case;
import modele.Plateau;
import Utilitaire.CoordDouble;
import controleur.Etat;
import controleur.SuperController;

public class PanneauJeu extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int DIMBOULE = 60;

	protected FenetreAbalone fenetre;

	public void visibiliteBoutonVide() {
		for (int i = 0; i < Plateau.HEIGHT; i++) {
			for (int j = 0; j < Plateau.WIDTH; j++) {
				BoutonRond bout = fenetre.getModele().getPlateau().getCase(i, j).getBouton();
				if (bout != null) {
					if (fenetre.getModele().getPlateau().getCase(i, j).estOccupee()) {
						bout.setVisible(true);
					} else {
						bout.setVisible(false);
					}
					bout.setCouleurActuelle(null);
				}
			}
		}
	}

	public void cacherBoutons() {
		for (int i = 0; i < Plateau.HEIGHT; i++) {
			for (int j = 0; j < Plateau.WIDTH; j++) {
				BoutonRond bout = fenetre.getModele().getPlateau().getCase(i, j).getBouton();
				if (bout != null) {
					bout.setVisible(false);
				}
			}
		}
	}

	public PanneauJeu(FenetreAbalone fenetre, final SuperController controller) {
		this.fenetre = fenetre;

		class listenerAnnuler extends MouseAdapter {

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					visibiliteBoutonVide();
					controller.setEtat(Etat.NORMAL);
					System.out.println("Etat : SELECTION");
				}
			}

		}

		this.addMouseListener(new listenerAnnuler());

		this.setLayout(null);

		for (int i = 0; i < Plateau.HEIGHT; i++) {
			for (int j = 0; j < Plateau.WIDTH; j++) {
				Case caseCourante = fenetre.getModele().getPlateau().getCase(i, j);
				if (caseCourante != null) {
					BoutonRond tmpBouton = new BoutonRond(DIMBOULE, i, j, controller);
					this.add(tmpBouton);
					fenetre.getModele().getPlateau().getCase(i, j).setBouton(tmpBouton);
				}

			}
		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// parcours du tableau
		for (int i = 0; i < Plateau.HEIGHT; i++) {
			for (int j = 0; j < Plateau.WIDTH; j++) {
				Case caseCourante = fenetre.getModele().getPlateau().getCase(i, j);
				// case existe ?
				if (caseCourante != null) {
					// case occupee ?
					if (caseCourante.estOccupee()) {
						CoordDouble coord = caseCourante.getBoule().getCoord();

						g.setColor(Color.BLACK);
						g.fillOval((int) (coord.getX() * DIMBOULE + coord.getY() * DIMBOULE / 2 - 2),
								(int) (coord.getY() * (DIMBOULE - DIMBOULE / 8) - 2), DIMBOULE, DIMBOULE);

						// selon la couleur
						switch (caseCourante.getBoule().getCouleur()) {
						case NOIR:
							g.setColor(Color.DARK_GRAY);
							break;
						case BLANC:
							g.setColor(Color.WHITE);
							break;
						default:
						}
						g.fillOval((int) (coord.getX() * DIMBOULE + coord.getY() * DIMBOULE / 2 - 4),
								(int) (coord.getY() * (DIMBOULE - DIMBOULE / 8) - 4), DIMBOULE, DIMBOULE);
					} else {
						if (caseCourante.getBord()) {
							// g.setColor(Color.GRAY);
							// g.fillOval(j * DIMBOULE + i * DIMBOULE / 2 - 2, i
							// * (DIMBOULE - DIMBOULE / 8) - 2, DIMBOULE,
							// DIMBOULE);
						} else {
							g.setColor(Color.LIGHT_GRAY);
							g.fillOval(j * DIMBOULE + i * DIMBOULE / 2 - 2, i * (DIMBOULE - DIMBOULE / 8) - 2,
									DIMBOULE, DIMBOULE);
						}

					} // fin case occupee

				} // fin case existe

				/*
				 * else { g.setColor(Color.DARK_GRAY); }
				 */

			}
		}

	}
}
