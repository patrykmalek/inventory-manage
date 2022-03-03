package kruszywo.sa.computers.manage.model;

public class Device {

	private int deviceID;
	
	private String deviceName;
	private String deviceUniqueNumber;
	private String deviceInventoryNumber;
	private String invoiceNumber;
	private String purchaseDate;
	private String lastInstallationDate;
	private String notes;
	
	private DeviceType deviceType;
	private Department assignedDepartment;
	private Employee assignedEmployee;
	
	public Device() {
		
	}

	public int getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceUniqueNumber() {
		return deviceUniqueNumber;
	}

	public void setDeviceUniqueNumber(String deviceUniqueNumber) {
		this.deviceUniqueNumber = deviceUniqueNumber;
	}

	public String getDeviceInventoryNumber() {
		return deviceInventoryNumber;
	}

	public void setDeviceInventoryNumber(String deviceInventoryNumber) {
		this.deviceInventoryNumber = deviceInventoryNumber;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getLastInstallationDate() {
		return lastInstallationDate;
	}

	public void setLastInstallationDate(String lastInstallationDate) {
		this.lastInstallationDate = lastInstallationDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public Department getAssignedDepartment() {
		return assignedDepartment;
	}

	public void setAssignedDepartment(Department assignedDepartment) {
		this.assignedDepartment = assignedDepartment;
	}

	public Employee getAssignedEmployee() {
		return assignedEmployee;
	}

	public void setAssignedEmployee(Employee assignedEmployee) {
		this.assignedEmployee = assignedEmployee;
	}

}
