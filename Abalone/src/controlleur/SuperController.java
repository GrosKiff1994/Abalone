package controlleur;

import java.awt.event.MouseEvent;

import modele.Case;
import modele.Coord;
import modele.Couleur;
import modele.Direction;
import modele.Joueur;
import modele.Modele;
import modele.Plateau;
import vue.BoutonRond;
import vue.FenetreAbalone;
import vue.PanneauJeu;

public class SuperController {

	private static Joueur[] tabJoueurs = new Joueur[2];
	private static Joueur perdant;

	private Coord depart;
	private FenetreAbalone fenetre;
	private Modele modele;

	public Joueur[] getTabJoueurs() {
		return tabJoueurs;
	}

	public SuperController(Modele modele) {
		this.modele = modele;
	}

	public int compteCouleur(Direction delta, Coord depart, Couleur couleur) {
		return this.compteCouleur(delta, depart.getY(), depart.getX(), couleur);
	}

	public int compteCouleur(Direction delta, int iDep, int jDep, Couleur couleur) {
		int nbCoul = 0;

		Coord parcours = new Coord(jDep, iDep);

		Plateau plateau = fenetre.getPanneau().getPlateau();

		while (plateau.getCase(parcours) != null && plateau.getCase(parcours).estOccupee()
				&& plateau.getCase(parcours).getBoule().getCouleur() == couleur) {

			nbCoul++;
			parcours.setX(parcours.getX() + delta.getX());
			parcours.setY(parcours.getY() + delta.getY());
		}
		return nbCoul;
	}

	public static void verifierVictoire() {
		String chaine = "";

		for (Joueur j : tabJoueurs) {
			chaine += j.getNom() + " : " + j.getBoulesDuJoueurEjectees() + " boule(s) ejectee(s); \n";
			if (j.getBoulesDuJoueurEjectees() >= 6) {
				perdant = j;
				System.out.println("Le joueur " + perdant + " a perdu !");

			}
		}

		System.out.print(chaine);
	}

	public void verifierBoules() {
		Plateau plateau = fenetre.getPanneau().getPlateau();
		// verification boule hors jeu
		for (int i = 0; i < Plateau.HEIGHT; i++) {
			for (int j = 0; j < Plateau.WIDTH; j++) {
				if (plateau.getCase(i, j).getBord() && plateau.getCase(i, j).estOccupee()) {
					for (Joueur joueur : tabJoueurs) {
						if (plateau.getCase(i, j).getBoule().getCouleur() == joueur.getCouleur()) {
							joueur.setBoulesDuJoueurEjectees(joueur.getBoulesDuJoueurEjectees() + 1);
						}
					}
					plateau.getCase(i, j).setBoule(null);
					verifierVictoire();
				}
			}
		}
	}

	public void sourisRelachee(MouseEvent e) {

		BoutonRond bouton = ((BoutonRond) e.getSource());
		PanneauJeu panneau = fenetre.getPanneau();
		Plateau plateau = panneau.getPlateau();

		System.out.println("clic : ligne " + bouton.getCoordI() + ", colonne " + bouton.getCoordJ());

		switch (e.getButton()) {
		case MouseEvent.BUTTON1:

			switch (bouton.getEtat()) {

			case SELECTION:
				// coordonnees depart
				depart = bouton.getCoord();
				BoutonRond.setEtat(Etat.DEPLACEMENTLIGNE);
				System.out.println("etat : DEPLACEMENTLIGNE");

				// cacher
				panneau.cacherBoutons();

				// afficher cercle voisins
				Direction[] lesDir = Direction.values();
				for (Direction dir : lesDir) {
					Coord dest = new Coord(bouton.getCoordJ() + dir.getX(), bouton.getCoordI() + dir.getY());

					BoutonRond tmp = plateau.getCase(dest).getBouton();
					if (tmp != null && !plateau.getCase(dest).getBord()) {
						tmp.setCouleurActuelle(BoutonRond.couleurSelecTour);
						tmp.setVisible(true);
					}
				}

				break;
			case DEPLACEMENTLIGNE:
				Coord arrive = bouton.getCoord();
				Coord delta = new Coord(arrive.getX() - depart.getX(), arrive.getY() - depart.getY());
				Direction dir = Direction.toDirection(delta);

				/* premiere ligne de boules */
				Couleur couleurDepart = plateau.getCase(depart).getBoule().getCouleur();
				int nbCouleurActuelle = compteCouleur(dir, depart, couleurDepart);
				int nbCouleurOpposee = 0;

				Case caseDeFinCouleurActuelle = plateau.getCase(depart.getY() + nbCouleurActuelle * delta.getY(), depart.getX() + nbCouleurActuelle
						* delta.getX());

				/* seconde ligne de boules */
				if (caseDeFinCouleurActuelle.estOccupee()) {
					Couleur couleurArr = caseDeFinCouleurActuelle.getBoule().getCouleur();
					nbCouleurOpposee = compteCouleur(dir, depart.getY() + nbCouleurActuelle * delta.getY(),
							depart.getX() + nbCouleurActuelle * delta.getX(), couleurArr);
				}

				int coeffDelta = nbCouleurActuelle + nbCouleurOpposee;
				boolean deplacementPossible = true;

				// verification de la case qui suit la derniere
				Case caseFinale = plateau.getCase(depart.getY() + coeffDelta * delta.getY(), depart.getX() + coeffDelta * delta.getX());
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
							plateau.deplacerBouleDirection(Direction.toDirection(delta), coordDepla);

						} catch (DeplacementException e1) {
							e1.printStackTrace();
						}

						coordDepla.setX(coordDepla.getX() - delta.getX());
						coordDepla.setY(coordDepla.getY() - delta.getY());
					}
					verifierBoules();
				}

