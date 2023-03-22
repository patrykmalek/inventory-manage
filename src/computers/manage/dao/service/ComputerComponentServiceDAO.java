package computers.manage.dao.service;

import javax.swing.JOptionPane;
import javax.swing.RowFilter;

import computers.manage.controller.Controller;
import computers.manage.dao.ManagerDAO;
import computers.manage.exception.SystemOperationException;
import computers.manage.model.CommonFunctions;
import computers.manage.model.ComputerCPU;
import computers.manage.model.ComputerComponent;
import computers.manage.model.ComputerMassStorage;
import computers.manage.model.ComputerRAM;
import computers.manage.model.Device;
import computers.manage.model.OperationType;
import computers.manage.view.details.window.ComputerComponentDetailsFrame;
import computers.manage.view.dictionary.table.panel.ComputerCPUDictionaryTablePanel;
import computers.manage.view.dictionary.table.panel.ComputerMassStorageDictionaryTablePanel;
import computers.manage.view.dictionary.table.panel.ComputerRAMDictionaryTablePanel;
import computers.manage.view.dictionary.table.panel.DeviceTablePanel;
import computers.manage.view.dictionary.table.panel.DictionaryFrame;
import computers.manage.view.util.PMCustomTextFieldWithDictionary;

public class ComputerComponentServiceDAO {

	private ManagerDAO managerDAO;
	private Controller controller;

	public ComputerComponentServiceDAO(Controller controller, ManagerDAO managerDAO) {
		setController(controller);
		setManagerDAO(managerDAO);
		getManagerDAO().setComputerComponentServiceDAO(this);
	}

	public void saveData(ComputerComponent computerComponent, OperationType operationType) {
		
		if(operationType == OperationType.INSERT) {
			insertComputerComponent(computerComponent);
		} else if (operationType == OperationType.UPDATE) {
			updateComputerComponent(computerComponent);
		} else if (operationType == OperationType.DELETE) {
			deleteComputerComponent(computerComponent);
		} else {
			new SystemOperationException("Brak określonego typu operacji");
			return;
		}
	}
	
	public void deleteComputerComponentWithPrompt(int computerComponentID) {	
		if(!CommonFunctions.validateID(computerComponentID)) return;
		ComputerComponent computerComponent = getManagerDAO().getComputerComponentDAO().get(computerComponentID);
		
		int result = JOptionPane.showOptionDialog(controller.getMainFrame(),
			    "Czy napewno chcesz usunąć kompent z bazy?",
			    "Potwierdzenie usunięcia",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    new Object[] {"Tak","Nie"},
			    "Tak");
	
		 if(result == JOptionPane.OK_OPTION) {
			deleteComputerComponent(computerComponent);
		}
		
	}
	
	public void openComputerComponentWindowToAddNew() {
		ComputerComponentDetailsFrame computerComponentDetailsWindow = new ComputerComponentDetailsFrame(controller);
		computerComponentDetailsWindow.setOperationType(OperationType.INSERT);
		computerComponentDetailsWindow.createWindow();
		computerComponentDetailsWindow.showWindow();
	}
	
	public void openComputerComponentWindowToOnlyShowDetails(int deviceID) {
		if(!CommonFunctions.validateID(deviceID)) return;
		ComputerComponent computerComponent = getManagerDAO().getComputerComponentDAO().get(deviceID);
		ComputerComponentDetailsFrame computerComponentDetailsWindow = new ComputerComponentDetailsFrame(controller);
		computerComponentDetailsWindow.setOperationType(OperationType.DISPLAY);
		computerComponentDetailsWindow.createWindow();
		computerComponentDetailsWindow.addComputerComponentDataToView(computerComponent);
		computerComponentDetailsWindow.showWindow();
	}
	
	public void openComputerComponentWindowToUpdate(int deviceID) {	
		if(!CommonFunctions.validateID(deviceID)) return;
		ComputerComponent computerComponent = getManagerDAO().getComputerComponentDAO().get(deviceID);
		ComputerComponentDetailsFrame computerComponentDetailsWindow = new ComputerComponentDetailsFrame(controller);
		computerComponentDetailsWindow.setOperationType(OperationType.UPDATE);
		computerComponentDetailsWindow.createWindow();
		computerComponentDetailsWindow.addComputerComponentDataToView(computerComponent);
		computerComponentDetailsWindow.showWindow();
	}

	public void insertComputerComponent(ComputerComponent computerComponent) {
		getManagerDAO().getComputerComponentDAO().insert(computerComponent);
		if(getController().getComputerComponentDictionaryTable() != null) {
			getController().getComputerComponentDictionaryTable().updateTable(getManagerDAO().getComputerComponentDAO().getAll());
		}
	}

