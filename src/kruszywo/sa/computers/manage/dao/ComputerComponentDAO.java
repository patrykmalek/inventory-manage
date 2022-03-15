package kruszywo.sa.computers.manage.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.exception.SystemOperationException;
import kruszywo.sa.computers.manage.model.ComputerCPU;
import kruszywo.sa.computers.manage.model.ComputerComponent;
import kruszywo.sa.computers.manage.model.ComputerMassStorage;
import kruszywo.sa.computers.manage.model.ComputerRAM;
import kruszywo.sa.computers.manage.model.Device;

public class ComputerComponentDAO implements DAO<ComputerComponent>{

	private List<ComputerComponent> computerComponents;
	private Controller controller;
	
	private static final String FIND_BY_ID = "SELECT * FROM COMPUTER_COMPONENTS WHERE ID_COMPUTER_COMPONENTS=?;";
	private static final String FIND_BY_COMPUTER_ID = "SELECT * FROM COMPUTER_COMPONENTS WHERE ID_DEVICE=?;";
    private static final String FIND_ALL = "SELECT * FROM COMPUTER_COMPONENTS;";
    private static final String INSERT = "INSERT INTO COMPUTER_COMPONENTS (ID_DEVICE, ID_COMPUTER_CPU, ID_MEMORY_RAM, ID_MASS_STORAGE_FIRST, ID_MASS_STORAGE_SECOND, ID_MASS_STORAGE_SECOND) VALUES(?, ?, ?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE COMPUTER_COMPONENTS SET ID_DEVICE=?, ID_COMPUTER_CPU=?, ID_MEMORY_RAM=?, ID_MASS_STORAGE_FIRST=?, ID_MASS_STORAGE_SECOND=?, ID_MASS_STORAGE_THIRD=? WHERE ID_COMPUTER_COMPONENTS=?;";
    private static final String DELETE = "DELETE FROM COMPUTER_COMPONENTS WHERE ID_COMPUTER_COMPONENTS=?;";
	
	public ComputerComponentDAO(Controller controller) {
		this.controller = controller;
		this.controller.getManagerDAO().setComputerComponentDAO(this);
	}
	
