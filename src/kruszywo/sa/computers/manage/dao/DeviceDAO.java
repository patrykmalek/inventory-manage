package kruszywo.sa.computers.manage.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.exception.SystemOperationException;
import kruszywo.sa.computers.manage.model.CommonFunctions;
import kruszywo.sa.computers.manage.model.Department;
import kruszywo.sa.computers.manage.model.Device;
import kruszywo.sa.computers.manage.model.DeviceType;
import kruszywo.sa.computers.manage.model.Employee;

public class DeviceDAO implements DAO<Device>{

	private List<Device> devices;
	private Controller controller;
	
	private static final String FIND_BY_ID = "SELECT * FROM DEVICES D LEFT JOIN DEVICE_TYPE DT ON (DT.ID_DEVICE_TYPE = D.ID_DEVICE_TYPE) LEFT JOIN DEPARTMENT DP ON (DP.ID_DEPARTMENT = D.ID_ASSIGNED_DEPARTMENT) LEFT JOIN EMPLOYEE E ON (E.ID_EMPLOYEE = D.ID_ASSIGNED_EMPLOYEE) WHERE D.ID_DEVICE=?;";
    private static final String FIND_BY_NAME = "SELECT * FROM DEVICES D LEFT JOIN DEVICE_TYPE DT ON (DT.ID_DEVICE_TYPE = D.ID_DEVICE_TYPE) LEFT JOIN DEPARTMENT DP ON (DP.ID_DEPARTMENT = D.ID_ASSIGNED_DEPARTMENT) LEFT JOIN EMPLOYEE E ON (E.ID_EMPLOYEE = D.ID_ASSIGNED_EMPLOYEE) WHERE D.DEVICE_UNIQUE_NUMBER || D.DEVICE_NAME || D.DEVICE_INVENTORY_NUMBER || D.INVOICE_NUMBER || D.PURCHASE_DATE || D.LAST_INSTALLATION_DATE || D.COMPUTER_NAME LIKE ?";
	private static final String FIND_ALL = "SELECT * FROM DEVICES D LEFT JOIN DEVICE_TYPE DT ON (DT.ID_DEVICE_TYPE = D.ID_DEVICE_TYPE) LEFT JOIN DEPARTMENT DP ON (DP.ID_DEPARTMENT = D.ID_ASSIGNED_DEPARTMENT) LEFT JOIN EMPLOYEE E ON (E.ID_EMPLOYEE = D.ID_ASSIGNED_EMPLOYEE);";
	private static final String FIND_ALL_COMPUTERS_AND_LAPTOP = "SELECT * FROM DEVICES D LEFT JOIN DEVICE_TYPE DT ON (DT.ID_DEVICE_TYPE = D.ID_DEVICE_TYPE) LEFT JOIN DEPARTMENT DP ON (DP.ID_DEPARTMENT = D.ID_ASSIGNED_DEPARTMENT) LEFT JOIN EMPLOYEE E ON (E.ID_EMPLOYEE = D.ID_ASSIGNED_EMPLOYEE) WHERE D.ID_DEVICE_TYPE IN (1001, 1013);";
    private static final String INSERT = "INSERT INTO devices (device_unique_number, device_name, device_inventory_number, id_device_type, id_assigned_department, id_assigned_employee, invoice_number, purchase_date, last_installation_date, device_notes, computer_name, last_modification_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE devices SET device_unique_number=?, device_name=?, device_inventory_number=?, id_device_type=?, id_assigned_department=?, id_assigned_employee=?, invoice_number=?, purchase_date=?, last_installation_date=?, device_notes=?, computer_name=?, last_modification_date=? WHERE id_device=?;";
    private static final String DELETE = "DELETE FROM DEVICES WHERE ID_DEVICE=?;";
	
	public DeviceDAO(Controller controller) {
		this.controller = controller;
		this.controller.getManagerDAO().setDeviceDAO(this);
	}
	
