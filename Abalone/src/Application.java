import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import objects.Coord;
import objects.Direction;
import objects.Plateau;
import core.DeplacementException;
import display.FenetreAbalone;

public class Application {

	static int i = 4;

	public static void main(String[] args) {

		char tabVide[][] = new char[][] {
				{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
				{ 'x', 'x', 'x', 'x', 'x', 'v', 'v', 'v', 'v', 'v', 'x' },
				{ 'x', 'x', 'x', 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'x' },
				{ 'x', 'x', 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'x' },
				{ 'x', 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'x' },
				{ 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'x' },
				{ 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'x', 'x' },
				{ 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'v', 'x', 'x', 'x' },
				{ 'x', 'v', 'v', 'v', 'v', 'v', 'v', 'x', 'x', 'x', 'x' },
				{ 'x', 'v', 'v', 'v', 'v', 'v', 'x', 'x', 'x', 'x', 'x' },
				{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' } };

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

		char tabAtomouche[][] = new char[][] {
				{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' },
				{ 'x', 'x', 'x', 'x', 'x', 'n', 'b', 'v', 'v', 'v', 'x' },
				{ 'x', 'x', 'x', 'x', 'b', 'v', 'v', 'n', 'b', 'n', 'x' },
				{ 'x', 'x', 'x', 'v', 'v', 'n', 'v', 'v', 'v', 'v', 'x' },
				{ 'x', 'x', 'n', 'v', 'b', 'v', 'v', 'v', 'v', 'b', 'x' },
				{ 'x', 'b', 'v', 'v', 'n', 'v', 'b', 'v', 'v', 'n', 'x' },
				{ 'x', 'n', 'v', 'v', 'v', 'v', 'n', 'v', 'b', 'x', 'x' },
				{ 'x', 'v', 'v', 'v', 'v', 'b', 'v', 'v', 'x', 'x', 'x' },
				{ 'x', 'b', 'n', 'b', 'v', 'v', 'n', 'x', 'x', 'x', 'x' },
				{ 'x', 'v', 'v', 'v', 'n', 'b', 'x', 'x', 'x', 'x', 'x' },
				{ 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x', 'x' } };

		final Plateau plateauJeu = new Plateau();

		plateauJeu.chargerTab(tabAtomouche);

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
