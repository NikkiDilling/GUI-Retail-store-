import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JSplitPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.Insets;


public class VendorPage extends JFrame {

	
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenu mnNewMenu_1;
	private JMenuItem menu_changeAccInformation;
	private JPanel infoPane;
	private JLabel lblStore;
	private static String username; //saving the username that is passed to the frame
	//variables to display user information
	private String storeName;
	private String budget;
	private String currency;
	private JMenuItem menu_showOrders;
	 // variable to check if the client user is initialized (order saved, user data file exists)
	private Client currentUser;
	//List variables
	private JList<String> list;//receives data from listModel
	//receives data from listData
	private DefaultListModel<String> listModel = new DefaultListModel<>();
	private List<String> listData = new ArrayList <String>();
	
	private JSplitPane splitPane;
	private JScrollPane scrollPane_Clothing;
	private JScrollPane scrollPane_Action;
	private JTextArea mainTextArea;
	private JPanel panelOther;
	private JLabel lblBudget;
	private JLabel lblStoreNameDisplay;
	private JLabel lblBudgetDisplay;
	private JLabel lblSpent;
	private JLabel lblSpentDisplay;
	private JMenuItem menuNewOrder;
	//Value for current new order
	//updated with each new order
	private Order newOrder;
	private JLabel lblTotalPrice;
	private JLabel lblDisplayPrice;
	private JButton btnCalculate;
	//Creating a thread as a global variable
	private Thread thread;
	
	private JButton btnSaveOrder;
	private JMenu mnNewMenu_2;
	private JMenuItem menu_showItems;

	/**
	 * Create the frame.
	 * @throws FileNotFoundException 
	 */
	//crating a constructor and passing a parameter from login dialog page
	public VendorPage(String username) throws FileNotFoundException {
		VendorPage.username = username; //setting the user name
		
		setTitle("Order Managment");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 752, 523);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnNewMenu = new JMenu("Account");
		menuBar.add(mnNewMenu);
		
