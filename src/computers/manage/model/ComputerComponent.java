package computers.manage.model;

public class ComputerComponent {

	
	private int computerComponentID;
	private Device device;
	private ComputerCPU computerCPU;
	private ComputerRAM computerRAM;
	private ComputerMassStorage computerMassStorageFirst;
	private ComputerMassStorage computerMassStorageSecond;
	private ComputerMassStorage computerMassStorageThird;
	private String computerSystemName;
	
	public ComputerComponent() {}
	
	public ComputerCPU getComputerCPU() {
		return (computerCPU != null) ? computerCPU : new ComputerCPU();
	}
	public void setComputerCPU(ComputerCPU computerCPU) {
		this.computerCPU = computerCPU;
	}
	public ComputerRAM getComputerRAM() {
		return (computerRAM != null) ? computerRAM : new ComputerRAM();
	}
	public void setComputerRAM(ComputerRAM computerRAM) {
		this.computerRAM = computerRAM;
	}
	public ComputerMassStorage getComputerMassStorageFirst() {
		return (computerMassStorageFirst != null) ? computerMassStorageFirst : new ComputerMassStorage();
	}
	public void setComputerMassStorageFirst(ComputerMassStorage computerMassStorageFirst) {
		this.computerMassStorageFirst = computerMassStorageFirst;
	}
	public ComputerMassStorage getComputerMassStorageSecond() {
		return (computerMassStorageSecond != null) ? computerMassStorageSecond : new ComputerMassStorage();
	}
	public void setComputerMassStorageSecond(ComputerMassStorage computerMassStorageSecond) {
		this.computerMassStorageSecond = computerMassStorageSecond;
	}
	public ComputerMassStorage getComputerMassStorageThird() {
		return (computerMassStorageThird != null) ? computerMassStorageThird : new ComputerMassStorage();
	}
	public void setComputerMassStorageThird(ComputerMassStorage computerMassStorageThird) {
		this.computerMassStorageThird = computerMassStorageThird;
	}

	public Device getDevice() {
		return (device != null) ? device : new Device();
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public int getComputerComponentID() {
		return computerComponentID;
	}

	public void setComputerComponentID(int computerComponentID) {
		this.computerComponentID = computerComponentID;
	}

	public String getComputerSystemName() {
		return computerSystemName;
	}

	public void setComputerSystemName(String computerSystemName) {
		this.computerSystemName = computerSystemName;
	}
	
}
