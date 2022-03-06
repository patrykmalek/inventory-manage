package kruszywo.sa.computers.manage.dao;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.dao.service.DepartmentServiceDAO;
import kruszywo.sa.computers.manage.dao.service.DeviceServiceDAO;
import kruszywo.sa.computers.manage.dao.service.DeviceTypeServiceDAO;
import kruszywo.sa.computers.manage.dao.service.EmployeeServiceDAO;

public class ManagerDAO {

	
	private Controller controller;
	
	private DeviceServiceDAO deviceServiceDAO;
	private DeviceTypeServiceDAO deviceTypeServiceDAO;
	private DepartmentServiceDAO departmentServiceDAO;
	private EmployeeServiceDAO employeeServiceDAO;
	
	private DeviceDAO deviceDAO;
	private DeviceTypeDAO deviceTypeDAO;
	private DepartmentDAO departmentDAO;
	private EmployeeDAO employeeDAO;
	private SoftwareDAO softwareDAO;
	private LicenseDAO licenseDAO;
	
	public ManagerDAO(Controller controller) {
		this.controller = controller;
		this.controller.setManagerDAO(this);
		
		this.setDeviceDAO(new DeviceDAO(controller));
		this.setDeviceTypeDAO(new DeviceTypeDAO(controller));
		this.setDepartmentDAO(new DepartmentDAO(controller));
		this.setEmployeeDAO(new EmployeeDAO(controller));
		
		this.setDeviceServiceDAO(new DeviceServiceDAO(controller, this));
		this.setDeviceTypeServiceDAO(new DeviceTypeServiceDAO(controller, this));
		this.setDepartmentServiceDAO(new DepartmentServiceDAO(controller, this));
		this.setEmployeeServiceDAO(new EmployeeServiceDAO(controller, this));
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
}
