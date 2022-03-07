package kruszywo.sa.computers.manage.dao.service;

import javax.swing.JOptionPane;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.dao.ManagerDAO;
import kruszywo.sa.computers.manage.exception.SystemOperationException;
import kruszywo.sa.computers.manage.model.CommonFunctions;
import kruszywo.sa.computers.manage.model.Software;
import kruszywo.sa.computers.manage.model.License;
import kruszywo.sa.computers.manage.model.LicenseType;
import kruszywo.sa.computers.manage.model.Employee;
import kruszywo.sa.computers.manage.model.OperationType;
import kruszywo.sa.computers.manage.model.Software;
import kruszywo.sa.computers.manage.view.details.window.LicenseDetailsFrame;
import kruszywo.sa.computers.manage.view.dictionary.table.panel.SoftwareDictionaryTablePanel;
import kruszywo.sa.computers.manage.view.dictionary.table.panel.LicenseTypeDictionaryTablePanel;
import kruszywo.sa.computers.manage.view.dictionary.table.panel.DictionaryFrame;
import kruszywo.sa.computers.manage.view.dictionary.table.panel.EmployeeDictionaryTablePanel;

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

	
	public void findSoftwareByTextAndAddItemToLicenseDetailsPanel() {
		String textToFind = getController().getLicenseDetailsFrame().getLicenseSoftwareField().getCustomTextField().getText();
		Software software = getController().getManagerDAO().getSoftwareDAO().get(textToFind);
		getController().getLicenseDetailsFrame().getLicenseSoftwareField().addItem(software);
	}


	public void insertLicense(License license) {
		getManagerDAO().getLicenseDAO().insert(license);
		getController().getLicenseDictionaryTablePanel().updateTable(getManagerDAO().getLicenseDAO().getAll());
	}

	public void updateLicense(License license) {
		getManagerDAO().getLicenseDAO().update(license);
		getController().getLicenseDictionaryTablePanel().updateTable(getManagerDAO().getLicenseDAO().getAll());
	}
	
	public void deleteLicense(License license) {
		getManagerDAO().getLicenseDAO().delete(license);
		getController().getLicenseDictionaryTablePanel().updateTable(getManagerDAO().getLicenseDAO().getAll());
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
