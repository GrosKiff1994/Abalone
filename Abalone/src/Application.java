import Abalone.FenetreAbalone;
import Abalone.Plateau;

public class Application {

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

		Plateau plateauJeu = new Plateau();

		plateauJeu.chargerTab(tabClassique);

		System.out.println(plateauJeu.toString());

		new FenetreAbalone(plateauJeu);

	}

}
