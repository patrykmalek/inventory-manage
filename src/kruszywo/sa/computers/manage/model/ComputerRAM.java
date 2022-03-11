package kruszywo.sa.computers.manage.model;

public class ComputerRAM {

	private int memoryRamID;
	private String memoryRamType;
	private int memoryRamCapacityMB;
	
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
	
	
}
