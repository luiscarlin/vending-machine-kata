package luis.carlin.vm;

/**
 * This class represents a product to sell from the vending machine
 */
public class Product {
	
	private VendingMachine.Inventory productType;
	private int button;
	private double price; 
	private int quantity;
	
	public Product() { 
		button = 0;
		price = 0;
		quantity = 0; 
	}
	
	public VendingMachine.Inventory getProductType() { return productType; }
	public void setProductType(VendingMachine.Inventory productType) { this.productType = productType; }

	public double getPrice() { return price; }
	public void setPrice(double price) { this.price = price; }

	public int getQuantity() { return quantity; }
	public void setQuantity(int quantity) { this.quantity = quantity; }

	public int getButton() { return button; }
	public void setButton(int button) { this.button = button; }
}