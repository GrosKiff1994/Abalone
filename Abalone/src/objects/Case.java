package objects;

public class Case {

	private Boule boule;
	private boolean bord;

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

}
