package actions;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import objects.Coord;
import objects.Couleur;
import objects.Direction;
import objects.Joueur;
import objects.Plateau;
import core.DeplacementException;
import core.Partie;
import core.Partie.Etat;
import display.BoutonRond;
import display.PanneauJeu;

public class listenerSelection extends MouseAdapter {

	private static Coord depart = new Coord(0, 0);

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {

		if (e.getButton() != MouseEvent.BUTTON1)
			return;

		BoutonRond leBouton = ((BoutonRond) e.getSource());
		PanneauJeu lePanneau = leBouton.getPanneauJeu();

		System.out.println("clic : ligne " + leBouton.getCoordI()
				+ ", colonne " + leBouton.getCoordJ());
		if (leBouton.getEtat() == Etat.SELECTION) {
			// afficher les cases entourant :

			// cacher
			for (int i = 0; i < Plateau.HEIGHT; i++) {
				for (int j = 0; j < Plateau.WIDTH; j++) {
					BoutonRond bout = lePanneau.getTabBouton()[i][j];
					if (bout != null) {
						bout.setVisible(false);
					}
				}
			}

			// afficher cercle
			Direction[] lesDir = Direction.values();
			for (Direction dir : lesDir) {
				int iDest = leBouton.getCoordI() + dir.getY();
				int jDest = leBouton.getCoordJ() + dir.getX();

				BoutonRond tmp = lePanneau.getTabBouton()[iDest][jDest];
				if (tmp != null
						&& !lePanneau.getPlateau().getCase(iDest, jDest)
								.getBord()) {
					tmp.setCouleurActuelle(BoutonRond.couleurSelecTour);
					tmp.setVisible(true);
				}
			}

			// coordonnees depart
			depart.setY(leBouton.getCoordI());
			depart.setX(leBouton.getCoordJ());
			BoutonRond.setEtat(Etat.DEPLACEMENT);
			System.out.println("etat : deplacement");
		} else {
			// deplacer la boule

			boolean deplacementPossible = true;
			Coord arrive = new Coord(leBouton.getCoordJ(), leBouton.getCoordI());
			Coord delta = new Coord(arrive.getX() - depart.getX(),
					arrive.getY() - depart.getY());
			int coeffDelta = 0;
			Direction tabDir[] = Direction.values();
			for (Direction dir : tabDir) {
				if (dir.getCoord().equals(delta)) {
					// compte le nombre de boules a deplacer
					int nbCouleurActuelle = 0;
					int nbCouleurOpposee = 0;

					Couleur couleurDep = lePanneau.getPlateau()
							.getCase(depart.getY(), depart.getX()).getBoule()
							.getCouleur();

					while (lePanneau.getPlateau().getCase(
							(coeffDelta - 1) * delta.getY() + arrive.getY(),
							(coeffDelta - 1) * delta.getX() + arrive.getX()) != null
							&& lePanneau
									.getPlateau()
									.getCase(
											(coeffDelta - 1) * delta.getY()
													+ arrive.getY(),
											(coeffDelta - 1) * delta.getX()
													+ arrive.getX())
									.estOccupee()
							&& lePanneau
									.getPlateau()
									.getCase(
											(coeffDelta - 1) * delta.getY()
													+ arrive.getY(),
											(coeffDelta - 1) * delta.getX()
													+ arrive.getX()).getBoule()
									.getCouleur() == couleurDep
							&& nbCouleurActuelle < 4) {

						nbCouleurActuelle++;
						coeffDelta++;
					}

					while (lePanneau.getPlateau().getCase(
							(coeffDelta - 1) * delta.getY() + arrive.getY(),
							(coeffDelta - 1) * delta.getX() + arrive.getX()) != null
							&& lePanneau
									.getPlateau()
									.getCase(
											(coeffDelta - 1) * delta.getY()
													+ arrive.getY(),
											(coeffDelta - 1) * delta.getX()
													+ arrive.getX())
									.estOccupee()
							&& lePanneau
									.getPlateau()
									.getCase(
											(coeffDelta - 1) * delta.getY()
													+ arrive.getY(),
											(coeffDelta - 1) * delta.getX()
													+ arrive.getX()).getBoule()
									.getCouleur() != couleurDep
							&& nbCouleurOpposee < 3) {

						nbCouleurOpposee++;
						coeffDelta++;
					}

					while (lePanneau.getPlateau().getCase(
							(coeffDelta - 1) * delta.getY() + arrive.getY(),
							(coeffDelta - 1) * delta.getX() + arrive.getX()) != null
							&& lePanneau
									.getPlateau()
									.getCase(
											(coeffDelta - 1) * delta.getY()
													+ arrive.getY(),
											(coeffDelta - 1) * delta.getX()
													+ arrive.getX())
									.estOccupee()
							&& lePanneau
									.getPlateau()
									.getCase(
											(coeffDelta - 1) * delta.getY()
													+ arrive.getY(),
											(coeffDelta - 1) * delta.getX()
													+ arrive.getX()).getBoule()
									.getCouleur() == couleurDep
							&& nbCouleurActuelle < 4) {

						nbCouleurActuelle++;
						coeffDelta++;
						deplacementPossible = false;
					}

					if (nbCouleurOpposee < nbCouleurActuelle
							&& nbCouleurActuelle < 4 && deplacementPossible) {

						System.out.println(coeffDelta + " boule(s) a deplacer");
						// la derniere boule est la premiere deplacee
						depart.setX(depart.getX() + (coeffDelta - 1)
								* delta.getX());
						depart.setY(depart.getY() + (coeffDelta - 1)
								* delta.getY());
						while (coeffDelta > 0) {
							try {
								coeffDelta--;
								lePanneau.getPlateau().deplacerBouleDirection(
										dir, depart);
								depart.setX(depart.getX() - delta.getX());
								depart.setY(depart.getY() - delta.getY());
							} catch (DeplacementException e1) {
								e1.printStackTrace();
							}
						}

					}
					break;
				}
			}

			// verification boule hors jeu
			for (int i = 0; i < Plateau.HEIGHT; i++) {
				for (int j = 0; j < Plateau.WIDTH; j++) {
					if (lePanneau.getPlateau().getCase(i, j).getBord()
							&& lePanneau.getPlateau().getCase(i, j)
									.estOccupee()) {
						for (Joueur joueur : Partie.getTabJoueurs()) {
							if (lePanneau.getPlateau().getCase(i, j).getBoule()
									.getCouleur() == joueur.getCouleur()) {
								joueur.setBoulesDuJoueurEjectees(joueur
										.getBoulesDuJoueurEjectees() + 1);
							}
						}
						lePanneau.getPlateau().getCase(i, j).setBoule(null);
						Partie.verifierVictoire();
					}
				}
			}

			lePanneau.visibiliteBoutonVide();

			System.out.println("etat : selection");
			BoutonRond.setEtat(Etat.SELECTION);

		}

	}
}