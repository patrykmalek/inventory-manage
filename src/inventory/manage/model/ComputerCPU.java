package inventory.manage.model;

public class ComputerCPU {

	private int computerCpuID;
	private String computerCpuName;
	private int computerCpuCoresNumber;
	private int computerCpuClockSpeed;
	private boolean used;
	
	public ComputerCPU() {}

	public int getComputerCpuID() {
		return computerCpuID;
	}

	public void setComputerCpuID(int computerCpuID) {
		this.computerCpuID = computerCpuID;
	}

	public String getComputerCpuName() {
		return computerCpuName;
	}

	public void setComputerCpuName(String computerCpuName) {
		this.computerCpuName = computerCpuName;
	}

	public int getComputerCpuCoresNumber() {
		return computerCpuCoresNumber;
	}

	public void setComputerCpuCoresNumber(int computerCpuCoresNumber) {
		this.computerCpuCoresNumber = computerCpuCoresNumber;
	}

	public int getComputerCpuClockSpeed() {
		return computerCpuClockSpeed;
	}

	public void setComputerCpuClockSpeed(int computerCpuClockSpeed) {
		this.computerCpuClockSpeed = computerCpuClockSpeed;
	}

	@Override
	public String toString() {
		return getComputerCpuName();
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
	



}
