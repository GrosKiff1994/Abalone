package actions;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import objects.Case;
import core.Partie.Etat;
import display.BoutonRond;
import display.PanneauJeu;

public class listenerPassageSouris extends MouseAdapter {

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		BoutonRond leBouton = ((BoutonRond) e.getSource());
		PanneauJeu lePanneau = leBouton.getPanneauJeu();

		int i = leBouton.getCoordI();
		int j = leBouton.getCoordJ();
		Case caseCourante = lePanneau.getPlateau().getCase(i, j);
		if (caseCourante.estOccupee()) {
			if (leBouton.getCouleurActuelle() == null) {
				leBouton.setCouleurActuelle(BoutonRond.couleurMouseOver);
			}
		}
		if (leBouton.getCouleurActuelle() == BoutonRond.couleurSelecTour) {
			leBouton.setCouleurActuelle(BoutonRond.couleurMouseOver);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		BoutonRond leBouton = ((BoutonRond) e.getSource());

		if (leBouton.getCouleurActuelle() == BoutonRond.couleurMouseOver) {
			if (leBouton.getEtat() == Etat.SELECTION) {
				leBouton.setCouleurActuelle(null);
			} else if (leBouton.getEtat() == Etat.DEPLACEMENTLIGNE || leBouton.getEtat() == Etat.DEPLACEMENTLATERAL) {
				leBouton.setCouleurActuelle(BoutonRond.couleurSelecTour);
			}

		}
	}
}
