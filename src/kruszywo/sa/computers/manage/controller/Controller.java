package kruszywo.sa.computers.manage.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kruszywo.sa.computers.manage.dao.DeviceDAO;
import kruszywo.sa.computers.manage.exception.SystemOperationException;
import kruszywo.sa.computers.manage.model.CommonFunctions;
import kruszywo.sa.computers.manage.model.Device;
import kruszywo.sa.computers.manage.model.OperationType;
import kruszywo.sa.computers.manage.provider.DatabaseProvider;
import kruszywo.sa.computers.manage.view.TabbedPanel;
import kruszywo.sa.computers.manage.view.DepartmentDictionaryTablePanel;
import kruszywo.sa.computers.manage.view.DeviceTablePanel;
import kruszywo.sa.computers.manage.view.DeviceTypeDictionaryTablePanel;
import kruszywo.sa.computers.manage.view.MainFrame;
import kruszywo.sa.computers.manage.view.WaitWindow;
import kruszywo.sa.computers.manage.view.device.DeviceDetailsFrame;

public class Controller {

	private DatabaseProvider databaseProvider;
	
	private MainFrame mainFrame;
	private TabbedPanel tabbedPanel;
	private DeviceTablePanel deviceTablePanel;
	private DeviceTypeDictionaryTablePanel deviceTypeDictionaryTable;
	private DepartmentDictionaryTablePanel departmentDictionaryTable;
	
	private DeviceDetailsFrame deviceDetailsFrame;
	
	public static WaitWindow waitWindow;
	
	private DeviceDAO deviceDAO;
	
	private static List<String> errors = new ArrayList<String>();
	
	private SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private Calendar calendar;
	
	
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
		this.deviceDAO = new DeviceDAO(this);
		this.calendar = Calendar.getInstance();
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
		this.getDeviceTablePanel().updateTable(getDeviceDAO().getAll());
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

	public SimpleDateFormat getDefaultDateFormat() {
		return defaultDateFormat;
	}

	public void setDefaultDateFormat(SimpleDateFormat defaultDateFormat) {
		this.defaultDateFormat = defaultDateFormat;
	}

	public DeviceDetailsFrame getDeviceDetailsFrame() {
		return deviceDetailsFrame;
	}

	public void setDeviceDetailsFrame(DeviceDetailsFrame deviceDetailsFrame) {
		this.deviceDetailsFrame = deviceDetailsFrame;
	}

	public Calendar getCalendarWithTodayDate() {
		this.calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar;
	}
	
	public Calendar getCalendarWithCustomDate(String date, SimpleDateFormat simpleDateFormat) {
		calendar.setTime(CommonFunctions.parseDate(date, simpleDateFormat));
		return calendar;
	}
	
	public Calendar getCalendar() {
		return calendar;
	}
	
	public void openDeviceWindowToAddNew() {
		DeviceDetailsFrame deviceDetailsWindow = new DeviceDetailsFrame(this);
		deviceDetailsWindow.setEditable(true);
		deviceDetailsWindow.setOperationType(OperationType.INSERT);
		deviceDetailsWindow.showWindow();
	}
	
	public void openDeviceWindowToOnlyShowDetails(int deviceID) {
		Device device = getDeviceDAO().get(deviceID);
		
		DeviceDetailsFrame deviceDetailsWindow = new DeviceDetailsFrame(this);
		deviceDetailsWindow.setEditable(false);
		deviceDetailsWindow.setOperationType(OperationType.DISPLAY);
		deviceDetailsWindow.showWindow();
		deviceDetailsWindow.addDeviceDataToView(device);
	}
	
	public void openDeviceWindowToUpdate(int deviceID) {	
		Device device = getDeviceDAO().get(deviceID);
		DeviceDetailsFrame deviceDetailsWindow = new DeviceDetailsFrame(this);
		deviceDetailsWindow.setEditable(true);
		deviceDetailsWindow.setOperationType(OperationType.UPDATE);
		deviceDetailsWindow.showWindow();
		deviceDetailsWindow.addDeviceDataToView(device);
	}

	public void insertDevice(Device device) {
		getDeviceDAO().insert(device);
	}

	public void updateDevice(Device device) {
		getDeviceDAO().update(device);
	}
	
	public void deleteDevice(Device device) {
		getDeviceDAO().delete(device);
	}

	public void saveDeviceData(Device device, OperationType operationType) {
		
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

}
