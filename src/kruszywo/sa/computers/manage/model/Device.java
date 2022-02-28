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
	
	private int deviceTypeID;
	private int assignedDepartmentID;
	private int assignedEmployeeID;
	
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

	public int getDeviceTypeID() {
		return deviceTypeID;
	}

	public void setDeviceTypeID(int deviceTypeID) {
		this.deviceTypeID = deviceTypeID;
	}

	public int getAssignedDepartmentID() {
		return assignedDepartmentID;
	}

	public void setAssignedDepartmentID(int assignedDepartmentID) {
		this.assignedDepartmentID = assignedDepartmentID;
	}

	public int getAssignedEmployeeID() {
		return assignedEmployeeID;
	}

	public void setAssignedEmployeeID(int assignedEmployeeID) {
		this.assignedEmployeeID = assignedEmployeeID;
	}

	@Override
	public String toString() {
		return "Device [deviceID=" + deviceID + ", deviceName=" + deviceName + ", deviceUniqueNumber="
				+ deviceUniqueNumber + ", deviceInventoryNumber=" + deviceInventoryNumber + ", invoiceNumber="
				+ invoiceNumber + ", purchaseDate=" + purchaseDate + ", lastInstallationDate=" + lastInstallationDate
				+ ", notes=" + notes + ", deviceTypeID=" + deviceTypeID + ", assignedDepartmentID="
				+ assignedDepartmentID + ", assignedEmployeeID=" + assignedEmployeeID + "]";
	}


}
