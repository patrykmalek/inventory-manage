package inventory.manage.controller;



import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import inventory.manage.config.ApplicationConfig;
import inventory.manage.dao.ManagerDAO;
import inventory.manage.exception.SystemOperationException;
import inventory.manage.model.CommonFunctions;
import inventory.manage.provider.DatabaseProvider;
import inventory.manage.view.AboutFrame;
import inventory.manage.view.ConfigFrame;
import inventory.manage.view.FooterPanel;
import inventory.manage.view.MainFrame;
import inventory.manage.view.TabbedPanel;
import inventory.manage.view.WaitWindow;
import inventory.manage.view.details.window.ComputerCPUDetailsFrame;
import inventory.manage.view.details.window.ComputerComponentDetailsFrame;
import inventory.manage.view.details.window.ComputerLicenseAssignedDetailsFrame;
import inventory.manage.view.details.window.ComputerMassStorageDetailsFrame;
import inventory.manage.view.details.window.ComputerRAMDetailsFrame;
import inventory.manage.view.details.window.DepartmentDetailsFrame;
import inventory.manage.view.details.window.DeviceConnectedDocumentDetailsFrame;
import inventory.manage.view.details.window.DeviceDetailsFrame;
import inventory.manage.view.details.window.DeviceTypeDetailsFrame;
import inventory.manage.view.details.window.DocumentDetailsFrame;
import inventory.manage.view.details.window.EmployeeDetailsFrame;
import inventory.manage.view.details.window.LicenseDetailsFrame;
import inventory.manage.view.details.window.SoftwareDetailsFrame;
import inventory.manage.view.dictionary.table.panel.ComputerCPUDictionaryTablePanel;
import inventory.manage.view.dictionary.table.panel.ComputerComponentDictionaryTablePanel;
import inventory.manage.view.dictionary.table.panel.ComputerLicenseAssignedTablePanel;
import inventory.manage.view.dictionary.table.panel.ComputerMassStorageDictionaryTablePanel;
import inventory.manage.view.dictionary.table.panel.ComputerRAMDictionaryTablePanel;
import inventory.manage.view.dictionary.table.panel.DepartmentDictionaryTablePanel;
import inventory.manage.view.dictionary.table.panel.DeviceConnectedDocumentTablePanel;
import inventory.manage.view.dictionary.table.panel.DeviceTablePanel;
import inventory.manage.view.dictionary.table.panel.DeviceTypeDictionaryTablePanel;
import inventory.manage.view.dictionary.table.panel.DocumentDictionaryTablePanel;
import inventory.manage.view.dictionary.table.panel.EmployeeDictionaryTablePanel;
import inventory.manage.view.dictionary.table.panel.LicenseDictionaryTablePanel;
import inventory.manage.view.dictionary.table.panel.SoftwareDictionaryTablePanel;

public class Controller {

	private DatabaseProvider databaseProvider;
	
	private MainFrame mainFrame;
	private FooterPanel footerPanel;
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
	private DocumentDictionaryTablePanel documentDictionaryTablePanel;
	private DeviceConnectedDocumentTablePanel deviceConnectedDocumentTablePanel;
	
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
	private DocumentDetailsFrame documentDetailsFrame;
	private DeviceConnectedDocumentDetailsFrame deviceConnectedDocumentDetailsFrame;
	private ConfigFrame configFrame;

	
	public static WaitWindow waitWindow;
	
	private ManagerDAO managerDAO;
	
	private static List<String> errors = new ArrayList<String>();
	
	private final static SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private final static SimpleDateFormat defaultDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private Calendar calendar;
	
	private ApplicationConfig applicationConfig;
	
	
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
	
	public void addDocumentsDictionaryPanel() {
		this.getTabbedPanel().addTabbedPanel(TabbedPanel.DOCUMENTS_DICTIONARY_PANEL, new DocumentDictionaryTablePanel(this));
		this.getDocumentDictionaryTable().updateTable(getManagerDAO().getDocumentDAO().getAll());
	}
	
	public void showConfigWindow() {
		new ConfigFrame(this);
	}
	
	public void updateDatabasePathInfo() {
		this.getFooterPanel().updateDatabasePathInfo(getApplicationConfig().getDatabasePath());
	}
	

	public void updateConfigView() {
		this.getConfigFrame().getDatabasePathField().setText(getApplicationConfig().getDatabasePath());
	}
	
	public void updateDatabasePath(String databasePath) {
		this.getApplicationConfig().setDatabasePathAndSaveToConfigFile(databasePath);
		this.updateDatabasePathInfo();
		this.createNewDatabaseProvider();	
		this.getTabbedPanel().getTabbedPane().removeAll();
	}
	
