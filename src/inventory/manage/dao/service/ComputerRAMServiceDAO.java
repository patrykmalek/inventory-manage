package inventory.manage.dao.service;

import javax.swing.JOptionPane;

import inventory.manage.controller.Controller;
import inventory.manage.dao.ManagerDAO;
import inventory.manage.exception.SystemOperationException;
import inventory.manage.model.CommonFunctions;
import inventory.manage.model.ComputerRAM;
import inventory.manage.model.OperationType;
import inventory.manage.view.details.window.ComputerRAMDetailsFrame;

public class ComputerRAMServiceDAO {

	private ManagerDAO managerDAO;
	private Controller controller;

	public ComputerRAMServiceDAO(Controller controller, ManagerDAO managerDAO) {
		setController(controller);
		setManagerDAO(managerDAO);
		getManagerDAO().setComputerRAMServiceDAO(this);
	}

	public void saveData(ComputerRAM computerRAM, OperationType operationType) {
		
		if(operationType == OperationType.INSERT) {
			insertComputerRAM(computerRAM);
		} else if (operationType == OperationType.UPDATE) {
			updateComputerRAM(computerRAM);
		} else if (operationType == OperationType.DELETE) {
			deleteComputerRAM(computerRAM);
		} else {
			new SystemOperationException("Brak określonego typu operacji");
			return;
		}
	}
	
	public void deleteComputerRAMWithPrompt(int computerRAMID) {	
		if(!CommonFunctions.validateID(computerRAMID)) return;
		ComputerRAM computerRAM = getManagerDAO().getComputerRAMDAO().get(computerRAMID);
		
		int result = JOptionPane.showOptionDialog(controller.getMainFrame(),
			    "Czy napewno chcesz usunąć podzespół z bazy?",
			    "Potwierdzenie usunięcia",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    new Object[] {"Tak","Nie"},
			    "Tak");
	
		 if(result == JOptionPane.OK_OPTION) {
			deleteComputerRAM(computerRAM);
		}
		
	}
	
	public void openComputerRAMWindowToAddNew() {
		ComputerRAMDetailsFrame computerRAMDetailsWindow = new ComputerRAMDetailsFrame(controller);
		computerRAMDetailsWindow.setOperationType(OperationType.INSERT);
		computerRAMDetailsWindow.createWindow();
		computerRAMDetailsWindow.showWindow();
	}
	
	public void openComputerRAMWindowToOnlyShowDetails(int deviceID) {
		if(!CommonFunctions.validateID(deviceID)) return;
		ComputerRAM computerRAM = getManagerDAO().getComputerRAMDAO().get(deviceID);
		ComputerRAMDetailsFrame computerRAMDetailsWindow = new ComputerRAMDetailsFrame(controller);
		computerRAMDetailsWindow.setOperationType(OperationType.DISPLAY);
		computerRAMDetailsWindow.createWindow();
		computerRAMDetailsWindow.addComputerRAMDataToView(computerRAM);
		computerRAMDetailsWindow.showWindow();
	}
	
	public void openComputerRAMWindowToUpdate(int deviceID) {	
		if(!CommonFunctions.validateID(deviceID)) return;
		ComputerRAM computerRAM = getManagerDAO().getComputerRAMDAO().get(deviceID);
		ComputerRAMDetailsFrame computerRAMDetailsWindow = new ComputerRAMDetailsFrame(controller);
		computerRAMDetailsWindow.setOperationType(OperationType.UPDATE);
		computerRAMDetailsWindow.createWindow();
		computerRAMDetailsWindow.addComputerRAMDataToView(computerRAM);
		computerRAMDetailsWindow.showWindow();
	}

	public void insertComputerRAM(ComputerRAM computerRAM) {
		getManagerDAO().getComputerRAMDAO().insert(computerRAM);
		getController().getComputerRAMDictionaryTable().updateTable(getManagerDAO().getComputerRAMDAO().getAll());
	}

	public void updateComputerRAM(ComputerRAM computerRAM) {
		getManagerDAO().getComputerRAMDAO().update(computerRAM);
		getController().getComputerRAMDictionaryTable().updateTable(getManagerDAO().getComputerRAMDAO().getAll());
	}
	
	public void deleteComputerRAM(ComputerRAM computerRAM) {
		getManagerDAO().getComputerRAMDAO().delete(computerRAM);
		getController().getComputerRAMDictionaryTable().updateTable(getManagerDAO().getComputerRAMDAO().getAll());
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
