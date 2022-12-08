import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Scanner;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class editAccount extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField_Store;
	private JTextField textField_Budget;
	//variable for displaying user name
	private JLabel lblUsernameDisplay;
	
	//variable for storing current user object and rewriting bin file
	private Client currentUser;
	//variable for getting user information from file (before edit)
	private Client userFromFile;
	
	
	/**
	 * Create the dialog.
	 */
	public editAccount(String username) {
			
		setTitle("Edit Account Information");
		setBounds(100, 100, 360, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{35, 58, 35, 117, 37, 0};
		gbl_contentPanel.rowHeights = new int[]{35, 13, 0, 31, 16, 19, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblUsername = new JLabel("Username: ");
			GridBagConstraints gbc_lblUsername = new GridBagConstraints();
			gbc_lblUsername.anchor = GridBagConstraints.NORTHWEST;
			gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
			gbc_lblUsername.gridx = 1;
			gbc_lblUsername.gridy = 1;
			contentPanel.add(lblUsername, gbc_lblUsername);
		}
		{
			lblUsernameDisplay = new JLabel("-");
			GridBagConstraints gbc_lblUsernameDisplay = new GridBagConstraints();
			gbc_lblUsernameDisplay.anchor = GridBagConstraints.NORTHWEST;
			gbc_lblUsernameDisplay.insets = new Insets(0, 0, 5, 5);
			gbc_lblUsernameDisplay.gridx = 3;
			gbc_lblUsernameDisplay.gridy = 1;
			contentPanel.add(lblUsernameDisplay, gbc_lblUsernameDisplay);
		}
		{
			JLabel lblInformation = new JLabel("You cannot change your user name");
			lblInformation.setForeground(new Color(128, 128, 128));
			GridBagConstraints gbc_lblInformation = new GridBagConstraints();
			gbc_lblInformation.insets = new Insets(0, 0, 5, 5);
			gbc_lblInformation.gridx = 3;
			gbc_lblInformation.gridy = 2;
			contentPanel.add(lblInformation, gbc_lblInformation);
		}
		{
			JLabel lblStoreName = new JLabel("Store Name:");
			GridBagConstraints gbc_lblStoreName = new GridBagConstraints();
			gbc_lblStoreName.anchor = GridBagConstraints.SOUTHWEST;
			gbc_lblStoreName.insets = new Insets(0, 0, 5, 5);
			gbc_lblStoreName.gridx = 1;
			gbc_lblStoreName.gridy = 3;
			contentPanel.add(lblStoreName, gbc_lblStoreName);
		}
		{
			textField_Store = new JTextField();
			GridBagConstraints gbc_textField_Store = new GridBagConstraints();
			gbc_textField_Store.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_Store.anchor = GridBagConstraints.SOUTH;
			gbc_textField_Store.insets = new Insets(0, 0, 5, 5);
			gbc_textField_Store.gridx = 3;
			gbc_textField_Store.gridy = 3;
			contentPanel.add(textField_Store, gbc_textField_Store);
			textField_Store.setColumns(10);
		}
		{
			JLabel lblBudget = new JLabel("Budget: ");
			GridBagConstraints gbc_lblBudget = new GridBagConstraints();
			gbc_lblBudget.anchor = GridBagConstraints.WEST;
			gbc_lblBudget.insets = new Insets(0, 0, 0, 5);
			gbc_lblBudget.gridx = 1;
			gbc_lblBudget.gridy = 5;
			contentPanel.add(lblBudget, gbc_lblBudget);
		}
		{
			textField_Budget = new JTextField();
			GridBagConstraints gbc_textField_Budget = new GridBagConstraints();
			gbc_textField_Budget.insets = new Insets(0, 0, 0, 5);
			gbc_textField_Budget.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_Budget.anchor = GridBagConstraints.NORTH;
			gbc_textField_Budget.gridx = 3;
			gbc_textField_Budget.gridy = 5;
			contentPanel.add(textField_Budget, gbc_textField_Budget);
			textField_Budget.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							
							FileOutputStream outputStream = new FileOutputStream("bin\\" +username +"-Data.data",false);
							ObjectOutputStream binaryWriter = new ObjectOutputStream(outputStream);
							
														
							//rewriting the user information
							//changing store name if the field is not empty
							if(!textField_Store.getText().isEmpty()) {
								currentUser.setStoreName(textField_Store.getText());
	
							}
							
							//changing budget if the field is not empty
							if(!textField_Budget.getText().isEmpty()) {
								currentUser.setBudget(Double.parseDouble(textField_Budget.getText()));
	
							}
							
							//rewriting/editing current user information (writing user to file)
							binaryWriter.writeObject(currentUser);
							//closing stream
							outputStream.close();
							binaryWriter.close();
							
							//refreshing the vendor page
							VendorPage vendorPage = new VendorPage(username);
							vendorPage.setVisible(true);
							
							//closing the window after saving changes
							dispose();
							
						}catch(IOException ex) {
							ex.printStackTrace();
						}
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
						//opening the vendor page again
						VendorPage vendorPage;
						try {
							vendorPage = new VendorPage(username);
							vendorPage.setVisible(true);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						//closing dialog without making changes
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		try {

			FileInputStream inputStream = new FileInputStream("bin\\" +username +"-Data.data");
			ObjectInputStream binaryReader = new ObjectInputStream(inputStream);
		
			//Initializing a temporary client 
			userFromFile = (Client) binaryReader.readObject();
			//Initializing current user
			currentUser = userFromFile;
			
			//Display uneditable user name
			lblUsernameDisplay.setText(userFromFile.getUsername());
			
			//Getting the value to change: Store Name and setting to text field
			textField_Store.setText(userFromFile.getStoreName());
			
			//Getting the value to change: Budget and setting to text field
			textField_Budget.setText(userFromFile.getBudget()+"");
					
				
		} catch (Exception ex) {
			
			//ex.printStackTrace();
		}
		
		//set visible? or not
	}

}