	@Override
	public ComputerComponent get(int computerComponentID) {
		ComputerComponent computerComponent = null;
		Device device = null;
		ComputerCPU computerCPU = null;
		ComputerRAM computerRAM = null;
		ComputerMassStorage computerMassStorageFirst = null;
		ComputerMassStorage computerMassStorageSecond = null;
		ComputerMassStorage computerMassStorageThird = null;
     	try {
	     	 PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_ID);
	     	 ps.setInt(1, computerComponentID);
	     	
	   		 controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
	   		 ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
   		 
			 if(resultSet.next()) {
					
			 	computerComponent = new ComputerComponent();
			 	device = controller.getManagerDAO().getDeviceDAO().get(resultSet.getInt("ID_DEVICE"));
			 	computerCPU = controller.getManagerDAO().getComputerCPUDAO().get(resultSet.getInt("ID_COMPUTER_CPU"));
			 	computerRAM = controller.getManagerDAO().getComputerRAMDAO().get(resultSet.getInt("ID_MEMORY_RAM"));
			 	computerMassStorageFirst = controller.getManagerDAO().getComputerMassStorageDAO().get(resultSet.getInt("ID_MASS_STORAGE_FIRST"));
			 	computerMassStorageSecond = controller.getManagerDAO().getComputerMassStorageDAO().get(resultSet.getInt("ID_MASS_STORAGE_SECOND"));
			 	computerMassStorageThird = controller.getManagerDAO().getComputerMassStorageDAO().get(resultSet.getInt("ID_MASS_STORAGE_THIRD"));
			 	
			 	computerComponent.setComputerComponentID(resultSet.getInt("ID_COMPUTER_COMPONENTS"));
			 	computerComponent.setDevice(device);
			 	computerComponent.setComputerCPU(computerCPU);
			 	computerComponent.setComputerRAM(computerRAM);
			 	computerComponent.setComputerMassStorageFirst(computerMassStorageFirst);
			 	computerComponent.setComputerMassStorageSecond(computerMassStorageSecond);
			 	computerComponent.setComputerMassStorageThird(computerMassStorageThird);
			 }
		  ps.close();
		  resultSet.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu komponentów komputera z bazy", e);
		}
      return computerComponent;
	}
	
	public ComputerComponent getByComputerID(int computerID) {
		ComputerComponent computerComponent = null;
		Device device = null;
		ComputerCPU computerCPU = null;
		ComputerRAM computerRAM = null;
		ComputerMassStorage computerMassStorageFirst = null;
		ComputerMassStorage computerMassStorageSecond = null;
		ComputerMassStorage computerMassStorageThird = null;
     	try {
	     	 PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_COMPUTER_ID);
	     	 ps.setInt(1, computerID);
	     	
	   		 controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
	   		 ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
   		 
			 if(resultSet.next()) {
					
			 	computerComponent = new ComputerComponent();
			 	device = controller.getManagerDAO().getDeviceDAO().get(resultSet.getInt("ID_DEVICE"));
			 	computerCPU = controller.getManagerDAO().getComputerCPUDAO().get(resultSet.getInt("ID_COMPUTER_CPU"));
			 	computerRAM = controller.getManagerDAO().getComputerRAMDAO().get(resultSet.getInt("ID_MEMORY_RAM"));
			 	computerMassStorageFirst = controller.getManagerDAO().getComputerMassStorageDAO().get(resultSet.getInt("ID_MASS_STORAGE_FIRST"));
			 	computerMassStorageSecond = controller.getManagerDAO().getComputerMassStorageDAO().get(resultSet.getInt("ID_MASS_STORAGE_SECOND"));
			 	computerMassStorageThird = controller.getManagerDAO().getComputerMassStorageDAO().get(resultSet.getInt("ID_MASS_STORAGE_THIRD"));
			 	
			 	computerComponent.setComputerComponentID(resultSet.getInt("ID_COMPUTER_COMPONENTS"));
			 	computerComponent.setDevice(device);
			 	computerComponent.setComputerCPU(computerCPU);
			 	computerComponent.setComputerRAM(computerRAM);
			 	computerComponent.setComputerMassStorageFirst(computerMassStorageFirst);
			 	computerComponent.setComputerMassStorageSecond(computerMassStorageSecond);
			 	computerComponent.setComputerMassStorageThird(computerMassStorageThird);
			 }
		  ps.close();
		  resultSet.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu komponentów komputera z bazy", e);
		}
      return computerComponent;
	}

	@Override
	public List<ComputerComponent> getAll() {
		 computerComponents = new ArrayList<ComputerComponent>();
	 
         	try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_ALL);
				controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
				ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
				ComputerComponent computerComponent = null;
				Device device = null;
				ComputerCPU computerCPU = null;
				ComputerRAM computerRAM = null;
				ComputerMassStorage computerMassStorageFirst = null;
				ComputerMassStorage computerMassStorageSecond = null;
				ComputerMassStorage computerMassStorageThird = null;
			 while (resultSet.next()) {
					
				 	computerComponent = new ComputerComponent();
				 	device = controller.getManagerDAO().getDeviceDAO().get(resultSet.getInt("ID_DEVICE"));
				 	computerCPU = controller.getManagerDAO().getComputerCPUDAO().get(resultSet.getInt("ID_COMPUTER_CPU"));
				 	computerRAM = controller.getManagerDAO().getComputerRAMDAO().get(resultSet.getInt("ID_MEMORY_RAM"));
				 	computerMassStorageFirst = controller.getManagerDAO().getComputerMassStorageDAO().get(resultSet.getInt("ID_MASS_STORAGE_FIRST"));
				 	computerMassStorageSecond = controller.getManagerDAO().getComputerMassStorageDAO().get(resultSet.getInt("ID_MASS_STORAGE_SECOND"));
				 	computerMassStorageThird = controller.getManagerDAO().getComputerMassStorageDAO().get(resultSet.getInt("ID_MASS_STORAGE_THIRD"));
				 	
				 	computerComponent.setComputerComponentID(resultSet.getInt("ID_COMPUTER_COMPONENTS"));
				 	computerComponent.setDevice(device);
				 	computerComponent.setComputerCPU(computerCPU);
				 	computerComponent.setComputerRAM(computerRAM);
				 	computerComponent.setComputerMassStorageFirst(computerMassStorageFirst);
				 	computerComponent.setComputerMassStorageSecond(computerMassStorageSecond);
				 	computerComponent.setComputerMassStorageThird(computerMassStorageThird);
				 	computerComponents.add(computerComponent);
				}
			  ps.close();
			  resultSet.close();
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
			}
	      return computerComponents;
	}

	@Override
	public void insert(ComputerComponent computerComponent) {
			try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(INSERT);
	            ps.setInt(1, computerComponent.getDevice().getDeviceID());
	            ps.setObject(2, (computerComponent.getComputerCPU().getComputerCpuID() == 0) ? null :computerComponent.getComputerCPU().getComputerCpuID());
	            ps.setObject(3, (computerComponent.getComputerRAM().getMemoryRamID() == 0) ? null : computerComponent.getComputerRAM().getMemoryRamID());
	            ps.setObject(4, (computerComponent.getComputerMassStorageFirst().getMassStorageID() == 0) ? null : computerComponent.getComputerMassStorageFirst().getMassStorageID());
	            ps.setObject(5, (computerComponent.getComputerMassStorageSecond().getMassStorageID() == 0) ? null : computerComponent.getComputerMassStorageSecond().getMassStorageID());
	            ps.setObject(6, (computerComponent.getComputerMassStorageThird().getMassStorageID() == 0) ? null : computerComponent.getComputerMassStorageThird().getMassStorageID());
	            
				controller.getDatabaseProvider().executePreparedStatement(ps);
				
	            System.out.println("ComputerComponent with following details was saved in DB: " + computerComponent.toString());
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas zapisywania danych urządzenia do bazy.", e);
			}
	}

	@Override
	public void update(ComputerComponent computerComponent) {
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(UPDATE);
			 
			ps.setInt(1, computerComponent.getDevice().getDeviceID());
			ps.setObject(2, (computerComponent.getComputerCPU().getComputerCpuID() == 0) ? null :computerComponent.getComputerCPU().getComputerCpuID());
            ps.setObject(3, (computerComponent.getComputerRAM().getMemoryRamID() == 0) ? null : computerComponent.getComputerRAM().getMemoryRamID());
            ps.setObject(4, (computerComponent.getComputerMassStorageFirst().getMassStorageID() == 0) ? null : computerComponent.getComputerMassStorageFirst().getMassStorageID());
            ps.setObject(5, (computerComponent.getComputerMassStorageSecond().getMassStorageID() == 0) ? null : computerComponent.getComputerMassStorageSecond().getMassStorageID());
            ps.setObject(6, (computerComponent.getComputerMassStorageThird().getMassStorageID() == 0) ? null : computerComponent.getComputerMassStorageThird().getMassStorageID());
            ps.setInt(7, computerComponent.getComputerComponentID());

			controller.getDatabaseProvider().executePreparedStatement(ps);
			
            System.out.println("ComputerComponent with id " + computerComponent.getComputerComponentID() + " was updated in DB with following details: " + computerComponent.toString());
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

	@Override
	public void delete(ComputerComponent computerComponent) {
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(DELETE);
			 
            ps.setInt(1, computerComponent.getComputerComponentID());
            
			controller.getDatabaseProvider().executePreparedStatement(ps);
			
			System.out.println("ComputerComponent with id: " +  computerComponent.getComputerComponentID() + " was sucesfully deleted from DB.");
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

	@Override
	public ComputerComponent get(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