	private void createNewDatabaseProvider(){
		DatabaseProvider databaseProvider = new DatabaseProvider(this.getApplicationConfig().getDatabaseURL());
		databaseProvider.connect();
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

	public EmployeeDictionaryTablePanel getEmployeeDictionaryTable() {
		return employeeDictionaryTable;
	}

	public void setEmployeeDictionaryTable(EmployeeDictionaryTablePanel employeeDictionaryTable) {
		this.employeeDictionaryTable = employeeDictionaryTable;
	}

	public SimpleDateFormat getDefaultDateFormat() {
		return defaultDateFormat;
	}
	
	public DeviceDetailsFrame getDeviceDetailsFrame() {
		return deviceDetailsFrame;
	}

	public void setDeviceDetailsFrame(DeviceDetailsFrame deviceDetailsFrame) {
		this.deviceDetailsFrame = deviceDetailsFrame;
	}

	public Calendar getCalendarWithTodayDate() {
		calendar.setTime(CommonFunctions.parseDate(java.time.LocalDate.now().toString(), defaultDateFormat));
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

	public ApplicationConfig getApplicationConfig() {
		return applicationConfig;
	}

	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	public void setFooterPanel(FooterPanel footerPanel) {
		this.footerPanel = footerPanel;
	}
	public FooterPanel getFooterPanel() {
		return footerPanel;
	}

	public ConfigFrame getConfigFrame() {
		return configFrame;
	}

	public void setConfigFrame(ConfigFrame configFrame) {
		this.configFrame = configFrame;
	}

	public void showAboutWindow() {
		new AboutFrame();
	}

	public DocumentDictionaryTablePanel getDocumentDictionaryTable() {
		return documentDictionaryTablePanel;
	}

	public void setDocumentDictionaryTable(DocumentDictionaryTablePanel documentDictionaryTablePanel) {
		this.documentDictionaryTablePanel = documentDictionaryTablePanel;
	}

	public DocumentDetailsFrame getDocumentDetailsFrame() {
		return documentDetailsFrame;
	}

	public void setDocumentDetailsFrame(DocumentDetailsFrame documentDetailsFrame) {
		this.documentDetailsFrame = documentDetailsFrame;
	}
	
	public String openFileChooserAndGetPath() {
		String path = "";
			JFileChooser file = new JFileChooser();
		      file.setMultiSelectionEnabled(true);
		      file.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		      file.setFileHidingEnabled(false);
		      if (file.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		         java.io.File f = file.getSelectedFile();
		         path = f.getPath();
		      }
			return path;
	}
	
	public String getFileNameFromFilePath(String filePath) {
        Path path = Paths.get(filePath);
  
        return path.getFileName().toString();
	}
	
	public String getFileNameFromFilePathWithoutExtension(String filePath) {
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
        	fileName = fileName.substring(0, dotIndex);
        }
        return fileName;
	}
	
	public String getFolderPathFromFilePath(String filePath) {
        Path path = Paths.get(filePath);
  
        return path.getParent().toString();
	}

	public void openFileByFilePath(String filePath) {
		File file = new File(filePath);   
		if(!Desktop.isDesktopSupported()) {  
			System.out.println("Desktop Class is not supported");  
		}  
		
		if(file.exists()) {
			try {
				Desktop desktop = Desktop.getDesktop();  
				desktop.open(file);
			} catch (IOException e) {
				new SystemOperationException("Nie można otworzyć pliku -> " + filePath, e);
			}         
		} else {
			JOptionPane.showMessageDialog(new JPanel(), "Plik o podanej ścieżce nie istnieje! -> " + filePath, "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
		}
	}  
	
	public void copyFile(String sourcePathString, String destinationPathString) {
		try {
			Path sourcePath = Paths.get(sourcePathString);
			Path destinationPath = Paths.get(destinationPathString);
	        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
	    } catch (Exception e) {
	        new SystemOperationException("Błąd podczas przenoszenia pliku.", e);
	    }
	}
	
	public void copyFileWithDeleteSource(String sourcePathString, String destinationPathString) {
		try {
			Path sourcePath = Paths.get(sourcePathString);
			Path destinationPath = Paths.get(destinationPathString);
	        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
	        Files.deleteIfExists(sourcePath);
	    } catch (Exception e) {
	        new SystemOperationException("Błąd podczas przenoszenia pliku.", e);
	    }
	}
	
	public boolean isFileExist(String filePath) {
			return new File(filePath).exists();
	}
	
	public void createFolderForFilesIfNotExist() {
		String directoryName = getApplicationConfig().getLocalDirectoryPath() + "deleted";
		 File directory = new File(directoryName);
		    if (! directory.exists()){
		        directory.mkdirs();
		    }
	}
	
	public String getFileExtension(String filePath) {
	    int lastIndexOf = filePath.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; // empty extension
	    }
	    return filePath.substring(lastIndexOf);
	}

	public DeviceConnectedDocumentTablePanel getDeviceConnectedDocumentTablePanel() {
		return deviceConnectedDocumentTablePanel;
	}

	public void setDeviceConnectedDocumentTablePanel(DeviceConnectedDocumentTablePanel deviceConnectedDocumentTablePanel) {
		this.deviceConnectedDocumentTablePanel = deviceConnectedDocumentTablePanel;
	}

	public DeviceConnectedDocumentDetailsFrame getDeviceConnectedDocumentDetailsFrame() {
		return deviceConnectedDocumentDetailsFrame;
	}

	public void setDeviceConnectedDocumentDetailsFrame(DeviceConnectedDocumentDetailsFrame deviceConnectedDocumentDetailsFrame) {
		this.deviceConnectedDocumentDetailsFrame = deviceConnectedDocumentDetailsFrame;
	}

	public SimpleDateFormat getDefaultDateTimeFormat() {
		return defaultDateTimeFormat;
	}

}
