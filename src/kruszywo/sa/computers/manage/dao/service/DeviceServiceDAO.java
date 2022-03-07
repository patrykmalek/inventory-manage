package kruszywo.sa.computers.manage.dao.service;

import javax.swing.JOptionPane;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.dao.ManagerDAO;
import kruszywo.sa.computers.manage.exception.SystemOperationException;
import kruszywo.sa.computers.manage.model.CommonFunctions;
import kruszywo.sa.computers.manage.model.Department;
import kruszywo.sa.computers.manage.model.Device;
import kruszywo.sa.computers.manage.model.DeviceType;
import kruszywo.sa.computers.manage.model.Employee;
import kruszywo.sa.computers.manage.model.OperationType;
import kruszywo.sa.computers.manage.view.details.window.DeviceDetailsFrame;
import kruszywo.sa.computers.manage.view.dictionary.table.panel.DepartmentDictionaryTablePanel;
import kruszywo.sa.computers.manage.view.dictionary.table.panel.DeviceTypeDictionaryTablePanel;
import kruszywo.sa.computers.manage.view.dictionary.table.panel.DictionaryFrame;
import kruszywo.sa.computers.manage.view.dictionary.table.panel.EmployeeDictionaryTablePanel;

public class DeviceServiceDAO {

	
	private ManagerDAO managerDAO;
	private Controller controller;

	public DeviceServiceDAO(Controller controller, ManagerDAO managerDAO) {
		setController(controller);
		setManagerDAO(managerDAO);
		getManagerDAO().setDeviceServiceDAO(this);
	}

	public void saveData(Device device, OperationType operationType) {
		
		if(operationType == OperationType.INSERT) {
			insertDevice(device);
		} else if (operationType == OperationType.UPDATE) {
			updateDevice(device);
		} else if (operationType == OperationType.DELETE) {
			deleteDevice(device);
		} else {
			new SystemOperationException("Brak określonego typu operacji");
		}
	}
	
	public void deleteDeviceWithPrompt(int deviceID) {	
		if(!CommonFunctions.validateID(deviceID)) return;
		Device device = getManagerDAO().getDeviceDAO().get(deviceID);
		
		int result = JOptionPane.showOptionDialog(controller.getMainFrame(),
			    "Czy napewno chcesz usunąć urządzenie z bazy?",
			    "Potwierdzenie usunięcia",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    new Object[] {"Tak","Nie"},
			    "Tak");
	
		 if(result == JOptionPane.OK_OPTION) {
			deleteDevice(device);
		}
		
	}
	
	public void openDeviceWindowToAddNew() {
		DeviceDetailsFrame deviceDetailsWindow = new DeviceDetailsFrame(controller);
		deviceDetailsWindow.setOperationType(OperationType.INSERT);
		deviceDetailsWindow.createWindow();
		deviceDetailsWindow.showWindow();
	}
	
	public void openDeviceWindowToOnlyShowDetails(int deviceID) {
		if(!CommonFunctions.validateID(deviceID)) return;
		Device device = getManagerDAO().getDeviceDAO().get(deviceID);
		DeviceDetailsFrame deviceDetailsWindow = new DeviceDetailsFrame(controller);
		deviceDetailsWindow.setOperationType(OperationType.DISPLAY);
		deviceDetailsWindow.createWindow();
		deviceDetailsWindow.addDeviceDataToView(device);
		deviceDetailsWindow.showWindow();
	}
	
	public void openDeviceWindowToUpdate(int deviceID) {	
		if(!CommonFunctions.validateID(deviceID)) return;
		Device device = getManagerDAO().getDeviceDAO().get(deviceID);
		DeviceDetailsFrame deviceDetailsWindow = new DeviceDetailsFrame(controller);
		deviceDetailsWindow.setOperationType(OperationType.UPDATE);
		deviceDetailsWindow.createWindow();
		deviceDetailsWindow.addDeviceDataToView(device);
		deviceDetailsWindow.showWindow();
	}
	
