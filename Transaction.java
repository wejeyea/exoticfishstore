package model;

public class Transaction {
	
	protected String fishid, email;
	protected int quantity, transid;

	public Transaction(int transid, String email, int quantity, String fishid) {
		this.transid = transid;
		this.email = email;
		this.quantity = quantity;
		this.fishid = fishid;
	}
	
	public int getTransid() {
		return transid;
	}

	public void setTransid(int transid) {
		this.transid = transid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getFishid() {
		return fishid;
	}

	public void setFishid(String fishid) {
		this.fishid = fishid;
	}
}