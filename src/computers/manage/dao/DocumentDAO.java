package computers.manage.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import computers.manage.controller.Controller;
import computers.manage.exception.SystemOperationException;
import computers.manage.model.ConnectedDocument;
import computers.manage.model.Device;
import computers.manage.model.Document;

public class DocumentDAO implements DAO<Document>{

	private List<Document> documents;
	private Controller controller;
	
	private static final String FIND_BY_ID = "SELECT * FROM DOCUMENTS DD WHERE DD.ID_DOCUMENT = ?;";
	private static final String FIND_BY_DEVICE_ID = "SELECT * FROM DOCUMENTS DD INNER JOIN CONNECTED_DOCUMENTS CD ON (CD.ID_DOCUMENT = DD.ID_DOCUMENT) WHERE CD.ID_DEVICE = ?;";
	private static final String FIND_CONNECTED_BY_ID = "SELECT * FROM DOCUMENTS DD INNER JOIN CONNECTED_DOCUMENTS CD ON (CD.ID_DOCUMENT = DD.ID_DOCUMENT) WHERE DD.ID_DOCUMENT = ?;";
//	private static final String FIND_BY_LICENSE_ID = "SELECT * FROM DOCUMENTS DD INNER JOIN CONNECTED_DOCUMENTS CD ON (CD.ID_DOCUMENT = DD.ID_DOCUMENT) WHERE CD.ID_LICENSE = ?;";
//	private static final String FIND_BY_MEMORY_RAM_ID = "SELECT * FROM DOCUMENTS DD INNER JOIN CONNECTED_DOCUMENTS CD ON (CD.ID_DOCUMENT = DD.ID_DOCUMENT) WHERE CD.ID_MEMORY_RAM = ?;";
//	private static final String FIND_BY_MASS_STORAGE_ID = "SELECT * FROM DOCUMENTS DD INNER JOIN CONNECTED_DOCUMENTS CD ON (CD.ID_DOCUMENT = DD.ID_DOCUMENT) WHERE CD.ID_MASS_STORAGE = ?;";
	private static final String FIND_ALL = "SELECT * FROM DOCUMENTS DD;";
    private static final String INSERT = "INSERT INTO DOCUMENTS (DOCUMENT_NAME, DOCUMENT_PATH, DOCUMENT_DATE, ADDED_DATE, ORIGINAL_FILE_NAME) VALUES(?, ?, ?, ?, ?);";
    private static final String INSERT_CONNECTED_DOCUMENT = "INSERT INTO CONNECTED_DOCUMENTS (ID_DOCUMENT, ID_DEVICE, ID_LICENSE, ID_MEMORY_RAM, ID_MASS_STORAGE) VALUES(?, ?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE DOCUMENTS SET DOCUMENT_NAME=?, DOCUMENT_PATH=?, DOCUMENT_DATE=?, ADDED_DATE=?, ORIGINAL_FILE_NAME=? WHERE ID_DOCUMENT=?;";
    private static final String DELETE = "DELETE FROM DOCUMENTS WHERE ID_DOCUMENT=?;";
    private static final String DELETE_CONNECTED_DOCUMENT = "DELETE FROM CONNECTED_DOCUMENTS WHERE ID_DOCUMENT=? AND ID_DEVICE=?;";
	
	public DocumentDAO(Controller controller) {
		this.controller = controller;
		this.controller.getManagerDAO().setDocumentDAO(this);
	}
	
