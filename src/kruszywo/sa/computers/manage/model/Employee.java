package kruszywo.sa.computers.manage.model;

public class Employee {
	
	private int employeeID;
	private String firstName;
	private String lastName;
	private int uniqueNumber;

	public Employee() {}
	
	public Employee(int employeeID, String firstName, String lastName, int uniqueNumber) {
		this.employeeID = employeeID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.uniqueNumber = uniqueNumber;
	}

	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getUniqueNumber() {
		return uniqueNumber;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUniqueNumber(String uniqueNumber) {
		this.uniqueNumber = Integer.parseInt(uniqueNumber);
	}

}
