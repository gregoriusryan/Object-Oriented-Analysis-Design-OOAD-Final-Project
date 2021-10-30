package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Connector.Connector;
import Model.Cart;
import Model.Cashier;
import Model.Product;
import View.CartView;
import View.Login;

public class CashierController implements ActionListener{
	
	private JTextField TF_Search, TF_ProductID, TF_Stocks;
	private JLabel ProductName_label, ProductDesc_label, ProductSalary_label, ProductStocks_label, CashierName_label, ProductID_label;
	private JButton btn_search, btn_add, btn_logout, btn_viewCart;
	private JFrame frame;
	private JTable table_prod;
	private DefaultTableModel model_tabel_prod;
	private Cashier chr;
	private Product prd;
	
	
	
	
	public CashierController(JTextField tF_Search, JTextField tF_ProductID, JTextField tF_Stocks,
			JLabel productName_label, JLabel productDesc_label, JLabel productSalary_label, JLabel productStocks_label,
			JLabel cashierName_label, JLabel productID_label, JButton btn_search, JButton btn_add, 
			JButton btn_logout, JButton btn_viewCart, JFrame frame, JTable table_prod,
			DefaultTableModel model_tabel_prod, Cashier chr) {
		super();
		TF_Search = tF_Search;
		TF_ProductID = tF_ProductID;
		TF_Stocks = tF_Stocks;
		ProductName_label = productName_label;
		ProductDesc_label = productDesc_label;
		ProductSalary_label = productSalary_label;
		ProductStocks_label = productStocks_label;
		CashierName_label = cashierName_label;
		ProductID_label = productID_label;
		this.btn_search = btn_search;
		this.btn_add = btn_add;
		this.btn_logout = btn_logout;
		this.btn_viewCart = btn_viewCart;
		this.frame = frame;
		this.table_prod = table_prod;
		this.model_tabel_prod = model_tabel_prod;
		this.chr = chr;
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




	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btn_search) {
			Connection con = Connector.connect();
			setTabelModel();
			if(TF_Search.getText().equals("")) {
				refreshDataTabel();
				ProductName_label.setText("Nama: ");
				ProductDesc_label.setText("Description: ");
				ProductSalary_label.setText("Price Per Item: ");
				TF_Stocks.setText("");
			}else {
				try {
					model_tabel_prod.setRowCount(0);
					Statement stat = con.createStatement();
					String query = String.format("SELECT * FROM Product WHERE ProductID = %d", Integer.valueOf(TF_Search.getText()));
					ResultSet res = stat.executeQuery(query);
					
					while(res.next()) {
						prd = new Product(res.getInt("ProductID"), res.getInt("ProductPrice"), res.getInt("ProductStock"), res.getString("ProductName"), res.getString("ProductDescription"));
					}
					
					ProductName_label.setText("Nama: " + prd.getName());
					ProductDesc_label.setText("Description: "+prd.getDescription());
					ProductSalary_label.setText("Price Per Item: "+prd.getPrice());
					refreshDataTabel();
					
					
				}catch(SQLException E) {
					E.printStackTrace();
				}
			}
		}else if(e.getSource() == btn_logout) {
			frame.setVisible(false);
			new Login();
		}else if(e.getSource() == btn_add) {
			int CartID = chr.getCartIDNow(), ProductID = prd.getID(), ProductQuantity = 0;
			String ProductQuantityText = TF_Stocks.getText();
			
			if(ProductQuantityText.equals("")) {
				JOptionPane.showMessageDialog(frame, "Field Harus Diisi!");
			}else {
				ProductQuantity = Integer.valueOf(TF_Stocks.getText());
				if(getQuantityStock(ProductQuantity, prd.getStock()) && ProductQuantity != 0) {
					if(chr.addItemToCart(CartID, ProductID, ProductQuantity)) {
						TF_Search.setText("");			
						refreshDataTabel();
						ProductName_label.setText("Nama: ");
						ProductDesc_label.setText("Description: ");
						ProductSalary_label.setText("Price Per Item: ");
						TF_Stocks.setText("");
						JOptionPane.showMessageDialog(frame, "Success Add Item To Cart");
						
					}else {
						JOptionPane.showMessageDialog(frame, "Over The Stock!! Failed Add Item To Cart");
					}
					
				}else {
					JOptionPane.showMessageDialog(frame, "Failed Add Item To Cart");
				}				
			}
			
			
		}else if(e.getSource() == btn_viewCart) {
			frame.setVisible(false);
			new CartView(chr, chr.getCartItem());
		}
	}
	
	
	private boolean getQuantityStock(int Quantity, int Stock) {
		return (Quantity<=Stock) ? true:false;
	}

}
