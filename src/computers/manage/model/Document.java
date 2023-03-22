package computers.manage.model;

import java.nio.file.Paths;

public class Document {

	private int documentID;
	private String documentName;
	private String documentPath;
	private String documentDate;
	private String addedDate;
	private String originalName;
	private ConnectedDocument connectedDocument;


	public int getDocumentID() {
		return documentID;
	}

	public void setDocumentID(int documentID) {
		this.documentID = documentID;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	public String getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(String addedDate) {
		this.addedDate = addedDate;
	}

	public String getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(String documentDate) {
		this.documentDate = documentDate;
	}

	public ConnectedDocument getConnectedDocument() {
		return connectedDocument;
	}

	public void setConnectedDocument(ConnectedDocument connectedDocument) {
		this.connectedDocument = connectedDocument;
	}

	@Override
	public String toString() {
		return documentName + " (" + Paths.get(documentPath).getFileName().toString() +")";
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	
}
