package kruszywo.sa.computers.manage.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.exception.SystemOperationException;
import kruszywo.sa.computers.manage.model.Device;

public class DeviceDAO implements DAO<Device>{

	private List<Device> devices;
	private Controller controller;
	
	private static final String FIND_BY_ID = "SELECT * FROM DEVICES WHERE DEVICES.ID_DEVICE=?;";
    private static final String FIND_ALL = "SELECT * FROM DEVICES;";
    private static final String INSERT = "INSERT INTO devices (device_unique_number, device_name, device_inventory_number, id_device_type, id_assigned_department, id_assigned_employee, invoice_number, purchase_date, last_installation_date, notes) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE devices SET device_unique_number=?, device_name=?, device_inventory_number=?, id_device_type=?, id_assigned_department=?, id_assigned_employee=?, invoice_number=?, purchase_date=?, last_installation_date=?, notes=? WHERE id_device=?;";
 
	
	public DeviceDAO(Controller controller) {
		this.controller = controller;
		this.controller.setDeviceDAO(this);
	}
	
	@Override
	public Device get(int deviceID) {
		Device device = null;
     	try {
     	 PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_ID);
     	 ps.setInt(1, deviceID);
     	 
   		 controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
   		 ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
   		 
		 if(resultSet.next()) {
				
		 	device = new Device();
		 	device.setDeviceID(resultSet.getInt("id_device"));
		 	device.setDeviceUniqueNumber(resultSet.getString("device_unique_number"));
		 	device.setDeviceName(resultSet.getString("device_name"));
		 	device.setDeviceInventoryNumber(resultSet.getString("device_inventory_number"));
		 	device.setDeviceTypeID(resultSet.getInt("id_device_type"));
		 	device.setAssignedDepartmentID(resultSet.getInt("id_assigned_department"));
		 	device.setAssignedEmployeeID(resultSet.getInt("id_assigned_employee"));
		 	device.setInvoiceNumber(resultSet.getString("invoice_number"));
		 	device.setPurchaseDate(resultSet.getString("purchase_date"));
		 	device.setLastInstallationDate(resultSet.getString("last_installation_date"));
		 	device.setNotes(resultSet.getString("notes"));
			 
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
			 while (resultSet.next()) {
					
			 	device = new Device();
			 	device.setDeviceID(resultSet.getInt("id_device"));
			 	device.setDeviceUniqueNumber(resultSet.getString("device_unique_number"));
			 	device.setDeviceName(resultSet.getString("device_name"));
			 	device.setDeviceInventoryNumber(resultSet.getString("device_inventory_number"));
			 	device.setDeviceTypeID(resultSet.getInt("id_device_type"));
			 	device.setAssignedDepartmentID(resultSet.getInt("id_assigned_department"));
			 	device.setAssignedEmployeeID(resultSet.getInt("id_assigned_employee"));
			 	device.setInvoiceNumber(resultSet.getString("invoice_number"));
			 	device.setPurchaseDate(resultSet.getString("purchase_date"));
			 	device.setLastInstallationDate(resultSet.getString("last_installation_date"));
			 	device.setNotes(resultSet.getString("notes"));
				 	
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
				 
	            ps.setString(1, device.getDeviceUniqueNumber());
	            ps.setString(2, device.getDeviceName());
	            ps.setString(3,  device.getDeviceInventoryNumber());
	            ps.setInt(4, device.getDeviceTypeID());
	            ps.setInt(5, device.getAssignedDepartmentID());
	            ps.setInt(6, device.getAssignedEmployeeID());
				ps.setString(7, device.getInvoiceNumber());
	            ps.setString(8, device.getPurchaseDate());
	            ps.setString(9, device.getLastInstallationDate());
	            ps.setString(10, device.getNotes());
	            
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
			 
            ps.setString(1, device.getDeviceUniqueNumber());
            ps.setString(2, device.getDeviceName());
            ps.setString(3,  device.getDeviceInventoryNumber());
            ps.setInt(4, device.getDeviceTypeID());
            ps.setInt(5, device.getAssignedDepartmentID());
            ps.setInt(6, device.getAssignedEmployeeID());
			ps.setString(7, device.getInvoiceNumber());
            ps.setString(8, device.getPurchaseDate());
            ps.setString(9, device.getLastInstallationDate());
            ps.setString(10, device.getNotes());
            ps.setInt(11, device.getDeviceID());
            
			controller.getDatabaseProvider().executePreparedStatement(ps);
			
            System.out.println("Device with id " + device.getDeviceID() + " was updated in DB with following details: " + device.toString());
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

	@Override
	public void delete(Device device) {
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(UPDATE);
			 
            ps.setInt(1, device.getDeviceID());
            
			controller.getDatabaseProvider().executePreparedStatement(ps);
			
			System.out.println("Device with id: " +  device.getDeviceID() + " was sucesfully deleted from DB.");
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

}
