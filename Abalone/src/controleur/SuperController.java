package controleur;

import java.awt.event.MouseEvent;
import java.util.HashSet;
import modele.Boule;
import modele.Case;
import modele.Couleur;
import modele.Direction;
import modele.Joueur;
import modele.Modele;
import modele.Plateau;
import utilitaire.Coord;
import utilitaire.CoordDouble;
import vue.BoutonRond;
import vue.FenetreAbalone;
import vue.PanneauJeu;

public class SuperController {

  public static final int CLICGAUCHE = MouseEvent.BUTTON1;
  public static final int CLICDROIT = MouseEvent.BUTTON3;
  public static final int ips = 35;
  public static final int temps = 100;

  private Coord b1;
  private Coord b2;
  private Coord b3;

  private FenetreAbalone fenetre;
  private Modele modele;
  private Etat etat;

  // private Coord sensDeuxBoules;

  public Coord getB1() {
    return b1;
  }

  public Coord getB2() {
    return b2;
  }

  public Coord getB3() {
    return b3;
  }

  public void setEtat(controleur.Etat etat) {
    this.etat = etat;
  }

  public Etat getEtat() {
    return this.etat;
  }

  public SuperController(Modele modele) {
    this.modele = modele;
  }

  public void afficherB1B2B3() {
    System.out.println("b1 : " + b1);
    System.out.println("b2 : " + b2);
    System.out.println("b3 : " + b3);
  }

  public void viderB1B2B3() {
    b1 = null;
    b2 = null;
    b3 = null;
    System.out.println("dans viderB1B2B3");
    afficherB1B2B3();
  }

  public int compteCouleur(Direction delta, Coord depart, Couleur couleur) {
    return this.compteCouleur(delta, depart.getY(), depart.getX(), couleur);
  }

  public int compteCouleur(Direction delta, int iDep, int jDep, Couleur couleur) {
    int nbCoul = 0;

    Coord parcours = new Coord(jDep, iDep);

    Plateau plateau = modele.getPlateau();

    while (plateau.getCase(parcours) != null && plateau.getCase(parcours).estOccupee()
        && plateau.getCase(parcours).getBoule().getCouleur() == couleur) {

      nbCoul++;
      parcours.setX(parcours.getX() + delta.getX());
      parcours.setY(parcours.getY() + delta.getY());
    }
    return nbCoul;
  }

  public void verifierBoules() {
    Plateau plateau = modele.getPlateau();
    // verification boule hors jeu
    for (int i = 0; i < Plateau.HEIGHT; i++) {
      for (int j = 0; j < Plateau.WIDTH; j++) {
        if (plateau.getCase(i, j).getBord() && plateau.getCase(i, j).estOccupee()) {
          for (Joueur joueur : modele.getTabJoueurs()) {
            if (plateau.getCase(i, j).getBoule().getCouleur() == joueur.getCouleur()) {
              joueur.setBoulesDuJoueurEjectees(joueur.getBoulesDuJoueurEjectees() + 1);
            }
          }
          plateau.getCase(i, j).setBoule(null);
          modele.verifierVictoire();
        }
      }
    }
  }

