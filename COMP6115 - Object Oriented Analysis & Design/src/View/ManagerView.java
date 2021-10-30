package View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Connector.Connector;
import Controller.ManagerController;
import Model.Manager;

public class ManagerView {
	
	private JFrame frame = new JFrame("Manager");
	private JLabel Manager_name, Selected_transaction;
	private JButton btn_logout, btn_viewDetail, btn_employee;
	private Manager mng;
	private JTable table;
	private DefaultTableModel model_table;
	
	public ManagerView(Manager mng) {
		this.mng = mng;
		framePlay();
	}
	
	private void framePlay() {
		setFrame();
		setPanel();
		setTableMode();
		refreshData();
	}
	
	private void setFrame() {
		frame.setSize(900, 800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}
	
	private void setPanel() {
		
		// SET PANEL
		JPanel content = new JPanel();
		content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		content.setLayout(new BorderLayout());
		
		// Top Panel
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new GridLayout(2, 2));
		Manager_name = new JLabel("Manager Name: "+mng.getFullname());
		titlePanel.add(Manager_name);
		btn_logout = new JButton("LogOut");
		titlePanel.add(btn_logout);
		content.add(titlePanel, BorderLayout.NORTH);
		
		
		// Center
		JScrollPane jsPane = new JScrollPane();
		table = new JTable();
		jsPane.setViewportView(table);
		content.add(jsPane, BorderLayout.CENTER);
		
		// Bottom Panel
		JPanel botPanel = new JPanel();
		botPanel.setLayout(new GridLayout(2, 1));
		Selected_transaction = new JLabel("Selected Transaction [ ID ]: None");
		botPanel.add(Selected_transaction);
		// Button Panel
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(1, 2));
		btn_viewDetail = new JButton("View Detail Transaction");
		btn_employee = new JButton("View Employee");
		btnPanel.add(btn_viewDetail);
		btnPanel.add(btn_employee);
		botPanel.add(btnPanel);
		
		content.add(botPanel, BorderLayout.SOUTH);
		frame.setContentPane(content);
		
		ManagerController mgc = new ManagerController(btn_logout, btn_viewDetail, btn_employee, mng, table, model_table, frame, Selected_transaction);
		btn_viewDetail.addActionListener(mgc);
		btn_logout.addActionListener(mgc);
		btn_employee.addActionListener(mgc);
	}
	
	private void setTableMode() {
		model_table = new DefaultTableModel(new String[] {"TransactionID", "Date", "Employee Name", "Total Price"}, 0);
		table.setModel(model_table);
	}
	
	private void refreshData() {
		model_table.setRowCount(0);
		Connection con = Connector.connect();
		
		try {
			Statement stat = con.createStatement();
			String query = "SELECT * FROM TransactionHeader ORDER BY TransactionDate ASC";
			ResultSet res = stat.executeQuery(query);
			
			while(res.next()) {
//				java.sql.Date date1 = res.getDate("TransactionDate");
//				Date date = new Date(date1.getTime());
				model_table.addRow(new Object []{
					res.getInt("TransactionID"),
					res.getDate("TransactionDate"),
					getEmployeeName(res.getInt("EmployeeID")),
					res.getInt("TotalPrice")
				});
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String getEmployeeName(int ID) throws SQLException {
		String Name = "";
		Connection con = Connector.connect();
		String query = "SELECT * FROM Employee WHERE EmployeeID = "+ID;
		
		Statement stat = con.createStatement();
		ResultSet res = stat.executeQuery(query);
		
		while(res.next()) {
			Name = res.getString("EmployeeName");
		}
		
		return Name;
	}
	
	
}
