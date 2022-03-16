package kruszywo.sa.computers.manage.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.exception.SystemOperationException;
import kruszywo.sa.computers.manage.model.ComputerMassStorage;

public class ComputerMassStorageDAO implements DAO<ComputerMassStorage>{

	private List<ComputerMassStorage> computerMassStorages;
	private Controller controller;
	
	private static final String FIND_BY_ID = "SELECT MS.ID_MASS_STORAGE, MS.MASS_STORAGE_CAPACITY, MS.MASS_STORAGE_NAME, MS.MASS_STORAGE_SERIAL_NUMBER, MS.MASS_STORAGE_TYPE, CC.ID_COMPUTER_COMPONENTS FROM MASS_STORAGE MS LEFT JOIN COMPUTER_COMPONENTS CC ON (CC.ID_MASS_STORAGE_FIRST = MS.ID_MASS_STORAGE OR CC.ID_MASS_STORAGE_SECOND = MS.ID_MASS_STORAGE OR CC.ID_MASS_STORAGE_THIRD = MS.ID_MASS_STORAGE) WHERE MS.ID_MASS_STORAGE=?;";
	private static final String FIND_BY_NAME = "SELECT MS.ID_MASS_STORAGE, MS.MASS_STORAGE_CAPACITY, MS.MASS_STORAGE_NAME, MS.MASS_STORAGE_SERIAL_NUMBER, MS.MASS_STORAGE_TYPE, CC.ID_COMPUTER_COMPONENTS FROM MASS_STORAGE MS LEFT JOIN COMPUTER_COMPONENTS CC ON (CC.ID_MASS_STORAGE_FIRST = MS.ID_MASS_STORAGE OR CC.ID_MASS_STORAGE_SECOND = MS.ID_MASS_STORAGE OR CC.ID_MASS_STORAGE_THIRD = MS.ID_MASS_STORAGE) WHERE MS.MASS_STORAGE_NAME LIKE ? LIMIT 1;";
    private static final String FIND_ALL = "SELECT MS.ID_MASS_STORAGE, MS.MASS_STORAGE_CAPACITY, MS.MASS_STORAGE_NAME, MS.MASS_STORAGE_SERIAL_NUMBER, MS.MASS_STORAGE_TYPE, CC.ID_COMPUTER_COMPONENTS FROM MASS_STORAGE MS LEFT JOIN COMPUTER_COMPONENTS CC ON (CC.ID_MASS_STORAGE_FIRST = MS.ID_MASS_STORAGE OR CC.ID_MASS_STORAGE_SECOND = MS.ID_MASS_STORAGE OR CC.ID_MASS_STORAGE_THIRD = MS.ID_MASS_STORAGE);";
    private static final String INSERT = "INSERT INTO MASS_STORAGE (MASS_STORAGE_NAME, MASS_STORAGE_SERIAL_NUMBER, MASS_STORAGE_TYPE, MASS_STORAGE_CAPACITY) VALUES(?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE MASS_STORAGE SET MASS_STORAGE_NAME=?, MASS_STORAGE_SERIAL_NUMBER=?, MASS_STORAGE_TYPE=?, MASS_STORAGE_CAPACITY=? WHERE ID_MASS_STORAGE=?;";
    private static final String DELETE = "DELETE FROM MASS_STORAGE WHERE ID_MASS_STORAGE=?;";
	
	public ComputerMassStorageDAO(Controller controller) {
		this.controller = controller;
		this.controller.getManagerDAO().setComputerMassStorageDAO(this);
	}
	
	public List<String> getMassStorageTypes() {
		
		 List<String> massStorageType = new ArrayList<>();
		 
		 massStorageType.add("Dysk fizyczny SSD");
		 massStorageType.add("Dysk fizyczny HDD");
		 massStorageType.add("Napęd CD-ROM");
		 massStorageType.add("Dysk wirtualny");
		 
		return massStorageType;
	}
	
