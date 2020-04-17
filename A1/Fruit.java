
public class Fruit extends MarketProduct{
	private double weight; //kg
	private int pricePerKilo; //cents
	
	public Fruit(String name, double weight, int pricePerKilo) {
		super(name);
		this.weight = weight;
		this.pricePerKilo = pricePerKilo;
	}
	public int getCost() {
		return (int)(weight*pricePerKilo);
	}
	public boolean equals(Object obj) {
		if(!(obj instanceof Fruit)) {
			return false;
		}
		Fruit temp = (Fruit)obj;
		
		if(!temp.getName().equals(this.getName())) {
			return false;
		}
		if(temp.pricePerKilo!=this.pricePerKilo) {
			return false;
		}
		if(temp.weight!=this.weight) {
			return false;
		}
		return true;
	}
	
}
