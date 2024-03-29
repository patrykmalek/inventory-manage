package inventory.manage.dao.service;


import javax.swing.JOptionPane;
import javax.swing.RowFilter;

import inventory.manage.controller.Controller;
import inventory.manage.dao.ManagerDAO;
import inventory.manage.exception.SystemOperationException;
import inventory.manage.model.CommonFunctions;
import inventory.manage.model.Device;
import inventory.manage.model.License;
import inventory.manage.model.OperationType;
import inventory.manage.model.Software;
import inventory.manage.view.details.window.ComputerLicenseAssignedDetailsFrame;
import inventory.manage.view.details.window.LicenseDetailsFrame;
import inventory.manage.view.dictionary.table.panel.DeviceTablePanel;
import inventory.manage.view.dictionary.table.panel.DictionaryFrame;
import inventory.manage.view.dictionary.table.panel.LicenseDictionaryTablePanel;
import inventory.manage.view.dictionary.table.panel.SoftwareDictionaryTablePanel;

public class LicenseServiceDAO {

	
	private ManagerDAO managerDAO;
	private Controller controller;

	public LicenseServiceDAO(Controller controller, ManagerDAO managerDAO) {
		setController(controller);
		setManagerDAO(managerDAO);
		getManagerDAO().setLicenseServiceDAO(this);
	}

	public void saveData(License license, OperationType operationType) {
		
		if(operationType == OperationType.INSERT) {
			insertLicense(license);
		} else if (operationType == OperationType.UPDATE) {
			updateLicense(license);
		} else if (operationType == OperationType.DELETE) {
			deleteLicense(license);
		} else {
			new SystemOperationException("Brak określonego typu operacji");
		}
	}
	
	public void deleteLicenseWithPrompt(int licenseID) {	
		if(!CommonFunctions.validateID(licenseID)) return;
		License license = getManagerDAO().getLicenseDAO().get(licenseID);
		
		int result = JOptionPane.showOptionDialog(controller.getMainFrame(),
			    "Czy napewno chcesz usunąć urządzenie z bazy?",
			    "Potwierdzenie usunięcia",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    new Object[] {"Tak","Nie"},
			    "Tak");
	
		 if(result == JOptionPane.OK_OPTION) {
			deleteLicense(license);
		}
		
	}
	
	public void openLicenseWindowToAddNew() {
		LicenseDetailsFrame licenseDetailsWindow = new LicenseDetailsFrame(controller);
		licenseDetailsWindow.setOperationType(OperationType.INSERT);
		licenseDetailsWindow.createWindow();
		licenseDetailsWindow.showWindow();
	}
	
	public void openLicenseWindowToOnlyShowDetails(int licenseID) {
		if(!CommonFunctions.validateID(licenseID)) return;
		License license = getManagerDAO().getLicenseDAO().get(licenseID);
		LicenseDetailsFrame licenseDetailsWindow = new LicenseDetailsFrame(controller);
		licenseDetailsWindow.setOperationType(OperationType.DISPLAY);
		licenseDetailsWindow.createWindow();
		licenseDetailsWindow.addLicenseDataToView(license);
		licenseDetailsWindow.showWindow();
	}
	
	public void openLicenseWindowToUpdate(int licenseID) {	
		if(!CommonFunctions.validateID(licenseID)) return;
		License license = getManagerDAO().getLicenseDAO().get(licenseID);
		LicenseDetailsFrame licenseDetailsWindow = new LicenseDetailsFrame(controller);
		licenseDetailsWindow.setOperationType(OperationType.UPDATE);
		licenseDetailsWindow.createWindow();
		licenseDetailsWindow.addLicenseDataToView(license);
		licenseDetailsWindow.showWindow();
	}
	
	public void openSoftwareDictionaryWindowAndAddItem() {	
		SoftwareDictionaryTablePanel softwareDictionaryTablePanel = new SoftwareDictionaryTablePanel(new Controller(getController().getDatabaseProvider()));
		softwareDictionaryTablePanel.updateTable(getManagerDAO().getSoftwareDAO().getAll());
		DictionaryFrame<Software> dictionaryFrame = new DictionaryFrame<>(getController(), softwareDictionaryTablePanel);
		dictionaryFrame.showWindow();
		
		int choosenID = softwareDictionaryTablePanel.getChoosenID();
		if(choosenID < 0) return;
		Software software = getManagerDAO().getSoftwareDAO().get(choosenID);
		getController().getLicenseDetailsFrame().getLicenseSoftwareField().addItem(software);
		softwareDictionaryTablePanel.resetChoosenID();
	}
	
