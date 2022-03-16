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
import kruszywo.sa.computers.manage.view.details.window.ComputerCPUDetailsFrame;
import kruszywo.sa.computers.manage.view.details.window.ComputerComponentDetailsFrame;
import kruszywo.sa.computers.manage.view.details.window.ComputerLicenseAssignedDetailsFrame;
import kruszywo.sa.computers.manage.view.details.window.ComputerMassStorageDetailsFrame;
import kruszywo.sa.computers.manage.view.details.window.ComputerRAMDetailsFrame;
import kruszywo.sa.computers.manage.view.details.window.DepartmentDetailsFrame;
import kruszywo.sa.computers.manage.view.details.window.DeviceDetailsFrame;
import kruszywo.sa.computers.manage.view.details.window.DeviceTypeDetailsFrame;
import kruszywo.sa.computers.manage.view.details.window.EmployeeDetailsFrame;
import kruszywo.sa.computers.manage.view.details.window.LicenseDetailsFrame;
import kruszywo.sa.computers.manage.view.details.window.SoftwareDetailsFrame;
import kruszywo.sa.computers.manage.view.dictionary.table.panel.ComputerCPUDictionaryTablePanel;
import kruszywo.sa.computers.manage.view.dictionary.table.panel.ComputerComponentDictionaryTablePanel;
import kruszywo.sa.computers.manage.view.dictionary.table.panel.ComputerLicenseAssignedTablePanel;
import kruszywo.sa.computers.manage.view.dictionary.table.panel.ComputerMassStorageDictionaryTablePanel;
import kruszywo.sa.computers.manage.view.dictionary.table.panel.ComputerRAMDictionaryTablePanel;
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
	private	LicenseDictionaryTablePanel licenseDictionaryTable;
	private SoftwareDictionaryTablePanel softwareDictionaryTable;
	private ComputerCPUDictionaryTablePanel computerCPUDictionaryTable;
	private ComputerRAMDictionaryTablePanel computerRAMDictionaryTable;
	private ComputerMassStorageDictionaryTablePanel computerMassStorageDictionaryTable;
	private ComputerComponentDictionaryTablePanel computerComponentDictionaryTable;
	private ComputerLicenseAssignedTablePanel computerLicenseAssignedTable;
	
	private DeviceDetailsFrame deviceDetailsFrame;
	private DeviceTypeDetailsFrame deviceTypeDetailsFrame;
	private DepartmentDetailsFrame departmentDetailsFrame;
	private EmployeeDetailsFrame employeeDetailsFrame;
	private LicenseDetailsFrame licenseDetailsFrame;
	private SoftwareDetailsFrame softwareDetailsFrame;
	private ComputerCPUDetailsFrame computerCPUDetailsFrame;
	private ComputerRAMDetailsFrame computerRAMDetailsFrame;
	private ComputerMassStorageDetailsFrame computerMassStorageDetailsFrame;
	private ComputerComponentDetailsFrame computerComponentDetailsFrame;
	private ComputerLicenseAssignedDetailsFrame computerLicenseAssignedDetailsFrame;
	
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
	
	public void addLicensesDictionaryPanel() {
		this.getTabbedPanel().addTabbedPanel(TabbedPanel.LICENSES_DICTIONARY_PANEL, new LicenseDictionaryTablePanel(this));
		this.getLicenseDictionaryTable().updateTable(getManagerDAO().getLicenseDAO().getAll());
	}
	
	public void addSoftwareDictionaryPanel() {
		this.getTabbedPanel().addTabbedPanel(TabbedPanel.SOFTWARE_DICTIONARY_PANEL, new SoftwareDictionaryTablePanel(this));
		this.getSoftwareDictionaryTable().updateTable(getManagerDAO().getSoftwareDAO().getAll());
	}
	
	public void addComputerCpuDictionaryPanel() {
		this.getTabbedPanel().addTabbedPanel(TabbedPanel.CPU_DICTIONARY_PANEL, new ComputerCPUDictionaryTablePanel(this));
		this.getComputerCPUDictionaryTable().updateTable(getManagerDAO().getComputerCPUDAO().getAll());
	}
	
	public void addComputerRamDictionaryPanel() {
		this.getTabbedPanel().addTabbedPanel(TabbedPanel.RAM_DICTIONARY_PANEL, new ComputerRAMDictionaryTablePanel(this));
		this.getComputerRAMDictionaryTable().updateTable(getManagerDAO().getComputerRAMDAO().getAll());
	}
	
	public void addComputerMassStorageDictionaryPanel() {
		this.getTabbedPanel().addTabbedPanel(TabbedPanel.MASS_STORAGE_DICTIONARY_PANEL, new ComputerMassStorageDictionaryTablePanel(this));
		this.getComputerMassStorageDictionaryTable().updateTable(getManagerDAO().getComputerMassStorageDAO().getAll());
	}
	
	public void addComputerComponentsDictionaryPanel() {
		this.getTabbedPanel().addTabbedPanel(TabbedPanel.COMPUTERS_COMPONENTS_DICTIONARY_PANEL, new ComputerComponentDictionaryTablePanel(this));
		this.getComputerComponentDictionaryTable().updateTable(getManagerDAO().getComputerComponentDAO().getAll());
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

	public LicenseDictionaryTablePanel getLicenseDictionaryTable() {
		return licenseDictionaryTable;
	}

	public void setLicenseDictionaryTable(LicenseDictionaryTablePanel licenseDictionaryTable) {
		this.licenseDictionaryTable = licenseDictionaryTable;
	}

	public SoftwareDictionaryTablePanel getSoftwareDictionaryTable() {
		return softwareDictionaryTable;
	}

	public void setSoftwareDictionaryTable(SoftwareDictionaryTablePanel softwareDictionaryTable) {
		this.softwareDictionaryTable = softwareDictionaryTable;
	}

	public SoftwareDetailsFrame getSoftwareDetailsFrame() {
		return softwareDetailsFrame;
	}

	public void setSoftwareDetailsFrame(SoftwareDetailsFrame softwareDetailsFrame) {
		this.softwareDetailsFrame = softwareDetailsFrame;
	}

	public ComputerCPUDetailsFrame getComputerCPUDetailsFrame() {
		return computerCPUDetailsFrame;
	}

	public void setComputerCPUDetailsFrame(ComputerCPUDetailsFrame computerCPUDetailsFrame) {
		this.computerCPUDetailsFrame = computerCPUDetailsFrame;
	}

	public ComputerCPUDictionaryTablePanel getComputerCPUDictionaryTable() {
		return computerCPUDictionaryTable;
	}

	public void setComputerCPUDictionaryTable(ComputerCPUDictionaryTablePanel computerCPUDictionaryTable) {
		this.computerCPUDictionaryTable = computerCPUDictionaryTable;
	}

	public ComputerRAMDictionaryTablePanel getComputerRAMDictionaryTable() {
		return computerRAMDictionaryTable;
	}

	public void setComputerRAMDictionaryTable(ComputerRAMDictionaryTablePanel computerRAMDictionaryTable) {
		this.computerRAMDictionaryTable = computerRAMDictionaryTable;
	}

	public ComputerRAMDetailsFrame getComputerRAMDetailsFrame() {
		return computerRAMDetailsFrame;
	}

	public void setComputerRAMDetailsFrame(ComputerRAMDetailsFrame computerRAMDetailsFrame) {
		this.computerRAMDetailsFrame = computerRAMDetailsFrame;
	}

	public ComputerMassStorageDictionaryTablePanel getComputerMassStorageDictionaryTable() {
		return computerMassStorageDictionaryTable;
	}

	public void setComputerMassStorageDictionaryTable(ComputerMassStorageDictionaryTablePanel computerMassStorageDictionaryTable) {
		this.computerMassStorageDictionaryTable = computerMassStorageDictionaryTable;
	}

	public ComputerMassStorageDetailsFrame getComputerMassStorageDetailsFrame() {
		return computerMassStorageDetailsFrame;
	}

	public void setComputerMassStorageDetailsFrame(ComputerMassStorageDetailsFrame computerMassStorageDetailsFrame) {
		this.computerMassStorageDetailsFrame = computerMassStorageDetailsFrame;
	}

	public ComputerComponentDictionaryTablePanel getComputerComponentDictionaryTable() {
		return computerComponentDictionaryTable;
	}

	public void setComputerComponentDictionaryTable(ComputerComponentDictionaryTablePanel computerComponentDictionaryTable) {
		this.computerComponentDictionaryTable = computerComponentDictionaryTable;
	}

	public ComputerComponentDetailsFrame getComputerComponentDetailsFrame() {
		return computerComponentDetailsFrame;
	}

	public void setComputerComponentDetailsFrame(ComputerComponentDetailsFrame computerComponentDetailsFrame) {
		this.computerComponentDetailsFrame = computerComponentDetailsFrame;
	}

	public ComputerLicenseAssignedTablePanel getComputerLicenseAssignedTable() {
		return computerLicenseAssignedTable;
	}

	public void setComputerLicenseAssignedTable(ComputerLicenseAssignedTablePanel computerLicenseAssignedTable) {
		this.computerLicenseAssignedTable = computerLicenseAssignedTable;
	}

	public ComputerLicenseAssignedDetailsFrame getComputerLicenseAssignedDetailsFrame() {
		return computerLicenseAssignedDetailsFrame;
	}

	public void setComputerLicenseAssignedDetailsFrame(ComputerLicenseAssignedDetailsFrame computerLicenseAssignedDetailsFrame) {
		this.computerLicenseAssignedDetailsFrame = computerLicenseAssignedDetailsFrame;
	}


}
