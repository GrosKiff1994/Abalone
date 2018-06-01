package vue;

import javax.swing.JFrame;

import modele.Modele;
import controleur.SuperController;

public class FenetreAbalone extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PanneauJeu panelPrincipal;
	private Modele modele;
	private SuperController controller;

	public FenetreAbalone(Modele modele) {
		this.modele = modele;
		setSize((int) (PanneauJeu.DIMBOULE * 16.15), (int) (11 * (PanneauJeu.DIMBOULE - PanneauJeu.DIMBOULE / 16)));

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	public PanneauJeu getPanneau() {
		return this.panelPrincipal;
	}

	public void creerPanneau() {
		panelPrincipal = new PanneauJeu(this, controller);
		setContentPane(panelPrincipal);
	}

	public void setController(SuperController controller) {
		this.controller = controller;
	}

	public SuperController getController() {
		return this.controller;
	}

	public Modele getModele() {
		return modele;
	}
}
