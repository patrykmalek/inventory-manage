package kruszywo.sa.computers.manage.model;

import java.io.File;

public class License {

	private int licenseID;
	private String licenseKey;
	private String licenseMainKey;
	private String invoiceNumber;
	private String purchaseDate;
	private String lastInstallationDate;
	private String licenseNotes;
	private String assignedEmail;
	private File licenseFile;
	
	private Software software;
	
	public License() {}

	public int getLicenseID() {
		return licenseID;
	}

	public void setLicenseID(int licenseID) {
		this.licenseID = licenseID;
	}

	public String getLicenseKey() {
		return licenseKey;
	}

	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}

	public String getLicenseMainKey() {
		return licenseMainKey;
	}

	public void setLicenseMainKey(String licenseMainKey) {
		this.licenseMainKey = licenseMainKey;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
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

	public String getLicenseNotes() {
		return licenseNotes;
	}

	public void setLicenseNotes(String licenseNotes) {
		this.licenseNotes = licenseNotes;
	}

	public String getAssignedEmail() {
		return assignedEmail;
	}

	public void setAssignedEmail(String assignedEmail) {
		this.assignedEmail = assignedEmail;
	}

	public Software getSoftware() {
		return software;
	}

	public void setSoftware(Software software) {
		this.software = software;
	}

	@Override
	public String toString() {
		return software.toString();
	}

	public File getLicenseFile() {
		return licenseFile;
	}

	public void setLicenseFile(File licenseFile) {
		this.licenseFile = licenseFile;
	}
	
	public byte[] getLicenseFileAsByte() {
		return (licenseFile != null) ? CommonFunctions.readFileAsByte(licenseFile) : null;
	}
	
}
