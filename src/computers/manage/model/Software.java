package computers.manage.model;

public class Software {

	private int softwareID;
	private String softwareName;
	private String softwareNotes;
	
	public Software() {}
	
	public Software(int softwareID, String softwareName) {
		this.softwareID = softwareID;
		this.softwareName = softwareName;
	}
	
	public int getSoftwareID() {
		return softwareID;
	}
	
	public void setSoftwareID(int softwareID) {
		this.softwareID = softwareID;
	}
	
	public String getSoftwareName() {
		return softwareName;
	}
	
	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	public String getSoftwareNotes() {
		return softwareNotes;
	}

	public void setSoftwareNotes(String softwareNotes) {
		this.softwareNotes = softwareNotes;
	}

	@Override
	public String toString() {
		return softwareName;
	}
}
