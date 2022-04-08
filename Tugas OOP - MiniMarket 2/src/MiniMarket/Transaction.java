package MiniMarket;

import java.util.Vector;

public class Transaction {
	
	private String transactionId;
	private Vector<Groceries> listGroceries = new Vector<>();
	private int totalPrice;

	public Transaction(String transactionId, Vector<Groceries> listGroceries) {
		this.transactionId = transactionId;
		this.listGroceries = listGroceries;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Vector<Groceries> getListGroceries() {
		return listGroceries;
	}

	public void setListGroceries(Vector<Groceries> listGroceries) {
		this.listGroceries = listGroceries;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	

}
