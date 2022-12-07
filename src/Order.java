import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class Order implements Serializable{
	
	private final String orderNumber;
	//Array list for storing all the items of class Clothing in the order
	private ArrayList<Clothing> itemsArray = new ArrayList<>();
	//total price with default value
	private int totalPrice = 0;
	//creating SwingWorker as a global variable
			private SwingWorker<Boolean, Integer> worker;

	//Creating an order with an order number
	public Order() {
	
		Random r = new Random();
		//generating random numbers from 100 to 200
		int number = r.nextInt(100)+100;
		
		//assigning a random number to an order to make "unique" order number
		this.orderNumber = "O-" + number;
		//debugging
		System.out.println("(ORDER) new order added: " + this.orderNumber);
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
	
	
	public int getTotalOrderPrice() {
		System.out.println("(ORDER)total price of order: " + totalPrice);
		return totalPrice;
	}
	
	public void setTotalOrderPrice(int price) {
		this.totalPrice = price;
	}

	//method that sets order price in a label in Vendor page
	//used in a thread
	public void setLabelTotalPrice(JLabel lblDisplayPrice) {
		
		lblDisplayPrice.setText(getTotalOrderPrice()+" USD");
		
		
	}


}
