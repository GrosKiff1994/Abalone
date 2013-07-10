
public class Case {

	private boolean occupee;
	private Boule boule;
	
	public Case(){
		this.occupee = false;
	}
	
	public void setBoule(Boule boule){
		this.boule = boule;			
	}
	
	public Boule getBoule(){
		return this.boule;			
	}
	
	public void setOccupee(boolean occupee){
		this.occupee = occupee;
	}
	
	public boolean estOccupee(){
		return this.occupee;
	}
	
}
