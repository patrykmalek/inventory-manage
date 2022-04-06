package kruszywo.sa.computers.manage.model;

public class ComputerRAM {

	private int memoryRamID;
	private String memoryRamName;
	private String memoryRamType;
	private String memoryRamSerialNumber;
	private int memoryRamCapacityMB;
	private boolean used;
	
	public ComputerRAM() {}
	

	public int getRamCapacityGB() {
		double ramCapacityGB = memoryRamCapacityMB/1024;
		return (int) Math.round(ramCapacityGB);
	}


	public int getMemoryRamID() {
		return memoryRamID;
	}


	public void setMemoryRamID(int memoryRamID) {
		this.memoryRamID = memoryRamID;
	}


	public String getMemoryRamType() {
		return memoryRamType;
	}


	public void setMemoryRamType(String memoryRamType) {
		this.memoryRamType = memoryRamType;
	}


	public int getMemoryRamCapacityMB() {
		return memoryRamCapacityMB;
	}


	public void setMemoryRamCapacityMB(int memoryRamCapacityMB) {
		this.memoryRamCapacityMB = memoryRamCapacityMB;
	}


	@Override
	public String toString() {
		String toString = "";
		
		if(getMemoryRamType() != null) {
			toString = getMemoryRamType() + " ("+ getMemoryRamCapacityMB() + " MB)";
		}
		
		return toString;
	}


	public boolean isUsed() {
		return used;
	}


	public void setUsed(boolean used) {
		this.used = used;
	}


	public String getMemoryRamName() {
		return memoryRamName;
	}


	public void setMemoryRamName(String memoryRamName) {
		this.memoryRamName = memoryRamName;
	}


	public String getMemoryRamSerialNumber() {
		return memoryRamSerialNumber;
	}


	public void setMemoryRamSerialNumber(String memoryRamSerialNumber) {
		this.memoryRamSerialNumber = memoryRamSerialNumber;
	}
	
	
}
