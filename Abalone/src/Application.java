import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import objects.Joueur;
import core.Partie;
import display.FenetreAbalone;

public class Application {

	static Joueur gagnant = null;

	public static void play(String filename) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(filename)));
			clip.start();
		} catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
	}

	public static void main(String[] args) {
		// play("sound.wav");
		FenetreAbalone fenetre = new FenetreAbalone();
		Partie laPartie = new Partie(fenetre);
		fenetre.setVisible(true);
		laPartie.lancerPartie();
	}
}
