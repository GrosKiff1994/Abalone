package actions;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import objects.Case;
import objects.Coord;
import objects.Couleur;
import objects.Direction;
import objects.Plateau;
import core.DeplacementException;
import core.Partie.Etat;
import display.BoutonRond;
import display.PanneauJeu;

public class listenerSelection extends MouseAdapter {
	private PanneauJeu lePanneau;
	private static Coord depart;

	public int compteCouleur(Direction delta, Coord depart, Couleur couleur) {
		return this.compteCouleur(delta, depart.getY(), depart.getX(), couleur);
	}

	public int compteCouleur(Direction delta, int iDep, int jDep, Couleur couleur) {
		int nbCoul = 0;

		Coord parcours = new Coord(jDep, iDep);

		while (lePanneau.getPlateau().getCase(parcours) != null && lePanneau.getPlateau().getCase(parcours).estOccupee()
				&& lePanneau.getPlateau().getCase(parcours).getBoule().getCouleur() == couleur) {

			nbCoul++;
			parcours.setX(parcours.getX() + delta.getX());
			parcours.setY(parcours.getY() + delta.getY());
		}
		return nbCoul;
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {

		BoutonRond leBouton = ((BoutonRond) e.getSource());
		lePanneau = leBouton.getPanneauJeu();

		System.out.println("clic : ligne " + leBouton.getCoordI() + ", colonne " + leBouton.getCoordJ());

		switch (e.getButton()) {
		case MouseEvent.BUTTON1:

			switch (leBouton.getEtat()) {

			case SELECTION:
				// coordonnees depart
				depart = leBouton.getCoord();
				BoutonRond.setEtat(Etat.DEPLACEMENTLIGNE);
				System.out.println("etat : DEPLACEMENTLIGNE");

				// cacher
				lePanneau.cacherBoutons();

				// afficher cercle voisins
				Direction[] lesDir = Direction.values();
				for (Direction dir : lesDir) {
					Coord dest = new Coord(leBouton.getCoordJ() + dir.getX(), leBouton.getCoordI() + dir.getY());

					BoutonRond tmp = lePanneau.getPlateau().getCase(dest).getBouton();
					if (tmp != null && !lePanneau.getPlateau().getCase(dest).getBord()) {
						tmp.setCouleurActuelle(BoutonRond.couleurSelecTour);
						tmp.setVisible(true);
					}
				}

				break;
			case DEPLACEMENTLIGNE:
				Coord arrive = leBouton.getCoord();
				Coord delta = new Coord(arrive.getX() - depart.getX(), arrive.getY() - depart.getY());
				Direction dir = Direction.toDirection(delta);

				/* premiere ligne de boules */
				Couleur couleurDepart = lePanneau.getPlateau().getCase(depart).getBoule().getCouleur();
				int nbCouleurActuelle = compteCouleur(dir, depart, couleurDepart);
				int nbCouleurOpposee = 0;

				Case caseDeFinCouleurActuelle = lePanneau.getPlateau().getCase(depart.getY() + nbCouleurActuelle * delta.getY(),
						depart.getX() + nbCouleurActuelle * delta.getX());

				/* seconde ligne de boules */
				if (caseDeFinCouleurActuelle.estOccupee()) {
					Couleur couleurArr = caseDeFinCouleurActuelle.getBoule().getCouleur();
					nbCouleurOpposee = compteCouleur(dir, depart.getY() + nbCouleurActuelle * delta.getY(),
							depart.getX() + nbCouleurActuelle * delta.getX(), couleurArr);
				}

				int coeffDelta = nbCouleurActuelle + nbCouleurOpposee;
				boolean deplacementPossible = true;

				// verification de la case qui suit la derniere
				Case caseFinale = lePanneau.getPlateau()
						.getCase(depart.getY() + coeffDelta * delta.getY(), depart.getX() + coeffDelta * delta.getX());
				if (caseFinale != null && caseFinale.estOccupee()) {
					deplacementPossible = false;
				}

				System.out.println("nbCouleurAcuelle = " + nbCouleurActuelle + ", nbCouleurOpposee = " + nbCouleurOpposee);

				// deplacement reel
				if (nbCouleurActuelle <= Plateau.MAXDEPLACEMENT && nbCouleurOpposee < nbCouleurActuelle && deplacementPossible) {
					System.out.println(coeffDelta + " boule(s) a deplacer");
					// la derniere boule est la premiere deplacee
					Coord coordDepla = new Coord(depart.getX() + (coeffDelta - 1) * delta.getX(), depart.getY() + (coeffDelta - 1) * delta.getY());
					while (coeffDelta > 0) {
						coeffDelta--;
						try {
							lePanneau.getPlateau().deplacerBouleDirection(Direction.toDirection(delta), coordDepla);

						} catch (DeplacementException e1) {
							e1.printStackTrace();
						}

						coordDepla.setX(coordDepla.getX() - delta.getX());
						coordDepla.setY(coordDepla.getY() - delta.getY());
					}

				}

				/* nettoyage et reaffichage */
				lePanneau.getPlateau().verifierBoules();
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

				BoutonRond boutonDep = lePanneau.getPlateau().getCase(depart).getBouton();
				boutonDep.setVisible(true);
				boutonDep.setCouleurActuelle(BoutonRond.couleurSelec);

				// afficher cercle voisins
				Direction[] lesDir = Direction.values();
				for (Direction dir : lesDir) {
					Case caseDest = lePanneau.getPlateau().getCase(leBouton.getCoordI() + dir.getY(), leBouton.getCoordJ() + dir.getX());

					BoutonRond tmp = caseDest.getBouton();
					if (tmp != null && !caseDest.getBord() && caseDest.estOccupee()
							&& caseDest.getBoule().getCouleur() == lePanneau.getPlateau().getCase(depart).getBoule().getCouleur()) {
						tmp.setCouleurActuelle(BoutonRond.couleurSelecTour);
						tmp.setVisible(true);
					}
				}

				BoutonRond.setEtat(Etat.SELECTION2);
				System.out.println("etat : SELECTION2");
				break;
			case SELECTION2:
				Coord bouleSecond = leBouton.getCoord();
				Case caseSecond = lePanneau.getPlateau().getCase(bouleSecond);
				BoutonRond tmp = caseSecond.getBouton();

				if (tmp != null && !caseSecond.getBord() && caseSecond.estOccupee()
						&& caseSecond.getBoule().getCouleur() == lePanneau.getPlateau().getCase(depart).getBoule().getCouleur()) {
					Coord delta = new Coord(bouleSecond.getX() - depart.getX(), bouleSecond.getY() - depart.getY());
				}

				break;
			case DEPLACEMENTLIGNE:
				break;
			case DEPLACEMENTLATERAL2:
				Coord arrivee = new Coord(leBouton.getCoordJ(), leBouton.getCoordI());
				Coord delta = new Coord(arrivee.getX() - depart.getX(), arrivee.getY() - depart.getY());

				lePanneau.cacherBoutons();

				Case caseDecalDepart = lePanneau.getPlateau().getCase(arrivee.getY() + delta.getY(), arrivee.getX() + delta.getX());
				Case caseDecalArrivee = lePanneau.getPlateau().getCase(depart.getY() - delta.getY(), depart.getX() - delta.getX());
				Case caseDepart = lePanneau.getPlateau().getCase(depart);
				Case caseArrivee = lePanneau.getPlateau().getCase(arrivee);

				if (caseDecalArrivee.getBoule().getCouleur() == caseArrivee.getBoule().getCouleur()) {
					caseDecalArrivee.getBouton().setVisible(true);
				}

				if (caseDecalDepart.getBoule().getCouleur() == caseDepart.getBoule().getCouleur()) {
					caseDecalDepart.getBouton().setVisible(true);
				}

				break;
			default:
				break;
			}
			break;
		}
	}
}