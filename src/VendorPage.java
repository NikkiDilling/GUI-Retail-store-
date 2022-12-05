import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

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

public class VendorPage extends JFrame {

	private JPanel listPane;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenu mnNewMenu_1;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmNewMenuItem_1;
	private JMenuItem mntmNewMenuItem_2;
	private JMenuItem mntmNewMenuItem_3;
	private JMenuItem mntmNewMenuItem_4;
	private JList list;
	private JPanel infoPane;
	private JLabel lblNewLabel;
	private JLabel lblStoreName;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private String storeName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//creating a frame and passing a default value
					VendorPage frame = new VendorPage("default value");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws FileNotFoundException 
	 */
	//crating a constructor and passing a parameter from login dialog page
	public VendorPage(String username) throws FileNotFoundException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 752, 542);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		mntmNewMenuItem = new JMenuItem("Save page information");
		mnNewMenu.add(mntmNewMenuItem);
		
		mntmNewMenuItem_1 = new JMenuItem("Sort items");
		mnNewMenu.add(mntmNewMenuItem_1);
		
		mntmNewMenuItem_4 = new JMenuItem("Change account info");
		mnNewMenu.add(mntmNewMenuItem_4);
		
		mnNewMenu_1 = new JMenu("Orders");
		menuBar.add(mnNewMenu_1);
		
		mntmNewMenuItem_2 = new JMenuItem("Save receit");
		mnNewMenu_1.add(mntmNewMenuItem_2);
		
		mntmNewMenuItem_3 = new JMenuItem("Update budget info");
		mnNewMenu_1.add(mntmNewMenuItem_3);
		listPane = new JPanel();
		listPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(listPane);
		listPane.setLayout(new BorderLayout(0, 0));
		
		list = new JList();
		listPane.add(list, BorderLayout.WEST);
		
		infoPane = new JPanel();
		listPane.add(infoPane, BorderLayout.NORTH);
		
		lblNewLabel = new JLabel("Store:");
		infoPane.add(lblNewLabel);
		
		try {
			FileInputStream stream = new FileInputStream("userData.txt");
			Scanner buffer = new Scanner(stream);
			
			while(buffer.hasNext()) {
				//Saving the scanned line to a string
				String line = buffer.nextLine();
				//Saving the two split tokens in an array 
				//(the first 2 values are updated each while loop)
				String [] tokens = line.split(":");
				
				
				if(tokens[0].equals("UserName")) {
					if(tokens[1].equals(username)) {
						System.out.println("token[0]: " + tokens[0]);
						System.out.println("token[1]: " + tokens[1]);
						//Saving the scanned line to a string
						String line2 = buffer.nextLine();
						//Saving the two split tokens in an array 
						//(the first 2 values are updated each while loop)
						String [] tokens2 = line2.split(":");
						
						storeName = tokens2[1];
						
					}
				}
			}
		}catch(Exception ex) {
			JOptionPane.showMessageDialog(VendorPage.this, ex.getMessage());
		}
		
		lblStoreName = new JLabel(storeName);
		infoPane.add(lblStoreName);
		
		lblNewLabel_2 = new JLabel("Total budget:");
		infoPane.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel(".00");
		infoPane.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("Spent:");
		infoPane.add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel(".00");
		infoPane.add(lblNewLabel_5);
	}

}
