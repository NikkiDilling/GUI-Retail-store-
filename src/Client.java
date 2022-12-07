import java.io.Serializable;
import java.util.ArrayList;

//Class that saves clients order list as a binary file
public class Client implements Serializable{
	//add final keyword to user name!
	private String username;
	private ArrayList <Order> clientOrders = new ArrayList <Order>();
	private String storeName;
	private Double budget;
	private String currency;
	private Double moneySpent;
	
	//constructor
	public Client(String username) {
		setUsername(username);
	}
	
	//constructor overloading
		public Client(String username, ArrayList<Order> clientOrders, String storeName, Double moneySpent) {
		super();
		this.username = username;
		this.clientOrders = clientOrders;
		this.storeName = storeName;
		this.moneySpent = moneySpent;
	}

		public Double getMoneySpent() {
			return moneySpent;
		}

		public void setMoneySpent(Double moneySpent) {
			this.moneySpent = moneySpent;
		}

		public String getStoreName() {
			return storeName;
		}

		public void setStoreName(String storeName) {
			this.storeName = storeName;
		}

		public double getBudget() {
			return budget;
		}

		public void setBudget(double budget) {
			this.budget = budget;
		}

		public String getCurrency() {
			return currency;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public ArrayList<Order> getClientOrders() {
		return clientOrders;
	}

	//setter for array list
	public void setClientOrders(ArrayList <Order> clientOrders){
		this.clientOrders = clientOrders;
	}
		

}
