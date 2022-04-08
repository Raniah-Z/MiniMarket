package MiniMarket;

public class Groceries {
	
	private String productId;
	private String productName;
	private int productPrice;
	private int productQty; 
	
	public Groceries(String productId, int productQty) {
		this.productId = productId;
		this.productQty = productQty;
	}
	
	public int calculateTotalPrice(int productPrice) {
		return (productPrice * productQty);
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public int getProductQty() {
		return productQty;
	}

	public void setProductQty(int productQty) {
		this.productQty = productQty;
	}
	
	
}
