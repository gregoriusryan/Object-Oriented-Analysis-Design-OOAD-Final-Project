package Model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import Connector.Connector;

public class ProductManagement extends Employee{

	public ProductManagement(int iD, int roleID, int salary, String fullname, String username, String status,
			String password) {
		super(iD, roleID, salary, fullname, username, status, password);
		// TODO Auto-generated constructor stub
	}
	
	public boolean addProduct(Product product) {
		Connection con = Connector.connect();
		try {
			Statement stat = con.createStatement();
			String query = String.format("INSERT INTO Product(ProductName, ProductDescription, ProductPrice, ProductStock) VALUES('%s', '%s', %d, %d)", product.getName(), product.getDescription(), product.getPrice(), product.getStock());
			if(stat.executeUpdate(query) == 0) return false;
			else return true;
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
		
		return false;
	}
	
	public boolean deleteProduct(int ProductID) {
		Connection con = Connector.connect();
		try {
			Statement stat = con.createStatement();
			String query = String.format("DELETE FROM Product WHERE ProductID = %d", ProductID);
			if(stat.executeUpdate(query) == 0) return false;
			else return true;
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public boolean updateProduct(Product product) {
		Connection con = Connector.connect();
		try {
			Statement stat = con.createStatement();
			String query = String.format("UPDATE Product SET ProductName = '%s', ProductDescription = '%s', ProductPrice = %d, ProductStock = %d WHERE ProductID = %d", product.getName(), product.getDescription(), product.getPrice(), product.getStock(), product.getID());
			if(stat.executeUpdate(query) == 0) return false;
			else return true;
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
}