  public void sourisRelachee(MouseEvent e) {

    BoutonRond bouton = ((BoutonRond) e.getSource());
    PanneauJeu panneau = fenetre.getPanneau();
    Plateau plateau = modele.getPlateau();

    System.out.println("clic : ligne " + bouton.getCoordI() + ", colonne " + bouton.getCoordJ());

    Direction[] lesDir = Direction.values();

    // BoutonRond tmp; /* tous les boutons temporaires de parcours
    // circulaire */

    switch (etat) {
      case NORMAL:
        BoutonRond tmp;
        switch (e.getButton()) {
          case CLICGAUCHE:
            // coordonnees depart
            b1 = bouton.getCoord();
            etat = Etat.SELECTIONLIGNE;
            System.out.println("etat : DEPLACEMENTLIGNE");

            // cacher
            panneau.cacherBoutons();

            // afficher cercle voisins
            for (Direction dir : lesDir) {
              Coord dest =
                  new Coord(bouton.getCoordJ() + dir.getX(), bouton.getCoordI() + dir.getY());

              tmp = plateau.getCase(dest).getBouton();
              if (tmp != null && !plateau.getCase(dest).getBord()) {
                tmp.mettreCliquableGauche();
              }
            }
            break;
          case CLICDROIT:
            // coordonnees depart

            b1 = bouton.getCoord();

            System.out.println("dans 1er clic droit");
            afficherB1B2B3();
            System.out.println(bouton);

            panneau.cacherBoutons();

            BoutonRond boutonDep = plateau.getCase(b1).getBouton();
            boutonDep.setVisible(true);

            // afficher cercle voisins
            for (Direction dir : lesDir) {
              Case caseDest =
                  plateau.getCase(bouton.getCoordI() + dir.getY(), bouton.getCoordJ() + dir.getX());

              tmp = caseDest.getBouton();
              if (tmp != null && !caseDest.getBord() && caseDest.estOccupee() && caseDest.getBoule()
                  .getCouleur() == plateau.getCase(b1).getBoule().getCouleur()) {
                tmp.mettreCliquableDroit();
              }
            }

            etat = Etat.SELECTIONLATERAL;
            System.out.println("etat : SELECTIONLATERAL");
            break;
          default:
            break;
        }
        break;
      case SELECTIONLIGNE:
        int nbBoulesDeplac;
        switch (e.getButton()) {
          case CLICGAUCHE:
            b2 = bouton.getCoord();
            Coord sensDeuxBoules = Coord.difference(b2, b1);

            Direction dir = Direction.toDirection(sensDeuxBoules);

            /* premiere ligne de boules */
            Couleur couleurDepart = plateau.getCase(b1).getBoule().getCouleur();
            int nbCouleurActuelle = compteCouleur(dir, b1, couleurDepart);
            int nbCouleurOpposee = 0;

            Case caseDeFinCouleurActuelle =
                plateau.getCase(b1.getY() + nbCouleurActuelle * sensDeuxBoules.getY(),
                    b1.getX() + nbCouleurActuelle * sensDeuxBoules.getX());

            /* seconde ligne de boules */
            if (caseDeFinCouleurActuelle.estOccupee()) {
              Couleur couleurArr = caseDeFinCouleurActuelle.getBoule().getCouleur();
              nbCouleurOpposee =
                  compteCouleur(dir, b1.getY() + nbCouleurActuelle * sensDeuxBoules.getY(),
                      b1.getX() + nbCouleurActuelle * sensDeuxBoules.getX(), couleurArr);
            }

            nbBoulesDeplac = nbCouleurActuelle + nbCouleurOpposee;
            boolean deplacementPossible = true;

            // verification de la case qui suit la derniere
            Case caseFinale = plateau.getCase(b1.getY() + nbBoulesDeplac * sensDeuxBoules.getY(),
                b1.getX() + nbBoulesDeplac * sensDeuxBoules.getX());
            if (caseFinale != null && caseFinale.estOccupee()) {
              deplacementPossible = false;
            }

            System.out.println("nbCouleurAcuelle = " + nbCouleurActuelle + ", nbCouleurOpposee = "
                + nbCouleurOpposee);

            // deplacement reel
            if (nbCouleurActuelle <= Plateau.MAXDEPLACEMENT && nbCouleurOpposee < nbCouleurActuelle
                && deplacementPossible) {
              panneau.cacherBoutons();
              deplacerLigneBoules(nbBoulesDeplac, sensDeuxBoules);
              verifierBoules();
            }

            /* nettoyage et reaffichage */
            viderB1B2B3();
            panneau.cacherBoutons();
            panneau.visibiliteBoutonVide();

            System.out.println("etat : NORMAL");
            etat = Etat.NORMAL;
            break;
          default:
            break;
        }
        break;
      case SELECTIONLATERAL:
        switch (e.getButton()) {
          case CLICDROIT:
            if (bouton.isSelectionne())
              break;

            b2 = bouton.getCoord();

            Coord sensDeuxBoules = Coord.difference(b2, b1);

            panneau.cacherBoutons();

            /* caseDecalDepart - caseDepart - caseArrivee - caseDecalArrivee */
            Case caseDepart = plateau.getCase(b1);
            Case caseArrivee = plateau.getCase(b2);
            Case caseDecalDepart = plateau.getCase(Coord.difference(b1, sensDeuxBoules));
            Case caseDecalArrivee = plateau.getCase(Coord.somme(b2, sensDeuxBoules));

            HashSet<Case> listeCases = new HashSet<Case>();
            listeCases.add(caseDepart);
            listeCases.add(caseArrivee);
            listeCases.add(caseDecalDepart);
            listeCases.add(caseDecalArrivee);

            /* affichage des boutons lateraux */
            cercleBoutonsLateraux(listeCases, caseDepart);
            cercleBoutonsLateraux(listeCases, caseArrivee);

            /* affichage des boutons decal */

            if (caseDecalArrivee.estOccupee() && caseDecalArrivee.getBoule()
                .getCouleur() == caseArrivee.getBoule().getCouleur()) {
              caseDecalArrivee.getBouton().mettreCliquableDroit();
            } else {
              caseDecalArrivee.getBouton().reset();
            }

            if (caseDecalDepart.estOccupee()
                && caseDecalDepart.getBoule().getCouleur() == caseDepart.getBoule().getCouleur()) {
              caseDecalDepart.getBouton().mettreCliquableDroit();
            } else {
              caseDecalDepart.getBouton().reset();
            }

            eliminerForeverAlone();

            System.out.println("etat : SELECTIONLATERAL2");
            etat = Etat.SELECTIONLATERAL2;

            break;
          default:
            break;
        }
        break;
      case SELECTIONLATERAL2:
        switch (e.getButton()) {
          case CLICGAUCHE:
            Coord sensDeuxBoules = Coord.difference(b2, b1);

            Case caseProlonge = plateau.getCase(Coord.somme(bouton.getCoord(), sensDeuxBoules));
            Case caseInverse = plateau.getCase(Coord.difference(bouton.getCoord(), sensDeuxBoules));

            if ((caseProlonge.getBouton() != null && !caseProlonge.getBord()
                && caseProlonge.getBouton().isMouseOver() && !caseProlonge.estOccupee())
                || (caseInverse.getBouton() != null && !caseInverse.getBord()
                    && caseInverse.getBouton().isMouseOver() && !caseInverse.estOccupee()
                    && caseInverse.getBouton().isCliquableGauche())) {

              /* TODO déplacer les 2 boules */
              Coord sensDeplac = Coord.difference(bouton.getCoord(), b1);

              int nbBoules = 2;
              int periode = temps / nbBoules / ips;

              if (!caseProlonge.getBouton().isCliquableGauche()) {
                sensDeplac = Coord.difference(sensDeplac, sensDeuxBoules);
              }
              panneau.cacherBoutons();
              try {
                deplacerBouleDirection(Direction.toDirection(sensDeplac), b1, periode);
                deplacerBouleDirection(Direction.toDirection(sensDeplac), b2, periode);
              } catch (DeplacementException e1) {
                e1.printStackTrace();
              }

              viderB1B2B3();
              panneau.cacherBoutons();
              panneau.visibiliteBoutonVide();

              System.out.println("etat : NORMAL");
              etat = Etat.NORMAL;

              break;

            }

            break;
          case CLICDROIT:
            if (bouton.isSelectionne())
              break;

            b3 = bouton.getCoord();

            reordonne(b1, b2, b3);
            afficherB1B2B3();

            /*
             * caseDecalDepart - caseDepart - caseMilieu - caseArrivee - caseDecalArrivee
             */
            Case caseDepart = plateau.getCase(b1);
            Case caseMilieu = plateau.getCase(b2);
            Case caseArrivee = plateau.getCase(b3);

            sensDeuxBoules = Coord.difference(b2, b1);

            Case caseDecalDepart = plateau.getCase(Coord.difference(b1, sensDeuxBoules));
            Case caseDecalArrivee = plateau.getCase(Coord.somme(b3, sensDeuxBoules));

            HashSet<Case> casesSpeciales = new HashSet<Case>();
            casesSpeciales.add(caseDecalDepart);
            casesSpeciales.add(caseDecalArrivee);
            casesSpeciales.add(caseDepart);
            casesSpeciales.add(caseArrivee);
            casesSpeciales.add(caseMilieu);

            /* affichage des boutons lateraux */
            cercleBoutonsLateraux(casesSpeciales, caseDepart);
            cercleBoutonsLateraux(casesSpeciales, caseMilieu);
            cercleBoutonsLateraux(casesSpeciales, caseArrivee);

            caseDecalDepart.getBouton().reset();
            caseDecalArrivee.getBouton().reset();

            eliminerForeverAlone();

            System.out.println("etat : SELECTIONLATERAL3");
            etat = Etat.SELECTIONLATERAL3;
            break;
          default:
            break;
        }
        break;
      case SELECTIONLATERAL3:
        switch (e.getButton()) {
          case CLICGAUCHE:
            etat = Etat.NORMAL;

            /* blabla */
            viderB1B2B3();

            break;
          default:
            break;
        }
        break;
      default:
        break;

    }
  }

