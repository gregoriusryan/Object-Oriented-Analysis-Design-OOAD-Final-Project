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
import Controller.DetailManagerController;
import Model.Manager;

public class DetailManagerView {
	private Manager mng;
	private int SelectedItems;
	private JFrame frame;
	private JLabel manager_name, Trs_date = new JLabel(), cashier_name = new JLabel();
	private JButton btn_back;
	private JTable table;
	private DefaultTableModel model_table;
	
	public DetailManagerView(Manager mng, int selectedItems) {
		super();
		this.mng = mng;
		SelectedItems = selectedItems;
		framePlay();
	}
	
	private void framePlay() {
		setFrame();
		setPanel();
		setTableMode();
		refreshData();
		updateCashier();
	}
	
	private void setFrame() {
		frame = new JFrame("Manager - Transaction ID"+SelectedItems);
		frame.setSize(900, 800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}
	
	private void setPanel() {
		// Content
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		// Top Panel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(3, 1));
		manager_name = new JLabel("Manager Name: "+mng.getFullname());
		topPanel.add(manager_name);
		topPanel.add(Trs_date);
		topPanel.add(cashier_name);
		content.add(topPanel, BorderLayout.NORTH);
				
		// Center Panel
		JScrollPane jsPane = new JScrollPane();
		table = new JTable();
		jsPane.setViewportView(table);
		content.add(jsPane, BorderLayout.CENTER);
		
		// Bottom Panel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 1));
		btn_back = new JButton("Back To Main");
		bottomPanel.add(btn_back);
		content.add(bottomPanel, BorderLayout.SOUTH);
		
		frame.setContentPane(content);
		
		DetailManagerController dmc = new DetailManagerController(frame, btn_back, mng);
		btn_back.addActionListener(dmc);
	}
	
	private void setTableMode() {
		model_table = new DefaultTableModel(new String[] {"Product Name", "Quantity", "Price Per Item", "Total Price"}, 0);
		table.setModel(model_table);
	}
	
	private void refreshData() {
		model_table.setRowCount(0);
		Connection con = Connector.connect();
		
		try {
			Statement stat = con.createStatement();
			String query = "SELECT * FROM TransactionDetail WHERE TransactionID = "+SelectedItems;
			
			ResultSet res = stat.executeQuery(query);
			
			while(res.next()) {
				model_table.addRow(new Object[] {
						getProductName(res.getInt("ProductID")),
						res.getInt("Quantity"),
						getPricePerItem(res.getInt("ProductID")),
						getTotalPricePerItem(res.getInt("ProductID"), res.getInt("Quantity"))
				});
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getProductName(int ProductID) {
		String Name = "";
		Connection con = Connector.connect();
		
		try {
			Statement stat = con.createStatement();
			String query = "SELECT * FROM Product WHERE ProductID="+ProductID;
			ResultSet res = stat.executeQuery(query);
			
			while(res.next()) {
				Name = res.getString("ProductName");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Name;
	}
	
	private int getPricePerItem(int ProductID) {
		int price = 0;
		Connection con = Connector.connect();
		
		try {
			Statement stat = con.createStatement();
			String query = "SELECT * FROM Product WHERE ProductID="+ProductID;
			ResultSet res = stat.executeQuery(query);
			
			while(res.next()) {
				price = res.getInt("ProductPrice");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return price;
	}
	
	private int getTotalPricePerItem(int ProductID, int Quantity) {
		int price = 0;
		Connection con = Connector.connect();
		
		try {
			Statement stat = con.createStatement();
			String query = "SELECT * FROM Product WHERE ProductID="+ProductID;
			ResultSet res = stat.executeQuery(query);
			
			while(res.next()) {
				price = Quantity*res.getInt("ProductPrice");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return price;
	}
	
	private void updateCashier() {
		Connection con = Connector.connect();
		try {
			Statement stat = con.createStatement();
			String query = "SELECT * FROM TransactionHeader WHERE TransactionID = "+SelectedItems;
			
			ResultSet res = stat.executeQuery(query);
			
			while(res.next()) {
				cashier_name.setText("Cashier Name: "+getCashierName(res.getInt("EmployeeID")));
				Trs_date.setText("Transaction Date: "+res.getDate("TransactionDate"));
			}
			
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	private String getCashierName(int ID) {
		String Name = "";
		Connection con = Connector.connect();
		
		try {
			Statement stat = con.createStatement();
			String query = "SELECT * FROM Employee WHERE EmployeeID="+ID;
			ResultSet res = stat.executeQuery(query);
			
			while(res.next()) {
				Name = res.getString("EmployeeName");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Name;
	}
	
	
	
}
