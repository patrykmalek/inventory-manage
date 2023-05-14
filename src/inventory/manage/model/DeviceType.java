package inventory.manage.model;

public class DeviceType {

	private int deviceTypeID;
	private String deviceTypeName;
	private String deviceTypeNotes;
	
	public DeviceType() {}
	
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

	@Override
	public String toString() {
		return deviceTypeName;
	}

	public String getDeviceTypeNotes() {
		return deviceTypeNotes;
	}

	public void setDeviceTypeNotes(String deviceTypeNotes) {
		this.deviceTypeNotes = deviceTypeNotes;
	}
	
}
