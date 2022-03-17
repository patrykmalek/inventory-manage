package kruszywo.sa.computers.manage.model;

public class ComputerMassStorage {

	private int massStorageID;
	private String massStorageName;
	private String massStorageSerialNumber;
	private String massStorageType;
	private int massStorageCapacityMB;
	private boolean used;
	
	public ComputerMassStorage() {}

	public int getMassStorageID() {
		return massStorageID;
	}

	public void setMassStorageID(int massStorageID) {
		this.massStorageID = massStorageID;
	}

	public String getMassStorageName() {
		return (massStorageName == null) ? "----" : massStorageName;
	}

	public void setMassStorageName(String massStorageName) {
		this.massStorageName = massStorageName;
	}

	public String getMassStorageSerialNumber() {
		return (massStorageSerialNumber == null) ? "----" : massStorageSerialNumber;
	}

	public void setMassStorageSerialNumber(String massStorageSerialNumber) {
		this.massStorageSerialNumber = massStorageSerialNumber;
	}

	public String getMassStorageType() {
		return (massStorageType == null) ? "----" : massStorageType;
	}

	public void setMassStorageType(String massStorageType) {
		this.massStorageType = massStorageType;
	}

	public int getMassStorageCapacityMB() {
		return massStorageCapacityMB;
	}

	public void setMassStorageCapacityMB(int massStorageCapacityMB) {
		this.massStorageCapacityMB = massStorageCapacityMB;
	}
	
	public int getMassStorageCapacityGB() {
		double massStorageCapacityGB = massStorageCapacityMB/1024;
		return (int) Math.round(massStorageCapacityGB);
	}

	@Override
	public String toString() {
		String toString = "";
		
		if(massStorageType != null && massStorageName != null) {
			toString = getMassStorageName() + ", " + getMassStorageType() + ", " + getMassStorageSerialNumber() + " (" + getMassStorageCapacityGB() + " GB)";
		}

		return toString;
	}
	
	public String getMassStorageCapacityGBtoString() {
		return getMassStorageCapacityGB() + " GB";
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	
	
}
