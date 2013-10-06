package Abalone;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PanneauJeu extends JPanel {

	public PanneauJeu() {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		g.fillOval(50, 50, 20, 20);
	}

}
