
public class Egg extends MarketProduct{
	private int numEggs;
	private int pricePerDozen;//all prices are in cents
	
	public Egg(String name, int number, int pricePerDozen) {
		super(name);
		numEggs = number;
		this.pricePerDozen = pricePerDozen;
	}
	//return cost of product in cents
	public int getCost() {
		return (int)(pricePerDozen/12.0*numEggs);
	}
	
	public boolean equals(Object obj) {
		if(!(obj instanceof Egg)) {
			return false;
		}
		Egg temp = (Egg)obj;
		
		if(!temp.getName().equals(this.getName())) {
			return false;
		}
		if(temp.pricePerDozen!=this.pricePerDozen) {
			return false;
		}
		if(temp.numEggs!=this.numEggs) {
			return false;
		}
		return true;
		
	}
	
}