  /*
   * met les trois coordonnées telles que premier - second - troisième en ligne
   */
  private void reordonne(Coord p, Coord s, Coord t) {
    Coord sToT = Coord.difference(t, s);
    if (Math.abs(sToT.getX()) > 1 || Math.abs(sToT.getY()) > 1) {
      Coord pSave = new Coord(p);
      Coord sSave = new Coord(s);
      Coord tSave = new Coord(t);

      p.setCoord(tSave);
      s.setCoord(pSave);
      t.setCoord(sSave);
    }
  }

  /*
   * met en cliquable gauche les boutons qui ne sont pas dans listeCases et qui entourent centre
   */
  private void cercleBoutonsLateraux(HashSet<Case> listeCases, Case centre) {
    Plateau plateau = this.modele.getPlateau();
    Direction[] lesDir = Direction.values();

    for (Direction dir : lesDir) {

      Case caseDest = plateau.getCase(centre.getBouton().getCoordI() + dir.getY(),
          centre.getBouton().getCoordJ() + dir.getX());

      BoutonRond tmp = caseDest.getBouton();
      if (tmp != null && !caseDest.getBord() && !caseDest.estOccupee()) {

        for (Case caseTmp : listeCases) {
          if (caseDest.getBouton().equals(caseTmp.getBouton()))
            break;
        }

        tmp.mettreCliquableGauche();
      }
    }
  }

