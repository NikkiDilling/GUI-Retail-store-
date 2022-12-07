import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;

public class SignUpDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldUsername;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldConfirm;
	private JTextField textFieldStoreName;
	private JTextField textFieldBudget;
	private FileOutputStream outputstream;
	private PrintWriter buffer;
	private JSpinner spinnerCurrency;

	private Client registeredClient;
	/**
	 * Create the dialog.
	 */
	public SignUpDialog() {
		setTitle("Registration");
		setBounds(100, 100, 550, 434);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panelImg = new JPanel();
			contentPanel.add(panelImg, BorderLayout.WEST);
			{
				
				JLabel img = new JLabel("");
				img.setIcon(new ImageIcon("C:\\Users\\ndill\\eclipse-workspace\\Final Project\\imgStore2.jpg"));
				panelImg.add(img);
			}
		}
		{
			JPanel panelInformation = new JPanel();
			contentPanel.add(panelInformation, BorderLayout.CENTER);
			GridBagLayout gbl_panelInformation = new GridBagLayout();
			gbl_panelInformation.columnWidths = new int[]{128, 128, 0};
			gbl_panelInformation.rowHeights = new int[]{40, 0, 60, 40, 60, 40, 60, 0, 40, 0};
			gbl_panelInformation.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_panelInformation.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panelInformation.setLayout(gbl_panelInformation);
			{
				JLabel lblName = new JLabel("Store name*");
				lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
				GridBagConstraints gbc_lblName = new GridBagConstraints();
				gbc_lblName.anchor = GridBagConstraints.WEST;
				gbc_lblName.fill = GridBagConstraints.VERTICAL;
				gbc_lblName.insets = new Insets(0, 0, 5, 5);
				gbc_lblName.gridx = 0;
				gbc_lblName.gridy = 1;
				panelInformation.add(lblName, gbc_lblName);
			}
			{
				textFieldStoreName = new JTextField();
				GridBagConstraints gbc_textFieldStoreName = new GridBagConstraints();
				gbc_textFieldStoreName.fill = GridBagConstraints.HORIZONTAL;
				gbc_textFieldStoreName.insets = new Insets(0, 0, 5, 0);
				gbc_textFieldStoreName.gridx = 1;
				gbc_textFieldStoreName.gridy = 1;
				panelInformation.add(textFieldStoreName, gbc_textFieldStoreName);
				textFieldStoreName.setColumns(10);
			}
			{
				JLabel lblUsername = new JLabel("Username*");
				lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
				GridBagConstraints gbc_lblUsername = new GridBagConstraints();
				gbc_lblUsername.fill = GridBagConstraints.BOTH;
				gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
				gbc_lblUsername.gridx = 0;
				gbc_lblUsername.gridy = 2;
				panelInformation.add(lblUsername, gbc_lblUsername);
			}
			{
				textFieldUsername = new JTextField();
				GridBagConstraints gbc_textFieldUsername = new GridBagConstraints();
				gbc_textFieldUsername.fill = GridBagConstraints.HORIZONTAL;
				gbc_textFieldUsername.insets = new Insets(0, 0, 5, 0);
				gbc_textFieldUsername.gridx = 1;
				gbc_textFieldUsername.gridy = 2;
				panelInformation.add(textFieldUsername, gbc_textFieldUsername);
				textFieldUsername.setColumns(10);
			}
			{
				JLabel lblPassword = new JLabel("Password*");
				lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
				GridBagConstraints gbc_lblPassword = new GridBagConstraints();
				gbc_lblPassword.fill = GridBagConstraints.BOTH;
				gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
				gbc_lblPassword.gridx = 0;
				gbc_lblPassword.gridy = 3;
				panelInformation.add(lblPassword, gbc_lblPassword);
			}
			{
				passwordField = new JPasswordField();
				GridBagConstraints gbc_passwordField = new GridBagConstraints();
				gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
				gbc_passwordField.insets = new Insets(0, 0, 5, 0);
				gbc_passwordField.gridx = 1;
				gbc_passwordField.gridy = 3;
				panelInformation.add(passwordField, gbc_passwordField);
			}
			{
				JLabel lblConfirmPassword = new JLabel("Confirm Password*");
				lblConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
				GridBagConstraints gbc_lblConfirmPassword = new GridBagConstraints();
				gbc_lblConfirmPassword.fill = GridBagConstraints.BOTH;
				gbc_lblConfirmPassword.insets = new Insets(0, 0, 5, 5);
				gbc_lblConfirmPassword.gridx = 0;
				gbc_lblConfirmPassword.gridy = 4;
				panelInformation.add(lblConfirmPassword, gbc_lblConfirmPassword);
			}
			{
				passwordFieldConfirm = new JPasswordField();
				GridBagConstraints gbc_passwordFieldConfirm = new GridBagConstraints();
				gbc_passwordFieldConfirm.insets = new Insets(0, 0, 5, 0);
				gbc_passwordFieldConfirm.fill = GridBagConstraints.HORIZONTAL;
				gbc_passwordFieldConfirm.gridx = 1;
				gbc_passwordFieldConfirm.gridy = 4;
				panelInformation.add(passwordFieldConfirm, gbc_passwordFieldConfirm);
			}
			{
				JLabel lblBudget = new JLabel("Budget");
				lblBudget.setFont(new Font("Tahoma", Font.PLAIN, 14));
				GridBagConstraints gbc_lblBudget = new GridBagConstraints();
				gbc_lblBudget.anchor = GridBagConstraints.WEST;
				gbc_lblBudget.insets = new Insets(0, 0, 5, 5);
				gbc_lblBudget.gridx = 0;
				gbc_lblBudget.gridy = 5;
				panelInformation.add(lblBudget, gbc_lblBudget);
			}
			{
				textFieldBudget = new JTextField();
				GridBagConstraints gbc_textFieldBudget = new GridBagConstraints();
				gbc_textFieldBudget.insets = new Insets(0, 0, 5, 0);
				gbc_textFieldBudget.fill = GridBagConstraints.HORIZONTAL;
				gbc_textFieldBudget.gridx = 1;
				gbc_textFieldBudget.gridy = 5;
				panelInformation.add(textFieldBudget, gbc_textFieldBudget);
				textFieldBudget.setColumns(10);
			}
			{
				
			}
			{
				JLabel lblCurrency = new JLabel("Currency");
				lblCurrency.setFont(new Font("Tahoma", Font.PLAIN, 14));
				GridBagConstraints gbc_lblCurrency = new GridBagConstraints();
				gbc_lblCurrency.anchor = GridBagConstraints.WEST;
				gbc_lblCurrency.insets = new Insets(0, 0, 5, 5);
				gbc_lblCurrency.gridx = 0;
				gbc_lblCurrency.gridy = 6;
				panelInformation.add(lblCurrency, gbc_lblCurrency);
			}
			//Values for the spinner
			String currencyArray[]  = {"KRW", "USD" , "EUR", "DKK"};
			//Assigning values to a model that can be passed as a parameter to spinner
			SpinnerModel model = new SpinnerListModel(currencyArray);
			//Initializing spinner with array values
			spinnerCurrency = new JSpinner(model);
			
			GridBagConstraints gbc_spinnerCurrency = new GridBagConstraints();
			gbc_spinnerCurrency.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinnerCurrency.insets = new Insets(0, 0, 5, 0);
			gbc_spinnerCurrency.gridx = 1;
			gbc_spinnerCurrency.gridy = 6;
			panelInformation.add(spinnerCurrency, gbc_spinnerCurrency);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Register");
				//Adding "writing" to the document action from register button
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						try {
							//Initializing the stream and buffer
							outputstream = new FileOutputStream("userData.txt",true);
							buffer = new PrintWriter(outputstream);
							
							//Assigning getter methods into variables, for easier use
							String getStoreName = textFieldStoreName.getText();
							String getUsername = textFieldUsername.getText();
							String getPassword = passwordField.getText();
							String getPasswordConfirm = passwordFieldConfirm.getText();
							
							//saving to text file to use for logging in
							buffer.println("UserName:" + getUsername);
							//Initializing the client (later saved to a binary file)
							registeredClient = new Client(getUsername);
							
							//saving store in second place for easier use of data in VendorPage
							registeredClient.setStoreName(getStoreName);
							
							//Saves budget information if the user decides to fill it out
							if(!textFieldBudget.getText().isEmpty()) {
								
								
							//checking is the values is double, else -> throws NumberFormatException
							Double budgetNumber = Double.parseDouble(textFieldBudget.getText());
							registeredClient.setBudget(budgetNumber);
								//saving currency value to the client
								registeredClient.setCurrency(spinnerCurrency.getValue()+"");
							}
								//Saving default spent number to change later
								registeredClient.setMoneySpent(00.00);
							
							//Exception for not matching passwords
							if(passwordField.getText().equals(passwordFieldConfirm.getText())) {
								buffer.println("Password:" + passwordField.getText());
							}else {
								throw new Exception("Password doesn't match. Please try again");
							}
							
							//Exception for empty fields
							if(getStoreName.isEmpty() || getUsername.isEmpty() || 
									getPassword.isEmpty() || getPasswordConfirm.isEmpty() ) {
								throw new Exception("Please fill out all the fileds marked with: * ");
							}
							
							
													
							//Closing the stream and writer
							buffer.close();
							outputstream.close();
							
							//Saving the registered client for the first time
							//first time writing binary file for registered client
								//each user has unique data file (user name + type of document) in bin folder
								FileOutputStream outputStream = new FileOutputStream("bin\\" + getUsername +"-Data.data",false);
								ObjectOutputStream binaryWriter = new ObjectOutputStream(outputStream);
								//saving user information (writing user to file)
								binaryWriter.writeObject(registeredClient);
							
							JOptionPane.showMessageDialog(SignUpDialog.this, "Thank you for registering! You can now login");
							
							//Opening the main application again for login
							mainApplication main = new mainApplication();
							main.setVisible(true);
							//Closing the register dialog
							dispose();
							
						}catch(NumberFormatException ex){
							JOptionPane.showMessageDialog(SignUpDialog.this, "Please enter only numbers: Budget");
						}catch(Exception ex) {
							JOptionPane.showMessageDialog(SignUpDialog.this, ex.getMessage());
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
						
						//Opening the main application again 
						mainApplication main = new mainApplication();
						main.setVisible(true);
						//Closing the register dialog without saving any data
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
