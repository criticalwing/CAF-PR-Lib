package ie.cit.patrick;

public class Member {
	
	private int id, bookAllowance;
	private String name, address1, address2, town, contactNumber;
	private double balance;
	private boolean active;
	
	public Member(int id, int bookAllowance, String name, String address1,
			String address2, String town, String contactNumber, double balance,
			boolean active) {
		super();
		this.id = id;
		this.bookAllowance = bookAllowance;
		this.name = name;
		this.address1 = address1;
		this.address2 = address2;
		this.town = town;
		this.contactNumber = contactNumber;
		this.balance = balance;
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBookAllowance() {
		return bookAllowance;
	}

	public void setBookAllowance(int bookAllowance) {
		this.bookAllowance = bookAllowance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	
	@Override
	public String toString() {
		return "Member [id=" + id + ", bookAllowance=" + bookAllowance
				+ ", name=" + name + ", address1=" + address1 + ", address2="
				+ address2 + ", town=" + town + ", contactNumber="
				+ contactNumber + ", balance=" + balance + ", active=" + active
				+ "]";
	}
	
	
	

}