		menu_changeAccInformation = new JMenuItem("Change information");
		menu_changeAccInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Creating a pop up dialog to edit information from userData.txt
				//passing user name String
				editAccount editDialog = new editAccount(username);
				editDialog.setVisible(true);
				dispose();
			}
		});
		mnNewMenu.add(menu_changeAccInformation);
		
		mnNewMenu_2 = new JMenu("Items");
		menuBar.add(mnNewMenu_2);
		
		mnNewMenu_1 = new JMenu("Orders");
		menuBar.add(mnNewMenu_1);
		
		menu_showOrders = new JMenuItem("Show orders");
		menu_showOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					FileInputStream inputStream = new FileInputStream("bin\\" +username +"-Data.data");
					ObjectInputStream binaryReader = new ObjectInputStream(inputStream);
				
					//Initializing a client if the client information exists
					Client userFromFile = (Client) binaryReader.readObject();
					
					//showing orders if the array is not null
					if(!userFromFile.getClientOrders().isEmpty()) {
						//Fill the ArrayList with data
							listData.clear();
							for(Order order : userFromFile.getClientOrders()) {
								listData.add(order.getOrderNumber());
							}
							//resetting the list model
							listModel.clear();
							//Pass listData to listModel
							listModel.addAll(listData);
							
							//Adding action on click to list items
							list.addMouseListener(new MouseAdapter() {
								
								
								@Override
								public void mouseReleased(MouseEvent e) {
									
									try {
															
									//saving index of the selected item in the ArrayList clothing.rawData
									int index = list.getSelectedIndex();
									//no need for anything else because list index and order index will always match
									Order clickedOrder = userFromFile.getClientOrders().get(index);
									
									//Displaying order information
									//resets text area
									mainTextArea.setText(clickedOrder.getOrderNumber()+" \n");
									//printing order items information
									for(int i = 0; i < clickedOrder.getItemsArray().size();i++) {
										
										Clothing item = clickedOrder.getItemsArray().get(i);
										mainTextArea.append(item.getItemName()+ " Quantity: " + item.getQuantity() +
												" Price: " +item.getTotalPrice() +" \n");
									}
									//Order end
									mainTextArea.append("=====================================================" + "\n");
									
	
									}catch(NullPointerException ex){
										JOptionPane.showMessageDialog(VendorPage.this, ex.getMessage());
										ex.printStackTrace();
										
									}
								}
									
							
							});
							
							
					}else {
						throw new IOException("You have no orders. \n Please place an order first");
					}
							
				}catch(IOException ex){
					JOptionPane.showMessageDialog(VendorPage.this, ex.getMessage());
				}catch(ClassNotFoundException ex){
					ex.printStackTrace();
				}
			}
		});
		
		
		mnNewMenu_1.add(menu_showOrders);
		
		menu_showItems = new JMenuItem("Show Items");
		menu_showItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				listData.clear();
				//Fill the ArrayList with data
				for(String clothingItem : Clothing.rawData) {
					listData.add(clothingItem);
				}
				//resetting the list model
				listModel.clear();
				//Pass listData to listModel
				listModel.addAll(listData);
				
				//initialize JList
				//list = new JList(listModel);
				
				
					//Adding action on click to list items
				list.addMouseListener(new MouseAdapter() {
					
					//Throws exception if clicked before creating an order		
					@Override
					public void mouseClicked(MouseEvent e) {
						
						try {
												
						//saving index of the selected item in the ArrayList
						int index = list.getSelectedIndex();
						//getting value of the item by its index
						String value = listModel.get(index);
						//splitting the value string into 2 strings
						String [] splits = value.split("-");
						
						//debugging
						/*
						System.out.println("from vendor - [0]: " + splits[0]);
						System.out.println("from vendor - [1]: " + splits[1]);
						*/
						System.out.println("(VENDOR) openning add to order");
						
						//Creating add to order dialog and passing item and order information
						addToOrder addDialog = new addToOrder(VendorPage.this, mainTextArea, newOrder, splits[0], splits[1]);
						addDialog.setVisible(true);
						
							//Enabling buttons the calculate button when an order has an item in it	
							btnCalculate.setEnabled(true);
							btnSaveOrder.setEnabled(true);	
						}catch(NullPointerException ex){
							JOptionPane.showMessageDialog(VendorPage.this, "Please create a new order first!" + "\n" +"Orders -> New Order");
							ex.printStackTrace();
						}
					}
						
				
				});
			}
		});
		mnNewMenu_2.add(menu_showItems);
			
		menuNewOrder = new JMenuItem("New Order");
		menuNewOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newOrder = new Order();
				mainTextArea.append("Order: " + newOrder.getOrderNumber()+ " is created" + "\n");
				mainTextArea.append("=====================================================" + "\n");
				
			
			}
		});
		mnNewMenu_1.add(menuNewOrder);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		infoPane = new JPanel();
		contentPane.add(infoPane, BorderLayout.NORTH);
		
		//displaying user data
				lblStore = new JLabel("Store: ");
				lblStore.setFont(new Font("Tahoma", Font.PLAIN, 14));
				infoPane.add(lblStore);
				
				lblStoreNameDisplay = new JLabel(storeName + " ");
				lblStoreNameDisplay.setFont(new Font("Tahoma", Font.ITALIC, 14));
				infoPane.add(lblStoreNameDisplay);
				
				lblBudget = new JLabel("Total budget:");
				lblBudget.setFont(new Font("Tahoma", Font.PLAIN, 14));
				infoPane.add(lblBudget);
				
				//displaying user data
				lblBudgetDisplay = new JLabel(budget + " " + currency + " ");
				lblBudgetDisplay.setFont(new Font("Tahoma", Font.ITALIC, 14));
				infoPane.add(lblBudgetDisplay);
				
				lblSpent = new JLabel("Spent:");
				lblSpent.setFont(new Font("Tahoma", Font.PLAIN, 14));
				infoPane.add(lblSpent);
				
				lblSpentDisplay = new JLabel("00.00 ");
				lblSpentDisplay.setFont(new Font("Tahoma", Font.ITALIC, 14));
				infoPane.add(lblSpentDisplay);
		
		splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		scrollPane_Clothing = new JScrollPane();
		splitPane.setLeftComponent(scrollPane_Clothing);
		
		
		scrollPane_Action = new JScrollPane();
		splitPane.setRightComponent(scrollPane_Action);
		
		mainTextArea = new JTextArea();
		mainTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		scrollPane_Action.setViewportView(mainTextArea);
	
		
		//initialize JList
		list = new JList<String>(listModel);
		list.setFont(new Font("Tahoma", Font.PLAIN, 14));
		//Add JList to the scroll pane
		scrollPane_Clothing.setViewportView(list);

		
		panelOther = new JPanel();
		contentPane.add(panelOther, BorderLayout.SOUTH);
		GridBagLayout gbl_panelOther = new GridBagLayout();
		gbl_panelOther.columnWidths = new int[]{243, 76, 68, 74, 71, 0, 132, 0};
		gbl_panelOther.rowHeights = new int[]{25, 0, 0};
		gbl_panelOther.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelOther.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelOther.setLayout(gbl_panelOther);
		
		btnCalculate = new JButton("Calculate total price");
		//disabling the calculate button until there is an order with >0 items in it
		btnCalculate.setEnabled(false);
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Disabling the calculate button after the calculation stops
				btnCalculate.enable(false);
				
				try {
					System.out.println("(VENDOR_btn) starting thread");
					//Starting the thread that calculates and displays the total order price
					threadTask task = new threadTask();
					thread = new Thread(task);
					thread.start();
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(VendorPage.this, ex.getMessage());
				}
			}
		});
		
		lblTotalPrice = new JLabel("Total Price: ");
		lblTotalPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblTotalPrice = new GridBagConstraints();
		gbc_lblTotalPrice.anchor = GridBagConstraints.WEST;
		gbc_lblTotalPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalPrice.gridx = 3;
		gbc_lblTotalPrice.gridy = 0;
		panelOther.add(lblTotalPrice, gbc_lblTotalPrice);
		
		lblDisplayPrice = new JLabel("-");
		lblDisplayPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblDisplayPrice = new GridBagConstraints();
		gbc_lblDisplayPrice.anchor = GridBagConstraints.WEST;
		gbc_lblDisplayPrice.gridwidth = 2;
		gbc_lblDisplayPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblDisplayPrice.gridx = 4;
		gbc_lblDisplayPrice.gridy = 0;
		panelOther.add(lblDisplayPrice, gbc_lblDisplayPrice);
		btnCalculate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnCalculate = new GridBagConstraints();
		gbc_btnCalculate.insets = new Insets(0, 0, 5, 0);
		gbc_btnCalculate.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnCalculate.gridx = 6;
		gbc_btnCalculate.gridy = 0;
		panelOther.add(btnCalculate, gbc_btnCalculate);
		
		btnSaveOrder = new JButton("Save order");
		//disabling the save btn if there is no new order
		btnSaveOrder.setEnabled(false);
		btnSaveOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
				
					//checking if the file for the client data exists
					if(doRead() == true) {
						
						FileInputStream inputStream = new FileInputStream("bin\\" +username +"-Data.data");
						ObjectInputStream binaryReader = new ObjectInputStream(inputStream);
					
						//Initializing a client if the client information exists
						Client userFromFile = (Client) binaryReader.readObject();
							
							//closing stream
							inputStream.close();
							binaryReader.close();
							
						//Initializing the current user information from the file and passed
						//the old user will be rewritten
						currentUser = new Client(username,userFromFile.getClientOrders(), userFromFile.getStoreName(),userFromFile.getMoneySpent());
						
						if(userFromFile.getBudget() != 0) {
							currentUser.setBudget(userFromFile.getBudget());
							currentUser.setCurrency(userFromFile.getCurrency());

						}
						
							/*
							//debugging
							System.out.println("order from file 1: " + userFromFile.getClientOrders().get(0).getOrderNumber());
							if(userFromFile.getClientOrders().size() >= 2) {
								System.out.println("order from file 2: " + userFromFile.getClientOrders().get(1).getOrderNumber());
								if(userFromFile.getClientOrders().size() > 2) {
								System.out.println("order from file 3: " + userFromFile.getClientOrders().get(2).getOrderNumber());
								}
							}//end of debugging
							*/
					

					//adding the current order to client arrayList
					currentUser.getClientOrders().add(newOrder);
					
						//rewriting the file with new array
						FileOutputStream outputStream = new FileOutputStream("bin\\" +username +"-Data.data",false);
						ObjectOutputStream binaryWriter = new ObjectOutputStream(outputStream);
						//saving user information (writing user to file)
						binaryWriter.writeObject(currentUser);
						System.out.println("current user orders saved" + currentUser.getClientOrders());
						/* debugging
						System.out.println("order 1: " + currentUser.getClientOrders().get(0).getOrderNumber());
						System.out.println("order 2: " + currentUser.getClientOrders().get(1).getOrderNumber());
						if(currentUser.getClientOrders().size() > 2) {
							System.out.println("order 3: " + currentUser.getClientOrders().get(2).getOrderNumber());
						}
						*/
						//Runs only if somehow file does not exist
							
						//closing streams
							outputStream.close();
							binaryWriter.close();
					 	}else {
							System.out.println("file doesn't exist. Creating file");
							//Initializing a client if the client array information doesn't exist
							currentUser = new Client(username);
							
							//adding the current order to client arrayList
							currentUser.getClientOrders().add(newOrder);
							System.out.println("current user orders" + currentUser.getClientOrders());
							
							//creating the file for the first time
							FileOutputStream outputStream = new FileOutputStream("bin\\" +username +"-Data.data",false);
							ObjectOutputStream binaryWriter = new ObjectOutputStream(outputStream);
							//saving user information (writing user to file)
							binaryWriter.writeObject(currentUser);
							
							//closing streams
							outputStream.close();
							binaryWriter.close();
						}
						
								
				}catch(IOException | ClassNotFoundException ex) {
										
						ex.printStackTrace();
					
					
				}
				
			}
		});
		btnSaveOrder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnSaveOrder = new GridBagConstraints();
		gbc_btnSaveOrder.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSaveOrder.gridx = 6;
		gbc_btnSaveOrder.gridy = 1;
		panelOther.add(btnSaveOrder, gbc_btnSaveOrder);
		
		//Reading user information from userData.txt and displaying it
		try {
			if(doRead() == true) {
				
				FileInputStream inputStream = new FileInputStream("bin\\" +username +"-Data.data");
				ObjectInputStream binaryReader = new ObjectInputStream(inputStream);
			
				//Initializing a client if the client information exists
				Client userFromFile = (Client) binaryReader.readObject();
				//Setting the page information from client that is read from file
				lblStoreNameDisplay.setText(userFromFile.getStoreName()+" ");
				lblBudgetDisplay.setText(userFromFile.getBudget()+userFromFile.getCurrency()+ " ");
				lblSpentDisplay.setText(userFromFile.getMoneySpent()+" ");
				
				//closing streams
				inputStream.close();
				binaryReader.close();
			}
		}catch(Exception ex) {
			//error with reading the file
			JOptionPane.showMessageDialog(VendorPage.this, ex.getMessage());
		}
		
		//Setting the frame to be visible
		setVisible(true);
		
		}

		
		
	
	//Thread that calculates total price of the order and displays it
	public class threadTask implements Runnable{

		@Override
		public void run() {
			System.out.println("(THREAD) calculating");
			//saving the current order price in a variable (default value)
			int totalOrderPrice = newOrder.getTotalOrderPrice();
			
			//btn calculating is disabled while calculating
			btnCalculate.setEnabled(false);
			//Going through the order item array and calculating the total price one by one
				for(Clothing item : newOrder.getItemsArray()) {
					
					try {
						//slowing down the thread for smooth display
						thread.sleep(100);
					//calculating final order price
					totalOrderPrice += item.getTotalPrice();
					//updating the price in order class
					newOrder.setTotalOrderPrice(totalOrderPrice);
					//updating the label in Vendor class
					newOrder.setLabelTotalPrice(lblDisplayPrice);
					
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
				
			
			
		
			
		}
		
	}
	
	private boolean doRead() {
		boolean flage = false;
		try {
			FileInputStream inputStream = new FileInputStream("bin\\" +VendorPage.username +"-Data.data");
			ObjectInputStream binaryReader = new ObjectInputStream(inputStream);
				flage = true;
			
				//closing streams
				inputStream.close();
				binaryReader.close();
		} catch (IOException e) {
			
			flage = false;
			
		}
		
		return flage;
	}
	
	
}
