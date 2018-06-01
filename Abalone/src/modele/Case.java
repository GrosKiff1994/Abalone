package modele;

import vue.BoutonRond;

public class Case {

	private Boule boule;
	private boolean bord;
	private BoutonRond bouton;

	public Case() {
		boule = null;
		bord = false;
	}

	public void setBoule(Boule boule) {
		this.boule = boule;
	}

	public Boule getBoule() {
		return this.boule;
	}

	public boolean estOccupee() {
		return this.boule != null;
	}

	public void setBord(boolean b) {
		this.bord = b;
	}

	public boolean getBord() {
		return bord;
	}

	public void setBouton(BoutonRond bouton) {
		this.bouton = bouton;
	}

	public BoutonRond getBouton() {
		return bouton;
	}

}
