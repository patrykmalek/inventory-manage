package kruszywo.sa.computers.manage.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.exception.SystemOperationException;
import kruszywo.sa.computers.manage.model.DeviceType;

public class DeviceTypeDAO implements DAO<DeviceType>{

	private List<DeviceType> deviceTypes;
	private Controller controller;
	
	private static final String FIND_BY_ID = "SELECT * FROM DEVICE_TYPE WHERE ID_DEVICE_TYPE=?;";
	private static final String FIND_BY_NAME = "SELECT * FROM DEVICE_TYPE WHERE DEVICE_TYPE_NAME LIKE ? LIMIT 1;";
    private static final String FIND_ALL = "SELECT * FROM DEVICE_TYPE;";
    private static final String INSERT = "INSERT INTO DEVICE_TYPE (DEVICE_TYPE_NAME, DEVICE_TYPE_NOTES) VALUES(?,?);";
    private static final String UPDATE = "UPDATE DEVICE_TYPE SET DEVICE_TYPE_NAME=?, DEVICE_TYPE_NOTES=? WHERE ID_DEVICE_TYPE=?;";
    private static final String DELETE = "DELETE FROM DEVICE_TYPE WHERE ID_DEVICE_TYPE=?;";
	
	public DeviceTypeDAO(Controller controller) {
		this.controller = controller;
		this.controller.getManagerDAO().setDeviceTypeDAO(this);
	}
	
	@Override
	public DeviceType get(int deviceTypeID) {
		DeviceType deviceType = null;
     	try {
	     	 PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_ID);
	     	 ps.setInt(1, deviceTypeID);
	     	 
	   		 controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
	   		 ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
   		 
			 if(resultSet.next()) {
					
			 	deviceType = new DeviceType();
	
			 	deviceType.setDeviceTypeID(resultSet.getInt("id_device_type"));
			 	deviceType.setDeviceTypeName(resultSet.getString("device_type_name"));
			 	deviceType.setDeviceTypeNotes(resultSet.getString("device_type_notes"));
	
			 }
		  ps.close();
		  resultSet.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
		}
      return deviceType;
	}
	
	public DeviceType get(String deviceTypeName) {
		DeviceType deviceType = null;
     	try {
	     	 PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_NAME);
	     	 ps.setString(1, "%" + deviceTypeName + "%");
	   		 controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
	   		 ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
   		 
			 if(resultSet.next()) {
					
			 	deviceType = new DeviceType();
	
			 	deviceType.setDeviceTypeID(resultSet.getInt("id_device_type"));
			 	deviceType.setDeviceTypeName(resultSet.getString("device_type_name"));
			 	deviceType.setDeviceTypeNotes(resultSet.getString("device_type_notes"));
			 	
			 }
		  ps.close();
		  resultSet.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
		}
      return deviceType;
	}

	@Override
	public List<DeviceType> getAll() {
		 deviceTypes = new ArrayList<DeviceType>();
	 
         	try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_ALL);
				controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
				ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
				DeviceType deviceType = null;
			 while (resultSet.next()) {
					
				 	deviceType = new DeviceType();
				 	
				 	deviceType.setDeviceTypeID(resultSet.getInt("id_device_type"));
				 	deviceType.setDeviceTypeName(resultSet.getString("device_type_name"));
				 	deviceType.setDeviceTypeNotes(resultSet.getString("device_type_notes"));

				 	deviceTypes.add(deviceType);
				}
			  ps.close();
			  resultSet.close();
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
			}
	      return deviceTypes;
	}

	@Override
	public void insert(DeviceType deviceType) {
			try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(INSERT);
				 
	            ps.setString(1, deviceType.getDeviceTypeName());
	            ps.setString(2, deviceType.getDeviceTypeNotes());
	            
	            ps.executeUpdate();
				ps.close();
				
	            System.out.println("Device Type with following details was saved in DB: " + deviceType.toString());
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas zapisywania danych urządzenia do bazy.", e);
			}
	}

	@Override
	public void update(DeviceType deviceType) {
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(UPDATE);
			 
            ps.setString(1, deviceType.getDeviceTypeName());
            ps.setString(2, deviceType.getDeviceTypeNotes());
            ps.setInt(3, deviceType.getDeviceTypeID());
           

            ps.executeUpdate();
			ps.close();
			
            System.out.println("Device Type with id " + deviceType.getDeviceTypeID() + " was updated in DB with following details: " + deviceType.toString());
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

	@Override
	public void delete(DeviceType deviceType) {
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(DELETE);
			 
            ps.setInt(1, deviceType.getDeviceTypeID());
            
            ps.executeUpdate();
			ps.close();
			
			System.out.println("Device Type with id: " +  deviceType.getDeviceTypeID() + " was sucesfully deleted from DB.");
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

}
