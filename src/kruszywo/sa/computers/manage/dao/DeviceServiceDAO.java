package kruszywo.sa.computers.manage.dao;

import javax.swing.JOptionPane;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.exception.SystemOperationException;
import kruszywo.sa.computers.manage.model.CommonFunctions;
import kruszywo.sa.computers.manage.model.Device;
import kruszywo.sa.computers.manage.model.OperationType;
import kruszywo.sa.computers.manage.view.device.DeviceDetailsFrame;

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
