package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import Controller.LoginController;

public class Login{
	
	private JFrame frame = new JFrame("Just Du It");
	private JPanel mainPanel, topPanel, botPanel, centerPanel;
	private JLabel JL_title, JL_email, JL_password;
	private JTextField TF_email, TF_password;
	private JButton btn_login;
	

	public Login() {
		setPanel();
		setFrame();
	}
	
	private void setFrame() {
		frame.setSize(400, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void setPanel() {
				
		// Set Main Panel
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(new LineBorder(Color.WHITE, 4));
		// Set Top Panel
		topPanel = new JPanel();
		JL_title = new JLabel("Login");
		
		topPanel.add(JL_title);
		
		
		// Set Center Panel
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 2));
		
		JL_email = new JLabel("Username: ");
		JL_password = new JLabel("Password: ");
		TF_email = new JTextField();
		TF_password = new JTextField();
		
		
		centerPanel.add(JL_email);
		centerPanel.add(TF_email);
		centerPanel.add(JL_password);
		centerPanel.add(TF_password);
		
		// Set For Button
		botPanel = new JPanel();
		btn_login = new JButton("Login");
		
		
		botPanel.add(btn_login);
		
		// set All Panel To Main Panel
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(botPanel,  BorderLayout.SOUTH);
		mainPanel.setBackground(Color.ORANGE);
		
		frame.add(mainPanel);
		
		LoginController controller = new LoginController(TF_email, TF_password, btn_login, frame);
		btn_login.addActionListener(controller);
	}

	
}
