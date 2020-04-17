
public class Jam extends MarketProduct{
	private int numJars;
	private int pricePerJar; //cents
	
	public Jam(String name, int numJars, int pricePerJar) {
		super(name);
		this.numJars = numJars;
		this.pricePerJar = pricePerJar;
		
	}
	
	public int getCost() {
		return pricePerJar*numJars;
	}
	
	public boolean equals(Object obj) {
		if(!(obj instanceof Jam)) {
			return false;
		}
		Jam temp = (Jam)obj;
		
		if(!temp.getName().equals(this.getName())) {
			return false;
		}
		if(temp.pricePerJar!=this.pricePerJar) {
			return false;
		}
		if(temp.numJars!=this.numJars) {
			return false;
		}
		return true;
	}
	
}
