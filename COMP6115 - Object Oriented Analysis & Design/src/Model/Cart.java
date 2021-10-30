package Model;

public class Cart {
	private int ID, ProductID, ProductQuantity;
	
		
	public Cart(int iD, int productID, int productQuantity) {
		super();
		ID = iD;
		ProductID = productID;
		ProductQuantity = productQuantity;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getProductID() {
		return ProductID;
	}

	public void setProductID(int productID) {
		ProductID = productID;
	}

	public int getProductQuantity() {
		return ProductQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		ProductQuantity = productQuantity;
	}
	
}
