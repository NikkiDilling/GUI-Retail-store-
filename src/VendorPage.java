import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListModel;
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
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VendorPage extends JFrame {

	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenu mnNewMenu_1;
	private JMenuItem menu_saveReciet;
	private JMenuItem menu_updateBudget;
	private JMenuItem menu_changeAccInformation;
	private JPanel infoPane;
	private JLabel lblStore;
	//variables to display user information
	private String storeName;
	private String budget;
	private String currency;
	private JMenuItem menu_sortOrders;
	//List variables
	private JList list;//receives data from listModel
	//receives data from listData
	private DefaultListModel<String> listModel = new DefaultListModel<>();
	private List<String> listData = new ArrayList <String>();
	private JScrollPane scrollPaneList;
	private JScrollPane scrollPaneAction;
	private JTextArea textArea;
	private JSplitPane splitPane;
	private JScrollPane scrollPane_Clothing;
	private JScrollPane scrollPane_Action;
	private JTextArea mainTextArea;
	private JPanel panelOther;
	private JLabel lblNewLabel_2;
	private JLabel lblBudget;
	private JLabel lblStoreNameDisplay;
	private JLabel lblBudgetDisplay;
	private JLabel lblSpent;
	private JLabel lblSpentDisplay;
	private JMenuItem menuNewOrder;
	//Value for current new order
	//updated with each new order
	private Order newOrder;

	/**
	 * Create the frame.
	 * @throws FileNotFoundException 
	 */
	//crating a constructor and passing a parameter from login dialog page
	public VendorPage(String username) throws FileNotFoundException {
		setTitle("Order Managment");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 752, 523);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnNewMenu = new JMenu("Account");
		menuBar.add(mnNewMenu);
		
		menu_changeAccInformation = new JMenuItem("Change information");
		mnNewMenu.add(menu_changeAccInformation);
		
		menu_updateBudget = new JMenuItem("Update budget ");
		mnNewMenu.add(menu_updateBudget);
		
		mnNewMenu_1 = new JMenu("Orders");
		menuBar.add(mnNewMenu_1);
		
		menuNewOrder = new JMenuItem("New Order");
		menuNewOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newOrder = new Order();
				mainTextArea.append("Order: " + newOrder.getOrderNumber()+ " is created" + "\n");
				mainTextArea.append("=====================================================" + "\n");
			}
		});
		mnNewMenu_1.add(menuNewOrder);
		
		menu_sortOrders = new JMenuItem("Sort orders");
		mnNewMenu_1.add(menu_sortOrders);
		
		menu_saveReciet = new JMenuItem("Save receit");
		mnNewMenu_1.add(menu_saveReciet);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		//Reading user information from userData.txt
		try {
			
			//creating streams
			FileInputStream stream = new FileInputStream("userData.txt");
			Scanner buffer = new Scanner(stream);
			
			//Scanning the document
			while(buffer.hasNext()) {
				//Saving the scanned line to a string
				String line_userName = buffer.nextLine();
				//Saving the two split tokens in an array 
				//(the first 2 values are updated each while loop)
				String [] tokens = line_userName.split(":");
				
				
				if(tokens[0].equals("UserName")) {
					if(tokens[1].equals(username)) {
					/*
						 //debugging
						System.out.println("token[0]: " + tokens[0]);
						System.out.println("token[1]: " + tokens[1]);
					 */
						//go to next line and spilt
						//Saving the scanned line to a string
						String line_storeName = buffer.nextLine();
						//Saving the two split tokens in an array 
						//(the first 2 values are updated each while loop)
						String [] tokens2 = line_storeName.split(":");
						
						//initializing store name
						storeName = tokens2[1];
						
						//go to next line and spilt
						String line_budget = buffer.nextLine();
						String [] tokens3 = line_budget.split(":");
						
						if(tokens3[0].equals("Budget")) {
							//initializing budget
							budget = tokens3[1];
							
							//go to next line and spilt
							String line_currency = buffer.nextLine();
							String [] tokens4 = line_currency.split(":");
							
							if(tokens4[0].equals("Currency")) {
								//initializing currency
								currency = tokens4[1];
							}
						}else {
							budget = "00.00";
						}
						
					}
				}
			}
		}catch(Exception ex) {
			//I'm not sure what this is hah...
			JOptionPane.showMessageDialog(VendorPage.this, ex.getMessage());
		}
		
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
		
		//Fill the ArrayList with data
		for(String clothingItem : Clothing.rawData) {
			listData.add(clothingItem);
		}
		
		//Pass listData to listModel
		listModel.addAll(listData);
		
		//initialize JList
		list = new JList(listModel);
		
		
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
				//System.out.println("from vendor - price: " + splits[1]);
				
				//Creating add to order dialog and passing item and order information
				addToOrder addDialog = new addToOrder(VendorPage.this, newOrder, splits[0], splits[1]);
				
				}catch(NullPointerException ex){
					JOptionPane.showMessageDialog(VendorPage.this, "Please create a new order first!");
					ex.printStackTrace();
				}
			}
				
		
		});
		
		
		
		list.setFont(new Font("Tahoma", Font.PLAIN, 14));
		//Add JList to the scroll pane
		scrollPane_Clothing.setViewportView(list);
		
		scrollPane_Action = new JScrollPane();
		splitPane.setRightComponent(scrollPane_Action);
		
		mainTextArea = new JTextArea();
		mainTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		scrollPane_Action.setViewportView(mainTextArea);
		
		panelOther = new JPanel();
		contentPane.add(panelOther, BorderLayout.SOUTH);
		panelOther.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		
		//Setting the frame to be visible
		setVisible(true);		
		
	}

}
