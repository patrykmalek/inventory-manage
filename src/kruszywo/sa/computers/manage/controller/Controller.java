package kruszywo.sa.computers.manage.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import kruszywo.sa.computers.manage.dao.ManagerDAO;
import kruszywo.sa.computers.manage.exception.SystemOperationException;
import kruszywo.sa.computers.manage.model.CommonFunctions;
import kruszywo.sa.computers.manage.provider.DatabaseProvider;
import kruszywo.sa.computers.manage.view.TabbedPanel;
import kruszywo.sa.computers.manage.view.MainFrame;
import kruszywo.sa.computers.manage.view.WaitWindow;
import kruszywo.sa.computers.manage.view.details.window.DepartmentDetailsFrame;
import kruszywo.sa.computers.manage.view.details.window.DeviceDetailsFrame;
import kruszywo.sa.computers.manage.view.details.window.DeviceTypeDetailsFrame;
import kruszywo.sa.computers.manage.view.details.window.EmployeeDetailsFrame;
import kruszywo.sa.computers.manage.view.details.window.LicenseDetailsFrame;
import kruszywo.sa.computers.manage.view.dictionary.table.panel.DepartmentDictionaryTablePanel;
import kruszywo.sa.computers.manage.view.dictionary.table.panel.DeviceTablePanel;
import kruszywo.sa.computers.manage.view.dictionary.table.panel.DeviceTypeDictionaryTablePanel;
import kruszywo.sa.computers.manage.view.dictionary.table.panel.EmployeeDictionaryTablePanel;
import kruszywo.sa.computers.manage.view.dictionary.table.panel.LicenseDictionaryTablePanel;
import kruszywo.sa.computers.manage.view.dictionary.table.panel.SoftwareDictionaryTablePanel;

public class Controller {

	private DatabaseProvider databaseProvider;
	
	private MainFrame mainFrame;
	private TabbedPanel tabbedPanel;
	private DeviceTablePanel deviceTablePanel;
	private DeviceTypeDictionaryTablePanel deviceTypeDictionaryTable;
	private DepartmentDictionaryTablePanel departmentDictionaryTable;
	private EmployeeDictionaryTablePanel employeeDictionaryTable;
	private	LicenseDictionaryTablePanel licenseDictionaryTablePanel;
	private SoftwareDictionaryTablePanel softwareDictionaryTablePanel;
	
	private DeviceDetailsFrame deviceDetailsFrame;
	private DeviceTypeDetailsFrame deviceTypeDetailsFrame;
	private DepartmentDetailsFrame departmentDetailsFrame;
	private EmployeeDetailsFrame employeeDetailsFrame;
	private LicenseDetailsFrame licenseDetailsFrame;
	
	public static WaitWindow waitWindow;
	
	private ManagerDAO managerDAO;
	
	private static List<String> errors = new ArrayList<String>();
	
	private SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private Calendar calendar;
	
	public Controller(DatabaseProvider databaseProvider) {
		setDatabaseProvider(databaseProvider);
		setCalendar(Calendar.getInstance());
		setManagerDAO(new ManagerDAO(this));
	}
	
	public void addDevicesPanel() {
		this.getTabbedPanel().addTabbedPanel(TabbedPanel.DEVICES_PANEL, new DeviceTablePanel(this));
		this.getDeviceTablePanel().updateTable(getManagerDAO().getDeviceDAO().getAll());
	}
	
	public void addDevicesTypeDictionaryPanel() {
		this.getTabbedPanel().addTabbedPanel(TabbedPanel.DEVICES_TYPE_DICTIONARY_PANEL, new DeviceTypeDictionaryTablePanel(this));	
		this.getDeviceTypeDictionaryTable().updateTable(getManagerDAO().getDeviceTypeDAO().getAll());
	}
	
	public void addDepartmentDictionaryPanel() {
		this.getTabbedPanel().addTabbedPanel(TabbedPanel.DEPARTMENT_DICTIONARY_PANEL, new DepartmentDictionaryTablePanel(this));
		this.getDepartmentDictionaryTable().updateTable(getManagerDAO().getDepartmentDAO().getAll());
	}
	
	public void addEmployeeDictionaryPanel() {
		this.getTabbedPanel().addTabbedPanel(TabbedPanel.EMPLOYEE_DICTIONARY_PANEL, new EmployeeDictionaryTablePanel(this));
		this.getEmployeeDictionaryTable().updateTable(getManagerDAO().getEmployeeDAO().getAll());
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

	public EmployeeDictionaryTablePanel getEmployeeDictionaryTable() {
		return employeeDictionaryTable;
	}

	public void setEmployeeDictionaryTable(EmployeeDictionaryTablePanel employeeDictionaryTable) {
		this.employeeDictionaryTable = employeeDictionaryTable;
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
	
	public Calendar getCalendarWithCustomDate(String date, SimpleDateFormat simpleDateFormat, boolean showMessageIfDateIsNull) {
		if(date == null || date.equals("")) {
			if(showMessageIfDateIsNull) new SystemOperationException("Nie można zwrócić prawidłowo kalendarza ponieważ data przyjmuje wartość null. Kalendarz przyjmie wartość null");
			Calendar calendar = null;
			return calendar;
		}
		calendar.setTime(CommonFunctions.parseDate(date, simpleDateFormat));
		return calendar;
	}
	
	public Calendar getCalendar() {
		return calendar;
	}
	
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public ManagerDAO getManagerDAO() {
		return managerDAO;
	}

	public void setManagerDAO(ManagerDAO managerDAO) {
		this.managerDAO = managerDAO;
	}

	public DeviceTypeDetailsFrame getDeviceTypeDetailsFrame() {
		return deviceTypeDetailsFrame;
	}

	public void setDeviceTypeDetailsFrame(DeviceTypeDetailsFrame deviceTypeDetailsFrame) {
		this.deviceTypeDetailsFrame = deviceTypeDetailsFrame;
	}

	public DepartmentDetailsFrame getDepartmentDetailsFrame() {
		return departmentDetailsFrame;
	}

	public void setDepartmentDetailsFrame(DepartmentDetailsFrame departmentDetailsFrame) {
		this.departmentDetailsFrame = departmentDetailsFrame;
	}

	public EmployeeDetailsFrame getEmployeeDetailsFrame() {
		return employeeDetailsFrame;
	}

	public void setEmployeeDetailsFrame(EmployeeDetailsFrame employeeDetailsFrame) {
		this.employeeDetailsFrame = employeeDetailsFrame;
	}

	public LicenseDetailsFrame getLicenseDetailsFrame() {
		return licenseDetailsFrame;
	}

	public void setLicenseDetailsFrame(LicenseDetailsFrame licenseDetailsFrame) {
		this.licenseDetailsFrame = licenseDetailsFrame;
	}

	public LicenseDictionaryTablePanel getLicenseDictionaryTablePanel() {
		return licenseDictionaryTablePanel;
	}

	public void setLicenseDictionaryTablePanel(LicenseDictionaryTablePanel licenseDictionaryTablePanel) {
		this.licenseDictionaryTablePanel = licenseDictionaryTablePanel;
	}

	public SoftwareDictionaryTablePanel getSoftwareDictionaryTablePanel() {
		return softwareDictionaryTablePanel;
	}

	public void setSoftwareDictionaryTablePanel(SoftwareDictionaryTablePanel softwareDictionaryTablePanel) {
		this.softwareDictionaryTablePanel = softwareDictionaryTablePanel;
	}


}