  private void eliminerForeverAlone() {
    Plateau plateau = this.modele.getPlateau();
    Direction[] lesDir = Direction.values();

    // cache les boutons isolés
    for (int i = 0; i < Plateau.HEIGHT; i++) {
      for (int j = 0; j < Plateau.WIDTH; j++) {
        if (plateau.getCase(i, j).getBouton().isCliquableGauche()) {
          boolean aVoisin = false;
          for (Direction dir : lesDir) {
            if (plateau.getCase(i + dir.getY(), j + dir.getX()).getBouton().isCliquableGauche()) {
              aVoisin = true;
              break;
            }
          }
          if (!aVoisin) {
            plateau.getCase(i, j).getBouton().reset();
          }
        }
      }
    }
  }

  private void deplacerLigneBoules(int nbBoules, Coord delta) {
    int periode = temps / nbBoules / ips;

    System.out.println(nbBoules + " boule(s) a deplacer");
    // la derniere boule est la premiere deplacee
    Coord coordDepla = new Coord(b1.getX() + (nbBoules - 1) * delta.getX(),
        b1.getY() + (nbBoules - 1) * delta.getY());
    while (nbBoules > 0) {
      nbBoules--;
      try {
        deplacerBouleDirection(Direction.toDirection(delta), coordDepla, periode);

      } catch (DeplacementException e1) {
        e1.printStackTrace();
      }

      coordDepla.setX(coordDepla.getX() - delta.getX());
      coordDepla.setY(coordDepla.getY() - delta.getY());
    }
  }

