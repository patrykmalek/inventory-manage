package computers.manage.dao.service;

import javax.swing.JOptionPane;

import computers.manage.controller.Controller;
import computers.manage.dao.ManagerDAO;
import computers.manage.exception.SystemOperationException;
import computers.manage.model.CommonFunctions;
import computers.manage.model.DeviceType;
import computers.manage.model.OperationType;
import computers.manage.view.details.window.DeviceTypeDetailsFrame;

public class DeviceTypeServiceDAO {

	private ManagerDAO managerDAO;
	private Controller controller;

	public DeviceTypeServiceDAO(Controller controller, ManagerDAO managerDAO) {
		setController(controller);
		setManagerDAO(managerDAO);
		getManagerDAO().setDeviceTypeServiceDAO(this);
	}

	public void saveData(DeviceType deviceType, OperationType operationType) {
		
		if(operationType == OperationType.INSERT) {
			insertDeviceType(deviceType);
		} else if (operationType == OperationType.UPDATE) {
			updateDeviceType(deviceType);
		} else if (operationType == OperationType.DELETE) {
			deleteDeviceType(deviceType);
		} else {
			new SystemOperationException("Brak określonego typu operacji");
			return;
		}
	}
	
	public void deleteDeviceTypeWithPrompt(int deviceTypeID) {	
		if(!CommonFunctions.validateID(deviceTypeID)) return;
		DeviceType deviceType = getManagerDAO().getDeviceTypeDAO().get(deviceTypeID);
		
		int result = JOptionPane.showOptionDialog(controller.getMainFrame(),
			    "Czy napewno chcesz usunąć typ urządzenia z bazy?",
			    "Potwierdzenie usunięcia",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    new Object[] {"Tak","Nie"},
			    "Tak");
	
		 if(result == JOptionPane.OK_OPTION) {
			deleteDeviceType(deviceType);
		}
		
	}
	
	public void openDeviceTypeWindowToAddNew() {
		DeviceTypeDetailsFrame deviceTypeDetailsWindow = new DeviceTypeDetailsFrame(controller);
		deviceTypeDetailsWindow.setOperationType(OperationType.INSERT);
		deviceTypeDetailsWindow.createWindow();
		deviceTypeDetailsWindow.showWindow();
	}
	
	public void openDeviceTypeWindowToOnlyShowDetails(int deviceID) {
		if(!CommonFunctions.validateID(deviceID)) return;
		DeviceType deviceType = getManagerDAO().getDeviceTypeDAO().get(deviceID);
		DeviceTypeDetailsFrame deviceTypeDetailsWindow = new DeviceTypeDetailsFrame(controller);
		deviceTypeDetailsWindow.setOperationType(OperationType.DISPLAY);
		deviceTypeDetailsWindow.createWindow();
		deviceTypeDetailsWindow.addDeviceTypeDataToView(deviceType);
		deviceTypeDetailsWindow.showWindow();
	}
	
	public void openDeviceTypeWindowToUpdate(int deviceID) {	
		if(!CommonFunctions.validateID(deviceID)) return;
		DeviceType deviceType = getManagerDAO().getDeviceTypeDAO().get(deviceID);
		DeviceTypeDetailsFrame deviceTypeDetailsWindow = new DeviceTypeDetailsFrame(controller);
		deviceTypeDetailsWindow.setOperationType(OperationType.UPDATE);
		deviceTypeDetailsWindow.createWindow();
		deviceTypeDetailsWindow.addDeviceTypeDataToView(deviceType);
		deviceTypeDetailsWindow.showWindow();
	}

	public void insertDeviceType(DeviceType deviceType) {
		getManagerDAO().getDeviceTypeDAO().insert(deviceType);
		getController().getDeviceTypeDictionaryTable().updateTable(getManagerDAO().getDeviceTypeDAO().getAll());
	}

	public void updateDeviceType(DeviceType deviceType) {
		getManagerDAO().getDeviceTypeDAO().update(deviceType);
		getController().getDeviceTypeDictionaryTable().updateTable(getManagerDAO().getDeviceTypeDAO().getAll());
	}
	
	public void deleteDeviceType(DeviceType deviceType) {
		getManagerDAO().getDeviceTypeDAO().delete(deviceType);
		getController().getDeviceTypeDictionaryTable().updateTable(getManagerDAO().getDeviceTypeDAO().getAll());
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
