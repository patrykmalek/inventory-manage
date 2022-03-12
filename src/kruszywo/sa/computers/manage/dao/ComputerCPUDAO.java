package kruszywo.sa.computers.manage.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.exception.SystemOperationException;
import kruszywo.sa.computers.manage.model.ComputerCPU;

public class ComputerCPUDAO implements DAO<ComputerCPU>{

	private List<ComputerCPU> computerCpus;
	private Controller controller;
	
	private static final String FIND_BY_ID = "SELECT * FROM COMPUTER_CPU WHERE ID_COMPUTER_CPU=?;";
	private static final String FIND_BY_NAME = "SELECT * FROM COMPUTER_CPU WHERE COMPUTER_CPU_NAME LIKE ? LIMIT 1;";
    private static final String FIND_ALL = "SELECT * FROM COMPUTER_CPU;";
    private static final String INSERT = "INSERT INTO COMPUTER_CPU (COMPUTER_CPU_NAME, COMPUTER_CPU_CORES_NUMBER, COMPUTER_CPU_CLOCK_SPEED) VALUES(?, ?, ?);";
    private static final String UPDATE = "UPDATE COMPUTER_CPU SET COMPUTER_CPU_NAME=?, COMPUTER_CPU_CORES_NUMBER=?, COMPUTER_CPU_CLOCK_SPEED=? WHERE ID_COMPUTER_CPU=?;";
    private static final String DELETE = "DELETE FROM COMPUTER_CPU WHERE ID_COMPUTER_CPU=?;";
	
	public ComputerCPUDAO(Controller controller) {
		this.controller = controller;
		this.controller.getManagerDAO().setComputerCPUDAO(this);
	}
	
	@Override
	public ComputerCPU get(int computerCpuID) {
		ComputerCPU computerCpu = null;
     	try {
	     	 PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_ID);
	     	 ps.setInt(1, computerCpuID);
	     	 
	   		 controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
	   		 ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
   		 
			 if(resultSet.next()) {
					
			 	computerCpu = new ComputerCPU();
	
			 	computerCpu.setComputerCpuID(resultSet.getInt("ID_COMPUTER_CPU"));
			 	computerCpu.setComputerCpuName(resultSet.getString("COMPUTER_CPU_NAME"));
			 	computerCpu.setComputerCpuCoresNumber(resultSet.getInt("COMPUTER_CPU_CORES_NUMBER"));
			 	computerCpu.setComputerCpuClockSpeed(resultSet.getInt("COMPUTER_CPU_CLOCK_SPEED"));
			 	
	
			 }
		  ps.close();
		  resultSet.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
		}
      return computerCpu;
	}
	
	public ComputerCPU get(String computerCpuName) {
		ComputerCPU computerCpu = null;
     	try {
	     	 PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_NAME);
	     	 ps.setString(1, "%" + computerCpuName + "%");
	   		 controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
	   		 ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
   		 
			 if(resultSet.next()) {
					
			 	computerCpu = new ComputerCPU();
	
			 	computerCpu.setComputerCpuID(resultSet.getInt("ID_COMPUTER_CPU"));
			 	computerCpu.setComputerCpuName(resultSet.getString("COMPUTER_CPU_NAME"));
			 	computerCpu.setComputerCpuCoresNumber(resultSet.getInt("COMPUTER_CPU_CORES_NUMBER"));
			 	computerCpu.setComputerCpuClockSpeed(resultSet.getInt("COMPUTER_CPU_CLOCK_SPEED"));
			 	
			 }
		  ps.close();
		  resultSet.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
		}
      return computerCpu;
	}

	@Override
	public List<ComputerCPU> getAll() {
		 computerCpus = new ArrayList<ComputerCPU>();
	 
         	try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_ALL);
				controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
				ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
				ComputerCPU computerCpu = null;
			 while (resultSet.next()) {
					
				 	computerCpu = new ComputerCPU();
				 	
				 	computerCpu.setComputerCpuID(resultSet.getInt("ID_COMPUTER_CPU"));
				 	computerCpu.setComputerCpuName(resultSet.getString("COMPUTER_CPU_NAME"));
				 	computerCpu.setComputerCpuCoresNumber(resultSet.getInt("COMPUTER_CPU_CORES_NUMBER"));
				 	computerCpu.setComputerCpuClockSpeed(resultSet.getInt("COMPUTER_CPU_CLOCK_SPEED"));

				 	computerCpus.add(computerCpu);
				}
			  ps.close();
			  resultSet.close();
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
			}
	      return computerCpus;
	}

	@Override
	public void insert(ComputerCPU computerCpu) {
			try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(INSERT);
				 
	            ps.setString(1, computerCpu.getComputerCpuName());
	            ps.setInt(2, computerCpu.getComputerCpuCoresNumber());
	            ps.setInt(3, computerCpu.getComputerCpuClockSpeed());
	            
				controller.getDatabaseProvider().executePreparedStatement(ps);
				
	            System.out.println("ComputerCPU with following details was saved in DB: " + computerCpu.toString());
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas zapisywania danych urządzenia do bazy.", e);
			}
	}

	@Override
	public void update(ComputerCPU computerCpu) {
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(UPDATE);
			 
			 ps.setString(1, computerCpu.getComputerCpuName());
	         ps.setInt(2, computerCpu.getComputerCpuCoresNumber());
	         ps.setInt(3, computerCpu.getComputerCpuClockSpeed());
             ps.setInt(4, computerCpu.getComputerCpuID());
           

			controller.getDatabaseProvider().executePreparedStatement(ps);
			
            System.out.println("ComputerCPU with id " + computerCpu.getComputerCpuID() + " was updated in DB with following details: " + computerCpu.toString());
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

	@Override
	public void delete(ComputerCPU computerCpu) {
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(DELETE);
			 
            ps.setInt(1, computerCpu.getComputerCpuID());
            
			controller.getDatabaseProvider().executePreparedStatement(ps);
			
			System.out.println("ComputerCPU with id: " +  computerCpu.getComputerCpuID() + " was sucesfully deleted from DB.");
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

}
