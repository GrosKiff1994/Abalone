package display;

import javax.swing.JFrame;

import objects.Plateau;

public class FenetreAbalone extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PanneauJeu panelPrincipal;

	public FenetreAbalone() {
		setSize((int) (PanneauJeu.DIMBOULE * 16.15), (int) (11 * (PanneauJeu.DIMBOULE - PanneauJeu.DIMBOULE / 16)));

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	public PanneauJeu getPanneau() {
		return this.panelPrincipal;
	}

	public void setPlateau(Plateau p) {
		panelPrincipal = new PanneauJeu(p);
		setContentPane(panelPrincipal);
	}
}
