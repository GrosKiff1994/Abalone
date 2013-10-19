package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import objects.Case;
import objects.Plateau;
import core.Partie;
import core.Partie.Etat;

public class PanneauJeu extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int DIMBOULE = 60;

	protected Plateau plateau;
	private BoutonRond tableauBoutons[][];

	public Partie laPartie;

	public void visibiliteBoutonVide() {
		for (int i = 0; i < Plateau.HEIGHT; i++) {
			for (int j = 0; j < Plateau.WIDTH; j++) {
				BoutonRond bout = tableauBoutons[i][j];
				if (bout != null) {
					if (plateau.getCase(i, j).estOccupee()) {
						bout.setVisible(true);
					} else {
						bout.setVisible(false);
					}
					bout.setCouleurActuelle(null);
				}
			}
		}
	}

	public PanneauJeu(Plateau plateau) {
		this.plateau = plateau;

		class listenerAnnuler extends MouseAdapter {

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					visibiliteBoutonVide();
					System.out.println("clic droit");
					System.out.println("etat : selection");
					BoutonRond.setEtat(Etat.SELECTION);
				}
			}

		}

		this.addMouseListener(new listenerAnnuler());

		tableauBoutons = new BoutonRond[Plateau.HEIGHT][Plateau.WIDTH];
		this.setPlateau(plateau);
		this.setLayout(null);

		for (int i = 0; i < Plateau.HEIGHT; i++) {
			for (int j = 0; j < Plateau.WIDTH; j++) {
				Case caseCourante = plateau.getCase(i, j);
				if (caseCourante != null) {
					BoutonRond tmpBouton = new BoutonRond(DIMBOULE, i, j, this);
					this.add(tmpBouton);
					tableauBoutons[i][j] = tmpBouton;
				}

			}
		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// parcours du tableau
		for (int i = 0; i < Plateau.HEIGHT; i++) {
			for (int j = 0; j < Plateau.WIDTH; j++) {
				Case caseCourante = plateau.getCase(i, j);
				// case existe ?
				if (caseCourante != null) {
					// case occupee ?
					if (caseCourante.estOccupee()) {

						g.setColor(Color.BLACK);
						g.fillOval(j * DIMBOULE + i * DIMBOULE / 2 - 2, i
								* (DIMBOULE - DIMBOULE / 8) - 2, DIMBOULE,
								DIMBOULE);

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
						g.fillOval(j * DIMBOULE + i * DIMBOULE / 2 - 4, i
								* (DIMBOULE - DIMBOULE / 8) - 4, DIMBOULE,
								DIMBOULE);
					} else {
						if (caseCourante.getBord()) {
							g.setColor(Color.GRAY);
						} else {
							g.setColor(Color.LIGHT_GRAY);
						}
						g.fillOval(j * DIMBOULE + i * DIMBOULE / 2 - 2, i
								* (DIMBOULE - DIMBOULE / 8) - 2, DIMBOULE,
								DIMBOULE);
					} // fin case occupee

				}
			}
		}

	}

	public Plateau getPlateau() {
		return plateau;
	}

	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}

	public BoutonRond[][] getTabBouton() {
		return tableauBoutons;
	}
}
