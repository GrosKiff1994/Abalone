package Abalone;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FenetreAbalone extends JFrame {
	public FenetreAbalone() {
		PanneauJeu panelPrincipal = new PanneauJeu();
		setSize(600, 300);
		setContentPane(panelPrincipal);
		panelPrincipal.setBackground(Color.RED);

		JPanel leRect = new JPanel();
		leRect.setPreferredSize(new Dimension(20, 20));
		panelPrincipal.add(leRect);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		setVisible(true);
	}
}
