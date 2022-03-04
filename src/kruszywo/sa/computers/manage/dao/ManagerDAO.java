package kruszywo.sa.computers.manage.dao;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.dao.service.DeviceServiceDAO;

public class ManagerDAO {

	
	private Controller controller;
	
	private DeviceServiceDAO deviceServiceDAO;
	
	private DeviceDAO deviceDAO;
	private DeviceTypeDAO deviceTypeDAO;
	private DepartmentDAO departmentDAO;
	
	public ManagerDAO(Controller controller) {
		this.controller = controller;
		this.controller.setManagerDAO(this);
		
		this.setDeviceDAO(new DeviceDAO(controller));
		this.setDeviceTypeDAO(new DeviceTypeDAO(controller));
		this.setDepartmentDAO(new DepartmentDAO(controller));
		
		this.setDeviceServiceDAO(new DeviceServiceDAO(controller, this));
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
}
