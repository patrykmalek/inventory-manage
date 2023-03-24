package inventory.manage.dao.service;

import javax.swing.JOptionPane;

import inventory.manage.controller.Controller;
import inventory.manage.dao.ManagerDAO;
import inventory.manage.exception.SystemOperationException;
import inventory.manage.model.CommonFunctions;
import inventory.manage.model.Employee;
import inventory.manage.model.OperationType;
import inventory.manage.view.details.window.EmployeeDetailsFrame;

public class EmployeeServiceDAO {

	private ManagerDAO managerDAO;
	private Controller controller;

	public EmployeeServiceDAO(Controller controller, ManagerDAO managerDAO) {
		setController(controller);
		setManagerDAO(managerDAO);
		getManagerDAO().setEmployeeServiceDAO(this);
	}

	public void saveData(Employee employee, OperationType operationType) {
		
		if(operationType == OperationType.INSERT) {
			insertEmployee(employee);
		} else if (operationType == OperationType.UPDATE) {
			updateEmployee(employee);
		} else if (operationType == OperationType.DELETE) {
			deleteEmployee(employee);
		} else {
			new SystemOperationException("Brak określonego typu operacji");
			return;
		}
	}
	
	public void deleteEmployeeWithPrompt(int employeeID) {	
		if(!CommonFunctions.validateID(employeeID)) return;
		Employee employee = getManagerDAO().getEmployeeDAO().get(employeeID);
		
		int result = JOptionPane.showOptionDialog(controller.getMainFrame(),
			    "Czy napewno chcesz usunąć pracownika z bazy?",
			    "Potwierdzenie usunięcia",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    new Object[] {"Tak","Nie"},
			    "Tak");
	
		 if(result == JOptionPane.OK_OPTION) {
			deleteEmployee(employee);
		}
		
	}
	
	public void openEmployeeWindowToAddNew() {
		EmployeeDetailsFrame employeeDetailsWindow = new EmployeeDetailsFrame(controller);
		employeeDetailsWindow.setOperationType(OperationType.INSERT);
		employeeDetailsWindow.createWindow();
		employeeDetailsWindow.showWindow();
	}
	
	public void openEmployeeWindowToOnlyShowDetails(int employeeID) {
		if(!CommonFunctions.validateID(employeeID)) return;
		Employee Employee = getManagerDAO().getEmployeeDAO().get(employeeID);
		EmployeeDetailsFrame employeeDetailsWindow = new EmployeeDetailsFrame(controller);
		employeeDetailsWindow.setOperationType(OperationType.DISPLAY);
		employeeDetailsWindow.createWindow();
		employeeDetailsWindow.addEmployeeDataToView(Employee);
		employeeDetailsWindow.showWindow();
	}
	
	public void openEmployeeWindowToUpdate(int employeeID) {	
		if(!CommonFunctions.validateID(employeeID)) return;
		Employee Employee = getManagerDAO().getEmployeeDAO().get(employeeID);
		EmployeeDetailsFrame employeeDetailsWindow = new EmployeeDetailsFrame(controller);
		employeeDetailsWindow.setOperationType(OperationType.UPDATE);
		employeeDetailsWindow.createWindow();
		employeeDetailsWindow.addEmployeeDataToView(Employee);
		employeeDetailsWindow.showWindow();
	}

	public void insertEmployee(Employee Employee) {
		getManagerDAO().getEmployeeDAO().insert(Employee);
		getController().getEmployeeDictionaryTable().updateTable(getManagerDAO().getEmployeeDAO().getAll());
	}

	public void updateEmployee(Employee Employee) {
		getManagerDAO().getEmployeeDAO().update(Employee);
		getController().getEmployeeDictionaryTable().updateTable(getManagerDAO().getEmployeeDAO().getAll());
	}
	
	public void deleteEmployee(Employee Employee) {
		getManagerDAO().getEmployeeDAO().delete(Employee);
		getController().getEmployeeDictionaryTable().updateTable(getManagerDAO().getEmployeeDAO().getAll());
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
