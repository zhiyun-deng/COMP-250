
public class Customer {
	private String name;
	private int balance; //cents
	private Basket cart;
	
	public Customer(String name, int balance) {
		this.name = name;
		this.balance = balance;
		cart = new Basket();
		
	}
	
	public String getName() {
		return name;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public Basket getBasket() {
		return cart;
	}
	
	public int addFunds(int addition) {
		if(addition<0) {
			throw new IllegalArgumentException("Funds to be added cannot ne negative.");
			
		}
		else {
			balance += addition;
			return balance;
		}
	}
	
	public void addToBasket(MarketProduct prod) {
		cart.add(prod);
	}
	
	public boolean removeFromBasket(MarketProduct prod) {
		return cart.remove(prod);
	}
	
	public String checkOut() {
		if(balance<cart.getTotalCost()) {
			throw new IllegalStateException();
		}
		else {
			String receipt = cart.toString();
			balance -= cart.getTotalCost();
			cart.clear();
			return receipt;
		}
	}
}
