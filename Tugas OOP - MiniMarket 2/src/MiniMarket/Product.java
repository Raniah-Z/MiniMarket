package MiniMarket;

public class Product {
	
	private String productId;
	private String productName;
	private int productPrice;
	private int poductStock;

	public Product(String productId, String productName, int productPrice, int productStock) {
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.poductStock = productStock;
	}

	public void decreaseStockSold(int qty) {
		setPoductStock(this.poductStock - qty);
	}
	
	public void increaseStockafterDelete(int qty)
	{
		setPoductStock(this.poductStock + qty);
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

	public int getPoductStock() {
		return poductStock;
	}

	public void setPoductStock(int poductStock) {
		this.poductStock = poductStock;
	}
	

}
