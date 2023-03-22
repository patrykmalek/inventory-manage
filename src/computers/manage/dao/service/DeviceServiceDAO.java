package computers.manage.dao.service;

import java.util.stream.Stream;

import javax.swing.JOptionPane;

import computers.manage.controller.Controller;
import computers.manage.dao.ManagerDAO;
import computers.manage.exception.SystemOperationException;
import computers.manage.model.CommonFunctions;
import computers.manage.model.ComputerComponent;
import computers.manage.model.Department;
import computers.manage.model.Device;
import computers.manage.model.DeviceType;
import computers.manage.model.Employee;
import computers.manage.model.OperationType;
import computers.manage.view.details.window.ComputerComponentDetailsFrame;
import computers.manage.view.details.window.DeviceDetailsFrame;
import computers.manage.view.dictionary.table.panel.ComputerLicenseAssignedTablePanel;
import computers.manage.view.dictionary.table.panel.DepartmentDictionaryTablePanel;
import computers.manage.view.dictionary.table.panel.DeviceConnectedDocumentTablePanel;
import computers.manage.view.dictionary.table.panel.DeviceTypeDictionaryTablePanel;
import computers.manage.view.dictionary.table.panel.DictionaryFrame;
import computers.manage.view.dictionary.table.panel.EmployeeDictionaryTablePanel;

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
		
		ComputerLicenseAssignedTablePanel computerLicenseAssignedTablePanel = new ComputerLicenseAssignedTablePanel(getController());
		computerLicenseAssignedTablePanel.setOperationType(OperationType.INSERT);
		computerLicenseAssignedTablePanel.createPanel();
		deviceDetailsWindow.setComputerLicenseAssignedTablePanel(computerLicenseAssignedTablePanel);
		ComputerComponentDetailsFrame computerComponentDetailsFrame = new ComputerComponentDetailsFrame(getController());
		computerComponentDetailsFrame.setOperationType(OperationType.INSERT);
		computerComponentDetailsFrame.createPanels();
		computerComponentDetailsFrame.createEventListeners();
		computerComponentDetailsFrame.setAddedFromDevice(false);
		deviceDetailsWindow.setComputerComponentDetailsFrame(computerComponentDetailsFrame);
		deviceDetailsWindow.setComputerComponentDetailsPanel(computerComponentDetailsFrame.getDetailsPanel());
		
		deviceDetailsWindow.createWindow();
		deviceDetailsWindow.showWindow();
	}
	
	public void openDeviceWindowToOnlyShowDetails(int deviceID) {
		if(!CommonFunctions.validateID(deviceID)) return;
		Device device = getManagerDAO().getDeviceDAO().get(deviceID);
		DeviceDetailsFrame deviceDetailsWindow = new DeviceDetailsFrame(controller);
		deviceDetailsWindow.setOperationType(OperationType.DISPLAY);
		
		
		DeviceConnectedDocumentTablePanel deviceConnectedDocumentTablePanel = new DeviceConnectedDocumentTablePanel(getController());
		deviceConnectedDocumentTablePanel.setOperationType(OperationType.DISPLAY);
		deviceConnectedDocumentTablePanel.createPanel();
		deviceConnectedDocumentTablePanel.setDevice(device);
		deviceConnectedDocumentTablePanel.updateTable(getManagerDAO().getDocumentDAO().getAllByDevice(deviceID));
		deviceDetailsWindow.setDeviceConnectedDocumentTablePanel(deviceConnectedDocumentTablePanel);
		
		ComputerLicenseAssignedTablePanel computerLicenseAssignedTablePanel = new ComputerLicenseAssignedTablePanel(getController());
		computerLicenseAssignedTablePanel.setOperationType(OperationType.DISPLAY);
		computerLicenseAssignedTablePanel.createPanel();
		computerLicenseAssignedTablePanel.setDevice(device);
		computerLicenseAssignedTablePanel.updateTable(getManagerDAO().getLicenseDAO().getAllByDeviceID(deviceID));
		deviceDetailsWindow.setComputerLicenseAssignedTablePanel(computerLicenseAssignedTablePanel);
		
		ComputerComponent computerComponent = getManagerDAO().getComputerComponentDAO().getByComputerID(deviceID);
		ComputerComponentDetailsFrame computerComponentDetailsFrame = new ComputerComponentDetailsFrame(getController());
		computerComponentDetailsFrame.setOperationType(OperationType.DISPLAY);
		computerComponentDetailsFrame.createPanels();
		computerComponentDetailsFrame.createEventListeners();
		computerComponentDetailsFrame.setAddedFromDevice(false);
		deviceDetailsWindow.setComputerComponentDetailsFrame(computerComponentDetailsFrame);
		deviceDetailsWindow.setComputerComponentDetailsPanel(computerComponentDetailsFrame.getDetailsPanel());
		computerComponentDetailsFrame.addComputerComponentDataToView(computerComponent);
		
		deviceDetailsWindow.createWindow();
		deviceDetailsWindow.addDeviceDataToView(device);
		deviceDetailsWindow.showWindow();
	}
	
	public void openDeviceWindowToUpdate(int deviceID) {	
		if(!CommonFunctions.validateID(deviceID)) return;
		Device device = getManagerDAO().getDeviceDAO().get(deviceID);
		DeviceDetailsFrame deviceDetailsWindow = new DeviceDetailsFrame(controller);
		deviceDetailsWindow.setOperationType(OperationType.UPDATE);
		
		DeviceConnectedDocumentTablePanel deviceConnectedDocumentTablePanel = new DeviceConnectedDocumentTablePanel(getController());
		deviceConnectedDocumentTablePanel.setOperationType(OperationType.UPDATE);
		deviceConnectedDocumentTablePanel.createPanel();
		deviceConnectedDocumentTablePanel.setDevice(device);
		deviceConnectedDocumentTablePanel.updateTable(getManagerDAO().getDocumentDAO().getAllByDevice(deviceID));
		deviceDetailsWindow.setDeviceConnectedDocumentTablePanel(deviceConnectedDocumentTablePanel);
		
		ComputerLicenseAssignedTablePanel computerLicenseAssignedTablePanel = new ComputerLicenseAssignedTablePanel(getController());
		ComputerComponent computerComponent = getManagerDAO().getComputerComponentDAO().getByComputerID(deviceID);
		ComputerComponentDetailsFrame computerComponentDetailsFrame = new ComputerComponentDetailsFrame(getController());
		computerLicenseAssignedTablePanel.setOperationType((computerComponent != null) ? OperationType.UPDATE : OperationType.INSERT);
		computerComponentDetailsFrame.setOperationType((computerComponent != null) ? OperationType.UPDATE : OperationType.INSERT);
		computerLicenseAssignedTablePanel.createPanel();
		computerLicenseAssignedTablePanel.setDevice(device);
		computerLicenseAssignedTablePanel.updateTable(getManagerDAO().getLicenseDAO().getAllByDeviceID(deviceID));
		deviceDetailsWindow.setComputerLicenseAssignedTablePanel(computerLicenseAssignedTablePanel);
		
		computerComponentDetailsFrame.createPanels();
		computerComponentDetailsFrame.createEventListeners();
		computerComponentDetailsFrame.setAddedFromDevice(false);
		deviceDetailsWindow.setComputerComponentDetailsFrame(computerComponentDetailsFrame);
		deviceDetailsWindow.setComputerComponentDetailsPanel(computerComponentDetailsFrame.getDetailsPanel());
		computerComponentDetailsFrame.addComputerComponentDataToView(computerComponent);
		
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
		getController().getDeviceDetailsFrame().setComputer(Stream.of(getController().getDeviceDetailsFrame().getNamesToShowComputerNameField())
																		.anyMatch(x -> x.equals(deviceType.getDeviceTypeName().toLowerCase())));
		if(getController().getDeviceDetailsFrame().isComputer()) {
			getController().getDeviceDetailsFrame().removeComputerComponents();
			getController().getDeviceDetailsFrame().showComputerComponents();
		} else {
			getController().getDeviceDetailsFrame().removeComputerComponents();
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
