package View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Connector.Connector;
import Controller.CashierController;
import Model.Cart;
import Model.Cashier;
import Model.Product;

public class CashierView {
	private JFrame frame = new JFrame("Cashier");
	private Cashier chr;
	private JTextField TF_Search, TF_ProductID, TF_Stocks;
	private JLabel ProductName_label, ProductDesc_label, ProductSalary_label, ProductStocks_label, CashierName_label, ProductID_label;
	private JButton btn_search, btn_add, btn_remove, btn_logout, btn_viewCart;
	private JTable table_prod;
	private DefaultTableModel model_tabel_prod;
	
	public CashierView(Cashier chr) {
		this.chr = chr;
		framePlay();
	}
	
	
	private void framePlay() {
		setFrame();
		setPanel();
		setTabelModel();
		refreshDataTabel();
	}
	
	private void setFrame() {
		frame.setSize(1100, 1000);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}
	
	private void setPanel() {
		// Content
		JPanel content = new JPanel();
		content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		content.setLayout(new BorderLayout());
		
		// TOP Panel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2, 2));
		CashierName_label = new JLabel("Cashier: " + chr.getFullname());
		btn_logout = new JButton("Log Out");
		TF_Search = new JTextField();
		btn_search = new JButton("Search By ID");
		topPanel.add(CashierName_label);
		topPanel.add(btn_logout);
		topPanel.add(TF_Search);
		topPanel.add(btn_search);
		
		content.add(topPanel, BorderLayout.NORTH);
		
		
		// Center Panel
		JScrollPane jspanel = new JScrollPane();
		table_prod = new JTable();
		jspanel.setViewportView(table_prod);
		content.add(jspanel, BorderLayout.CENTER);
		
		// Bottom Panel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(5, 1));
		
		ProductName_label = new JLabel("Nama: ");
		ProductDesc_label = new JLabel("Description: ");
		ProductSalary_label = new JLabel("Price per Item: ");
		bottomPanel.add(ProductName_label);
		bottomPanel.add(ProductDesc_label);
		bottomPanel.add(ProductSalary_label);
		
		// Quantity
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		ProductStocks_label = new JLabel("Quantity: ");
		TF_Stocks = new JTextField();
		panel.add(ProductStocks_label);
		panel.add(TF_Stocks);
		bottomPanel.add(panel);
		
		// Button Add
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1, 2));
		btn_add = new JButton("Add Product To Cart");
		btn_viewCart = new JButton("View Cart");
		panel2.add(btn_add);
		panel2.add(btn_viewCart);
		bottomPanel.add(panel2);
		
		content.add(bottomPanel, BorderLayout.SOUTH);
		frame.setContentPane(content);
		
		CashierController chrCont = new CashierController(TF_Search, TF_ProductID, TF_Stocks, ProductName_label, ProductDesc_label, ProductSalary_label, ProductStocks_label, CashierName_label, ProductID_label, btn_search, btn_add, btn_logout, btn_viewCart, frame, table_prod, model_tabel_prod, chr);
		btn_search.addActionListener(chrCont);
		btn_add.addActionListener(chrCont);
		btn_logout.addActionListener(chrCont);
		btn_viewCart.addActionListener(chrCont);
	}
	
	private void setTabelModel() {
		// Product
		model_tabel_prod = new DefaultTableModel(new String[] {
				"Product ID",
				"Product Name",
				"Product Description",
				"Product Price",
				"Product Stock"
		}, 0);
		table_prod.setModel(model_tabel_prod);
		
	}
	
	
	private void refreshDataTabel() {
		Connection con = Connector.connect();
		model_tabel_prod.setRowCount(0);
		
		try {
			Statement stat = con.createStatement();
			String query = String.format("SELECT * FROM Product");
			
			ResultSet res = stat.executeQuery(query);
			
			while(res.next()) {
				model_tabel_prod.addRow(new Object[] {
						res.getInt("ProductID"),
						res.getString("ProductName"),
						res.getString("ProductDescription"),
						res.getInt("ProductPrice"),
						res.getInt("ProductStock")
				});
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
}
