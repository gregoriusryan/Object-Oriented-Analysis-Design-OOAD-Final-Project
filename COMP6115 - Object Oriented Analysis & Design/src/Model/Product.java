package Model;

public class Product {
	
	private int ID, Price, Stock;
	private String Name, Description;
	
		
	public Product(int iD, int price, int stock, String name, String description) {
		super();
		ID = iD;
		Price = price;
		Stock = stock;
		Name = name;
		Description = description;
	}
	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getPrice() {
		return Price;
	}
	public void setPrice(int price) {
		Price = price;
	}
	public int getStock() {
		return Stock;
	}
	public void setStock(int stock) {
		Stock = stock;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	
	
	
}
