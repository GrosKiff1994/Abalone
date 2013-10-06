import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import abalone.Coord;
import abalone.DeplacementException;
import abalone.Direction;
import abalone.FenetreAbalone;
import abalone.Plateau;

public class Application {

	static int i = 4;

	public static void main(String[] args) {

		char tabClassique[][] = new char[][] {
				{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
				{ 'x', 'x', 'x', 'x', 'x', 'b', 'b', 'b', 'b', 'b', 'x' },
				{ 'x', 'x', 'x', 'x', 'b', 'b', 'b', 'b', 'b', 'b', 'x' },
				{ 'x', 'x', 'x', 'v', 'v', 'b', 'b', 'b', 'v', 'v', 'x' },
				{ 'x', 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'x' },
				{ 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'x' },
				{ 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'x', 'x' },
				{ 'x', 'v', 'v', 'n', 'n', 'n', 'v', 'v', 'x', 'x', 'x' },
				{ 'x', 'n', 'n', 'n', 'n', 'n', 'n', 'x', 'x', 'x', 'x' },
				{ 'x', 'n', 'n', 'n', 'n', 'n', 'x', 'x', 'x', 'x', 'x' },
				{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' } };

		final Plateau plateauJeu = new Plateau();

		plateauJeu.chargerTab(tabClassique);

		System.out.println(plateauJeu.toString());

		final FenetreAbalone fen = new FenetreAbalone(plateauJeu);
		/*
		 * plateauJeu.deplacerBouleDirection(Direction.dirBD, new Coord(5, 3));
		 * System.out.println(plateauJeu);
		 */

		int delay = 1000;

		final Timer timer = new Timer(delay, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Direction dir = Direction.dirBG;
				try {
					plateauJeu.deplacerBouleDirection(dir, new Coord(i, 2));
					fen.repaint();

				} catch (DeplacementException e1) {
					System.out.println(e1.getMessage());
				}

				i++;
				if (i == 10) {
					((Timer) e.getSource()).stop();
				}

			}
		});
		timer.start();
	}
}
