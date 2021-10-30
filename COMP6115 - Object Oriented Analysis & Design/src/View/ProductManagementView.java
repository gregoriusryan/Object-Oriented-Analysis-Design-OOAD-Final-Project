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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Connector.Connector;
import Controller.ProductManagementController;
import Model.Product;
import Model.ProductManagement;

public class ProductManagementView {
	private JFrame frame = new JFrame("Product Management");
	private ProductManagement pm;
	private JLabel label_name, id_label, ProductID_label, ProductName_label, ProductPrice_label, ProductStock_label, ProductDesc_label;
	private JTextField TF_search, TF_ProductID, TF_ProductName, TF_ProductPrice, TF_ProductStock, TF_ProductDesc;
	private JButton btn_search, btn_insert, btn_update, btn_delete, btn_logout;
	
	private JTable table;
	private DefaultTableModel model_table;
	
	public ProductManagementView(ProductManagement pm) {
		this.pm = pm;
		framePlay();
		setPanel();
		setTableModel();
		refreshData();
	}
	
	private void framePlay() {
		frame.setSize(900, 800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}
	
	private void setPanel() {
		// Content Panel
		JPanel content = new JPanel();
		content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		content.setLayout(new BorderLayout());
		
		// Title Panel
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new GridLayout(2, 2));
		label_name = new JLabel("Nama: " + pm.getFullname());
		btn_logout = new JButton("Log Out");
		
		TF_search = new JTextField();
		btn_search = new JButton("Search By ID");
		titlePanel.add(label_name);
		titlePanel.add(btn_logout);
		titlePanel.add(TF_search);
		titlePanel.add(btn_search);
		content.add(titlePanel, BorderLayout.NORTH);
		
		// Scroll Panel ( Tabel )
		JScrollPane jspanel = new JScrollPane();
		table = new JTable();
		jspanel.setViewportView(table);
		content.add(jspanel, BorderLayout.CENTER);
		
		// Bottom Panel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(13, 1));
		
		JPanel pan1 = new JPanel();
		bottomPanel.add(pan1);
		
		// ProductID
		ProductID_label = new JLabel("Product ID [ Update | Delete]");
		TF_ProductID = new JTextField();
		bottomPanel.add(ProductID_label);
		bottomPanel.add(TF_ProductID);
		
		// ProductName
		ProductName_label = new JLabel("Product Name");
		TF_ProductName = new JTextField();
		bottomPanel.add(ProductName_label);
		bottomPanel.add(TF_ProductName);
		
		//ProductDesc
		ProductDesc_label = new JLabel("Product Description");
		TF_ProductDesc = new JTextField();
		bottomPanel.add(ProductDesc_label);
		bottomPanel.add(TF_ProductDesc);
		
		// Product Price
		ProductPrice_label = new JLabel("Product Price");
		TF_ProductPrice = new JTextField();
		bottomPanel.add(ProductPrice_label);
		bottomPanel.add(TF_ProductPrice);
		
		// Product Stock
		ProductStock_label = new JLabel("Product Stocks");
		TF_ProductStock = new JTextField();
		bottomPanel.add(ProductStock_label);
		bottomPanel.add(TF_ProductStock);
		
		JPanel pan = new JPanel();
		bottomPanel.add(pan);
		
		// Button Insert Delete Update
		JPanel btn_panel = new JPanel();
		btn_panel.setLayout(new GridLayout(1, 3));
		btn_insert = new JButton("Insert Product");
		btn_update = new JButton("Update Product");
		btn_delete = new JButton("Delete Product");
		btn_panel.add(btn_insert);
		btn_panel.add(btn_update);
		btn_panel.add(btn_delete);
		
		bottomPanel.add(btn_panel);
		content.add(bottomPanel, BorderLayout.SOUTH);
		frame.setContentPane(content);
		
		ProductManagementController controller = new ProductManagementController(frame, TF_search, TF_ProductID, TF_ProductName, TF_ProductPrice, TF_ProductStock, TF_ProductDesc, btn_search, btn_insert, btn_update, btn_delete, table, model_table, pm, btn_logout);
		btn_search.addActionListener(controller);
		btn_update.addActionListener(controller);
		btn_insert.addActionListener(controller);
		btn_delete.addActionListener(controller);
		btn_logout.addActionListener(controller);
	}
	
	private void setTableModel() {
		model_table = new DefaultTableModel(new String[] {
			"ID",
			"Product Name",
			"Product Description",
			"Product Price",
			"Product Stocks"
		}, 0);
		table.setModel(model_table);
	}
	
	private void refreshData() {
		model_table.setRowCount(0);
		Connection con = Connector.connect();
		try {
			Statement stat = con.createStatement();
			String query = String.format("SELECT * FROM Product");
			
			ResultSet res = stat.executeQuery(query);
			
			while(res.next()) {
				model_table.addRow(new Object[] {
						res.getInt("ProductID"),
						res.getString("ProductName"),
						res.getString("ProductDescription"),
						res.getInt("ProductPrice"),
						res.getInt("ProductStock")
				});
			}
			
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
	}
	
}
