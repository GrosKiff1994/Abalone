package controlleur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import modele.Case;
import vue.BoutonRond;
import vue.PanneauJeu;
import controlleur.Partie.Etat;

public class listenerPassageSouris extends MouseAdapter {

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		BoutonRond bouton = ((BoutonRond) e.getSource());
		PanneauJeu panneau = bouton.getPanneauJeu();

		Case caseCourante = panneau.getPlateau().getCase(bouton.getCoord());
		if (caseCourante.estOccupee()) {
			if (bouton.getCouleurActuelle() == null) {
				bouton.setCouleurActuelle(BoutonRond.couleurMouseOver);
			}
		}
		if (bouton.getCouleurActuelle() == BoutonRond.couleurSelecTour) {
			bouton.setCouleurActuelle(BoutonRond.couleurMouseOver);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		BoutonRond bouton = ((BoutonRond) e.getSource());
		Etat etat = bouton.getEtat();

		if (bouton.getCouleurActuelle() == BoutonRond.couleurMouseOver) {
			if (etat == Etat.SELECTION) {
				bouton.setCouleurActuelle(null);
			} else if (etat == Etat.DEPLACEMENTLIGNE || etat == Etat.DEPLACEMENTLATERAL2 || etat == Etat.SELECTION2) {
				bouton.setCouleurActuelle(BoutonRond.couleurSelecTour);
			}

		}
	}
}
