import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;


public class addToOrder extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private Clothing clothesToAdd;
	private JLabel lblPriceDisplay;
	//Globally created thread for calculating and updating total price
	private Thread thread;

	
	//creating dialog and passing values from the list in Vendor Page
	public addToOrder(JFrame parentFrame, JTextArea mainTextArea, Order newOrder,String itemName, String price) {
		super(parentFrame, true);		
		
		setTitle("Add item");
		setBounds(100, 100, 383, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.NORTH);
			{
				JLabel lblNewLabel = new JLabel("Order: ");
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
				panel.add(lblNewLabel);
			}
			{
				JLabel lblNewLabel_1 = new JLabel(newOrder.getOrderNumber());
				lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
				panel.add(lblNewLabel_1);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{63, 69, 156, 66, 0};
			gbl_panel.rowHeights = new int[]{30, 17, 35, 23, 0, 35, 17, 0};
			gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JLabel lblItem = new JLabel("Item to add: ");
				lblItem.setFont(new Font("Tahoma", Font.PLAIN, 14));
				GridBagConstraints gbc_lblItem = new GridBagConstraints();
				gbc_lblItem.anchor = GridBagConstraints.NORTHWEST;
				gbc_lblItem.insets = new Insets(0, 0, 5, 5);
				gbc_lblItem.gridx = 1;
				gbc_lblItem.gridy = 1;
				panel.add(lblItem, gbc_lblItem);
			}
			{
				JLabel lblItemName = new JLabel(itemName);
				lblItemName.setFont(new Font("Tahoma", Font.PLAIN, 14));
				GridBagConstraints gbc_lblItemName = new GridBagConstraints();
				gbc_lblItemName.anchor = GridBagConstraints.NORTH;
				gbc_lblItemName.insets = new Insets(0, 0, 5, 5);
				gbc_lblItemName.gridx = 2;
				gbc_lblItemName.gridy = 1;
				panel.add(lblItemName, gbc_lblItemName);
			}
			{
				JLabel lblNewLabel_2 = new JLabel("Quantity");
				lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
				GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
				gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
				gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel_2.gridx = 1;
				gbc_lblNewLabel_2.gridy = 3;
				panel.add(lblNewLabel_2, gbc_lblNewLabel_2);
			}
			{	
				textField = new JTextField("1");
				textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
				GridBagConstraints gbc_textField = new GridBagConstraints();
				gbc_textField.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField.anchor = GridBagConstraints.NORTH;
				gbc_textField.insets = new Insets(0, 0, 5, 5);
				gbc_textField.gridx = 2;
				gbc_textField.gridy = 3;
				panel.add(textField, gbc_textField);
				textField.setColumns(10);
				
	
			}
			{
				JLabel lblInformation = new JLabel("Tip: press CAPS LOCK to update price");
				lblInformation.setForeground(new Color(128, 128, 128));
				lblInformation.setFont(new Font("Tahoma", Font.PLAIN, 10));
				GridBagConstraints gbc_lblInformation = new GridBagConstraints();
				gbc_lblInformation.insets = new Insets(0, 0, 5, 5);
				gbc_lblInformation.gridx = 2;
				gbc_lblInformation.gridy = 4;
				panel.add(lblInformation, gbc_lblInformation);
			}
			{
				JLabel lblPrice = new JLabel("Price:");
				lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
				GridBagConstraints gbc_lblPrice = new GridBagConstraints();
				gbc_lblPrice.anchor = GridBagConstraints.NORTHWEST;
				gbc_lblPrice.insets = new Insets(0, 0, 0, 5);
				gbc_lblPrice.gridx = 1;
				gbc_lblPrice.gridy = 6;
				panel.add(lblPrice, gbc_lblPrice);
			}
			{
				lblPriceDisplay = new JLabel(price);
				lblPriceDisplay.setFont(new Font("Tahoma", Font.PLAIN, 14));
				GridBagConstraints gbc_lblPriceDisplay = new GridBagConstraints();
				gbc_lblPriceDisplay.insets = new Insets(0, 0, 0, 5);
				gbc_lblPriceDisplay.anchor = GridBagConstraints.NORTH;
				gbc_lblPriceDisplay.gridx = 2;
				gbc_lblPriceDisplay.gridy = 6;
				panel.add(lblPriceDisplay, gbc_lblPriceDisplay);
			}
			{
				JLabel lblCurrency = new JLabel("USD");
				lblCurrency.setFont(new Font("Tahoma", Font.BOLD, 14));
				GridBagConstraints gbc_lblCurrency = new GridBagConstraints();
				gbc_lblCurrency.anchor = GridBagConstraints.WEST;
				gbc_lblCurrency.gridx = 3;
				gbc_lblCurrency.gridy = 6;
				panel.add(lblCurrency, gbc_lblCurrency);
			}
		}
		{
			
			//creating a clothing item in the class Clothing
			//passing item name and quantity
			//Quantity is read from user input in text field
			clothesToAdd = new Clothing(itemName,Integer.parseInt(textField.getText()));
			//Setting the price per item variable to use in the other thread
			clothesToAdd.setPricePerItem(Integer.parseInt(price));
			
			//Adding an action to text field on key "Enter"
			textField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode()==KeyEvent.VK_CAPS_LOCK){
						//System.out.println("Enter pressed");
						
						//MULTITHREADING
						//Run Thread			
						slowTask task = new slowTask();
						thread = new Thread (task);
						thread.start();
											
					}
					
					
				}
			});
			
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//updating quantity
						clothesToAdd.setQuantity(Integer.parseInt(textField.getText()));
						//setting the total price of the clothing
						clothesToAdd.setTotalPrice();
						//adding the clothing item in an ArrayList in the Order class
						newOrder.setItemsArray(clothesToAdd);
						//closing the thread if it has been initialized
						if(thread != null) {
							thread.stop();
						}
						mainTextArea.append("Item: " + clothesToAdd.getItemName() + "added to order." + "\n");
						mainTextArea.append("Quantity added: " + clothesToAdd.getQuantity() + "\n");
						mainTextArea.append("Price: " + clothesToAdd.getTotalPrice() + "\n" );
						mainTextArea.append("=====================================================" + "\n");
						System.out.println("(addToOrder) item added");
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//Closing the dialog without saving any data
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	//Multithreading method for calculating and displaying price of an item
	public class slowTask implements Runnable{

		@Override
		public void run() {
			
			clothesToAdd.setQuantity(Integer.parseInt(textField.getText()));
			//updating the price label
			lblPriceDisplay.setText("" + clothesToAdd.calculateTotalPrice());
		
		}
		
	}

}//End of Class


