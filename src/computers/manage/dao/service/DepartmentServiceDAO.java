package computers.manage.dao.service;

import javax.swing.JOptionPane;

import computers.manage.controller.Controller;
import computers.manage.dao.ManagerDAO;
import computers.manage.exception.SystemOperationException;
import computers.manage.model.CommonFunctions;
import computers.manage.model.Department;
import computers.manage.model.OperationType;
import computers.manage.view.details.window.DepartmentDetailsFrame;

public class DepartmentServiceDAO {

	private ManagerDAO managerDAO;
	private Controller controller;

	public DepartmentServiceDAO(Controller controller, ManagerDAO managerDAO) {
		setController(controller);
		setManagerDAO(managerDAO);
		getManagerDAO().setDepartmentServiceDAO(this);
	}

	public void saveData(Department department, OperationType operationType) {
		
		if(operationType == OperationType.INSERT) {
			insertDepartment(department);
		} else if (operationType == OperationType.UPDATE) {
			updateDepartment(department);
		} else if (operationType == OperationType.DELETE) {
			deleteDepartment(department);
		} else {
			new SystemOperationException("Brak określonego typu operacji");
			return;
		}
	}
	
	public void deleteDepartmentWithPrompt(int departmentID) {	
		if(!CommonFunctions.validateID(departmentID)) return;
		Department department = getManagerDAO().getDepartmentDAO().get(departmentID);
		
		int result = JOptionPane.showOptionDialog(controller.getMainFrame(),
			    "Czy napewno chcesz usunąć typ urządzenia z bazy?",
			    "Potwierdzenie usunięcia",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    new Object[] {"Tak","Nie"},
			    "Tak");
	
		 if(result == JOptionPane.OK_OPTION) {
			deleteDepartment(department);
		}
		
	}
	
	public void openDepartmentWindowToAddNew() {
		DepartmentDetailsFrame departmentDetailsWindow = new DepartmentDetailsFrame(controller);
		departmentDetailsWindow.setOperationType(OperationType.INSERT);
		departmentDetailsWindow.createWindow();
		departmentDetailsWindow.showWindow();
	}
	
	public void openDepartmentWindowToOnlyShowDetails(int departmentID) {
		if(!CommonFunctions.validateID(departmentID)) return;
		Department department = getManagerDAO().getDepartmentDAO().get(departmentID);
		DepartmentDetailsFrame departmentDetailsWindow = new DepartmentDetailsFrame(controller);
		departmentDetailsWindow.setOperationType(OperationType.DISPLAY);
		departmentDetailsWindow.createWindow();
		departmentDetailsWindow.addDepartmentDataToView(department);
		departmentDetailsWindow.showWindow();
	}
	
	public void openDepartmentWindowToUpdate(int departmentID) {	
		if(!CommonFunctions.validateID(departmentID)) return;
		Department department = getManagerDAO().getDepartmentDAO().get(departmentID);
		DepartmentDetailsFrame departmentDetailsWindow = new DepartmentDetailsFrame(controller);
		departmentDetailsWindow.setOperationType(OperationType.UPDATE);
		departmentDetailsWindow.createWindow();
		departmentDetailsWindow.addDepartmentDataToView(department);
		departmentDetailsWindow.showWindow();
	}

	public void insertDepartment(Department department) {
		getManagerDAO().getDepartmentDAO().insert(department);
		getController().getDepartmentDictionaryTable().updateTable(getManagerDAO().getDepartmentDAO().getAll());
	}

	public void updateDepartment(Department department) {
		getManagerDAO().getDepartmentDAO().update(department);
		getController().getDepartmentDictionaryTable().updateTable(getManagerDAO().getDepartmentDAO().getAll());
	}
	
	public void deleteDepartment(Department department) {
		getManagerDAO().getDepartmentDAO().delete(department);
		getController().getDepartmentDictionaryTable().updateTable(getManagerDAO().getDepartmentDAO().getAll());
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