				/* nettoyage et reaffichage */
				panneau.visibiliteBoutonVide();

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
			switch (bouton.getEtat()) {
			case SELECTION:
				// coordonnees depart
				depart = bouton.getCoord();

				panneau.cacherBoutons();

				BoutonRond boutonDep = plateau.getCase(depart).getBouton();
				boutonDep.setVisible(true);
				boutonDep.setCouleurActuelle(BoutonRond.couleurSelec);

				// afficher cercle voisins
				Direction[] lesDir = Direction.values();
				for (Direction dir : lesDir) {
					Case caseDest = plateau.getCase(bouton.getCoordI() + dir.getY(), bouton.getCoordJ() + dir.getX());

					BoutonRond tmp = caseDest.getBouton();
					if (tmp != null && !caseDest.getBord() && caseDest.estOccupee()
							&& caseDest.getBoule().getCouleur() == plateau.getCase(depart).getBoule().getCouleur()) {
						tmp.setCouleurActuelle(BoutonRond.couleurSelecTour);
						tmp.setVisible(true);
					}
				}

				BoutonRond.setEtat(Etat.SELECTION2);
				System.out.println("etat : SELECTION2");
				break;
			case SELECTION2:
				Coord bouleSecond = bouton.getCoord();
				Case caseSecond = plateau.getCase(bouleSecond);
				BoutonRond tmp = caseSecond.getBouton();

				if (tmp != null && !caseSecond.getBord() && caseSecond.estOccupee()
						&& caseSecond.getBoule().getCouleur() == plateau.getCase(depart).getBoule().getCouleur()) {
					Coord delta = new Coord(bouleSecond.getX() - depart.getX(), bouleSecond.getY() - depart.getY());
				}

				break;
			case DEPLACEMENTLIGNE:
				break;
			case DEPLACEMENTLATERAL2:
				Coord arrivee = new Coord(bouton.getCoordJ(), bouton.getCoordI());
				Coord delta = new Coord(arrivee.getX() - depart.getX(), arrivee.getY() - depart.getY());

				panneau.cacherBoutons();

				/* caseDecalDepart - caseDepart - caseArrivee - caseDecalArrivee */
				Case caseDecalDepart = plateau.getCase(arrivee.getY() + delta.getY(), arrivee.getX() + delta.getX());
				Case caseDecalArrivee = plateau.getCase(depart.getY() - delta.getY(), depart.getX() - delta.getX());
				Case caseDepart = plateau.getCase(depart);
				Case caseArrivee = plateau.getCase(arrivee);

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

	public void sourisEntree(MouseEvent e) {
		BoutonRond bouton = ((BoutonRond) e.getSource());

		Case caseCourante = fenetre.getPanneau().getPlateau().getCase(bouton.getCoord());
		if (caseCourante.estOccupee()) {
			if (bouton.getCouleurActuelle() == null) {
				bouton.setCouleurActuelle(BoutonRond.couleurMouseOver);
			}
		}
		if (bouton.getCouleurActuelle() == BoutonRond.couleurSelecTour) {
			bouton.setCouleurActuelle(BoutonRond.couleurMouseOver);
		}
	}

	public void sourisSortie(MouseEvent e) {
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

	public void setVue(FenetreAbalone fenetre) {
		this.fenetre = fenetre;
	}
}
