import modele.Carte;
import modele.Couleur;
import modele.Joueur;
import modele.Modele;
import modele.Plateau;
import vue.BoutonRond;
import vue.FenetreAbalone;
import controlleur.SuperController;

public class Application {

	public static void main(String[] args) {

		Modele modele = new Modele();
		SuperController controller = new SuperController(modele);
		FenetreAbalone fenetre = new FenetreAbalone(modele);

		fenetre.setController(controller);
		controller.setVue(fenetre);
		modele.setVue(fenetre);

		Plateau plateauJeu = new Plateau();
		plateauJeu.chargerTab(Carte.tabClassique);
		fenetre.setPlateau(plateauJeu);
		fenetre.getPanneau().visibiliteBoutonVide();
		BoutonRond.setEtat(controlleur.Etat.SELECTION);

		controller.getTabJoueurs()[0] = new Joueur("joueurNOIR", Couleur.NOIR);
		controller.getTabJoueurs()[1] = new Joueur("joueurBLANC", Couleur.BLANC);

		fenetre.setTitle("Abalone");
		fenetre.setVisible(true);
	}
}
