package inventory.manage.dao.service;


import java.util.List;

import javax.swing.JOptionPane;

import inventory.manage.controller.Controller;
import inventory.manage.dao.ManagerDAO;
import inventory.manage.exception.SystemOperationException;
import inventory.manage.model.CommonFunctions;
import inventory.manage.model.Device;
import inventory.manage.model.Document;
import inventory.manage.model.OperationType;
import inventory.manage.view.details.window.DeviceConnectedDocumentDetailsFrame;
import inventory.manage.view.details.window.DocumentDetailsFrame;
import inventory.manage.view.dictionary.table.panel.DictionaryFrame;
import inventory.manage.view.dictionary.table.panel.DocumentDictionaryTablePanel;


public class DocumentServiceDAO {

	
	private ManagerDAO managerDAO;
	private Controller controller;

	public DocumentServiceDAO(Controller controller, ManagerDAO managerDAO) {
		setController(controller);
		setManagerDAO(managerDAO);
		getManagerDAO().setDocumentServiceDAO(this);
	}

	public void saveData(Document document, OperationType operationType) {
		
		if(operationType == OperationType.INSERT) {
			insertDocument(document);
		} else if (operationType == OperationType.UPDATE) {
			updateDocument(document);
		} else if (operationType == OperationType.DELETE) {
			deleteDocument(document);
		} else {
			new SystemOperationException("Brak określonego typu operacji");
		}
	}
	
	public void deleteDocumentWithPrompt(int documentID) {	
		if(!CommonFunctions.validateID(documentID)) return;
		Document document = getManagerDAO().getDocumentDAO().get(documentID);
		
		int result = JOptionPane.showOptionDialog(controller.getMainFrame(),
			    "Czy napewno chcesz usunąć dokument z bazy?",
			    "Potwierdzenie usunięcia",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    new Object[] {"Tak","Nie"},
			    "Tak");
	
		 if(result == JOptionPane.OK_OPTION) {
			deleteDocument(document);
		}
		
	}
	
	public void openDocumentWindowToAddNew() {
		DocumentDetailsFrame documentDetailsWindow = new DocumentDetailsFrame(controller);
		documentDetailsWindow.setOperationType(OperationType.INSERT);
		documentDetailsWindow.createWindow();
		documentDetailsWindow.showWindow();
	}
	
	public void openDocumentWindowToAddNewWithAssign(int deviceID) {
		if(!CommonFunctions.validateID(deviceID)) return;
		DocumentDetailsFrame documentDetailsWindow = new DocumentDetailsFrame(controller);
		documentDetailsWindow.setNewAssignedDeviceID(deviceID);
		documentDetailsWindow.setOperationType(OperationType.INSERT);
		documentDetailsWindow.createWindow();
		documentDetailsWindow.showWindow();
	}
	
	public void openDocumentWindowToOnlyShowDetails(int documentID) {
		if(!CommonFunctions.validateID(documentID)) return;
		Document document = getManagerDAO().getDocumentDAO().get(documentID);
		DocumentDetailsFrame documentDetailsWindow = new DocumentDetailsFrame(controller);
		documentDetailsWindow.setOperationType(OperationType.DISPLAY);
		documentDetailsWindow.createWindow();
		documentDetailsWindow.addDocumentDataToView(document);
		documentDetailsWindow.showWindow();
	}
	
	public void openDocumentWindowToUpdate(int documentID) {	
		if(!CommonFunctions.validateID(documentID)) return;
		Document document = getManagerDAO().getDocumentDAO().get(documentID);
		DocumentDetailsFrame documentDetailsWindow = new DocumentDetailsFrame(controller);
		documentDetailsWindow.setOperationType(OperationType.UPDATE);
		documentDetailsWindow.createWindow();
		documentDetailsWindow.addDocumentDataToView(document);
		documentDetailsWindow.showWindow();
	}
	
	public void showDocumentFile(int documentID) {	
		if(!CommonFunctions.validateID(documentID)) return;
		Document document = getManagerDAO().getDocumentDAO().get(documentID);
		getController().openFileByFilePath(document.getDocumentPath());
	}
	
	public void openAssignDocumentToDevice(Device device) {
		DeviceConnectedDocumentDetailsFrame deviceConnectedDocumentDetailsFrame = new DeviceConnectedDocumentDetailsFrame(getController());
		deviceConnectedDocumentDetailsFrame.setOperationType(OperationType.UPDATE);
		deviceConnectedDocumentDetailsFrame.createWindow();
		deviceConnectedDocumentDetailsFrame.addComputerComponentDataToView(device);
		deviceConnectedDocumentDetailsFrame.showWindow();
	}
	
	public void openDocumentDictionaryWindowAndAddItem() {
		DocumentDictionaryTablePanel documentDictionaryTablePanel = new DocumentDictionaryTablePanel(new Controller(getController().getDatabaseProvider()));
		documentDictionaryTablePanel.updateTable(getManagerDAO().getDocumentDAO().getAll());
		DictionaryFrame<Document> dictionaryFrame = new DictionaryFrame<>(getController(), documentDictionaryTablePanel);
		dictionaryFrame.setSize(1200, 600);
		dictionaryFrame.setLocationRelativeTo(null);
		dictionaryFrame.showWindow();
		
		int choosenID = documentDictionaryTablePanel.getChoosenID();
		if(choosenID < 0) return;
		Document document = getManagerDAO().getDocumentDAO().get(choosenID);
		getController().getDeviceConnectedDocumentDetailsFrame().getDocumentField().addItem(document);
		documentDictionaryTablePanel.resetChoosenID();
	}	

