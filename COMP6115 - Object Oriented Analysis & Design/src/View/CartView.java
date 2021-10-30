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
import Controller.CartController;
import Model.Cart;
import Model.Cashier;

public class CartView {
	
	private Cashier chr;
	private JFrame frame = new JFrame("Cart View");
	private Vector<Cart> cart;
	private JLabel CashierName_label, ProductID_label, Payment_label, Total_Label;
	private JButton btn_remove, btn_payment, btn_CashierView;
	private JTextField TF_ProductID_Search, TF_paymentType;
	private JTable table;
	private DefaultTableModel model_tabel;
	
	public CartView(Cashier chr, Vector<Cart> cart) {
		super();
		this.chr = chr;
		this.cart = cart;
		framePlay();
	}
	
	private void framePlay() {
		setFrame();
		setPanel();
		setTableMode();
		refreshData();
	}
	
	private void  setFrame() {
		frame.setSize(900, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void setPanel() {
		// Panel
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setLayout(new BorderLayout());
		
		// Top Panel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 2));
		CashierName_label = new JLabel("Nama: "+chr.getFullname());
		btn_CashierView = new JButton("Cashier Menu");
		topPanel.add(CashierName_label);
		topPanel.add(btn_CashierView);
		panel.add(topPanel, BorderLayout.NORTH);
		
		
		// Center Panel
		JScrollPane jsPane = new JScrollPane();
		table = new JTable();
		jsPane.setViewportView(table);
		panel.add(jsPane, BorderLayout.CENTER);
		
		// Bottom Panel
		JPanel botpanel = new JPanel();
		botpanel.setLayout(new GridLayout(3, 1));
		Total_Label = new JLabel("Total: "+chr.calculateCart());
		botpanel.add(Total_Label);
		// Product Remove
		JPanel removePanel = new JPanel();
		removePanel.setLayout(new GridLayout(3, 1));
		ProductID_label = new JLabel("Product ID: ");
		TF_ProductID_Search = new JTextField();
		btn_remove = new JButton("Remove Product From Cart");
		removePanel.add(ProductID_label);
		removePanel.add(TF_ProductID_Search);
		removePanel.add(btn_remove);
		botpanel.add(removePanel);
		
		// btnPanel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 1));
		Payment_label = new JLabel("Payment [ Cash | Card ]: ");
		btn_payment = new JButton("Process To Payment");
		TF_paymentType = new JTextField();
		buttonPanel.add(Payment_label);
		buttonPanel.add(TF_paymentType);
		buttonPanel.add(btn_payment);
		botpanel.add(buttonPanel);
		
		panel.add(botpanel, BorderLayout.SOUTH);
		frame.setContentPane(panel);
		
		CartController ctCont = new CartController(chr, frame, btn_remove, btn_payment, btn_CashierView, TF_ProductID_Search, TF_paymentType, table, model_tabel, cart, Total_Label);
		btn_remove.addActionListener(ctCont);
		btn_CashierView.addActionListener(ctCont);
		btn_payment.addActionListener(ctCont);
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
			
			for (Cart cart2 : cart) {
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
			Total_Label.setText("Total: "+chr.calculateCart());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