	@Override
	public Device get(int deviceID) {
		Device device = null;
		DeviceType deviceType = null;
		Department assignedDepartment = null;
		Employee assignedEmployee = null;
     	try {
	     	 PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_ID);
	     	 ps.setInt(1, deviceID);
	     	 
	   		 controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
	   		 ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
   		 
		 if(resultSet.next()) {
				
		 	device = new Device();
		 	deviceType = new DeviceType();
		 	assignedDepartment = new Department();
		 	assignedEmployee = new Employee();
		 	
		 	device.setDeviceID(resultSet.getInt("id_device"));
		 	device.setDeviceUniqueNumber(resultSet.getString("device_unique_number"));
		 	device.setDeviceName(resultSet.getString("device_name"));
		 	device.setDeviceInventoryNumber(resultSet.getString("device_inventory_number"));
		 	device.setInvoiceNumber(resultSet.getString("invoice_number"));
		 	device.setPurchaseDate(resultSet.getString("purchase_date"));
		 	device.setLastInstallationDate(resultSet.getString("last_installation_date"));
		 	device.setNotes(resultSet.getString("device_notes"));
		 	device.setComputerName(resultSet.getString("computer_name"));
		 	device.setLastModificationDate(resultSet.getString("last_modification_date"));
		 	
		 	deviceType.setDeviceTypeID(resultSet.getInt("id_device_type"));
		 	deviceType.setDeviceTypeName(resultSet.getString("device_type_name"));
		 	
		 	assignedDepartment.setDepartmentID(resultSet.getInt("id_assigned_department"));
		 	assignedDepartment.setDepartmentCode(resultSet.getString("department_code"));
		 	assignedDepartment.setDepartmentName(resultSet.getString("department_name"));
		 	
		 	assignedEmployee.setEmployeeID(resultSet.getInt("id_assigned_employee"));
		 	assignedEmployee.setFirstName(resultSet.getString("employee_first_name"));
		 	assignedEmployee.setLastName(resultSet.getString("employee_last_name"));
		 	
		 	device.setDeviceType(deviceType);
		 	device.setAssignedDepartment(assignedDepartment);
		 	device.setAssignedEmployee(assignedEmployee);
			 
		 }
		  ps.close();
		  resultSet.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
		}
      return device;
	}
	
	@Override
	public Device get(String deviceTextToFind) {
		Device device = null;
		DeviceType deviceType = null;
		Department assignedDepartment = null;
		Employee assignedEmployee = null;
     	try {
	     	 PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_NAME);
	     	 ps.setString(1, "%" + deviceTextToFind + "%");
	     	 
	   		 controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
	   		 ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
   		 
		 if(resultSet.next()) {
				
		 	device = new Device();
		 	deviceType = new DeviceType();
		 	assignedDepartment = new Department();
		 	assignedEmployee = new Employee();
		 	
		 	device.setDeviceID(resultSet.getInt("id_device"));
		 	device.setDeviceUniqueNumber(resultSet.getString("device_unique_number"));
		 	device.setDeviceName(resultSet.getString("device_name"));
		 	device.setDeviceInventoryNumber(resultSet.getString("device_inventory_number"));
		 	device.setInvoiceNumber(resultSet.getString("invoice_number"));
		 	device.setPurchaseDate(resultSet.getString("purchase_date"));
		 	device.setLastInstallationDate(resultSet.getString("last_installation_date"));
		 	device.setNotes(resultSet.getString("device_notes"));
		 	device.setComputerName(resultSet.getString("computer_name"));
			device.setLastModificationDate(resultSet.getString("last_modification_date"));
		 	
		 	deviceType.setDeviceTypeID(resultSet.getInt("id_device_type"));
		 	deviceType.setDeviceTypeName(resultSet.getString("device_type_name"));
		 	
		 	assignedDepartment.setDepartmentID(resultSet.getInt("id_assigned_department"));
		 	assignedDepartment.setDepartmentCode(resultSet.getString("department_code"));
		 	assignedDepartment.setDepartmentName(resultSet.getString("department_name"));
		 	
		 	assignedEmployee.setEmployeeID(resultSet.getInt("id_assigned_employee"));
		 	assignedEmployee.setFirstName(resultSet.getString("employee_first_name"));
		 	assignedEmployee.setLastName(resultSet.getString("employee_last_name"));
		 	
		 	device.setDeviceType(deviceType);
		 	device.setAssignedDepartment(assignedDepartment);
		 	device.setAssignedEmployee(assignedEmployee);
			 
		 }
		  ps.close();
		  resultSet.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
		}
      return device;
	}

	@Override
	public List<Device> getAll() {
		 devices = new ArrayList<Device>();
	 
         	try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_ALL);
				controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
				ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
				Device device = null;
				DeviceType deviceType = null;
				Department assignedDepartment = null;
				Employee assignedEmployee = null;
			 while (resultSet.next()) {
					
				 	device = new Device();
				 	deviceType = new DeviceType();
				 	assignedDepartment = new Department();
				 	assignedEmployee = new Employee();
				 	
				 	device.setDeviceID(resultSet.getInt("id_device"));
				 	device.setDeviceUniqueNumber(resultSet.getString("device_unique_number"));
				 	device.setDeviceName(resultSet.getString("device_name"));
				 	device.setDeviceInventoryNumber(resultSet.getString("device_inventory_number"));
				 	device.setInvoiceNumber(resultSet.getString("invoice_number"));
				 	device.setPurchaseDate(resultSet.getString("purchase_date"));
				 	device.setLastInstallationDate(resultSet.getString("last_installation_date"));
				 	device.setNotes(resultSet.getString("device_notes"));
				 	device.setComputerName(resultSet.getString("computer_name"));
					device.setLastModificationDate(resultSet.getString("last_modification_date"));
				 	
				 	deviceType.setDeviceTypeID(resultSet.getInt("id_device_type"));
				 	deviceType.setDeviceTypeName(resultSet.getString("device_type_name"));
				 	
				 	assignedDepartment.setDepartmentID(resultSet.getInt("id_assigned_department"));
				 	assignedDepartment.setDepartmentCode(resultSet.getString("department_code"));
				 	assignedDepartment.setDepartmentName(resultSet.getString("department_name"));
				 	
				 	assignedEmployee.setEmployeeID(resultSet.getInt("id_assigned_employee"));
				 	assignedEmployee.setFirstName(resultSet.getString("employee_first_name"));
				 	assignedEmployee.setLastName(resultSet.getString("employee_last_name"));
				 	
				 	device.setDeviceType(deviceType);
				 	device.setAssignedDepartment(assignedDepartment);
				 	device.setAssignedEmployee(assignedEmployee);
				 	
				 devices.add(device);
				}
			  ps.close();
			  resultSet.close();
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
			}
	      return devices;
	}
	
	public List<Device> getOnlyComputersAll() {
		 devices = new ArrayList<Device>();
	 
        	try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_ALL_COMPUTERS_AND_LAPTOP);
				controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
				ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
				Device device = null;
				DeviceType deviceType = null;
				Department assignedDepartment = null;
				Employee assignedEmployee = null;
			 while (resultSet.next()) {
					
				 	device = new Device();
				 	deviceType = new DeviceType();
				 	assignedDepartment = new Department();
				 	assignedEmployee = new Employee();
				 	
				 	device.setDeviceID(resultSet.getInt("id_device"));
				 	device.setDeviceUniqueNumber(resultSet.getString("device_unique_number"));
				 	device.setDeviceName(resultSet.getString("device_name"));
				 	device.setDeviceInventoryNumber(resultSet.getString("device_inventory_number"));
				 	device.setInvoiceNumber(resultSet.getString("invoice_number"));
				 	device.setPurchaseDate(resultSet.getString("purchase_date"));
				 	device.setLastInstallationDate(resultSet.getString("last_installation_date"));
				 	device.setNotes(resultSet.getString("device_notes"));
				 	device.setComputerName(resultSet.getString("computer_name"));
				 	
				 	deviceType.setDeviceTypeID(resultSet.getInt("id_device_type"));
				 	deviceType.setDeviceTypeName(resultSet.getString("device_type_name"));
				 	
				 	assignedDepartment.setDepartmentID(resultSet.getInt("id_assigned_department"));
				 	assignedDepartment.setDepartmentCode(resultSet.getString("department_code"));
				 	assignedDepartment.setDepartmentName(resultSet.getString("department_name"));
				 	
				 	assignedEmployee.setEmployeeID(resultSet.getInt("id_assigned_employee"));
				 	assignedEmployee.setFirstName(resultSet.getString("employee_first_name"));
				 	assignedEmployee.setLastName(resultSet.getString("employee_last_name"));
				 	
				 	device.setDeviceType(deviceType);
				 	device.setAssignedDepartment(assignedDepartment);
				 	device.setAssignedEmployee(assignedEmployee);
				 	
				 devices.add(device);
				}
			  ps.close();
			  resultSet.close();
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
			}
	      return devices;
	}

	@Override
	public void insert(Device device) {
			try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(INSERT);
				
	            ps.setObject(1, device.getDeviceUniqueNumber());
	            ps.setObject(2, device.getDeviceName());
	            ps.setObject(3,  device.getDeviceInventoryNumber());
	            ps.setObject(4, (device.getDeviceType().getDeviceTypeID() == 0) ? null : device.getDeviceType().getDeviceTypeID());
	            ps.setObject(5, (device.getAssignedDepartment().getDepartmentID() == 0) ? null : device.getAssignedDepartment().getDepartmentID());
	            ps.setObject(6, (device.getAssignedEmployee().getEmployeeID() == 0) ? null : device.getAssignedEmployee().getEmployeeID());
				ps.setObject(7, device.getInvoiceNumber());
	            ps.setObject(8, device.getPurchaseDate());
	            ps.setObject(9, device.getLastInstallationDate());
	            ps.setObject(10, device.getNotes());
	            ps.setObject(11, device.getComputerName());
	            ps.setObject(12, CommonFunctions.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
	            
				controller.getDatabaseProvider().executePreparedStatement(ps);
				
	            System.out.println("Device with following details was saved in DB: " + device.toString());
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas zapisywania danych urządzenia do bazy.", e);
			}
	}

	@Override
	public void update(Device device) {
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(UPDATE);
			 
            ps.setObject(1, device.getDeviceUniqueNumber());
            ps.setObject(2, device.getDeviceName());
            ps.setObject(3,  device.getDeviceInventoryNumber());
            ps.setObject(4, (device.getDeviceType().getDeviceTypeID() == 0) ? null : device.getDeviceType().getDeviceTypeID());
            ps.setObject(5, (device.getAssignedDepartment().getDepartmentID() == 0) ? null : device.getAssignedDepartment().getDepartmentID());
            ps.setObject(6, (device.getAssignedEmployee().getEmployeeID() == 0) ? null : device.getAssignedEmployee().getEmployeeID());
			ps.setObject(7, device.getInvoiceNumber());
            ps.setObject(8, device.getPurchaseDate());
            ps.setObject(9, device.getLastInstallationDate());
            ps.setObject(10, device.getNotes());
            ps.setObject(11, device.getComputerName());
            ps.setObject(12, CommonFunctions.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            ps.setInt(13, device.getDeviceID());
            
            
			controller.getDatabaseProvider().executePreparedStatement(ps);
			
            System.out.println("Device with id " + device.getDeviceID() + " was updated in DB with following details: " + device.toString());
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

	@Override
	public void delete(Device device) {
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(DELETE);
			 
            ps.setInt(1, device.getDeviceID());
            
			controller.getDatabaseProvider().executePreparedStatement(ps);
			
			System.out.println("Device with id: " +  device.getDeviceID() + " was sucesfully deleted from DB.");
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

}
