import controleur.SuperController;
import modele.Carte;
import modele.Couleur;
import modele.Joueur;
import modele.Modele;
import modele.Plateau;
import vue.FenetreAbalone;

public class Application {

  public static void main(String[] args) {

    Modele modele = new Modele();
    SuperController controller = new SuperController(modele);
    FenetreAbalone fenetre = new FenetreAbalone(modele);

    fenetre.setController(controller);
    controller.setVue(fenetre);

    Plateau plateauJeu = new Plateau();
    plateauJeu.chargerTab(Carte.tabTest);
    modele.setPlateau(plateauJeu);
    fenetre.creerPanneau();
    controller.viderB1B2B3();
    fenetre.getPanneau().cacherBoutons();
    fenetre.getPanneau().visibiliteBoutonVide();
    controller.setEtat(controleur.Etat.NORMAL);

    fenetre.setTitle("Abalone");
    fenetre.setVisible(true);
    fenetre.getPanneau().genererFond();

    modele.getTabJoueurs()[0] = new Joueur("joueurNOIR", Couleur.NOIR);
    modele.getTabJoueurs()[1] = new Joueur("joueurBLANC", Couleur.BLANC);
  }
}
