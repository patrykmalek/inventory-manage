package kruszywo.sa.computers.manage.model;

public class DeviceType {

	private int deviceTypeID;
	private String deviceTypeName;
	
	public DeviceType() {
		
	}
	
	public DeviceType(int deviceTypeID, String deviceTypeName) {
		this.deviceTypeID = deviceTypeID;
		this.deviceTypeName = deviceTypeName;
	}
	
	public int getDeviceTypeID() {
		return deviceTypeID;
	}
	
	public void setDeviceTypeID(int deviceTypeID) {
		this.deviceTypeID = deviceTypeID;
	}
	
	public String getDeviceTypeName() {
		return deviceTypeName;
	}
	
	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}
	
}
