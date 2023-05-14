package inventory.manage.dao;

import inventory.manage.controller.Controller;
import inventory.manage.dao.service.ComputerCPUServiceDAO;
import inventory.manage.dao.service.ComputerComponentServiceDAO;
import inventory.manage.dao.service.ComputerMassStorageServiceDAO;
import inventory.manage.dao.service.ComputerRAMServiceDAO;
import inventory.manage.dao.service.DepartmentServiceDAO;
import inventory.manage.dao.service.DeviceServiceDAO;
import inventory.manage.dao.service.DeviceTypeServiceDAO;
import inventory.manage.dao.service.DocumentServiceDAO;
import inventory.manage.dao.service.EmployeeServiceDAO;
import inventory.manage.dao.service.LicenseServiceDAO;
import inventory.manage.dao.service.SoftwareServiceDAO;

public class ManagerDAO {

	
	private Controller controller;
	
	private DeviceServiceDAO deviceServiceDAO;
	private DeviceTypeServiceDAO deviceTypeServiceDAO;
	private DepartmentServiceDAO departmentServiceDAO;
	private EmployeeServiceDAO employeeServiceDAO;
	private LicenseServiceDAO licenseServiceDAO;
	private SoftwareServiceDAO softwareServiceDAO;
	private ComputerCPUServiceDAO computerCPUServiceDAO;
	private ComputerRAMServiceDAO computerRAMServiceDAO;
	private ComputerMassStorageServiceDAO computerMassStorageServiceDAO;
	private ComputerComponentServiceDAO computerComponentServiceDAO;
	private DocumentServiceDAO documentServiceDAO;
	
	private DeviceDAO deviceDAO;
	private DeviceTypeDAO deviceTypeDAO;
	private DepartmentDAO departmentDAO;
	private EmployeeDAO employeeDAO;
	private SoftwareDAO softwareDAO;
	private LicenseDAO licenseDAO;
	private ComputerMassStorageDAO computerMassStorageDAO;
	private ComputerRAMDAO computerRAMDAO;
	private ComputerCPUDAO computerCPUDAO;
	private ComputerComponentDAO computerComponentDAO;
	private DocumentDAO documentDAO;
	
	public ManagerDAO(Controller controller) {
		this.controller = controller;
		this.controller.setManagerDAO(this);
		
		this.setDeviceDAO(new DeviceDAO(controller));
		this.setDeviceTypeDAO(new DeviceTypeDAO(controller));
		this.setDepartmentDAO(new DepartmentDAO(controller));
		this.setEmployeeDAO(new EmployeeDAO(controller));
		this.setLicenseDAO(new LicenseDAO(controller));
		this.setSoftwareDAO(new SoftwareDAO(controller));
		this.setComputerMassStorageDAO(new ComputerMassStorageDAO(controller));
		this.setComputerRAMDAO(new ComputerRAMDAO(controller));
		this.setComputerCPUDAO(new ComputerCPUDAO(controller));
		this.setComputerComponentDAO(new ComputerComponentDAO(controller));
		this.setDocumentDAO(new DocumentDAO(controller));
		
		this.setDeviceServiceDAO(new DeviceServiceDAO(controller, this));
		this.setDeviceTypeServiceDAO(new DeviceTypeServiceDAO(controller, this));
		this.setDepartmentServiceDAO(new DepartmentServiceDAO(controller, this));
		this.setEmployeeServiceDAO(new EmployeeServiceDAO(controller, this));
		this.setLicenseServiceDAO(new LicenseServiceDAO(controller, this));
		this.setSoftwareServiceDAO(new SoftwareServiceDAO(controller, this));
		this.setComputerCPUServiceDAO(new ComputerCPUServiceDAO(controller, this));
		this.setComputerRAMServiceDAO(new ComputerRAMServiceDAO(controller, this));
		this.setComputerMassStorageServiceDAO(new ComputerMassStorageServiceDAO(controller, this));
		this.setComputerComponentServiceDAO(new ComputerComponentServiceDAO(controller, this));
		this.setDocumentServiceDAO(new DocumentServiceDAO(controller, this));
	}

	public DeviceDAO getDeviceDAO() {
		return deviceDAO;
	}

	public void setDeviceDAO(DeviceDAO deviceDAO) {
		this.deviceDAO = deviceDAO;
	}

	public DeviceTypeDAO getDeviceTypeDAO() {
		return deviceTypeDAO;
	}

	public void setDeviceTypeDAO(DeviceTypeDAO deviceTypeDAO) {
		this.deviceTypeDAO = deviceTypeDAO;
	}

	public DeviceServiceDAO getDeviceServiceDAO() {
		return deviceServiceDAO;
	}

	public void setDeviceServiceDAO(DeviceServiceDAO deviceServiceDAO) {
		this.deviceServiceDAO = deviceServiceDAO;
	}

	public DepartmentDAO getDepartmentDAO() {
		return departmentDAO;
	}

