package computers.manage.dao.service;

import javax.swing.JOptionPane;

import computers.manage.controller.Controller;
import computers.manage.dao.ManagerDAO;
import computers.manage.exception.SystemOperationException;
import computers.manage.model.CommonFunctions;
import computers.manage.model.ComputerMassStorage;
import computers.manage.model.OperationType;
import computers.manage.view.details.window.ComputerMassStorageDetailsFrame;

public class ComputerMassStorageServiceDAO {

	private ManagerDAO managerDAO;
	private Controller controller;

	public ComputerMassStorageServiceDAO(Controller controller, ManagerDAO managerDAO) {
		setController(controller);
		setManagerDAO(managerDAO);
		getManagerDAO().setComputerMassStorageServiceDAO(this);
	}

	public void saveData(ComputerMassStorage ccomputerMassStorage, OperationType operationType) {
		
		if(operationType == OperationType.INSERT) {
			insertComputerMassStorage(ccomputerMassStorage);
		} else if (operationType == OperationType.UPDATE) {
			updateComputerMassStorage(ccomputerMassStorage);
		} else if (operationType == OperationType.DELETE) {
			deleteComputerMassStorage(ccomputerMassStorage);
		} else {
			new SystemOperationException("Brak określonego typu operacji");
			return;
		}
	}
	
	public void deleteComputerMassStorageWithPrompt(int ccomputerMassStorageID) {	
		if(!CommonFunctions.validateID(ccomputerMassStorageID)) return;
		ComputerMassStorage ccomputerMassStorage = getManagerDAO().getComputerMassStorageDAO().get(ccomputerMassStorageID);
		
		int result = JOptionPane.showOptionDialog(controller.getMainFrame(),
			    "Czy napewno chcesz usunąć podzespół z bazy?",
			    "Potwierdzenie usunięcia",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    new Object[] {"Tak","Nie"},
			    "Tak");
	
		 if(result == JOptionPane.OK_OPTION) {
			deleteComputerMassStorage(ccomputerMassStorage);
		}
		
	}
	
	public void openComputerMassStorageWindowToAddNew() {
		ComputerMassStorageDetailsFrame ccomputerMassStorageDetailsWindow = new ComputerMassStorageDetailsFrame(controller);
		ccomputerMassStorageDetailsWindow.setOperationType(OperationType.INSERT);
		ccomputerMassStorageDetailsWindow.createWindow();
		ccomputerMassStorageDetailsWindow.showWindow();
	}
	
	public void openComputerMassStorageWindowToOnlyShowDetails(int deviceID) {
		if(!CommonFunctions.validateID(deviceID)) return;
		ComputerMassStorage ccomputerMassStorage = getManagerDAO().getComputerMassStorageDAO().get(deviceID);
		ComputerMassStorageDetailsFrame ccomputerMassStorageDetailsWindow = new ComputerMassStorageDetailsFrame(controller);
		ccomputerMassStorageDetailsWindow.setOperationType(OperationType.DISPLAY);
		ccomputerMassStorageDetailsWindow.createWindow();
		ccomputerMassStorageDetailsWindow.addComputerMassStorageDataToView(ccomputerMassStorage);
		ccomputerMassStorageDetailsWindow.showWindow();
	}
	
	public void openComputerMassStorageWindowToUpdate(int deviceID) {	
		if(!CommonFunctions.validateID(deviceID)) return;
		ComputerMassStorage ccomputerMassStorage = getManagerDAO().getComputerMassStorageDAO().get(deviceID);
		ComputerMassStorageDetailsFrame ccomputerMassStorageDetailsWindow = new ComputerMassStorageDetailsFrame(controller);
		ccomputerMassStorageDetailsWindow.setOperationType(OperationType.UPDATE);
		ccomputerMassStorageDetailsWindow.createWindow();
		ccomputerMassStorageDetailsWindow.addComputerMassStorageDataToView(ccomputerMassStorage);
		ccomputerMassStorageDetailsWindow.showWindow();
	}

	public void insertComputerMassStorage(ComputerMassStorage ccomputerMassStorage) {
		getManagerDAO().getComputerMassStorageDAO().insert(ccomputerMassStorage);
		getController().getComputerMassStorageDictionaryTable().updateTable(getManagerDAO().getComputerMassStorageDAO().getAll());
	}

	public void updateComputerMassStorage(ComputerMassStorage ccomputerMassStorage) {
		getManagerDAO().getComputerMassStorageDAO().update(ccomputerMassStorage);
		getController().getComputerMassStorageDictionaryTable().updateTable(getManagerDAO().getComputerMassStorageDAO().getAll());
	}
	
	public void deleteComputerMassStorage(ComputerMassStorage ccomputerMassStorage) {
		getManagerDAO().getComputerMassStorageDAO().delete(ccomputerMassStorage);
		getController().getComputerMassStorageDictionaryTable().updateTable(getManagerDAO().getComputerMassStorageDAO().getAll());
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