	@Override
	public Document get(int documentID) {
		Document document = null;
     	try {
	     	 PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_ID);
	     	 ps.setInt(1, documentID);
	     	 
	   		 controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
	   		 ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
   		 
			 if(resultSet.next()) {
					
				document = new Document();
			 	
			 	document.setDocumentID(resultSet.getInt("id_document"));
			 	document.setDocumentName(resultSet.getString("document_name"));
			 	document.setDocumentPath(resultSet.getString("document_path"));
			 	document.setDocumentDate(resultSet.getString("document_date"));
			 	document.setAddedDate(resultSet.getString("added_date"));
			 	document.setOriginalName(resultSet.getString("original_file_name"));
			 }
		  ps.close();
		  resultSet.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
		}
      return document;
	}
	
	public List<Device> getAllConnectedDevicesWithDocument(int documentID) {
		List<Device> connectedDevicesWithDocument = new ArrayList<Device>();
		 
     	try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_CONNECTED_BY_ID);
			ps.setInt(1, documentID);
			controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
			ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
			Device device = null;
			
		while (resultSet.next()) {
				device = controller.getManagerDAO().getDeviceDAO().get(resultSet.getInt("id_device"));
			 	connectedDevicesWithDocument.add(device);
		}
		
		  ps.close();
		  resultSet.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu powiązanych urządzeń z dokumentem z bazy", e);
		}
      return connectedDevicesWithDocument;
	}
	
	public List<Document> getAllByDevice(int deviceID) {
		 documents = new ArrayList<Document>();
	 
         	try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_DEVICE_ID);
				ps.setInt(1, deviceID);
				controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
				ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
				Document document = null;
				ConnectedDocument connectedDocument = null;
			while (resultSet.next()) {
						
					document = new Document();
				 
					connectedDocument = new ConnectedDocument();
					connectedDocument.setConnectedDocumentID(resultSet.getInt("id_connected_document_device"));
					connectedDocument.setDeviceID(deviceID);
					connectedDocument.setDocumentID(resultSet.getInt("id_document"));
					
				 	document.setDocumentID(resultSet.getInt("id_document"));
				 	document.setDocumentName(resultSet.getString("document_name"));
				 	document.setDocumentPath(resultSet.getString("document_path"));
				 	document.setDocumentDate(resultSet.getString("document_date"));
				 	document.setAddedDate(resultSet.getString("added_date"));
				 	document.setOriginalName(resultSet.getString("original_file_name"));
				 	document.setConnectedDocument(connectedDocument);
				 	
				 	documents.add(document);
				}
			  ps.close();
			  resultSet.close();
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
			}
	      return documents;
	}

	@Override
	public List<Document> getAll() {
		 documents = new ArrayList<Document>();
	 
         	try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_ALL);
				controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
				ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
				Document document = null;
			while (resultSet.next()) {
						
					document = new Document();
				 
				 	document.setDocumentID(resultSet.getInt("id_document"));
				 	document.setDocumentName(resultSet.getString("document_name"));
				 	document.setDocumentPath(resultSet.getString("document_path"));
				 	document.setDocumentDate(resultSet.getString("document_date"));
				 	document.setAddedDate(resultSet.getString("added_date"));
				 	document.setOriginalName(resultSet.getString("original_file_name"));
				 	
				 	documents.add(document);
				}
			  ps.close();
			  resultSet.close();
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
			}
	      return documents;
	}
	

	@Override
	public void insert(Document document) {
			try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(INSERT);

				
	            ps.setString(1, document.getDocumentName());
	            ps.setString(2, document.getDocumentPath());
	            ps.setString(3, document.getDocumentDate());
	            ps.setString(4, document.getAddedDate());
	            ps.setString(5, document.getOriginalName());
	            ps.executeUpdate();
				
				if(document.getConnectedDocument().getDeviceID() > 0) {
					PreparedStatement ps_connected_document = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(INSERT_CONNECTED_DOCUMENT);
					ResultSet rs = ps.getGeneratedKeys();
					int last_inserted_id = 0;
					if (rs.next()) {
					    last_inserted_id = rs.getInt(1);
					}
					 
		            ps_connected_document.setInt(1, last_inserted_id);
		            ps_connected_document.setInt(2, document.getConnectedDocument().getDeviceID());
		     
					ps_connected_document.executeUpdate();
					ps_connected_document.close();
				}
				
				ps.close();
	            System.out.println("Document with following details was saved in DB: " + document.toString());
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas zapisywania danych urządzenia do bazy.", e);
			}
	}

	@Override
	public void update(Document document) {

		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(UPDATE);
			 
			ps.setString(1, document.getDocumentName());
            ps.setString(2, document.getDocumentPath());
            ps.setString(3, document.getDocumentDate());
            ps.setString(4, document.getAddedDate());
            ps.setString(5, document.getOriginalName());
            ps.setInt(6, document.getDocumentID());

           System.out.println("TEST: -> " + document.getDocumentName());
           System.out.println("TEST: -> " + document.getOriginalName());
           System.out.println("TEST: -> " + document.getDocumentPath());
            
            ps.executeUpdate();
			ps.close();
			
            System.out.println("Document with id " + document.getDocumentID() + " was updated in DB with following details: " + document.toString());
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

	@Override
	public void delete(Document document) {
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(DELETE);
			 
            ps.setInt(1, document.getDocumentID());
            
            ps.executeUpdate();
			ps.close();
			
			System.out.println("Document with id: " +  document.getDocumentID() + " was sucesfully deleted from DB.");
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}
	
	public void assignDocumentToDevice(Document document) {
			try {
				
				PreparedStatement ps_connected_document = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(INSERT_CONNECTED_DOCUMENT);
				
	            ps_connected_document.setInt(1, document.getDocumentID());
	            ps_connected_document.setInt(2, document.getConnectedDocument().getDeviceID());
				ps_connected_document.executeUpdate();
				ps_connected_document.close();
	            System.out.println("Document with following details was connected with device in DB: " + document.toString());
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas zapisywania danych urządzenia do bazy.", e);
			}
	}
	
	public void detachDocumentFromDevice(int documentID, int deviceID) {
		try {
			
			PreparedStatement ps_connected_document = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(DELETE_CONNECTED_DOCUMENT);
			
            ps_connected_document.setInt(1, documentID);
            ps_connected_document.setInt(2, deviceID);
			ps_connected_document.executeUpdate();
			ps_connected_document.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas zapisywania danych urządzenia do bazy.", e);
		}
	}

	@Override
	public Document get(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
