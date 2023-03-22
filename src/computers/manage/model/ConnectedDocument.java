package computers.manage.model;


public class ConnectedDocument {

	private int connectedDocumentID;
	private int documentID;
	private int deviceID;
	private int licenseID;
	private int memoryRamID;
	private int massStorageID;


	public int getDocumentID() {
		return documentID;
	}

	public void setDocumentID(int documentID) {
		this.documentID = documentID;
	}

	public int getConnectedDocumentID() {
		return connectedDocumentID;
	}

	public void setConnectedDocumentID(int connectedDocumentID) {
		this.connectedDocumentID = connectedDocumentID;
	}

	public int getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
	}

	public int getLicenseID() {
		return licenseID;
	}

	public void setLicenseID(int licenseID) {
		this.licenseID = licenseID;
	}

	public int getMemoryRamID() {
		return memoryRamID;
	}

	public void setMemoryRamID(int memoryRamID) {
		this.memoryRamID = memoryRamID;
	}

	public int getMassStorageID() {
		return massStorageID;
	}

	public void setMassStorageID(int massStorageID) {
		this.massStorageID = massStorageID;
	}

	@Override
	public String toString() {
		return "ConnectedDocument [connectedDocumentID=" + connectedDocumentID + ", documentID=" + documentID
				+ ", deviceID=" + deviceID + ", licenseID=" + licenseID + ", memoryRamID=" + memoryRamID
				+ ", massStorageID=" + massStorageID + "]";
	}

	
}
