package objects;


public class Case {

	private Boule boule;

	public Case() {
		boule = null;
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

}
