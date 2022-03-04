package kruszywo.sa.computers.manage.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.exception.SystemOperationException;
import kruszywo.sa.computers.manage.model.Department;

public class DepartmentDAO implements DAO<Department>{

	private List<Department> departments;
	private Controller controller;
	
	private static final String FIND_BY_ID = "SELECT * FROM DEPARTMENT WHERE ID_DEPARTMENT=?;";
    private static final String FIND_ALL = "SELECT * FROM DEPARTMENT;";
    private static final String INSERT = "INSERT INTO DEPARTMENT (DEPARTMENT_NAME) VALUES(?);";
    private static final String UPDATE = "UPDATE DEPARTMENT SET DEPARTMENT_NAME=? WHERE ID_DEPARTMENT=?;";
    private static final String DELETE = "DELETE FROM DEPARTMENT WHERE ID_DEPARTMENT=?;";
	
	public DepartmentDAO(Controller controller) {
		this.controller = controller;
		this.controller.getManagerDAO().setDepartmentDAO(this);
	}
	
	@Override
	public Department get(int departmentID) {
		Department department = null;
     	try {
	     	 PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_ID);
	     	 ps.setInt(1, departmentID);
	     	 
	   		 controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
	   		 ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
   		 
			 if(resultSet.next()) {
				 department = new Department();
				 department.setDepartmentID(resultSet.getInt("id_department"));
				 department.setDepartmentName(resultSet.getString("department_name"));
			 }
		    ps.close();
		    resultSet.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
		}
      return department;
	}

	@Override
	public List<Department> getAll() {
		departments = new ArrayList<Department>();
	 
         	try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_ALL);
				controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
				ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
				Department department = null;
			 while (resultSet.next()) {
					 department = new Department();
					 department.setDepartmentID(resultSet.getInt("id_department"));
					 department.setDepartmentName(resultSet.getString("department_name"));
				 	departments.add(department);
				}
			  ps.close();
			  resultSet.close();
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
			}
	      return departments;
	}

	@Override
	public void insert(Department department) {
			try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(INSERT);
				 
	            ps.setString(1, department.getDepartmentName());
				controller.getDatabaseProvider().executePreparedStatement(ps);
				
	            System.out.println("Department with following details was saved in DB: " + department.toString());
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas zapisywania danych urządzenia do bazy.", e);
			}
	}

	@Override
	public void update(Department department) {
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(UPDATE);
			 
            ps.setString(1, department.getDepartmentName());
            ps.setInt(2, department.getDepartmentID());

			controller.getDatabaseProvider().executePreparedStatement(ps);
			
            System.out.println("Department with id " + department.getDepartmentID() + " was updated in DB with following details: " + department.toString());
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

	@Override
	public void delete(Department department) {
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(DELETE);
			 
            ps.setInt(1, department.getDepartmentID());
            
			controller.getDatabaseProvider().executePreparedStatement(ps);
			
			System.out.println("Department with id: " +  department.getDepartmentID() + " was sucesfully deleted from DB.");
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

}