	public void updateComputerComponent(ComputerComponent computerComponent) {
		getManagerDAO().getComputerComponentDAO().update(computerComponent);
		if(getController().getComputerComponentDictionaryTable() != null) {
			getController().getComputerComponentDictionaryTable().updateTable(getManagerDAO().getComputerComponentDAO().getAll());
		}
	}
	
	public void deleteComputerComponent(ComputerComponent computerComponent) {
		getManagerDAO().getComputerComponentDAO().delete(computerComponent);
		if(getController().getComputerComponentDictionaryTable() != null) {
			getController().getComputerComponentDictionaryTable().updateTable(getManagerDAO().getComputerComponentDAO().getAll());
		}
	}
	
	public void openDeviceDictionaryWindowAndAddItem() {	
		DeviceTablePanel deviceTablePanel = new DeviceTablePanel(new Controller(getController().getDatabaseProvider()));
		deviceTablePanel.updateTable(getManagerDAO().getDeviceDAO().getOnlyComputersAll());
		DictionaryFrame<Device> dictionaryFrame = new DictionaryFrame<>(getController(), deviceTablePanel);
		dictionaryFrame.setSize(1200, 600);
		dictionaryFrame.setLocationRelativeTo(null);
		dictionaryFrame.showWindow();
		
		int choosenID = deviceTablePanel.getChoosenID();
		if(choosenID < 0) return;
		Device device = getManagerDAO().getDeviceDAO().get(choosenID);
		getController().getComputerComponentDetailsFrame().getComputerComponentDeviceField().addItem(device);
		deviceTablePanel.resetChoosenID();
	}
	
	public void openCPUDictionaryWindowAndAddItem() {	
		ComputerCPUDictionaryTablePanel computerCPUDictionaryTablePanel = new ComputerCPUDictionaryTablePanel(new Controller(getController().getDatabaseProvider()));
		computerCPUDictionaryTablePanel.updateTable(getManagerDAO().getComputerCPUDAO().getAll());
		computerCPUDictionaryTablePanel.getTable().getTableSorter().setRowFilter(RowFilter.regexFilter("(?i)" + false, 4));
		DictionaryFrame<ComputerCPU> dictionaryFrame = new DictionaryFrame<>(getController(), computerCPUDictionaryTablePanel);
		dictionaryFrame.showWindow();
		
		int choosenID = computerCPUDictionaryTablePanel.getChoosenID();
		if(choosenID < 0) return;
		ComputerCPU computerCPU = getManagerDAO().getComputerCPUDAO().get(choosenID);
		getController().getComputerComponentDetailsFrame().getComputerComponentCPUField().addItem(computerCPU);
		computerCPUDictionaryTablePanel.resetChoosenID();
	}
	
	public void openRAMDictionaryWindowAndAddItem() {	
		ComputerRAMDictionaryTablePanel computerRAMDictionaryTablePanel = new ComputerRAMDictionaryTablePanel(new Controller(getController().getDatabaseProvider()));
		computerRAMDictionaryTablePanel.updateTable(getManagerDAO().getComputerRAMDAO().getAll());
		computerRAMDictionaryTablePanel.getTable().getTableSorter().setRowFilter(RowFilter.regexFilter("(?i)" + false, 4));
		DictionaryFrame<ComputerRAM> dictionaryFrame = new DictionaryFrame<>(getController(), computerRAMDictionaryTablePanel);
		dictionaryFrame.showWindow();
		
		int choosenID = computerRAMDictionaryTablePanel.getChoosenID();
		if(choosenID < 0) return;
		ComputerRAM computerRAM = getManagerDAO().getComputerRAMDAO().get(choosenID);
		getController().getComputerComponentDetailsFrame().getComputerComponentRAMField().addItem(computerRAM);
		computerRAMDictionaryTablePanel.resetChoosenID();
	}
	
	public void openMassStorageDictionaryWindowAndAddItem(PMCustomTextFieldWithDictionary<ComputerMassStorage> field) {
		ComputerMassStorageDictionaryTablePanel computerMassStorageDictionaryTablePanel = new ComputerMassStorageDictionaryTablePanel(new Controller(getController().getDatabaseProvider()));
		computerMassStorageDictionaryTablePanel.updateTable(getManagerDAO().getComputerMassStorageDAO().getAll());
		computerMassStorageDictionaryTablePanel.getTable().getTableSorter().setRowFilter(RowFilter.regexFilter("(?i)" + false, 5));
		DictionaryFrame<ComputerMassStorage> dictionaryFrame = new DictionaryFrame<>(getController(), computerMassStorageDictionaryTablePanel);
		dictionaryFrame.showWindow();
		
		int choosenID = computerMassStorageDictionaryTablePanel.getChoosenID();
		if(choosenID < 0) return;
		ComputerMassStorage computerMassStorage =  getManagerDAO().getComputerMassStorageDAO().get(choosenID);
		field.addItem(computerMassStorage);
		computerMassStorageDictionaryTablePanel.resetChoosenID();
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