	public void openDeviceTypeDictionaryWindowAndAddItem() {	
		DeviceTypeDictionaryTablePanel deviceTypeDictionaryTablePanel = new DeviceTypeDictionaryTablePanel(new Controller(getController().getDatabaseProvider()));
		deviceTypeDictionaryTablePanel.updateTable(getManagerDAO().getDeviceTypeDAO().getAll());
		DictionaryFrame<DeviceType> dictionaryFrame = new DictionaryFrame<>(getController(), deviceTypeDictionaryTablePanel);
		dictionaryFrame.showWindow();
		
		int choosenID = deviceTypeDictionaryTablePanel.getChoosenID();
		if(choosenID < 0) return;
		DeviceType deviceType = getManagerDAO().getDeviceTypeDAO().get(choosenID);
		getController().getDeviceDetailsFrame().getDeviceTypeField().addItem(deviceType);
		getController().getDeviceDetailsFrame().setComputer(deviceType.getDeviceTypeID() == 1001);
		if(getController().getDeviceDetailsFrame().isComputer()) {
			getController().getDeviceDetailsFrame().showComputerNameField();
		} else {
			getController().getDeviceDetailsFrame().removeComputerNameField();
		}
		deviceTypeDictionaryTablePanel.resetChoosenID();
	}
	
	public void openDepartmentDictionaryWindowAndAddItem() {	
		DepartmentDictionaryTablePanel departmentDictionaryTablePanel = new DepartmentDictionaryTablePanel(new Controller(getController().getDatabaseProvider()));
		departmentDictionaryTablePanel.updateTable(getManagerDAO().getDepartmentDAO().getAll());
		DictionaryFrame<Department> dictionaryFrame = new DictionaryFrame<>(getController(), departmentDictionaryTablePanel);
		dictionaryFrame.showWindow();
		
		int choosenID = departmentDictionaryTablePanel.getChoosenID();
		if(choosenID < 0) return;
		Department department = getManagerDAO().getDepartmentDAO().get(choosenID);
		getController().getDeviceDetailsFrame().getDeviceAssignedDepartmentField().addItem(department);
		departmentDictionaryTablePanel.resetChoosenID();
	}
	
	public void openEmployeeDictionaryWindowAndAddItem() {	
		EmployeeDictionaryTablePanel employeeDictionaryTablePanel = new EmployeeDictionaryTablePanel(new Controller(getController().getDatabaseProvider()));
		employeeDictionaryTablePanel.updateTable(getManagerDAO().getEmployeeDAO().getAll());
		DictionaryFrame<Employee> dictionaryFrame = new DictionaryFrame<>(getController(), employeeDictionaryTablePanel);
		dictionaryFrame.showWindow();
		
		int choosenID = employeeDictionaryTablePanel.getChoosenID();
		if(choosenID < 0) return;
		Employee employee = getManagerDAO().getEmployeeDAO().get(choosenID);
		getController().getDeviceDetailsFrame().getDeviceAssignedEmployeeField().addItem(employee);
		employeeDictionaryTablePanel.resetChoosenID();
	}
	
	public void findDeviceTypeByTextAndAddItemToDeviceDetailsPanel() {
		String textToFind = getController().getDeviceDetailsFrame().getDeviceTypeField().getCustomTextField().getText();
		DeviceType deviceType = getController().getManagerDAO().getDeviceTypeDAO().get(textToFind);
		getController().getDeviceDetailsFrame().getDeviceTypeField().addItem(deviceType);
	}
	
	public void findDepartmentByTextAndAddItemToDeviceDetailsPanel() {
		String textToFind = getController().getDeviceDetailsFrame().getDeviceAssignedDepartmentField().getCustomTextField().getText();
		Department department = getController().getManagerDAO().getDepartmentDAO().get(textToFind);
		getController().getDeviceDetailsFrame().getDeviceAssignedDepartmentField().addItem(department);
	}
	
	public void findEmployeeByTextAndAddItemToDeviceDetailsPanel() {
		String textToFind = getController().getDeviceDetailsFrame().getDeviceAssignedEmployeeField().getCustomTextField().getText();
		Employee employee = getController().getManagerDAO().getEmployeeDAO().get(textToFind);
		getController().getDeviceDetailsFrame().getDeviceAssignedEmployeeField().addItem(employee);
	}

	public void insertDevice(Device device) {
		getManagerDAO().getDeviceDAO().insert(device);
		getController().getDeviceTablePanel().updateTable(getManagerDAO().getDeviceDAO().getAll());
	}

	public void updateDevice(Device device) {
		getManagerDAO().getDeviceDAO().update(device);
		getController().getDeviceTablePanel().updateTable(getManagerDAO().getDeviceDAO().getAll());
	}
	
	public void deleteDevice(Device device) {
		getManagerDAO().getDeviceDAO().delete(device);
		getController().getDeviceTablePanel().updateTable(getManagerDAO().getDeviceDAO().getAll());
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
