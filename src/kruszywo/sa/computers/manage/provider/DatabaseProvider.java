package kruszywo.sa.computers.manage.provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import kruszywo.sa.computers.manage.exception.SystemOperationException;

public class DatabaseProvider {

	private Connection databaseConnection;
	private String url;
	private ResultSet resultSet;
	
	public DatabaseProvider(String url) {
		this.setUrl(url);
	}
	
	 public void connect() {
		 databaseConnection = null;
	        try {
	            databaseConnection = DriverManager.getConnection(getUrl());       
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	 }
	 
	public void close() {
		try {
			if(this.databaseConnection != null) {
				this.databaseConnection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void execute(String query) {
		
		try {
			Statement statement = this.databaseConnection.createStatement();
			statement.execute(query);
			statement.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas zapisu do bazy danych.", e);
		}
	}
		
	public void executeQuery(String query) {
			
		try {
			Statement statement = this.databaseConnection.createStatement();
			this.resultSet = null;
			this.resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu danych z bazy danych.", e);
		}
	}
	
	public void executePreparedStatement(PreparedStatement preparedStatement) {
		try {
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas zapisu do bazy danych.", e);
		}
	}
	
	public void executePreparedStatementWithResult(PreparedStatement preparedStatement) {
		try {
			this.resultSet = null;
			this.resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas zapisu do bazy danych.", e);
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Connection getDatabaseConnection() {
		return databaseConnection;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}
}
