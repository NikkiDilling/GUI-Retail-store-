
public class Clothing {
	
	//Variable to use in addToOrder JDialog
	private String itemName;
	private int quantity;
	private int pricePerItem;
	private int totalPrice;
	
	//Constructor
	public Clothing(String itemName, int quantity) {
		setItemName(itemName);
		setQuantity(quantity);

	}
	

	public final static String[] rawData = new String[]{
			"Summer Dress: S -10",
			"Summer Dress: M -10",
			"Summer Dress: L -10",
			"Little black Dress: S -12",
			"Little black Dress: M -12",
			"Little black Dress: L -12",
			"Rock band T-shirt: S -6",
			"Rock band T-shirt: M -6",
			"Rock band T-shirt: L -6",
			"Reaped jeans: S -10",
			"Reaped jeans: M -10",
			"Reaped jeans: L -10",
			"White tank top: S -2",
			"White tank top: M -2",
			"White tank top: L -2",
			"Black slim jeans: S -9",
			"Black slim jeans: M -9",
			"Black slim jeans: L -9",
			"Leather jacket: S -20",
			"Leather jacket: M -20",
			"Leather jacket: L -20",
			"Purple blouse: S -8",
			"Purple blouse: M -8",
			"Purple blouse: L -8"		
		
	};
	
	//Getters and setters for class fields
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}
	
	//Method that calculates total price
	public int calculateTotalPrice() {
		return  getPricePerItem() * getQuantity();
	}
	
	//Setting total price
	public void setTotalPrice() {
		this.totalPrice = calculateTotalPrice();
	}

	public int getPricePerItem() {
		return pricePerItem;
	}

	public void setPricePerItem(int pricePerItem) {
		this.pricePerItem = pricePerItem;
	}

}
