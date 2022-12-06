import java.util.ArrayList;
import java.util.Random;

public class Order {
	
	private final String orderNumber;
	private ArrayList<Clothing> itemsArray = new ArrayList<>();
	
	
	//Creating an order with an order number
	public Order() {
	
		Random r = new Random();
		//generating random numbers from 100 to 200
		int number = r.nextInt(100)+100;
		
		//assigning a random number to an order to make "unique" order number
		this.orderNumber = "O-" + number;
		//debugging
		System.out.println("from order class: " + this.orderNumber);
	}
	
	public String getOrderNumber() {
		return orderNumber;
	}

	public ArrayList<Clothing> getItemsArray() {
		return itemsArray;
	}

	public void setItemsArray(Clothing itemToAdd) {
		this.itemsArray.add(itemToAdd);
	}	

	

}
