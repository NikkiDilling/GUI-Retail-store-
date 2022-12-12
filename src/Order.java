import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
	private double totalPrice = 0;
	//creating SwingWorker as a global variable
	private SwingWorker<Boolean, Integer> worker;

	//Creating an order with an order number
	public Order() {
	
		Random r = new Random();
		//generating random numbers from 100 to 200
		int number = r.nextInt(99)+200;
		
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
	
	
	public double getTotalOrderPrice() {
		System.out.println("(ORDER)total price of order: " + totalPrice);
		return totalPrice;
	}
	
	public void setTotalOrderPrice(double totalOrderPrice) {
		this.totalPrice = round(totalOrderPrice,2);
	}

	//method that sets order price in a label in Vendor page
	//used in a thread
	public void setLabelTotalPrice(JLabel lblDisplayPrice, String currency) {
		
		lblDisplayPrice.setText(getTotalOrderPrice()+ " " + currency);
		
		
	}
	
	//solution function to limit decimal places in double values
	//from stackoverflow
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}


}
