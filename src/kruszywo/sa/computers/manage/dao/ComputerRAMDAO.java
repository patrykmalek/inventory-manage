package kruszywo.sa.computers.manage.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.exception.SystemOperationException;
import kruszywo.sa.computers.manage.model.ComputerRAM;

public class ComputerRAMDAO implements DAO<ComputerRAM>{

	private List<ComputerRAM> computerRAMs;
	private Controller controller;
	
	private static final String FIND_BY_ID = "SELECT MR.ID_MEMORY_RAM, MR.MEMORY_RAM_TYPE, MR.MEMORY_RAM_CAPACITY, CC.ID_COMPUTER_COMPONENTS FROM MEMORY_RAM MR LEFT JOIN COMPUTER_COMPONENTS CC ON (CC.ID_MEMORY_RAM = MR.ID_MEMORY_RAM) WHERE MR.ID_MEMORY_RAM=?;";
    private static final String FIND_ALL = "SELECT MR.ID_MEMORY_RAM, MR.MEMORY_RAM_TYPE, MR.MEMORY_RAM_CAPACITY, CC.ID_COMPUTER_COMPONENTS FROM MEMORY_RAM MR LEFT JOIN COMPUTER_COMPONENTS CC ON (CC.ID_MEMORY_RAM = MR.ID_MEMORY_RAM);";
    private static final String INSERT = "INSERT INTO MEMORY_RAM (MEMORY_RAM_TYPE, MEMORY_RAM_CAPACITY) VALUES(?, ?);";
    private static final String UPDATE = "UPDATE MEMORY_RAM SET MEMORY_RAM_TYPE=?, MEMORY_RAM_CAPACITY=? WHERE ID_MEMORY_RAM=?;";
    private static final String DELETE = "DELETE FROM MEMORY_RAM WHERE ID_MEMORY_RAM=?;";
	
	public ComputerRAMDAO(Controller controller) {
		this.controller = controller;
		this.controller.getManagerDAO().setComputerRAMDAO(this);
	}
	
	public List<String> getMemoryRamTypes() {
		
		 List<String> memoryRamTypes = new ArrayList<>();
		 
		 memoryRamTypes.add("DDR2");
		 memoryRamTypes.add("DDR3");
		 memoryRamTypes.add("DDR4");
		 memoryRamTypes.add("Element Nieznany");
		 
		return memoryRamTypes;
	}
	
	@Override
	public ComputerRAM get(int computerRAMID) {
		ComputerRAM computerRAM = null;
     	try {
	     	 PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_ID);
	     	 ps.setInt(1, computerRAMID);
	     	 
	   		 controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
	   		 ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
   		 
			 if(resultSet.next()) {
					
			 	computerRAM = new ComputerRAM();
	
			 	computerRAM.setMemoryRamID(resultSet.getInt("ID_MEMORY_RAM"));
			 	computerRAM.setMemoryRamType(resultSet.getString("MEMORY_RAM_TYPE"));
			 	computerRAM.setMemoryRamCapacityMB(resultSet.getInt("MEMORY_RAM_CAPACITY"));
			 	computerRAM.setUsed((resultSet.getObject("ID_COMPUTER_COMPONENTS") != null));
	
			 }
		  ps.close();
		  resultSet.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
		}
      return computerRAM;
	}


	@Override
	public List<ComputerRAM> getAll() {
		 computerRAMs = new ArrayList<ComputerRAM>();
	 
         	try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_ALL);
				controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
				ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
				ComputerRAM computerRAM = null;
			 while (resultSet.next()) {
					
				 	computerRAM = new ComputerRAM();
				 	
				 	computerRAM.setMemoryRamID(resultSet.getInt("ID_MEMORY_RAM"));
				 	computerRAM.setMemoryRamType(resultSet.getString("MEMORY_RAM_TYPE"));
				 	computerRAM.setMemoryRamCapacityMB(resultSet.getInt("MEMORY_RAM_CAPACITY"));
				 	computerRAM.setUsed((resultSet.getObject("ID_COMPUTER_COMPONENTS") != null));
				 	
				 	computerRAMs.add(computerRAM);
				}
			  ps.close();
			  resultSet.close();
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
			}
	      return computerRAMs;
	}

	@Override
	public void insert(ComputerRAM computerRAM) {
			try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(INSERT);
				 
	            ps.setString(1, computerRAM.getMemoryRamType());
	            ps.setInt(2, computerRAM.getMemoryRamCapacityMB());
	            
	            ps.executeUpdate();
				ps.close();
				
	            System.out.println("ComputerRAM with following details was saved in DB: " + computerRAM.toString());
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas zapisywania danych urządzenia do bazy.", e);
			}
	}

	@Override
	public void update(ComputerRAM computerRAM) {

		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(UPDATE);
			
			
			ps.setString(1, computerRAM.getMemoryRamType());
            ps.setInt(2, computerRAM.getMemoryRamCapacityMB());
            ps.setInt(3, computerRAM.getMemoryRamID());
           

            ps.executeUpdate();
			ps.close();
			
            System.out.println("ComputerRAM with id " + computerRAM.getMemoryRamID() + " was updated in DB with following details: " + computerRAM.toString());
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

	@Override
	public void delete(ComputerRAM computerRAM) {
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(DELETE);
			 
            ps.setInt(1, computerRAM.getMemoryRamID());
            
            ps.executeUpdate();
			ps.close();
			
			System.out.println("ComputerRAM with id: " +  computerRAM.getMemoryRamID() + " was sucesfully deleted from DB.");
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

	@Override
	public ComputerRAM get(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
