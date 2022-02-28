package kruszywo.sa.computers.manage.controller;

import java.util.ArrayList;
import java.util.List;

import kruszywo.sa.computers.manage.dao.DeviceDAO;
import kruszywo.sa.computers.manage.provider.DatabaseProvider;
import kruszywo.sa.computers.manage.view.TabbedPanel;
import kruszywo.sa.computers.manage.view.DepartmentDictionaryTablePanel;
import kruszywo.sa.computers.manage.view.DeviceTablePanel;
import kruszywo.sa.computers.manage.view.DeviceTypeDictionaryTablePanel;
import kruszywo.sa.computers.manage.view.MainFrame;
import kruszywo.sa.computers.manage.view.WaitWindow;

public class Controller {

	private DatabaseProvider databaseProvider;
	
	private MainFrame mainFrame;
	private TabbedPanel tabbedPanel;
	private DeviceTablePanel deviceTablePanel;
	private DeviceTypeDictionaryTablePanel deviceTypeDictionaryTable;
	private DepartmentDictionaryTablePanel departmentDictionaryTable;
	
	public static WaitWindow waitWindow;
	
	private DeviceDAO deviceDAO;
	
	private static List<String> errors = new ArrayList<String>();
	
	
//	public void doSomething() {
//		WaitWindow waitWindow = new WaitWindow(this);
//		Thread th = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				waitWindow.done();
//			}
//		});
//		th.start();
//		waitWindow.showWindow();
//	}
	
	public Controller(DatabaseProvider databaseProvider) {
		this.setDatabaseProvider(databaseProvider);
	}

	public DatabaseProvider getDatabaseProvider() {
		return databaseProvider;
	}

	public void setDatabaseProvider(DatabaseProvider databaseProvider) {
		this.databaseProvider = databaseProvider;
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public static List<String> getErrors() {
		return errors;
	}
	
	public static void setErrors(List<String> errors) {
		Controller.errors = errors;
	}

	public TabbedPanel getTabbedPanel() {
		return tabbedPanel;
	}

	public void setTabbedPanel(TabbedPanel tabbedPanel) {
		this.tabbedPanel = tabbedPanel;
	}

	public DeviceTablePanel getDeviceTablePanel() {
		return deviceTablePanel;
	}

	public void setDeviceTablePanel(DeviceTablePanel deviceTablePanel) {
		this.deviceTablePanel = deviceTablePanel;
	}

	public DeviceTypeDictionaryTablePanel getDeviceTypeDictionaryTable() {
		return deviceTypeDictionaryTable;
	}

	public void setDeviceTypeDictionaryTable(DeviceTypeDictionaryTablePanel deviceTypeDictionaryTable) {
		this.deviceTypeDictionaryTable = deviceTypeDictionaryTable;
	}

	public DepartmentDictionaryTablePanel getDepartmentDictionaryTable() {
		return departmentDictionaryTable;
	}

	public void setDepartmentDictionaryTable(DepartmentDictionaryTablePanel departmentDictionaryTable) {
		this.departmentDictionaryTable = departmentDictionaryTable;
	}
	
	public void addDevicesPanel() {
		this.getTabbedPanel().addTabbedPanel(TabbedPanel.DEVICES_PANEL, new DeviceTablePanel(this));
	}
	
	public void addDevicesTypeDictionaryPanel() {
		this.getTabbedPanel().addTabbedPanel(TabbedPanel.DEVICES_TYPE_DICTIONARY_PANEL, new DeviceTypeDictionaryTablePanel(this));
	}
	
	public void addDepartmentDictionaryPanel() {
		this.getTabbedPanel().addTabbedPanel(TabbedPanel.DEPARTMENT_DICTIONARY_PANEL, new DepartmentDictionaryTablePanel(this));
	}

	public DeviceDAO getDeviceDAO() {
		return deviceDAO;
	}

	public void setDeviceDAO(DeviceDAO deviceDAO) {
		this.deviceDAO = deviceDAO;
	}

}
