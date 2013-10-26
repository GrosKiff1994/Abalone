package actions;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import objects.Case;
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

		BoutonRond leBouton = ((BoutonRond) e.getSource());
		PanneauJeu lePanneau = leBouton.getPanneauJeu();

		System.out.println("clic : ligne " + leBouton.getCoordI() + ", colonne " + leBouton.getCoordJ());

		switch (e.getButton()) {
		case MouseEvent.BUTTON1:

			switch (leBouton.getEtat()) {

			case SELECTION:
				// coordonnees depart
				depart.setY(leBouton.getCoordI());
				depart.setX(leBouton.getCoordJ());
				BoutonRond.setEtat(Etat.DEPLACEMENTLIGNE);
				System.out.println("etat : DEPLACEMENTLIGNE");

				// cacher
				lePanneau.cacherBoutons();

				// afficher cercle voisins
				Direction[] lesDir = Direction.values();
				for (Direction dir : lesDir) {
					int iDest = leBouton.getCoordI() + dir.getY();
					int jDest = leBouton.getCoordJ() + dir.getX();

					BoutonRond tmp = lePanneau.getPlateau().getCase(iDest, jDest).getBouton();
					if (tmp != null && !lePanneau.getPlateau().getCase(iDest, jDest).getBord()) {
						tmp.setCouleurActuelle(BoutonRond.couleurSelecTour);
						tmp.setVisible(true);
					}
				}

				break;
			case DEPLACEMENTLIGNE:
				// deplacer la boule

				boolean deplacementPossible = true;
				Coord arrive = new Coord(leBouton.getCoordJ(), leBouton.getCoordI());
				Coord delta = new Coord(arrive.getX() - depart.getX(), arrive.getY() - depart.getY());
				int coeffDelta = 0;
				Direction tabDir[] = Direction.values();
				for (Direction dir : tabDir) {
					if (dir.getCoord().equals(delta)) {
						// compte le nombre de boules a deplacer
						int nbCouleurActuelle = 0;
						int nbCouleurOpposee = 0;

						Couleur couleurDep = lePanneau.getPlateau().getCase(depart.getY(), depart.getX()).getBoule().getCouleur();

						while (lePanneau.getPlateau().getCase((coeffDelta - 1) * delta.getY() + arrive.getY(),
								(coeffDelta - 1) * delta.getX() + arrive.getX()) != null
								&& lePanneau.getPlateau()
										.getCase((coeffDelta - 1) * delta.getY() + arrive.getY(), (coeffDelta - 1) * delta.getX() + arrive.getX())
										.estOccupee()
								&& lePanneau.getPlateau()
										.getCase((coeffDelta - 1) * delta.getY() + arrive.getY(), (coeffDelta - 1) * delta.getX() + arrive.getX())
										.getBoule().getCouleur() == couleurDep && nbCouleurActuelle < 4) {

							nbCouleurActuelle++;
							coeffDelta++;
						}

						while (lePanneau.getPlateau().getCase((coeffDelta - 1) * delta.getY() + arrive.getY(),
								(coeffDelta - 1) * delta.getX() + arrive.getX()) != null
								&& lePanneau.getPlateau()
										.getCase((coeffDelta - 1) * delta.getY() + arrive.getY(), (coeffDelta - 1) * delta.getX() + arrive.getX())
										.estOccupee()
								&& lePanneau.getPlateau()
										.getCase((coeffDelta - 1) * delta.getY() + arrive.getY(), (coeffDelta - 1) * delta.getX() + arrive.getX())
										.getBoule().getCouleur() != couleurDep && nbCouleurOpposee < 3) {

							nbCouleurOpposee++;
							coeffDelta++;
						}

						while (lePanneau.getPlateau().getCase((coeffDelta - 1) * delta.getY() + arrive.getY(),
								(coeffDelta - 1) * delta.getX() + arrive.getX()) != null
								&& lePanneau.getPlateau()
										.getCase((coeffDelta - 1) * delta.getY() + arrive.getY(), (coeffDelta - 1) * delta.getX() + arrive.getX())
										.estOccupee()
								&& lePanneau.getPlateau()
										.getCase((coeffDelta - 1) * delta.getY() + arrive.getY(), (coeffDelta - 1) * delta.getX() + arrive.getX())
										.getBoule().getCouleur() == couleurDep && nbCouleurActuelle < 4) {

							nbCouleurActuelle++;
							coeffDelta++;
							deplacementPossible = false;
						}

						if (nbCouleurOpposee < nbCouleurActuelle && nbCouleurActuelle < 4 && deplacementPossible) {

							System.out.println(coeffDelta + " boule(s) a deplacer");
							// la derniere boule est la premiere deplacee
							depart.setX(depart.getX() + (coeffDelta - 1) * delta.getX());
							depart.setY(depart.getY() + (coeffDelta - 1) * delta.getY());
							while (coeffDelta > 0) {
								try {
									coeffDelta--;
									lePanneau.getPlateau().deplacerBouleDirection(dir, depart);
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
						if (lePanneau.getPlateau().getCase(i, j).getBord() && lePanneau.getPlateau().getCase(i, j).estOccupee()) {
							for (Joueur joueur : Partie.getTabJoueurs()) {
								if (lePanneau.getPlateau().getCase(i, j).getBoule().getCouleur() == joueur.getCouleur()) {
									joueur.setBoulesDuJoueurEjectees(joueur.getBoulesDuJoueurEjectees() + 1);
								}
							}
							lePanneau.getPlateau().getCase(i, j).setBoule(null);
							Partie.verifierVictoire();
						}
					}
				}

				lePanneau.visibiliteBoutonVide();

				System.out.println("etat : SELECTION");
				BoutonRond.setEtat(Etat.SELECTION);
				break;
			case DEPLACEMENTLATERAL2:
				break;
			default:
				break;
			}

			break;
		case MouseEvent.BUTTON3:
			switch (leBouton.getEtat()) {
			case SELECTION:
				// coordonnees depart
				depart.setY(leBouton.getCoordI());
				depart.setX(leBouton.getCoordJ());

				lePanneau.cacherBoutons();

				BoutonRond boutonDep = lePanneau.getPlateau().getCase(depart.getY(), depart.getX()).getBouton();
				boutonDep.setVisible(true);
				boutonDep.setCouleurActuelle(BoutonRond.couleurSelec);

				// afficher cercle voisins
				Direction[] lesDir = Direction.values();
				for (Direction dir : lesDir) {
					Case caseDest = lePanneau.getPlateau().getCase(leBouton.getCoordI() + dir.getY(), leBouton.getCoordJ() + dir.getX());

					BoutonRond tmp = caseDest.getBouton();
					if (tmp != null
							&& !caseDest.getBord()
							&& caseDest.estOccupee()
							&& caseDest.getBoule().getCouleur() == lePanneau.getPlateau().getCase(depart.getY(), depart.getX()).getBoule()
									.getCouleur()) {
						tmp.setCouleurActuelle(BoutonRond.couleurSelecTour);
						tmp.setVisible(true);
					}
				}

				BoutonRond.setEtat(Etat.SELECTION2);
				System.out.println("etat : SELECTION2");
				break;
			case SELECTION2:
				Coord bouleSecond = new Coord(leBouton.getCoordJ(), leBouton.getCoordI());
				Case caseSecond = lePanneau.getPlateau().getCase(bouleSecond.getY(), bouleSecond.getX());
				BoutonRond tmp = caseSecond.getBouton();

				if (tmp != null && !caseSecond.getBord() && caseSecond.estOccupee()
						&& caseSecond.getBoule().getCouleur() == lePanneau.getPlateau().getCase(depart.getY(), depart.getX()).getBoule().getCouleur()) {
					Coord delta = new Coord(bouleSecond.getX() - depart.getX(), bouleSecond.getY() - depart.getY());
				}

				break;
			case DEPLACEMENTLIGNE:
				break;
			case DEPLACEMENTLATERAL2:
				Coord arrivee = new Coord(leBouton.getCoordJ(), leBouton.getCoordI());
				Coord delta = new Coord(arrivee.getX() - depart.getX(), arrivee.getY() - depart.getY());

				lePanneau.cacherBoutons();
				for (Direction dir : Direction.values()) {

					Case caseDecalDepart = lePanneau.getPlateau().getCase(arrivee.getY() + dir.getY(), arrivee.getX() + dir.getX());
					Case caseDecalArrivee = lePanneau.getPlateau().getCase(depart.getY() - dir.getY(), depart.getX() - dir.getX());
					Case caseDepart = lePanneau.getPlateau().getCase(depart.getY(), depart.getX());
					Case caseArrivee = lePanneau.getPlateau().getCase(arrivee.getY(), arrivee.getX());

					if (dir.getY() == delta.getY() && dir.getX() == delta.getX()
							&& caseDecalArrivee.getBoule().getCouleur() == caseArrivee.getBoule().getCouleur()) {
						caseDecalArrivee.getBouton().setVisible(true);
						break;
					}

					else if (dir.getY() == -delta.getY() && dir.getX() == -delta.getX()
							&& caseDecalDepart.getBoule().getCouleur() == caseDepart.getBoule().getCouleur()) {
						caseDecalDepart.getBouton().setVisible(true);
						break;
					}
				}

				break;
			default:
				break;
			}
			break;
		}
	}
}