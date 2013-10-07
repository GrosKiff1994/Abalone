package display;

import javax.swing.JFrame;

import objects.Plateau;


public class FenetreAbalone extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FenetreAbalone(Plateau p) {
		PanneauJeu panelPrincipal = new PanneauJeu(p);
		setSize((int) (PanneauJeu.DIMBOULE * 16.15),
				(int) (11 * (PanneauJeu.DIMBOULE - PanneauJeu.DIMBOULE / 16)));
		setContentPane(panelPrincipal);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
