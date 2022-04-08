package MiniMarket;

import java.util.Scanner;
import java.util.Vector;

public class Main {

	Scanner sc = new Scanner(System.in);
	Vector<Product> listProduct = new Vector<>();
	Vector<Customer> listCustomer = new Vector<>();
	Vector<Transaction> listOverallTransaction = new Vector<>();
	
	public void clear() {
		for (int i = 0; i < 20; i++) {
			System.out.println();
		}
	}
	
	public void printMainMenu() {
		clear();
		System.out.println("Mini-Market");
		System.out.println("1. Transaction");
		System.out.println("2. Product");
		System.out.println("3. Exit");
		System.out.print("Choice >> ");
	}
	
	public void printMenuTransaction() {
		clear();
		System.out.println("Transaction Menu");
		System.out.println("1. Create Transaction");
		System.out.println("2. Delete Transaction");
		System.out.println("3. View Transaction");
		System.out.println("4. Back");
		System.out.print("Choice >> ");
	}
	
	public boolean validateProductId(String productId) {
		for (int i = 0; i < listProduct.size(); i++) {
			if (listProduct.get(i).getProductId().equals(productId)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean validateCustomerId(String customerId) {
		for (int i = 0; i < listCustomer.size(); i++) {
			if (listCustomer.get(i).getCustomerId().equals(customerId)) {
				return true;
			}
		}
		return false;
	}
	
	public int validateOverallTransactionId(String transactionId) {
		for (int i = 0; i < listOverallTransaction.size(); i++) {
			if (listOverallTransaction.get(i).getTransactionId().equals(transactionId)) {
				return i;
			}
		}
		return -1;
	}
	
	public void newTransaction() {
		if (listProduct.isEmpty()) {
			System.out.println("Please Input Products To The List First!");
		} else {
			String inputCustomerId;
			String inputCustomerName;
			String inputTransactionId;
			
			do {
				System.out.print("Input Customer ID [e.g. CU001]: ");
				inputCustomerId = sc.nextLine();
			} while (inputCustomerId.length() != 5 || !validateId(inputCustomerId) || !inputCustomerId.contains("CU") || validateCustomerId(inputCustomerId));
			
			do {
				System.out.print("Input Customer Name: ");
				inputCustomerName = sc.nextLine();
			} while (inputCustomerName.isEmpty());
			
			int tran, checkTran = 0;
			do {
				System.out.print("Input Number of Transaction Done: ");
				try {
					tran = sc.nextInt();
				} catch (Exception e) {
					tran = 0;
				}
				sc.nextLine();
			} while (tran < 1);

			Vector<Transaction> listTransaction = new Vector<>();
			do {
				do {
					System.out.print("Input Transaction ID [e.g. TR001]: ");
					inputTransactionId = sc.nextLine();
				} while (inputTransactionId.length() != 5 || !validateId(inputTransactionId) || !inputTransactionId.contains("TR") || validateOverallTransactionId(inputTransactionId) != -1);
				
				int bought;
				do {
					System.out.print("Input Number of Products Purchased: ");
					try {
						bought = sc.nextInt();
					} catch (Exception e) {
						bought = 0;
					}
					sc.nextLine();
				} while (bought < 1);
				
				String inputProductId;
				int inputQty;

				int totalPrice = 0;
				int checkBought = 0;

				Vector<Groceries> listGroceries = new Vector<>();
				do {
					do {
						System.out.print("Input Product ID [e.g. PR001]: ");
						inputProductId = sc.nextLine();
					} while (inputProductId.length() != 5 || !validateId(inputProductId) || !inputProductId.contains("PR"));
					
					if (validateProductId(inputProductId)) {
						if (listProduct.get(getIndex(inputProductId)).getPoductStock() == 0) {
							if (!listGroceries.isEmpty()) {
								for (int i = 0; i < listGroceries.size(); i++) {
									listProduct.get( getIndex(listGroceries.get(i).getProductId()) ).increaseStockafterDelete(listGroceries.get(i).getProductQty());
								}
							}
							if (!listTransaction.isEmpty()) {
								listCustomer.add(new Customer(inputCustomerId, inputCustomerName, listTransaction));
							}
							System.out.println("Stock Is Empty! Please Add More Stock First!");
							System.out.println("\nThe transaction with ID: " + inputTransactionId + " is cancelled");
							System.out.println("\nPress [ENTER] To Return to Previous Menu... ");
							return;
						}
						do {
							System.out.print("Input Product Quantity [1 - " + listProduct.get(getIndex(inputProductId)).getPoductStock() + "]: ");
							try {
								inputQty = sc.nextInt();
							} catch (Exception e) {
								inputQty = 0;
							} 
							sc.nextLine();
						} while (inputQty < 1 || inputQty > listProduct.get(getIndex(inputProductId)).getPoductStock());
						
						listGroceries.add(new Groceries(inputProductId, inputQty));
						checkBought++;
						
						// mengurangi stok karna barangnya sudah terjual
						listProduct.get(getIndex(inputProductId)).decreaseStockSold(inputQty);
						
						// calculate total price persatuan
						totalPrice += listGroceries.get(listGroceries.size()-1).calculateTotalPrice(listProduct.get(getIndex(inputProductId)).getProductPrice() );
						System.out.println("Transaction Successfully Added!");
						
						
						System.out.println();
					} else {
						System.out.println("No Products Matched!");
					}		
				} while (checkBought < bought);
				
				checkTran++;
				listOverallTransaction.add(new Transaction(inputTransactionId, listGroceries));
				listTransaction.add(new Transaction(inputTransactionId, listGroceries));
				
				// calculate total price seluruhnya
				listTransaction.get(listTransaction.size()-1).setTotalPrice(totalPrice);
			} while (checkTran < tran);
			listCustomer.add(new Customer(inputCustomerId, inputCustomerName, listTransaction));
		}

		System.out.println("Press [ENTER] To Continue...");
	}
	
	public String getProductName(String id) {
		for (int i = 0; i < listProduct.size(); i++) {
			if (listProduct.get(i).getProductId().equals(id)) {
				return listProduct.get(i).getProductName();
			}
		}
		return null;
	}
	
	public void viewTransaction() {
		clear();
		if (listCustomer.isEmpty()) {
			System.out.println("No Transaction Added!");
		} else {
			System.out.println("Customer");
			for (int i = 0; i < listCustomer.size(); i++) {
				System.out.println("------------------------------");
				System.out.println((i + 1) + ". Customer ID   = " + listCustomer.get(i).getCustomerId());
				System.out.println("   Customer Name = " + listCustomer.get(i).getCustomerName());
				System.out.println("   Transaction");
				System.out.println("   ------------------------------");
				for (int j = 0; j < listCustomer.get(i).getListTransaction().size(); j++) {
					System.out.println("   " + (j + 1) + ". Transaction ID = " + listCustomer.get(i).getListTransaction().get(j).getTransactionId());
					System.out.println("      Product(s) Bought");
					System.out.println("      ------------------------------");
					for (int j2 = 0; j2 < listCustomer.get(i).getListTransaction().get(j).getListGroceries().size(); j2++) {
						System.out.println("      " + (j2 + 1) + ". Product ID     = " + listCustomer.get(i).getListTransaction().get(j).getListGroceries().get(j2).getProductId());
						System.out.println("         " + "Product Name   = " + getProductName(listCustomer.get(i).getListTransaction().get(j).getListGroceries().get(j2).getProductId()));
						System.out.println("         " + "Product Bought = " + listCustomer.get(i).getListTransaction().get(j).getListGroceries().get(j2).getProductQty() + " pc(s)");
						System.out.println("      ------------------------------");
					}
					System.out.println("      Total Price = Rp. " + listCustomer.get(i).getListTransaction().get(j).getTotalPrice());
					System.out.println("   ------------------------------");
				}
			}
		}
	}

	public boolean validateTransactionId(String id) {
		for (int i = 0; i < listCustomer.size(); i++) {
			for (int j = 0; j < listCustomer.get(i).getListTransaction().size(); j++) {
				if (listCustomer.get(i).getListTransaction().get(j).getTransactionId().equals(id)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public int getCustomerIndex(String customerId) {
		for (int i = 0; i < listCustomer.size(); i++) {
			if (listCustomer.get(i).getCustomerId().equals(customerId)) {
				return i;
			}
		}
		return -1;
	}
	
	public void printCustomerDetails(int customerIdx) {
		System.out.println("Customer");
		System.out.println("------------------------------");
		System.out.println("   Customer ID   = " + listCustomer.get(customerIdx).getCustomerId());
		System.out.println("   Customer Name = " + listCustomer.get(customerIdx).getCustomerName());
		System.out.println("   Transaction");
		System.out.println("   ------------------------------");
		for (int j = 0; j < listCustomer.get(customerIdx).getListTransaction().size(); j++) {
			System.out.println("   " + (j + 1) + ". Transaction ID = " + listCustomer.get(customerIdx).getListTransaction().get(j).getTransactionId());
		}
		System.out.println("   ------------------------------");
	}
	
	public int getTransactionIndex(int customerIdx, String transactionId) {
		for (int i = 0; i < listCustomer.get(customerIdx).getListTransaction().size(); i++) {
			if (listCustomer.get(customerIdx).getListTransaction().get(i).getTransactionId().equals(transactionId)) {
				return i;
			}
		}
		return -1;
	}
	
	public void deleteTransaction() {
		if(listCustomer.isEmpty()){
			clear();
			System.out.println("No Transaction Available!");
		} else {
			viewTransaction();
			boolean cek = false;
			while(cek == false) {
				String deleteCustomerId;
				String deleteTransactionId;
				
				do {
					System.out.print("Input Customer ID [e.g. CU001]: ");
					deleteCustomerId = sc.nextLine();
				} while (deleteCustomerId.length() != 5 || !validateId(deleteCustomerId) || !deleteCustomerId.contains("CU"));
				
				if (validateCustomerId(deleteCustomerId)) {
					cek = true;
					int idx = getCustomerIndex(deleteCustomerId);
					printCustomerDetails(idx);
					
					do {
						System.out.print("Input Transaction ID [e.g. TR001]: ");
						deleteTransactionId = sc.nextLine();
					} while (deleteTransactionId.length() != 5 || !validateId(deleteTransactionId) || !deleteTransactionId.contains("TR"));
					
					if (validateTransactionId(deleteTransactionId)) {
						for (int i = 0; i < listCustomer.get(idx).getListTransaction().get(getTransactionIndex(idx, deleteTransactionId)).getListGroceries().size(); i++) {
							String tempProductId = null;
							int tempQty = 0;
							
							tempProductId = listCustomer.get(idx).getListTransaction().get(getTransactionIndex(idx, deleteTransactionId)).getListGroceries().get(i).getProductId();
							tempQty = listCustomer.get(idx).getListTransaction().get(getTransactionIndex(idx, deleteTransactionId)).getListGroceries().get(i).getProductQty();
							listProduct.get(getIndex(tempProductId)).increaseStockafterDelete(tempQty);
						}
						listCustomer.get(idx).getListTransaction().remove(getTransactionIndex(idx, deleteTransactionId));
						listOverallTransaction.remove(validateOverallTransactionId(deleteTransactionId));
						if (listCustomer.get(idx).getListTransaction().size() == 0) {
							listCustomer.remove(idx);
						}
					} else {
						System.out.println("No Transaction ID Matched!");
					}
					System.out.println("Transaction Successfully Deleted");
				} else {
					System.out.println("No Customer ID Matched!");
				}
			}
		}
		System.out.println("Press [ENTER] To Continue...");
	}
	
	public void menuTransaction() {
		boolean isRun = true;
		int choice = -1;
		
		do {
			do {
				printMenuTransaction();
				try {
					choice = sc.nextInt();
				} catch (Exception e) {
					choice = -1;
				}
				sc.nextLine();
			} while (choice < 1 || choice > 5);
			
			switch(choice) {
				case 1:
					newTransaction();
					sc.nextLine();
					break;
				case 2:
					deleteTransaction();
					sc.nextLine();
					break;
				case 3:
					viewTransaction();
					System.out.println("Press [ENTER] To Continue...");
					sc.nextLine();
					break;
				case 4:
					isRun = false;
					break;
			}
			
		} while (isRun);
	}
	
	public boolean validateId(String productId) {
		char[] chars = productId.toCharArray();
		StringBuilder sb = new StringBuilder();
		for(char c : chars) {
			if(Character.isDigit(c)){
	            sb.append(c);
	         }
		}
		
		if (sb.length() == 3) {
			return true;
		}
		return false;
	}
	
	public Product newProduct() {
		String inputProductId;
		String inputProductName;
		int inputProductPrice;
		int inputProductStock;
		
		do {
			System.out.print("Input Product ID [e.g. PR001]: ");
			inputProductId = sc.nextLine();																			// HERE
		} while (inputProductId.length() != 5 || !validateId(inputProductId) || !inputProductId.contains("PR") || validateProductId(inputProductId));
		
		do {
			System.out.print("Input Product Name [e.g. Kopi]: ");
			inputProductName = sc.nextLine();
		} while (inputProductName.isEmpty());
		
		do {
			System.out.print("Input Product Price [between 100 - 100000]: ");
			try {
				inputProductPrice = sc.nextInt();
			} catch (Exception e) {
				inputProductPrice = 0;
			}
		} while (inputProductPrice < 100 || inputProductPrice > 100000);
		
		do {
			System.out.print("Input Product Stock [between 1 - 500]: ");
			try {
				inputProductStock = sc.nextInt();
			} catch (Exception e) {
				inputProductStock = 0;
			}
			sc.nextLine();
		} while (inputProductStock < 1 || inputProductStock > 500);
		
		System.out.println("Product Succeddfully Added!");
		System.out.println("Press [ENTER] To Continue...");
		
		return new Product(inputProductId, inputProductName, inputProductPrice, inputProductStock);
	}
	
	public boolean checkId(String id) {
		for (int i = 0; i < listProduct.size(); i++) {
			if (listProduct.get(i).getProductId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	public int getIndex(String id) {
		for (int i = 0; i < listProduct.size(); i++) {
			if (listProduct.get(i).getProductId().equals(id)) {
				return i;
			}
		}
		return -1;
	}

	public void updateProduct() {
		clear();
		if (listProduct.isEmpty()) {
			System.out.println("No Product Available!");
		} else {
			viewProduct();
			String removeProductId;
			
			do {
				System.out.print("Input Product ID [e.g. PR001]: ");
				removeProductId = sc.nextLine();
			} while (removeProductId.length() != 5 || !validateId(removeProductId) || !removeProductId.contains("PR"));
			
			int removeProductPrice;
			int removeProductStock;
			
			if (checkId(removeProductId)) {
				do {
					System.out.print("Input Product Price [between 100 - 100000]: ");
					try {
						removeProductPrice = sc.nextInt();
					} catch (Exception e) {
						removeProductPrice = 0;
					}
				} while (removeProductPrice < 100 || removeProductPrice > 100000);
				
				do {
					System.out.print("Input Product Stock [between 1 - 500]: ");
					try {
						removeProductStock = sc.nextInt();
					} catch (Exception e) {
						removeProductStock = 0;
					}
					sc.nextLine();
				} while (removeProductStock < 1 || removeProductStock > 500);
				
				listProduct.get(getIndex(removeProductId)).setProductPrice(removeProductPrice);
				listProduct.get(getIndex(removeProductId)).setPoductStock(removeProductStock);
				
				System.out.println("Product Successfully Updated!");
			} else {
				System.out.println("Product Id Not Found!");
			}
		}
		System.out.println("Press [ENTER] To Continue...");
	}
	
	public void removeProduct() {
		clear();
		if (listProduct.isEmpty()) {
			System.out.println("No Product Available!");
		} else {
			viewProduct();
			String removeProductId;
			
			do {
				System.out.print("Input Product ID [e.g. PR001]: ");
				removeProductId = sc.nextLine();
			} while (removeProductId.length() != 5 || !validateId(removeProductId) || !removeProductId.contains("PR"));
			
			if (checkId(removeProductId)) {
				listProduct.remove(getIndex(removeProductId));
				System.out.println("Product Successfully Removed!");
			} else {
				System.out.println("Product Id Not Found!");
			}
		}
		System.out.println("Press [ENTER] To Continue...");
	}
	
	public void viewProduct() {
		if (listProduct.isEmpty()) {
			System.out.println("No Product Available!");
		} else {
			System.out.println("Product");
			for (int i = 0; i < listProduct.size(); i++) {
				System.out.println("------------------------------");
				System.out.println((i + 1) + ". ID    = " + listProduct.get(i).getProductId());
				System.out.println("   Name  = " + listProduct.get(i).getProductName());
				System.out.println("   Price = Rp. " + listProduct.get(i).getProductPrice());
				System.out.println("   Stock = " + listProduct.get(i).getPoductStock() + " pc(s)");
			}
			System.out.println("------------------------------");
		}
	}
	
	public void printMenuProduct() {
		clear();
		System.out.println("Product Menu");
		System.out.println("1. Add Item");
		System.out.println("2. Update Item");
		System.out.println("3. Remove Item");
		System.out.println("4. View Item");
		System.out.println("5. Back");
		System.out.print("Choice >> ");
	}
	
	public void menuProduct() {
		boolean isRun = true;
		int choice = -1;
		do {
			do {
				printMenuProduct();
				try {
					choice = sc.nextInt();
				} catch (Exception e) {
					choice = -1;
				}
				sc.nextLine();
			} while (choice < 1 || choice > 5);
			
			switch(choice) {
				case 1:
					listProduct.add(newProduct());
					sc.nextLine();
					break;
				case 2:
					updateProduct();
					sc.nextLine();
					break;
				case 3:
					removeProduct();
					sc.nextLine();
					break;
				case 4:
					clear();
					viewProduct();
					System.out.println("Press [ENTER] To Continue...");
					sc.nextLine();
					break;
				case 5:
					isRun = false;
					break;
			}
		} while (isRun);
	}
	
	public Main() {
		boolean isRun = true;
		int choice = -1;
		do {
			do {
				printMainMenu();
				try {
					choice = sc.nextInt();
				} catch (Exception e) {
					choice = -1;
				}
				sc.nextLine();
			} while (choice < 1 || choice > 3);
			
			switch(choice) {
				case 1:
					menuTransaction();
					break;
				case 2:
					menuProduct();
					break;
				case 3:
					isRun = false;
					System.out.println("Thank You!");
					break;
			}
		} while (isRun);
	}

	public static void main(String[] args) {
		new Main();
	}

}
