package computers.manage.dao.service;

import javax.swing.JOptionPane;

import computers.manage.controller.Controller;
import computers.manage.dao.ManagerDAO;
import computers.manage.exception.SystemOperationException;
import computers.manage.model.CommonFunctions;
import computers.manage.model.OperationType;
import computers.manage.model.Software;
import computers.manage.view.details.window.SoftwareDetailsFrame;

public class SoftwareServiceDAO {

	private ManagerDAO managerDAO;
	private Controller controller;

	public SoftwareServiceDAO(Controller controller, ManagerDAO managerDAO) {
		setController(controller);
		setManagerDAO(managerDAO);
		getManagerDAO().setSoftwareServiceDAO(this);
	}

	public void saveData(Software software, OperationType operationType) {
		
		if(operationType == OperationType.INSERT) {
			insertSoftware(software);
		} else if (operationType == OperationType.UPDATE) {
			updateSoftware(software);
		} else if (operationType == OperationType.DELETE) {
			deleteSoftware(software);
		} else {
			new SystemOperationException("Brak określonego typu operacji");
			return;
		}
	}
	
	public void deleteSoftwareWithPrompt(int softwareID) {	
		if(!CommonFunctions.validateID(softwareID)) return;
		Software software = getManagerDAO().getSoftwareDAO().get(softwareID);
		
		int result = JOptionPane.showOptionDialog(controller.getMainFrame(),
			    "Czy napewno chcesz usunąć typ oprogramowania z bazy?",
			    "Potwierdzenie usunięcia",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    new Object[] {"Tak","Nie"},
			    "Tak");
	
		 if(result == JOptionPane.OK_OPTION) {
			deleteSoftware(software);
		}
		
	}
	
	public void openSoftwareWindowToAddNew() {
		SoftwareDetailsFrame softwareDetailsWindow = new SoftwareDetailsFrame(controller);
		softwareDetailsWindow.setOperationType(OperationType.INSERT);
		softwareDetailsWindow.createWindow();
		softwareDetailsWindow.showWindow();
	}
	
	public void openSoftwareWindowToOnlyShowDetails(int deviceID) {
		if(!CommonFunctions.validateID(deviceID)) return;
		Software software = getManagerDAO().getSoftwareDAO().get(deviceID);
		SoftwareDetailsFrame softwareDetailsWindow = new SoftwareDetailsFrame(controller);
		softwareDetailsWindow.setOperationType(OperationType.DISPLAY);
		softwareDetailsWindow.createWindow();
		softwareDetailsWindow.addSoftwareDataToView(software);
		softwareDetailsWindow.showWindow();
	}
	
	public void openSoftwareWindowToUpdate(int deviceID) {	
		if(!CommonFunctions.validateID(deviceID)) return;
		Software software = getManagerDAO().getSoftwareDAO().get(deviceID);
		SoftwareDetailsFrame softwareDetailsWindow = new SoftwareDetailsFrame(controller);
		softwareDetailsWindow.setOperationType(OperationType.UPDATE);
		softwareDetailsWindow.createWindow();
		softwareDetailsWindow.addSoftwareDataToView(software);
		softwareDetailsWindow.showWindow();
	}

	public void insertSoftware(Software software) {
		getManagerDAO().getSoftwareDAO().insert(software);
		getController().getSoftwareDictionaryTable().updateTable(getManagerDAO().getSoftwareDAO().getAll());
	}

	public void updateSoftware(Software software) {
		getManagerDAO().getSoftwareDAO().update(software);
		getController().getSoftwareDictionaryTable().updateTable(getManagerDAO().getSoftwareDAO().getAll());
	}
	
	public void deleteSoftware(Software software) {
		getManagerDAO().getSoftwareDAO().delete(software);
		getController().getSoftwareDictionaryTable().updateTable(getManagerDAO().getSoftwareDAO().getAll());
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
