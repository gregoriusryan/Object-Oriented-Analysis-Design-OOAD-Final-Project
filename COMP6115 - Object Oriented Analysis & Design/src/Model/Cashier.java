package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import Connector.Connector;

public class Cashier extends Employee{
	
	private Vector<Cart> cartItem = new Vector<>();
	private Connection con = Connector.connect();
	
	public Cashier(int iD, int roleID, int salary, String fullname, String username, String status, String password) {
		super(iD, roleID, salary, fullname, username, status, password);
		// TODO Auto-generated constructor stub
	}
	
	
	private boolean isCart(int ProductID) {
		for (Cart cart : cartItem) {
			if(cart.getProductID() == ProductID) return false;
		}
		return true;
	}
	
	public boolean isCartNow(int ProductID) {
		for (Cart cart : cartItem) {
			if(cart.getProductID() == ProductID) return true;
		}
		return false;
	}
	
	public boolean addItemToCart(int CartID, int ProductID, int ProductQuantity) {
		if(isCart(ProductID)) {
			cartItem.add(new Cart(CartID, ProductID, ProductQuantity));	
			return true;
		}else {
			for (Cart cart : cartItem) {
				if(cart.getProductID() == ProductID) {
					if(getStockQuantity(cart.getProductID()) - (cart.getProductQuantity()+ProductQuantity) >= 0 ) {
						cart.setProductQuantity(cart.getProductQuantity() + ProductQuantity);		
						return true;
					}else {
						return false;
					}
				}
			}
		}
		return false;
	}
	
	private int getStockQuantity(int ID){
		int Quantity = 0;
		Connection con = Connector.connect();
		
		Statement stat;
		try {
			stat = con.createStatement();
			ResultSet res = stat.executeQuery("SELECT * FROM Product WHERE ProductID="+ID);
			
			while(res.next()) {
				Quantity = res.getInt("ProductStock");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Quantity;
	}
	
	public boolean deleteItemFromCart(int ProductID) {
		for (Cart cart : cartItem) {
			if(cart.getProductID() == ProductID) {
				cartItem.remove(cart);
				return true;
			}
		}
		return false;
	}
	
	public int calculateCart() {
		int TotalPrice = 0;
		Connection con = Connector.connect();
		try {
			Statement stat = con.createStatement();
			for (Cart cart : cartItem) {
				String query = String.format("SELECT * FROM Product WHERE ProductID = %d", cart.getProductID());
				ResultSet res = stat.executeQuery(query);
				
				while(res.next()) {
					TotalPrice = TotalPrice + (cart.getProductQuantity() * res.getInt("ProductPrice")) ;
				}
			}
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
		return TotalPrice;
	}

	public Vector<Cart> getCartItem() {
		return cartItem;
	}

	public void setCartItem(Vector<Cart> cartItem) {
		this.cartItem = cartItem;
	}
	
	public int getHigherProductID() {
		int ProdID = 0;
		
		for (Cart cart : cartItem) {
			if(ProdID < cart.getProductID()) ProdID = cart.getProductID();
		}
		
		return ProdID;
	}
	
	public int totalCart() {
		return cartItem.size();
	}
	
	public int getCartIDNow() {
		Connection con = Connector.connect();
		int CartID = 0;
		try {
			Statement stat = con.createStatement();
			String query = String.format("SELECT * FROM TransactionHeader ORDER BY TransactionID DESC LIMIT 1");
			ResultSet res = stat.executeQuery(query);
			
			while(res.next()) {
				CartID = res.getInt("TransactionID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return CartID+1;
	}
	
	public void clearCart() {
		cartItem.clear();
	}
	

}
