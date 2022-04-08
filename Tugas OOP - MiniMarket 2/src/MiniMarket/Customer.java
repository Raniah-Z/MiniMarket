package MiniMarket;

import java.util.Vector;

public class Customer {

	private String customerId;
	private String customerName;
	private Vector<Transaction> listTransaction = new Vector<>();
	
	public Customer(String customerId, String customerName, Vector<Transaction> listTransaction) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.listTransaction = listTransaction;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Vector<Transaction> getListTransaction() {
		return listTransaction;
	}

	public void setListTransaction(Vector<Transaction> listTransaction) {
		this.listTransaction = listTransaction;
	}
	

}
