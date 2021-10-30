package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
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
import View.CashierView;

public class CartController implements ActionListener{
	
	private JFrame frame;
	private JButton btn_remove, btn_payment, btn_CashierView;
	private JTextField TF_ProductID_Search, TF_paymentType;
	private JTable table;
	private DefaultTableModel model_tabel;
	private Cashier chr;
	private Vector<Cart> Cart;
	private JLabel total_label;
	
	public CartController(Cashier chr, JFrame frame, JButton btn_remove, JButton btn_payment, JButton btn_CashierView,
			JTextField tF_ProductID_Search, JTextField tf_paymentType, JTable table, DefaultTableModel model_tabel, Vector<Cart> cart, JLabel total_label) {
		super();
		this.chr = chr;
		this.frame = frame;
		this.btn_remove = btn_remove;
		this.btn_payment = btn_payment;
		this.btn_CashierView = btn_CashierView;
		TF_ProductID_Search = tF_ProductID_Search;
		TF_paymentType = tf_paymentType;
		this.table = table;
		this.model_tabel = model_tabel;
		Cart = cart;
		this.total_label = total_label;
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btn_CashierView) {
			frame.setVisible(false);
			new CashierView(chr);
		}else if(e.getSource() == btn_remove) {
			setTableMode();
			model_tabel.setRowCount(0);
			int ProductID = Integer.valueOf(TF_ProductID_Search.getText());
			
			if(chr.isCartNow(ProductID)) {
				chr.deleteItemFromCart(ProductID);
				JOptionPane.showMessageDialog(frame, "Success Delete Product");
			}else {
				JOptionPane.showMessageDialog(frame, "Failed Delete Product");
			}
			
			refreshData();
		}else if(e.getSource() == btn_payment) {
			String payment = TF_paymentType.getText();
			
			setTableMode();
			model_tabel.setRowCount(0);
			
			if(payment.equals("")) {
				JOptionPane.showMessageDialog(frame, "Payment Type Yang Dipilih Antara Cash atau Card");
			}else if(payment.equals("Cash") || payment.equals("Card")){
				Connection con = Connector.connect();
				Vector<Cart> CartNow = chr.getCartItem();
				java.util.Date date = new java.util.Date();
				Date sqlDate = new Date(date.getTime());
				try {
					Statement stat = con.createStatement();
					String query = String.format("INSERT INTO TransactionHeader(TransactionID, TransactionDate, EmployeeID, TotalPrice,PaymentType) VALUES (%d, '"+sqlDate+"', %d, %d,'%s')", chr.getCartIDNow(), chr.getID(), chr.calculateCart(),payment);
					stat.execute(query);
					for (Cart cart : CartNow) {
						query = String.format("INSERT INTO TransactionDetail(TransactionID, ProductID, Quantity) VALUES (%d, %d, %d)", cart.getID(), cart.getProductID(), cart.getProductQuantity());
						stat.execute(query);
						
						query = String.format("UPDATE Product SET ProductStock = ProductStock - %d WHERE ProductID = %d", cart.getProductQuantity(), cart.getProductID());
						stat.execute(query);
					}
					
					chr.clearCart();
					TF_paymentType.setText("");
					JOptionPane.showMessageDialog(frame, "Success Doing Payment!");
					refreshData();
					
					frame.setVisible(false);
					new CashierView(chr);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else {
				JOptionPane.showMessageDialog(frame, "Payment Type Yang Dipilih Antara Cash atau Card");
			}
		}
	}
	
	
	
	private void setTableMode() {
		model_tabel = new DefaultTableModel(new String[] {
			"Product ID",
			"Product Name",
			"Product Description",
			"Product Price",
			"Product Quantity"
		}, 0);
		table.setModel(model_tabel);
	}
	
	private void refreshData() {
		Connection con = Connector.connect();
				
		try {
			model_tabel.setRowCount(0);
			Statement stat = con.createStatement();
			
			for (Cart cart2 : Cart) {
				String query = String.format("SELECT * FROM Product WHERE ProductID = %d", cart2.getProductID());
				ResultSet res = stat.executeQuery(query);
				
				while(res.next()) {
					model_tabel.addRow(new Object[] {
							res.getInt("ProductID"),
							res.getString("ProductName"),
							res.getString("ProductDescription"),
							cart2.getProductQuantity()*res.getInt("ProductPrice"),
							cart2.getProductQuantity()
					});
				}
			}
			total_label.setText("Total: "+chr.calculateCart());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