	@Override
	public ComputerMassStorage get(int computerMassStorageID) {
		ComputerMassStorage computerMassStorage = null;
     	try {
	     	 PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_ID);
	     	 ps.setInt(1, computerMassStorageID);
	     	 
	   		 controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
	   		 ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
   		 
			 if(resultSet.next()) {
					
			 	computerMassStorage = new ComputerMassStorage();
	
			 	computerMassStorage.setMassStorageID(resultSet.getInt("ID_MASS_STORAGE"));
			 	computerMassStorage.setMassStorageName(resultSet.getString("MASS_STORAGE_NAME"));
			 	computerMassStorage.setMassStorageSerialNumber(resultSet.getString("MASS_STORAGE_SERIAL_NUMBER"));
			 	computerMassStorage.setMassStorageType(resultSet.getString("MASS_STORAGE_TYPE"));
			 	computerMassStorage.setMassStorageCapacityMB(resultSet.getInt("MASS_STORAGE_CAPACITY"));
			 	computerMassStorage.setUsed((resultSet.getObject("ID_COMPUTER_COMPONENTS") != null));
			 }
		  ps.close();
		  resultSet.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
		}
      return computerMassStorage;
	}
	
	public ComputerMassStorage get(String computerMassStorageName) {
		ComputerMassStorage computerMassStorage = null;
     	try {
	     	 PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_NAME);
	     	 ps.setString(1, "%" + computerMassStorageName + "%");
	   		 controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
	   		 ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
   		 
			 if(resultSet.next()) {
					
			 	computerMassStorage = new ComputerMassStorage();
	
			 	computerMassStorage.setMassStorageID(resultSet.getInt("ID_MASS_STORAGE"));
			 	computerMassStorage.setMassStorageName(resultSet.getString("MASS_STORAGE_NAME"));
			 	computerMassStorage.setMassStorageSerialNumber(resultSet.getString("MASS_STORAGE_SERIAL_NUMBER"));
			 	computerMassStorage.setMassStorageType(resultSet.getString("MASS_STORAGE_TYPE"));
			 	computerMassStorage.setMassStorageCapacityMB(resultSet.getInt("MASS_STORAGE_CAPACITY"));
			 	computerMassStorage.setUsed((resultSet.getObject("ID_COMPUTER_COMPONENTS") != null));
			 }
		  ps.close();
		  resultSet.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
		}
      return computerMassStorage;
	}

	@Override
	public List<ComputerMassStorage> getAll() {
		 computerMassStorages = new ArrayList<ComputerMassStorage>();
	 
         	try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_ALL);
				controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
				ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
				ComputerMassStorage computerMassStorage = null;
			 while (resultSet.next()) {
					
				 	computerMassStorage = new ComputerMassStorage();
				 	
				 	computerMassStorage.setMassStorageID(resultSet.getInt("ID_MASS_STORAGE"));
				 	computerMassStorage.setMassStorageName(resultSet.getString("MASS_STORAGE_NAME"));
				 	computerMassStorage.setMassStorageSerialNumber(resultSet.getString("MASS_STORAGE_SERIAL_NUMBER"));
				 	computerMassStorage.setMassStorageType(resultSet.getString("MASS_STORAGE_TYPE"));
				 	computerMassStorage.setMassStorageCapacityMB(resultSet.getInt("MASS_STORAGE_CAPACITY"));
				 	computerMassStorage.setUsed((resultSet.getObject("ID_COMPUTER_COMPONENTS") != null));
				 	
				 	computerMassStorages.add(computerMassStorage);
				}
			  ps.close();
			  resultSet.close();
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
			}
	      return computerMassStorages;
	}

	@Override
	public void insert(ComputerMassStorage computerMassStorage) {
			try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(INSERT);
				 
	            ps.setString(1, computerMassStorage.getMassStorageName());
	            ps.setString(2, computerMassStorage.getMassStorageSerialNumber());
	            ps.setString(3, computerMassStorage.getMassStorageType());
	            ps.setInt(4, computerMassStorage.getMassStorageCapacityMB());
	            
				controller.getDatabaseProvider().executePreparedStatement(ps);
				
	            System.out.println("ComputerMassStorage with following details was saved in DB: " + computerMassStorage.toString());
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas zapisywania danych urządzenia do bazy.", e);
			}
	}

	@Override
	public void update(ComputerMassStorage computerMassStorage) {
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(UPDATE);
			 
			ps.setString(1, computerMassStorage.getMassStorageName());
            ps.setString(2, computerMassStorage.getMassStorageSerialNumber());
            ps.setString(3, computerMassStorage.getMassStorageType());
            ps.setInt(4, computerMassStorage.getMassStorageCapacityMB());
            ps.setInt(5, computerMassStorage.getMassStorageID());
           

			controller.getDatabaseProvider().executePreparedStatement(ps);
			
            System.out.println("ComputerMassStorage with id " + computerMassStorage.getMassStorageID() + " was updated in DB with following details: " + computerMassStorage.toString());
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

	@Override
	public void delete(ComputerMassStorage computerMassStorage) {
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(DELETE);
			 
            ps.setInt(1, computerMassStorage.getMassStorageID());
            
			controller.getDatabaseProvider().executePreparedStatement(ps);
			
			System.out.println("ComputerMassStorage with id: " +  computerMassStorage.getMassStorageID() + " was sucesfully deleted from DB.");
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

}
