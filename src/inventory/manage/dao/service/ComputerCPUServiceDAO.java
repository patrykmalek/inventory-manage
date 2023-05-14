package inventory.manage.dao.service;

import javax.swing.JOptionPane;

import inventory.manage.controller.Controller;
import inventory.manage.dao.ManagerDAO;
import inventory.manage.exception.SystemOperationException;
import inventory.manage.model.CommonFunctions;
import inventory.manage.model.ComputerCPU;
import inventory.manage.model.OperationType;
import inventory.manage.view.details.window.ComputerCPUDetailsFrame;

public class ComputerCPUServiceDAO {

	private ManagerDAO managerDAO;
	private Controller controller;

	public ComputerCPUServiceDAO(Controller controller, ManagerDAO managerDAO) {
		setController(controller);
		setManagerDAO(managerDAO);
		getManagerDAO().setComputerCPUServiceDAO(this);
	}

	public void saveData(ComputerCPU computerCPU, OperationType operationType) {
		
		if(operationType == OperationType.INSERT) {
			insertComputerCPU(computerCPU);
		} else if (operationType == OperationType.UPDATE) {
			updateComputerCPU(computerCPU);
		} else if (operationType == OperationType.DELETE) {
			deleteComputerCPU(computerCPU);
		} else {
			new SystemOperationException("Brak określonego typu operacji");
			return;
		}
	}
	
	public void deleteComputerCPUWithPrompt(int computerCPUID) {	
		if(!CommonFunctions.validateID(computerCPUID)) return;
		ComputerCPU computerCPU = getManagerDAO().getComputerCPUDAO().get(computerCPUID);
		
		int result = JOptionPane.showOptionDialog(controller.getMainFrame(),
			    "Czy napewno chcesz usunąć podzespół z bazy?",
			    "Potwierdzenie usunięcia",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    new Object[] {"Tak","Nie"},
			    "Tak");
	
		 if(result == JOptionPane.OK_OPTION) {
			deleteComputerCPU(computerCPU);
		}
		
	}
	
	public void openComputerCPUWindowToAddNew() {
		ComputerCPUDetailsFrame computerCPUDetailsWindow = new ComputerCPUDetailsFrame(controller);
		computerCPUDetailsWindow.setOperationType(OperationType.INSERT);
		computerCPUDetailsWindow.createWindow();
		computerCPUDetailsWindow.showWindow();
	}
	
	public void openComputerCPUWindowToOnlyShowDetails(int deviceID) {
		if(!CommonFunctions.validateID(deviceID)) return;
		ComputerCPU computerCPU = getManagerDAO().getComputerCPUDAO().get(deviceID);
		ComputerCPUDetailsFrame computerCPUDetailsWindow = new ComputerCPUDetailsFrame(controller);
		computerCPUDetailsWindow.setOperationType(OperationType.DISPLAY);
		computerCPUDetailsWindow.createWindow();
		computerCPUDetailsWindow.addComputerCPUDataToView(computerCPU);
		computerCPUDetailsWindow.showWindow();
	}
	
	public void openComputerCPUWindowToUpdate(int deviceID) {	
		if(!CommonFunctions.validateID(deviceID)) return;
		ComputerCPU computerCPU = getManagerDAO().getComputerCPUDAO().get(deviceID);
		ComputerCPUDetailsFrame computerCPUDetailsWindow = new ComputerCPUDetailsFrame(controller);
		computerCPUDetailsWindow.setOperationType(OperationType.UPDATE);
		computerCPUDetailsWindow.createWindow();
		computerCPUDetailsWindow.addComputerCPUDataToView(computerCPU);
		computerCPUDetailsWindow.showWindow();
	}

	public void insertComputerCPU(ComputerCPU computerCPU) {
		getManagerDAO().getComputerCPUDAO().insert(computerCPU);
		getController().getComputerCPUDictionaryTable().updateTable(getManagerDAO().getComputerCPUDAO().getAll());
	}

	public void updateComputerCPU(ComputerCPU computerCPU) {
		getManagerDAO().getComputerCPUDAO().update(computerCPU);
		getController().getComputerCPUDictionaryTable().updateTable(getManagerDAO().getComputerCPUDAO().getAll());
	}
	
	public void deleteComputerCPU(ComputerCPU computerCPU) {
		getManagerDAO().getComputerCPUDAO().delete(computerCPU);
		getController().getComputerCPUDictionaryTable().updateTable(getManagerDAO().getComputerCPUDAO().getAll());
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
