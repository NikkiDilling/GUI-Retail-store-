import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;

import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Font;

public class mainApplication extends JFrame {
	
	//Main frame 
	private JFrame frame;
	//Variables for background image
	private ImageIcon storeIcon;
	private JLabel myLabel;
	//Buttons
	private JButton btnRegister;
	private JButton btnLogin;

	
	public static void main(String[] args) {
		//Running the application
		//frame is created inside the method
		new mainApplication();
	}

	/**
	 * Create the frame.
	 */
	public mainApplication() {
		
		//Creating a frame (manually), to fit Image as background
		frame = new JFrame("Retail Store");
		frame.setSize(400, 600);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		
		//Creating path to background image and placing it in the frame
		storeIcon = new ImageIcon("img.jpg");
		myLabel = new JLabel("", storeIcon,JLabel.CENTER);
		myLabel.setBackground(Color.WHITE);
		myLabel.setBounds(0,0,400,600);
		frame.getContentPane().add(myLabel);
		
		//Button 1 "Register/Become a vendor"
		btnRegister = new JButton("Become a vendor");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Creating and initializing register dialog
				SignUpDialog register= new SignUpDialog();
				//Opening another window to register
				register.setVisible(true);
				//disables the main application when register button has been pressed
				frame.dispose();
				
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRegister.setForeground(Color.WHITE);
		btnRegister.setBackground(Color.RED);
		//placing the button manually (x,y,width, height)
		btnRegister.setBounds(95, 250, 200, 40);
		
		//Button 2 "Login"
		btnLogin = new JButton("Log in");
		btnLogin.setIcon(new ImageIcon("C:\\Users\\ndill\\eclipse-workspace\\Final Project\\login-icon.png"));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Creating and initializing login dialog
				LoginDialog login = new LoginDialog();
				//Opening another window to login
				login.setVisible(true);
				
				frame.dispose();
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnLogin.setBackground(Color.WHITE);
		//placing the button manually (x,y,width, height)
		btnLogin.setBounds(95, 300, 200, 40);
		myLabel.add(btnRegister);
		myLabel.add(btnLogin);
		
		
		//Making the frame visible
		frame.setVisible(true);	
		
		
	}
}
