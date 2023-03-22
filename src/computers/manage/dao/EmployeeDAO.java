package computers.manage.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import computers.manage.controller.Controller;
import computers.manage.exception.SystemOperationException;
import computers.manage.model.Employee;

public class EmployeeDAO implements DAO<Employee>{

	private List<Employee> employees;
	private Controller controller;
	
	private static final String FIND_BY_ID = "SELECT * FROM EMPLOYEE WHERE ID_EMPLOYEE=?;";
	private static final String FIND_BY_NAME = "SELECT * FROM EMPLOYEE WHERE EMPLOYEE_FIRST_NAME || EMPLOYEE_LAST_NAME LIKE ?";
    private static final String FIND_ALL = "SELECT * FROM EMPLOYEE;";
    private static final String INSERT = "INSERT INTO EMPLOYEE (EMPLOYEE_FIRST_NAME, EMPLOYEE_LAST_NAME) VALUES(?,?);";
    private static final String UPDATE = "UPDATE EMPLOYEE SET EMPLOYEE_FIRST_NAME=?, EMPLOYEE_LAST_NAME=? WHERE ID_EMPLOYEE=?;";
    private static final String DELETE = "DELETE FROM EMPLOYEE WHERE ID_EMPLOYEE=?;";
	
	public EmployeeDAO(Controller controller) {
		this.controller = controller;
		this.controller.getManagerDAO().setEmployeeDAO(this);
	}
	
	@Override
	public Employee get(int employeeID) {
		Employee employee = null;
     	try {
	     	 PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_ID);
	     	 ps.setInt(1, employeeID);
	     	 
	   		 controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
	   		 ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
   		 
			 if(resultSet.next()) {
					
				 employee = new Employee();
	
				 employee.setEmployeeID(resultSet.getInt("id_employee"));
				 employee.setFirstName(resultSet.getString("employee_first_name"));
				 employee.setLastName(resultSet.getString("employee_last_name"));
	
			 }
		  ps.close();
		  resultSet.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
		}
      return employee;
	}
	
	@Override
	public Employee get(String employeeTextToFind) {
		Employee employee = null;
     	try {
	     	 PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_NAME);
	     	 ps.setString(1, "%" + employeeTextToFind + "%");
	     	 
	   		 controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
	   		 ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
   		 
			 if(resultSet.next()) {
					
				 employee = new Employee();
	
				 employee.setEmployeeID(resultSet.getInt("id_employee"));
				 employee.setFirstName(resultSet.getString("employee_first_name"));
				 employee.setLastName(resultSet.getString("employee_last_name"));
	
			 }
		  ps.close();
		  resultSet.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
		}
      return employee;
	}

	@Override
	public List<Employee> getAll() {
		 employees = new ArrayList<Employee>();
	 
         	try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_ALL);
				controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
				ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
				Employee employee = null;
			 while (resultSet.next()) {
					
				 	employee = new Employee();
				 	
				 	employee.setEmployeeID(resultSet.getInt("id_employee"));
					employee.setFirstName(resultSet.getString("employee_first_name"));
					employee.setLastName(resultSet.getString("employee_last_name"));
					 
					employees.add(employee);
				}
			  ps.close();
			  resultSet.close();
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
			}
	      return employees;
	}

	@Override
	public void insert(Employee employee) {
			try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(INSERT);
				 
	            ps.setString(1, employee.getFirstName());
	            ps.setString(2, employee.getLastName());
	            
	            ps.executeUpdate();
				ps.close();
				
	            System.out.println("Employee Type with following details was saved in DB: " + employee.toString());
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas zapisywania danych urządzenia do bazy.", e);
			}
	}

	@Override
	public void update(Employee employee) {
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(UPDATE);
			 
			ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setInt(3, employee.getEmployeeID());

            ps.executeUpdate();
			ps.close();
			
            System.out.println("Employee Type with id " + employee.getEmployeeID() + " was updated in DB with following details: " + employee.toString());
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

	@Override
	public void delete(Employee employee) {
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(DELETE);
			 
            ps.setInt(1, employee.getEmployeeID());
            
            ps.executeUpdate();
			ps.close();
			
			System.out.println("Employee Type with id: " +  employee.getEmployeeID() + " was sucesfully deleted from DB.");
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

}
