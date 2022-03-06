package kruszywo.sa.computers.manage.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.exception.SystemOperationException;
import kruszywo.sa.computers.manage.model.Software;

public class SoftwareDAO implements DAO<Software>{

	private List<Software> softwares;
	private Controller controller;
	
	private static final String FIND_BY_ID = "SELECT * FROM SOFTWARE WHERE ID_SOFTWARE=?;";
	private static final String FIND_BY_NAME = "SELECT * FROM SOFTWARE WHERE SOFTWARE_NAME LIKE ? LIMIT 1;";
    private static final String FIND_ALL = "SELECT * FROM SOFTWARE;";
    private static final String INSERT = "INSERT INTO SOFTWARE (SOFTWARE_NAME, SOFTWARE_NOTES) VALUES(?,?);";
    private static final String UPDATE = "UPDATE SOFTWARE SET SOFTWARE_NAME=?, SOFTWARE_NOTES=? WHERE ID_SOFTWARE=?;";
    private static final String DELETE = "DELETE FROM SOFTWARE WHERE ID_SOFTWARE=?;";
	
	public SoftwareDAO(Controller controller) {
		this.controller = controller;
		this.controller.getManagerDAO().setSoftwareDAO(this);
	}
	
	@Override
	public Software get(int softwareID) {
		Software software = null;
     	try {
	     	 PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_ID);
	     	 ps.setInt(1, softwareID);
	     	 
	   		 controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
	   		 ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
   		 
			 if(resultSet.next()) {
					
			 	software = new Software();
	
			 	software.setSoftwareID(resultSet.getInt("id_software"));
			 	software.setSoftwareName(resultSet.getString("software_name"));
			 	software.setSoftwareNotes(resultSet.getString("software_notes"));
	
			 }
		  ps.close();
		  resultSet.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
		}
      return software;
	}
	
	public Software get(String softwareName) {
		Software software = null;
     	try {
	     	 PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_NAME);
	     	 ps.setString(1, "%" + softwareName + "%");
	   		 controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
	   		 ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
   		 
			 if(resultSet.next()) {
					
			 	software = new Software();
	
			 	software.setSoftwareID(resultSet.getInt("id_software"));
			 	software.setSoftwareName(resultSet.getString("software_name"));
			 	software.setSoftwareNotes(resultSet.getString("software_notes"));
			 	
			 }
		  ps.close();
		  resultSet.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
		}
      return software;
	}

	@Override
	public List<Software> getAll() {
		 softwares = new ArrayList<Software>();
	 
         	try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_ALL);
				controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
				ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
				Software software = null;
			 while (resultSet.next()) {
					
				 	software = new Software();
				 	
				 	software.setSoftwareID(resultSet.getInt("id_software"));
				 	software.setSoftwareName(resultSet.getString("software_name"));
				 	software.setSoftwareNotes(resultSet.getString("software_notes"));

				 	softwares.add(software);
				}
			  ps.close();
			  resultSet.close();
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
			}
	      return softwares;
	}

	@Override
	public void insert(Software software) {
			try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(INSERT);
				 
	            ps.setString(1, software.getSoftwareName());
	            ps.setString(2, software.getSoftwareNotes());
	            
				controller.getDatabaseProvider().executePreparedStatement(ps);
				
	            System.out.println("Software with following details was saved in DB: " + software.toString());
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas zapisywania danych urządzenia do bazy.", e);
			}
	}

	@Override
	public void update(Software software) {
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(UPDATE);
			 
            ps.setString(1, software.getSoftwareName());
            ps.setString(2, software.getSoftwareNotes());
            ps.setInt(3, software.getSoftwareID());
           

			controller.getDatabaseProvider().executePreparedStatement(ps);
			
            System.out.println("Software with id " + software.getSoftwareID() + " was updated in DB with following details: " + software.toString());
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

	@Override
	public void delete(Software software) {
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(DELETE);
			 
            ps.setInt(1, software.getSoftwareID());
            
			controller.getDatabaseProvider().executePreparedStatement(ps);
			
			System.out.println("Software with id: " +  software.getSoftwareID() + " was sucesfully deleted from DB.");
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

}