	public void insertDocument(Document document) {
		document = copyFileToServerDirectory(document);
		getManagerDAO().getDocumentDAO().insert(document);
		if(getController().getDocumentDictionaryTable() != null) getController().getDocumentDictionaryTable().updateTable(getManagerDAO().getDocumentDAO().getAll());
		if(getController().getDeviceConnectedDocumentTablePanel() != null) getController().getDeviceConnectedDocumentTablePanel().updateTable(getManagerDAO().getDocumentDAO().getAllByDevice(document.getConnectedDocument().getDeviceID()));
	}

	public void updateDocument(Document document) {
		Document oldDocument = getManagerDAO().getDocumentDAO().get(document.getDocumentID());
		if(document.getDocumentPath() != oldDocument.getDocumentPath()) document = copyFileToServerDirectory(document);
		getManagerDAO().getDocumentDAO().update(document);
		if(getController().getDocumentDictionaryTable() != null) getController().getDocumentDictionaryTable().updateTable(getManagerDAO().getDocumentDAO().getAll());
		if(getController().getDeviceConnectedDocumentTablePanel() != null && document.getConnectedDocument() != null) getController().getDeviceConnectedDocumentTablePanel().updateTable(getManagerDAO().getDocumentDAO().getAllByDevice(document.getConnectedDocument().getDeviceID()));
	}
	
	public void deleteDocument(Document document) {
		if(checkIfDocumentHaveConnect(document)) return;
		getManagerDAO().getDocumentDAO().delete(document);
		if(getController().getDocumentDictionaryTable() != null) getController().getDocumentDictionaryTable().updateTable(getManagerDAO().getDocumentDAO().getAll());
		if(getController().getDeviceConnectedDocumentTablePanel() != null && document.getConnectedDocument() != null) getController().getDeviceConnectedDocumentTablePanel().updateTable(getManagerDAO().getDocumentDAO().getAllByDevice(document.getConnectedDocument().getDeviceID()));
		String deletedFilePath = getController().getDatabaseConfig().getLocalDirectoryPath() + "deleted\\arch_" + getController().getFileNameFromFilePath(document.getDocumentPath());
		getController().copyFileWithDeleteSource(document.getDocumentPath(), deletedFilePath);
	}
	
	public Document copyFileToServerDirectory(Document document) {
		if(getController().isFileExist(getController().getDatabaseConfig().getLocalDirectoryPath() + getController().getFileNameFromFilePath(document.getDocumentPath()))) return document;
		String newDocumentPath = getController().getDatabaseConfig().getLocalDirectoryPath() + System.currentTimeMillis() + "_" + document.getOriginalName();
		getController().copyFile(document.getDocumentPath(), newDocumentPath);
		document.setDocumentPath(newDocumentPath);
		return document;
	}
	
	public boolean assignDocumentToDevice(Document document) {
		
		List<Document> connectedDocumentWithDevice = getManagerDAO().getDocumentDAO().getAllByDevice(document.getConnectedDocument().getDeviceID());
		
		boolean itemExists = connectedDocumentWithDevice.stream().anyMatch(item -> 
							item.getConnectedDocument().getDeviceID() == document.getConnectedDocument().getDeviceID() &&
							item.getConnectedDocument().getDocumentID() == document.getConnectedDocument().getDocumentID());
		
		if(itemExists) {
			JOptionPane.showMessageDialog(controller.getMainFrame(),
				    "Dokument, który chcesz powiązać jest już powiązany z tym urządzeniem",
				    "Ostrzeżenie",
				    JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		getManagerDAO().getDocumentDAO().assignDocumentToDevice(document);
		return true;
	}
	
	public void deleteAssignDocumentToDevice(int documentID, int deviceID) {
		if(!CommonFunctions.validateID(documentID)) return;
     
		int result = JOptionPane.showOptionDialog(controller.getMainFrame(),
			    "Czy napewno chcesz usunąć powiązanie dokumentu z tym urządzeniem?",
			    "Potwierdzenie odłączenia",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    new Object[] {"Tak","Nie"},
			    "Tak");
	
		 if(result != JOptionPane.OK_OPTION) return;

		 getManagerDAO().getDocumentDAO().detachDocumentFromDevice(documentID, deviceID);
	}
	
	public boolean checkIfDocumentHaveConnect(Document document){
		List<Device> connectedDevices = getManagerDAO().getDocumentDAO().getAllConnectedDevicesWithDocument(document.getDocumentID());
		String devicesName = "";
		
		if(connectedDevices.size() > 0) {
			System.out.println(connectedDevices);
			for (Device device : connectedDevices) {
				devicesName += "\n" + device.getDeviceName() + " (" + device.getDeviceUniqueNumber() +")";
			}
			
			JOptionPane.showMessageDialog(controller.getMainFrame(), 
					"Nie można usunąć wybranego dokumentu, ponieważ ma powiązanie z następującymi urządzeniami: \n " + devicesName, 
					"Ostrzeżenie",
				    JOptionPane.WARNING_MESSAGE);
			return true;
		}
		
		return false;
	}
	
	public boolean checkIfDocumentAlreadyExists(Document document) {
		List<Document> allDocuments = getManagerDAO().getDocumentDAO().getAll();
		
		boolean itemExists = allDocuments.stream().anyMatch(item -> item.getDocumentName().equals(document.getDocumentName()) && item.getDocumentID() != document.getDocumentID());
		
		if(itemExists) {
			JOptionPane.showMessageDialog(controller.getMainFrame(),
				    "Dokument o tej nazwie już istnieje. Zmień nazwę dokumentu. \n (Nazwy plików źródłowych mogą być takie same. Zostaną zapisane pod inną nazwą)",
				    "Ostrzeżenie",
				    JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		return true;
	}

	public ManagerDAO getManagerDAO() {
		return managerDAO;
	}

	public void setManagerDAO(ManagerDAO managerDAO) {
		this.managerDAO = managerDAO;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}



	
}
