import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
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
import java.awt.event.MouseListener;

import javax.swing.JButton;
import java.awt.Insets;
import java.awt.SystemColor;


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
	// variable to check if the client user is initialized (user data file exists)
	private Client currentUser;
	//variable to save the client read from file
	private Client userFromFile;
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
	//variable for saving index of the last item price added to totalOrderPrice in the thread
	private int indexOfLastItem = 0;
		
	private JButton btnSaveOrder;
	private JMenu mnNewMenu_2;
	private JMenuItem menu_showItems;
	//downcasted later to mouseAdapter to add and remove mouse listener from JList
	//needed to change between action in "show orders" and "show items
	private MouseListener mL;
	private JMenu menuFile;
	private JMenuItem menuResetTextArea;
	private JMenuItem menu_sortItems;
	
	private FileInputStream inputStream; //global variable for reading from file
	private ObjectInputStream binaryReader; //global variable to pass inputstream to 
	private JMenuItem menu_LogOut;
	private JButton btnCancelOrder;
	
	/**
	 * Create the frame.
	 * @throws FileNotFoundException 
	 */
	//crating a constructor and passing a parameter from login dialog page
	public VendorPage(String username) throws FileNotFoundException {
		VendorPage.username = username; //setting the user name
		
		//checking if the file for the logged on client data exists
		if(doRead() == true) {
			
				//Initializing streams
			try {
			    inputStream = new FileInputStream(returnFileLocation()+ "\\" +username +"-Data.data");
				binaryReader = new ObjectInputStream(inputStream);
				
				//initializing the global temporary variable to use when reading information from file
				userFromFile = (Client) binaryReader.readObject();
				
			} catch (IOException | ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		setTitle("Store Order Managment System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 823, 523);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		menuResetTextArea = new JMenuItem("Clear text area");
		menuResetTextArea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//resetting text area
				mainTextArea.setText("");
			}
		});
		menuFile.add(menuResetTextArea);
		
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
		
		//function that performs log out (unsaved information will be lost)
		menu_LogOut = new JMenuItem("Log out");
		menu_LogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showConfirmDialog(VendorPage.this, "All unsaved information will be lost");
				//creating a new main frame
				mainApplication main = new mainApplication();
				//closing the current frame
				dispose();
			}
		});
		mnNewMenu.add(menu_LogOut);
		mnNewMenu.add(menu_changeAccInformation);
		
		mnNewMenu_2 = new JMenu("Items");
		menuBar.add(mnNewMenu_2);
		
		mnNewMenu_1 = new JMenu("Orders");
		menuBar.add(mnNewMenu_1);
		
		menu_showOrders = new JMenuItem("Show orders");
		menu_showOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					//showing orders if the array is not null
					if(!userFromFile.getClientOrders().isEmpty()) {
						//Fill the ArrayList with data
						
							//resetting array list
							listData.clear();
							for(Order order : userFromFile.getClientOrders()) {
								listData.add(order.getOrderNumber());
							}
							//resetting the list model
							listModel.clear();
							//Pass listData to listModel
							listModel.addAll(listData);
							
							//resetting mouse adapter
							list.removeMouseListener(mL);
							//Adding action to list and initializing mL (mouseListener)
							list.addMouseListener(mL= new MouseAdapter() {
								
								
								@Override
								public void mouseReleased(MouseEvent e) {
									
									try {
										
									//saving index of the selected item in the ArrayList clothing.rawData
									//no need for anything else because list index and order index will always match
									int index = list.getSelectedIndex();
									//variable for storing order object from file, getting it by index from JList
									Order clickedOrder = userFromFile.getClientOrders().get(index);
									
									//Displaying order information
									mainTextArea.append(clickedOrder.getOrderNumber()+" \n");
									//printing order items information
									for(int i = 0; i < clickedOrder.getItemsArray().size();i++) {
										
										Clothing item = clickedOrder.getItemsArray().get(i);
										mainTextArea.append(item.getItemName()+ " Quantity: " + item.getQuantity() +
												" Price: " +item.getTotalPrice() +" \n");
								
									}
									mainTextArea.append("Total order price: " +clickedOrder.getTotalOrderPrice() + " " + userFromFile.getCurrency() +"\n");
									//Order end
									mainTextArea.append("=====================================================" + "\n");
									
	
									}catch(NullPointerException ex){
										ex.printStackTrace();
										
									}
								}
									
							
							});//end of mouse listener
							
					}else {
						throw new IOException("You have no orders. \n Please place an order first");
					}
							
				}catch(IOException ex){
					JOptionPane.showMessageDialog(VendorPage.this, ex.getMessage());
				}
			}
		});
		
		
		mnNewMenu_1.add(menu_showOrders);
		
		menu_showItems = new JMenuItem("Show Items");
		menu_showItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//enabling the sort items menu since items are now displayed
				menu_sortItems.setEnabled(true);
				
				//resetting array list
				listData.clear();
				//Fill the ArrayList with data
				for(String clothingItem : Clothing.rawData) {
					listData.add(clothingItem);
				}
				//resetting the list model
				listModel.clear();
				//Pass listData to listModel
				listModel.addAll(listData);
				
				//Resetting mouse adapter
				list.removeMouseListener(mL);
						

				//Adding action to list and initializing mL (mouseListener)
				list.addMouseListener(mL = new MouseAdapter() {
					
					//Throws exception if clicked before creating an order		
					@Override
					public void mouseClicked(MouseEvent event) {
						
						try {
												
						//saving index of the selected item in the ArrayList
						int indexClothing = list.getSelectedIndex();
						//getting value of the item by its index
						String value = listModel.get(indexClothing);
						//splitting the value string into 2 strings
						String [] splits = value.split("-");

						System.out.println("(VENDOR) openning add to order");
					
						//Creating add to order dialog and passing item and order information
						addToOrder addDialog = new addToOrder(VendorPage.this, mainTextArea, newOrder, splits[0], splits[1]);				
						
							//Enabling calculate button when an order has an item in it	
							btnCalculate.setEnabled(true);
							//cannot save order without calculating total order price
							//that's how total order price get's set
							btnSaveOrder.setEnabled(false);
							//disabling menu item "new order" when ordering is in process
							menuNewOrder.setEnabled(false);
						}catch(NullPointerException ex){
							JOptionPane.showMessageDialog(VendorPage.this, "Please create a new order first!" + "\n" +"Orders -> New Order");
							ex.printStackTrace();
						}
					}
					
				});	//end of mouse event/listener
				
				
				

			}//end of action performed
		}); 
		mnNewMenu_2.add(menu_showItems);
		
		menu_sortItems = new JMenuItem("Sort Items");
		//disabling the sort Items menu until show items is clicked again
		menu_sortItems.setEnabled(false);
		menu_sortItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//sorting the list alphabetically
				Collections.sort(listData);
				listModel.clear();
				listModel.addAll(listData);
				
				//disabling the menu again until show items is clicked again
				menu_sortItems.setEnabled(false);
			}
		});
		mnNewMenu_2.add(menu_sortItems);
			
		menuNewOrder = new JMenuItem("New Order");
		menuNewOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
					newOrder = new Order();
					mainTextArea.append("Order: " + newOrder.getOrderNumber()+ " is created" + "\n");
					mainTextArea.append("=====================================================" + "\n");
					//we can cancel an order when there is a new order
					btnCancelOrder.setEnabled(true);
					
			}
		});
		mnNewMenu_1.add(menuNewOrder);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		infoPane = new JPanel();
		infoPane.setBackground(SystemColor.info);
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
		gbl_panelOther.columnWidths = new int[]{243, 76, 68, 74, 142, 0, 132, 0, 0};
		gbl_panelOther.rowHeights = new int[]{25, 0, 0, 0};
		gbl_panelOther.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelOther.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelOther.setLayout(gbl_panelOther);
		
		btnCalculate = new JButton("Calculate total price");
		//disabling the calculate button until there is an order with >0 items in it
		btnCalculate.setEnabled(false);
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
							
				try {
					System.out.println("(VENDOR_btn) starting thread");
					//Starting the thread that calculates and displays the total order price
					threadTask task = new threadTask();
					thread = new Thread(task);
					thread.start();
					
					//button calculating is disabled after calculating
					btnCalculate.setEnabled(false);
					//enabling to save the order after calculating
					btnSaveOrder.setEnabled(true);
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
		gbc_btnCalculate.insets = new Insets(0, 0, 5, 5);
		gbc_btnCalculate.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnCalculate.gridx = 6;
		gbc_btnCalculate.gridy = 0;
		panelOther.add(btnCalculate, gbc_btnCalculate);
		
		btnSaveOrder = new JButton("Save order");
		//disabling the save btn if there is no new order
		btnSaveOrder.setEnabled(false);
		btnSaveOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//disabling the save btn after it was clicked
				//so the same order is not saved twice
				btnSaveOrder.setEnabled(false);
				
				try {

						//Initializing the current user information from the file and passed
						//the old user will be rewritten
						currentUser = new Client(username,userFromFile.getClientOrders(), userFromFile.getStoreName(),userFromFile.getMoneySpent());
						
						//setting current users budget if the userFromFile budget is not zero
						if(userFromFile.getBudget() != 0) {
							currentUser.setBudget(userFromFile.getBudget());
							currentUser.setCurrency(userFromFile.getCurrency());

						}
						
						//setting the spent amount for the first time (if there were no previous orders
						if(userFromFile.getMoneySpent() == 0.0) {
							currentUser.setMoneySpent(newOrder.getTotalOrderPrice());
						}else {
							//calculating and setting current users money spent (if the client had already placed orders)
							currentUser.setMoneySpent(userFromFile.getMoneySpent() + newOrder.getTotalOrderPrice());
						}
						
						//displaying the money spent in the information bar (yellow)
						lblSpentDisplay.setText(currentUser.getMoneySpent()+" " + userFromFile.getCurrency() + " ");

					//adding the current order to client arrayList
					currentUser.getClientOrders().add(newOrder);
					
						//opening output stream and writer
						FileOutputStream outputStream = new FileOutputStream(returnFileLocation()+ "\\" +username +"-Data.data",false);
						ObjectOutputStream binaryWriter = new ObjectOutputStream(outputStream);
						//saving user information (writing user to file)
						binaryWriter.writeObject(currentUser);
						
						//Printing information about saving order to user
						mainTextArea.append("Order: " + newOrder.getOrderNumber() + " is saved!\n");
						mainTextArea.append("=====================================================" + "\n");
						mainTextArea.append("To add more items please create new order. \n");
						mainTextArea.append("=====================================================" + "\n");
						//debugging
						System.out.println("current user orders saved" + currentUser.getClientOrders());
													
							//enabling menu item "new order" after order was saved
							menuNewOrder.setEnabled(true);
							//disabling cancel order after the order is saved
							btnCancelOrder.setEnabled(false);
							
						//Resetting the calculation label
						lblDisplayPrice.setText("-");
						//resetting the counter that is used in the thread after the order was saved
						indexOfLastItem=0;
						
						
						
				}catch(IOException ex) {
										
						ex.printStackTrace();
					
					
				}
				
			}
		});
		btnSaveOrder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnSaveOrder = new GridBagConstraints();
		gbc_btnSaveOrder.insets = new Insets(0, 0, 5, 5);
		gbc_btnSaveOrder.gridx = 6;
		gbc_btnSaveOrder.gridy = 1;
		panelOther.add(btnSaveOrder, gbc_btnSaveOrder);
		
		btnCancelOrder = new JButton("Cancel Order");
		//by default disabling this button
		btnCancelOrder.setEnabled(false);
		btnCancelOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//printing information to user
				mainTextArea.append("Order: " + newOrder.getOrderNumber() + " is cancelled\n");
				mainTextArea.append("=====================================================" + "\n");
				//disabling save the order & cancel order after it was cancelled
				btnSaveOrder.setEnabled(false);
				btnCancelOrder.setEnabled(false);
				//you can create a new order after the last one was it's cancelled
				menuNewOrder.setEnabled(true);
			}
		});
		btnCancelOrder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnCancelOrder = new GridBagConstraints();
		gbc_btnCancelOrder.fill = GridBagConstraints.VERTICAL;
		gbc_btnCancelOrder.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelOrder.gridx = 6;
		gbc_btnCancelOrder.gridy = 2;
		panelOther.add(btnCancelOrder, gbc_btnCancelOrder);
		
		//Reading user information from userData.txt and displaying it 
		//used for reading displaying the information when first opening the file
		try {
				//Setting the page information from client that is read from file
				lblStoreNameDisplay.setText(userFromFile.getStoreName()+" ");
				if(userFromFile.getBudget() != 00.00) {
					lblBudgetDisplay.setText(userFromFile.getBudget()+" "+userFromFile.getCurrency()+ " ");
				}else {
					lblBudgetDisplay.setText("-");
				}
				
				//setting spent in the information bar if the client has spent money (has orders)
				if(!userFromFile.getClientOrders().isEmpty()) {
					lblSpentDisplay.setText(userFromFile.getMoneySpent()+" "+userFromFile.getCurrency()+ " ");
				}
				//debugging
				System.out.println("(542)money spent file: " + userFromFile.getMoneySpent());
				
				//closing streams initialized in the begging of the frame
				inputStream.close();
				binaryReader.close();
			
		}catch(Exception ex) {
			
		}
		
		//Setting the frame to be visible
		setVisible(true);
		
		}

		
		
	
	//Thread that calculates total price of the order and displays it
	public class threadTask implements Runnable{

		@Override
		public void run() {
			
	
			//saving currency from a global variable that serves as a temporary user that is read from file
			String currency = userFromFile.getCurrency();
			
			System.out.println("(THREAD) calculating");
			//saving the current order price in a variable (default value)
			double totalOrderPrice = newOrder.getTotalOrderPrice();
			
			//fixing double calculation with this if-else-statement
			if(indexOfLastItem == 0) {
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
							newOrder.setLabelTotalPrice(lblDisplayPrice, "USD");
							//updating the index
							indexOfLastItem++;
							System.out.println("index of item: " + indexOfLastItem);
					} catch (InterruptedException e) {
					
						e.printStackTrace();
					}
				}//end of for each loop
				
						//converting USD currency of the order to clients budget currency
						if(currency.equals("KRW")) {
							double usdToKrw = totalOrderPrice * 1304.27;
							totalOrderPrice = usdToKrw;
						}if(currency.equals("EUR")) {
							double usdToEur = totalOrderPrice * 0.95;
							totalOrderPrice = usdToEur;
						}if(currency.equals("DKK")) {
							double usdToDkk = totalOrderPrice * 7.06;
							totalOrderPrice = usdToDkk;
						}
						//updating the price in order class after converting to other currency
						newOrder.setTotalOrderPrice(totalOrderPrice);
						//updating the label in Vendor class
						newOrder.setLabelTotalPrice(lblDisplayPrice, currency);
			}else {
				//new variale for summing usd price, converting and then adding to the total price
				double newItemsPrice = 0;
				//add the price of the newly added items to the previous calculation of the total order price
				for(int i = indexOfLastItem; i < newOrder.getItemsArray().size(); i++) {
					System.out.println("thread else item:" + newOrder.getItemsArray().get(i).getTotalPrice());
					try {
						//slowing down the thread for smooth display
						thread.sleep(100);
					//saving the price of the current item in USD
					newItemsPrice = newOrder.getItemsArray().get(i).getTotalPrice();
					
					//converting USD currency of the newly added item to clients budget currency
					if(currency.equals("KRW")) {
						double usdToKrw = newItemsPrice * 1304.27;
						newItemsPrice = usdToKrw;
					}if(currency.equals("EUR")) {
						double usdToEur = newItemsPrice * 0.95;
						newItemsPrice = usdToEur;
					}if(currency.equals("DKK")) {
						double usdToDkk = newItemsPrice * 7.06;
						newItemsPrice = usdToDkk;
					}
					//updating the price in order class with the converted single item
					newOrder.setTotalOrderPrice(totalOrderPrice + newItemsPrice);
					System.out.println("total in else: " + totalOrderPrice + newItemsPrice);
					//updating the label in Vendor class
					newOrder.setLabelTotalPrice(lblDisplayPrice, currency);
					//updating the index
					indexOfLastItem++;
					
					}catch (InterruptedException e) {
					
						e.printStackTrace();
					}	
				}//end of for each loop in else			
				
		
			}//end of else
		}
		
	}//end of thread
	
	private boolean doRead() {
		boolean flage = false;
		try {
			FileInputStream inputStream = new FileInputStream(returnFileLocation()+ "\\" +VendorPage.username +"-Data.data");
			ObjectInputStream binaryReader = new ObjectInputStream(inputStream);
				flage = true;
			
				//closing streams
				inputStream.close();
				binaryReader.close();
		} catch (IOException e) {
			
			flage = false;
			
		}
		
		return flage;
	}//end of method
	
	
	public static String returnFileLocation() {
		
		File jarPath=new File(SignUpDialog.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		System.out.println(" class path: "+ jarPath);
		return jarPath +"";
	}
	
	
	
}//end of Class
