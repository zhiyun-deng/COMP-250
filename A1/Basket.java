
public class Basket {
	private MarketProduct[] array;

	public Basket() {
		array = new MarketProduct[0];
	}
	public MarketProduct[] getProducts() {
		return array.clone();
	}
	public void add(MarketProduct prod) {
		MarketProduct[] temp = new MarketProduct[array.length+1];
		for (int i = 0; i < array.length; i++) {
			temp[i] = array[i];
		}
		temp[array.length] = prod;
		array = temp;
	}

	public boolean remove(MarketProduct prod) {
		int removeIndex = -1;
		boolean needRemove = false;
		for (int i = 0; i < array.length; i++) {
			if(array[i].equals(prod)) {
				needRemove = true;
				removeIndex = i;
				break;
			}
		}
		if(needRemove) {
			MarketProduct[] temp = new MarketProduct[array.length-1];
			for (int i = 0; i < removeIndex; i++) {
				temp[i] = array[i];
			}
			for (int i = removeIndex; i < temp.length; i++) {
				temp[i] = array[i+1];

			}
			array = temp;

		}
		return needRemove;
	}

	public void clear() {
		array = new MarketProduct[0];
	}

	public int getNumOfProducts() {
		return array.length;
	}

	public int getSubTotal() {
		int total = 0;
		for(MarketProduct prod:array) {
			total += prod.getCost();
		}
		return total;
	}

	public int getTotalTax() {
		double total = 0;
		for(MarketProduct prod:array) {
			if(prod instanceof Jam) {
				total += prod.getCost()*0.15;			
			}
		}
		return (int)total;
	}
	
	public int getTotalCost() {
		return (getSubTotal()+getTotalTax());
	}
	public String toString() {
		String receipt = "";
		for(MarketProduct prod:array) {
			receipt += prod.getName() + "\t" + toDollar(prod.getCost());
 		}
		receipt += "\n";
		receipt += "Subtotal\t" + toDollar(getSubTotal());//(getSubTotal()/100.0 + "\n")
		receipt += "Total Tax\t" + toDollar(getTotalTax());
		receipt += "\n";
		receipt += "Total Cost\t" + toDollar(getTotalCost());
		
		return receipt;
		
		
		
	}
	//cents to dollars and cents and if negative, "-", next line
	private String toDollar(int cost) {
		String res = "";
		if(cost<=0) {
			res += "-" + "\n";
		}
		else {
			//res += (cost/100.0 + "\n");
			
			if(cost%100==0) {
				res += cost/100 + ".00" + "\n";
			}
			else{
				res += cost/100 + "." + cost%100 + "\n";
			}
		}
		return res;
		
	}
	
	

}