	public void setDepartmentDAO(DepartmentDAO departmentDAO) {
		this.departmentDAO = departmentDAO;
	}

	public EmployeeDAO getEmployeeDAO() {
		return employeeDAO;
	}

	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	public DeviceTypeServiceDAO getDeviceTypeServiceDAO() {
		return deviceTypeServiceDAO;
	}

	public void setDeviceTypeServiceDAO(DeviceTypeServiceDAO deviceTypeServiceDAO) {
		this.deviceTypeServiceDAO = deviceTypeServiceDAO;
	}

	public DepartmentServiceDAO getDepartmentServiceDAO() {
		return departmentServiceDAO;
	}

	public void setDepartmentServiceDAO(DepartmentServiceDAO departmentServiceDAO) {
		this.departmentServiceDAO = departmentServiceDAO;
	}

	public EmployeeServiceDAO getEmployeeServiceDAO() {
		return employeeServiceDAO;
	}

	public void setEmployeeServiceDAO(EmployeeServiceDAO employeeServiceDAO) {
		this.employeeServiceDAO = employeeServiceDAO;
	}

	public SoftwareDAO getSoftwareDAO() {
		return softwareDAO;
	}

	public void setSoftwareDAO(SoftwareDAO softwareDAO) {
		this.softwareDAO = softwareDAO;
	}

	public LicenseDAO getLicenseDAO() {
		return licenseDAO;
	}

	public void setLicenseDAO(LicenseDAO licenseDAO) {
		this.licenseDAO = licenseDAO;
	}

	public LicenseServiceDAO getLicenseServiceDAO() {
		return licenseServiceDAO;
	}

	public void setLicenseServiceDAO(LicenseServiceDAO licenseServiceDAO) {
		this.licenseServiceDAO = licenseServiceDAO;
	}

	public SoftwareServiceDAO getSoftwareServiceDAO() {
		return softwareServiceDAO;
	}

	public void setSoftwareServiceDAO(SoftwareServiceDAO softwareServiceDAO) {
		this.softwareServiceDAO = softwareServiceDAO;
	}

	public ComputerMassStorageDAO getComputerMassStorageDAO() {
		return computerMassStorageDAO;
	}

	public void setComputerMassStorageDAO(ComputerMassStorageDAO computerMassStorageDAO) {
		this.computerMassStorageDAO = computerMassStorageDAO;
	}

	public ComputerRAMDAO getComputerRAMDAO() {
		return computerRAMDAO;
	}

	public void setComputerRAMDAO(ComputerRAMDAO computerRAMDAO) {
		this.computerRAMDAO = computerRAMDAO;
	}

	public ComputerCPUDAO getComputerCPUDAO() {
		return computerCPUDAO;
	}

	public void setComputerCPUDAO(ComputerCPUDAO computerCPUDAO) {
		this.computerCPUDAO = computerCPUDAO;
	}

	public ComputerCPUServiceDAO getComputerCPUServiceDAO() {
		return computerCPUServiceDAO;
	}

	public void setComputerCPUServiceDAO(ComputerCPUServiceDAO computerCPUServiceDAO) {
		this.computerCPUServiceDAO = computerCPUServiceDAO;
	}

	public ComputerRAMServiceDAO getComputerRAMServiceDAO() {
		return computerRAMServiceDAO;
	}

	public void setComputerRAMServiceDAO(ComputerRAMServiceDAO computerRAMServiceDAO) {
		this.computerRAMServiceDAO = computerRAMServiceDAO;
	}

	public ComputerMassStorageServiceDAO getComputerMassStorageServiceDAO() {
		return computerMassStorageServiceDAO;
	}

	public void setComputerMassStorageServiceDAO(ComputerMassStorageServiceDAO computerMassStorageServiceDAO) {
		this.computerMassStorageServiceDAO = computerMassStorageServiceDAO;
	}

	public ComputerComponentDAO getComputerComponentDAO() {
		return computerComponentDAO;
	}

	public void setComputerComponentDAO(ComputerComponentDAO computerComponentDAO) {
		this.computerComponentDAO = computerComponentDAO;
	}

	public ComputerComponentServiceDAO getComputerComponentServiceDAO() {
		return computerComponentServiceDAO;
	}

	public void setComputerComponentServiceDAO(ComputerComponentServiceDAO computerComponentServiceDAO) {
		this.computerComponentServiceDAO = computerComponentServiceDAO;
	}

	public DocumentDAO getDocumentDAO() {
		return documentDAO;
	}

	public void setDocumentDAO(DocumentDAO documentDAO) {
		this.documentDAO = documentDAO;
	}

	public DocumentServiceDAO getDocumentServiceDAO() {
		return documentServiceDAO;
	}

	public void setDocumentServiceDAO(DocumentServiceDAO documentServiceDAO) {
		this.documentServiceDAO = documentServiceDAO;
	}
	
}