	public void openDeviceDictionaryWindowAndAddItem() {	
		DeviceTablePanel deviceTablePanel = new DeviceTablePanel(new Controller(getController().getDatabaseProvider()));
		deviceTablePanel.updateTable(getManagerDAO().getDeviceDAO().getOnlyComputersAll());
		DictionaryFrame<Device> dictionaryFrame = new DictionaryFrame<>(getController(), deviceTablePanel);
		dictionaryFrame.setSize(1200, 600);
		dictionaryFrame.setLocationRelativeTo(null);
		dictionaryFrame.showWindow();
		
		int choosenID = deviceTablePanel.getChoosenID();
		if(choosenID < 0) return;
		Device device = getManagerDAO().getDeviceDAO().get(choosenID);
		getController().getComputerLicenseAssignedDetailsFrame().getDeviceField().addItem(device);
		deviceTablePanel.resetChoosenID();
	}
	

	public void openLicenseDictionaryWindowAndAddItem() {
		LicenseDictionaryTablePanel licenseDictionaryTablePanel = new LicenseDictionaryTablePanel(new Controller(getController().getDatabaseProvider()));
		licenseDictionaryTablePanel.updateTable(getManagerDAO().getLicenseDAO().getAll());
		licenseDictionaryTablePanel.getTable().getTableSorter().setRowFilter(RowFilter.regexFilter("(?i)" + false, 10));
		DictionaryFrame<License> dictionaryFrame = new DictionaryFrame<>(getController(), licenseDictionaryTablePanel);
		dictionaryFrame.setSize(1200, 600);
		dictionaryFrame.setLocationRelativeTo(null);
		dictionaryFrame.showWindow();
		
		int choosenID = licenseDictionaryTablePanel.getChoosenID();
		if(choosenID < 0) return;
		License license = getManagerDAO().getLicenseDAO().get(choosenID);
		getController().getComputerLicenseAssignedDetailsFrame().getLicenseField().addItem(license);
		licenseDictionaryTablePanel.resetChoosenID();
	}
	

	public void openAssignedLicenseToDevice(Device device) {
		ComputerLicenseAssignedDetailsFrame computerLicenseAssignedDetailsFrame = new ComputerLicenseAssignedDetailsFrame(getController());
		computerLicenseAssignedDetailsFrame.setOperationType(OperationType.UPDATE);
		computerLicenseAssignedDetailsFrame.createWindow();
		computerLicenseAssignedDetailsFrame.addComputerComponentDataToView(device);
		computerLicenseAssignedDetailsFrame.showWindow();
	}
	
	public void findSoftwareByTextAndAddItemToLicenseDetailsPanel() {
		String textToFind = getController().getLicenseDetailsFrame().getLicenseSoftwareField().getCustomTextField().getText();
		Software software = getController().getManagerDAO().getSoftwareDAO().get(textToFind);
		getController().getLicenseDetailsFrame().getLicenseSoftwareField().addItem(software);
	}


	public void insertLicense(License license) {
		getManagerDAO().getLicenseDAO().insert(license);
		getController().getLicenseDictionaryTable().updateTable(getManagerDAO().getLicenseDAO().getAll());
	}

	public void updateLicense(License license) {
		getManagerDAO().getLicenseDAO().update(license);
		getController().getLicenseDictionaryTable().updateTable(getManagerDAO().getLicenseDAO().getAll());
	}
	
	public void deleteLicense(License license) {
		getManagerDAO().getLicenseDAO().delete(license);
		getController().getLicenseDictionaryTable().updateTable(getManagerDAO().getLicenseDAO().getAll());
	}

	public void assignedDeviceToLicense(License license) {
		getManagerDAO().getLicenseDAO().update(license);
	}

	public void deleteAssignedLicense(int licenseID, int deviceID) {
		if(!CommonFunctions.validateID(licenseID)) return;
		License license = getManagerDAO().getLicenseDAO().get(licenseID);
		
		int result = JOptionPane.showOptionDialog(controller.getMainFrame(),
			    "Czy napewno chcesz usunąć powiązanie licencji z tym urządzeniem?",
			    "Potwierdzenie odłączenia",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    new Object[] {"Tak","Nie"},
			    "Tak");
	
		 if(result != JOptionPane.OK_OPTION) return;
		
		if(license.getDevice().getDeviceID() == deviceID) {
			license.setDevice(null);
			getManagerDAO().getLicenseDAO().update(license);
		} else {
			new SystemOperationException("Błąd podczas odłączania licencji od urządzenia");
		}
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