  public void sourisEntree(MouseEvent e) {
    BoutonRond bouton = ((BoutonRond) e.getSource());
    bouton.setMouseOver(true);
    if (etat == Etat.SELECTIONLATERAL2) {
      Coord sensDeuxBoules = Coord.difference(b2, b1);

      Coord coordDepla = Coord.somme(bouton.getCoord(), sensDeuxBoules);
      if (!modele.getPlateau().getCase(coordDepla).getBouton().isVisible()) {
        coordDepla = Coord.difference(bouton.getCoord(), sensDeuxBoules);
      }

      modele.getPlateau().getCase(coordDepla).getBouton().setMouseOver(true);

      fenetre.repaint();
    } else if (etat == Etat.SELECTIONLATERAL3) {
      // TODO On verra pus tard...
    }
  }

  public void sourisSortie(MouseEvent e) {
    BoutonRond bouton = ((BoutonRond) e.getSource());
    bouton.setMouseOver(false);
    if (etat == Etat.SELECTIONLATERAL2) {
      Coord sensDeuxBoules = Coord.difference(b2, b1);

      Coord coordDepla = Coord.somme(bouton.getCoord(), sensDeuxBoules);
      if (!modele.getPlateau().getCase(coordDepla).getBouton().isVisible()) {
        coordDepla = Coord.difference(bouton.getCoord(), sensDeuxBoules);
      }

      modele.getPlateau().getCase(coordDepla).getBouton().setMouseOver(false);

      fenetre.repaint();
    } else if (etat == Etat.SELECTIONLATERAL3) {
      // TODO On verra pus tard...
    }
  }

  public void setVue(FenetreAbalone fenetre) {
    this.fenetre = fenetre;
  }

  public void deplacerBouleDirection(Direction dir, Coord coordCase, int tempsPeriode)
      throws DeplacementException {

    System.out.println("deplacement de (" + coordCase.getX() + ";" + coordCase.getY()
        + ") en direction (" + dir.getX() + ";" + dir.getY() + ")");

    if (coordCase.getX() < 0 || coordCase.getX() >= Plateau.WIDTH || coordCase.getY() < 0
        || coordCase.getY() >= Plateau.HEIGHT) {
      throw new DeplacementException("case debut invalide (<0 | >" + Plateau.HEIGHT + ")");
    }

    Case caseActuelle = modele.getPlateau().getCase(coordCase);
    Coord coordCaseSuivante = modele.getPlateau().coordCaseSuivant(dir, coordCase);

    if (!caseActuelle.estOccupee()) {
      throw new DeplacementException("case debut non occupee");
    }
    if (modele.getPlateau().getCase(coordCaseSuivante).estOccupee()) {
      throw new DeplacementException("case arrivee occcupee");
    }

    Boule bouleADeplacer = caseActuelle.getBoule();

    CoordDouble delta = new CoordDouble((double) (dir.getX()) / ips, (double) (dir.getY()) / ips);

    for (int i = 0; i < ips; i++) {
      long debut = System.currentTimeMillis();
      bouleADeplacer.getCoord().setCoord(CoordDouble.somme(bouleADeplacer.getCoord(), delta));

      fenetre.repaint();

      fenetre.getPanneau().paintImmediately(0, 0, fenetre.getPanneau().getWidth(),
          fenetre.getPanneau().getHeight());

      try {
        long tpsRestant = tempsPeriode - (System.currentTimeMillis() - debut);
        if (tpsRestant > 0)
          Thread.sleep(tpsRestant);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }

    bouleADeplacer.getCoord().setCoord(coordCaseSuivante.getY(), coordCaseSuivante.getX());
    modele.getPlateau().getCase(coordCaseSuivante).setBoule(caseActuelle.getBoule());
    caseActuelle.setBoule(null);

  }
}
