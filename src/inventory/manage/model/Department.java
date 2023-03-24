package inventory.manage.model;

public class Department {
	
	private int departmentID;
	private String departmentCode;
	private String departmentName;
	
	public Department() {}

	public Department(int departmentID, String departmentCode, String departmentName) {
		super();
		this.departmentID = departmentID;
		this.departmentCode = departmentCode;
		this.departmentName = departmentName;
	}

	public int getDepartmentID() {
		return departmentID;
	}
	
	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}
	
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	@Override
	public String toString() {
		return (departmentCode == null || departmentName == null) ? "" : "(" + departmentCode + ") " + departmentName;
	}
	
}
