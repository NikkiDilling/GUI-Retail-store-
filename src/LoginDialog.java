import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class LoginDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField_Username;
	private JPasswordField passwordField;
	String userName;


	/**
	 * Create the dialog.
	 */
	public LoginDialog() {
		setBounds(100, 100, 402, 270);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{112, 189, 0};
		gbl_contentPanel.rowHeights = new int[]{29, 36, 19, 45, 19, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewLabel = new JLabel("Login");
			lblNewLabel.setIcon(new ImageIcon("C:\\Users\\ndill\\eclipse-workspace\\Final Project\\login-icon.png"));
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 0;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			JLabel lblUsername = new JLabel("Username:");
			lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
			GridBagConstraints gbc_lblUsername = new GridBagConstraints();
			gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
			gbc_lblUsername.gridx = 0;
			gbc_lblUsername.gridy = 2;
			contentPanel.add(lblUsername, gbc_lblUsername);
		}
		{
			textField_Username = new JTextField();
			GridBagConstraints gbc_textField_Username = new GridBagConstraints();
			gbc_textField_Username.anchor = GridBagConstraints.NORTH;
			gbc_textField_Username.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_Username.insets = new Insets(0, 0, 5, 0);
			gbc_textField_Username.gridx = 1;
			gbc_textField_Username.gridy = 2;
			contentPanel.add(textField_Username, gbc_textField_Username);
			textField_Username.setColumns(10);
		}
		{
			JLabel lblNewLabelPasword = new JLabel("Password:");
			lblNewLabelPasword.setFont(new Font("Tahoma", Font.PLAIN, 14));
			GridBagConstraints gbc_lblNewLabelPasword = new GridBagConstraints();
			gbc_lblNewLabelPasword.insets = new Insets(0, 0, 0, 5);
			gbc_lblNewLabelPasword.gridx = 0;
			gbc_lblNewLabelPasword.gridy = 4;
			contentPanel.add(lblNewLabelPasword, gbc_lblNewLabelPasword);
		}
		{
			passwordField = new JPasswordField();
			GridBagConstraints gbc_passwordField = new GridBagConstraints();
			gbc_passwordField.anchor = GridBagConstraints.NORTH;
			gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
			gbc_passwordField.gridx = 1;
			gbc_passwordField.gridy = 4;
			contentPanel.add(passwordField, gbc_passwordField);
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
							//saving the user name
							//only used if login is successful to pass as parameter
							userName = textField_Username.getText();
							FileInputStream stream = new FileInputStream("userData.txt");
							Scanner buffer = new Scanner(stream);
							
							//Creating a linked list to store true conditions if the user input and stream inputs match
							LinkedList conditions = new LinkedList<Boolean>();
							
							
							while(buffer.hasNext()) {
								
								//Saving the scanned line to a string
								String line = buffer.nextLine();
								//Saving the two split tokens in an array 
								//(the first 2 values are updated each while loop)
								String [] tokens = line.split(":");
								
								//Checking if the first token is saved/read as User name 
								if(tokens[0].equals("UserName")) {
									if(tokens[1].equals(textField_Username.getText())){
										//Adding a true condition if user input matches with the data in the file
										conditions.add(true);
										
									}
								}//End of if
								
								
								//Checking if the first token is saved/read as Password 
								if(tokens[0].equals("Password")) {
									if(tokens[1].equals(passwordField.getText())) {
										//Adding a true condition if user input matches with the data in the file
										conditions.add(true);
									}
								}//End of if
								
								//checking if the linked list has exactly 2 values
								if(conditions.size() == 2) {
									JOptionPane.showMessageDialog(LoginDialog.this, "Login was successful");
									//Creating the vendor page and passing the store name as parameter
									VendorPage vendorPage = new VendorPage(userName);
									vendorPage.setVisible(true);
									//Closing the login dialog
									dispose();
									
								}
								
							}//End of while
								
							//Throws exception if there are not 2 values in the linked list at the end of reading the document
								if(conditions.size() != 2) {
									throw new Exception("Incorrect username or password");
								}
								
						} catch (Exception ex) {
							//Printing the error message in a pop up dialog
							JOptionPane.showMessageDialog(LoginDialog.this, ex.getMessage());
							//ex.printStackTrace();
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
						//Opening main page
						mainApplication main = new mainApplication();
						main.setVisible(true);
						//Closing login window
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
