package inventory.manage.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import inventory.manage.controller.Controller;
import inventory.manage.exception.SystemOperationException;
import inventory.manage.model.Device;
import inventory.manage.model.License;
import inventory.manage.model.Software;

public class LicenseDAO implements DAO<License>{

	private List<License> licenses;
	private Controller controller;
	
	private static final String FIND_BY_ID = "SELECT * FROM LICENSES L LEFT JOIN SOFTWARE S ON (S.ID_SOFTWARE = L.ID_ASSIGNED_SOFTWARE) WHERE L.ID_LICENSE=?;";
	private static final String FIND_BY_NAME = "SELECT * FROM LICENSES L LEFT JOIN SOFTWARE S ON (S.ID_SOFTWARE = L.ID_ASSIGNED_SOFTWARE) WHERE L.LICENSE_NOTES LIKE ?;";
	private static final String FIND_ALL_BY_DEVICE = "SELECT * FROM LICENSES L LEFT JOIN SOFTWARE S ON (S.ID_SOFTWARE = L.ID_ASSIGNED_SOFTWARE) WHERE L.ID_DEVICE=?";
    private static final String FIND_ALL = "SELECT * FROM LICENSES L LEFT JOIN SOFTWARE S ON (S.ID_SOFTWARE = L.ID_ASSIGNED_SOFTWARE);";
    private static final String INSERT = "INSERT INTO LICENSES (LICENSE_KEY, LICENSE_MAIN_KEY, ID_ASSIGNED_SOFTWARE, INVOICE_NUMBER, PURCHASE_DATE, LAST_INSTALLATION_DATE, LICENSE_NOTES, ASSIGNED_EMAIL, ID_DEVICE) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE LICENSES SET LICENSE_KEY=?, LICENSE_MAIN_KEY=?, ID_ASSIGNED_SOFTWARE=?, INVOICE_NUMBER=?, PURCHASE_DATE=?, LAST_INSTALLATION_DATE=?, LICENSE_NOTES=?, ASSIGNED_EMAIL=?, ID_DEVICE=? WHERE ID_LICENSE=?;";
    private static final String DELETE = "DELETE FROM LICENSES WHERE ID_LICENSE=?;";
	
	public LicenseDAO(Controller controller) {
		this.controller = controller;
		this.controller.getManagerDAO().setLicenseDAO(this);
	}
	
	@Override
	public License get(int licenseID) {
		License license = null;
		Software software = null;
		Device device = null;
     	try {
	     	 PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_ID);
	     	 ps.setInt(1, licenseID);
	     	 
	   		 controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
	   		 ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
   		 
			 if(resultSet.next()) {
					
				license = new License();
			 	software = new Software();
			 	device = controller.getManagerDAO().getDeviceDAO().get(resultSet.getInt("id_device"));
			 
			 	software.setSoftwareID(resultSet.getInt("id_assigned_software"));
			 	software.setSoftwareName(resultSet.getString("software_name"));
			 	software.setSoftwareNotes(resultSet.getString("software_notes"));
			 	
			 	license.setSoftware(software);
			 	license.setLicenseID(resultSet.getInt("id_license"));
			 	license.setLicenseKey(resultSet.getString("license_key"));
			 	license.setLicenseMainKey(resultSet.getString("license_main_key"));
			 	license.setInvoiceNumber(resultSet.getString("invoice_number"));
			 	license.setPurchaseDate(resultSet.getString("purchase_date"));
			 	license.setLastInstallationDate(resultSet.getString("last_installation_date"));
			 	license.setAssignedEmail(resultSet.getString("assigned_email"));
			 	license.setLicenseNotes(resultSet.getString("license_notes"));
			 	license.setDevice(device);
			 	license.setUsed((resultSet.getObject("id_device") != null && resultSet.getBoolean("id_device")));
			 	
			 }
		  ps.close();
		  resultSet.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
		}
      return license;
	}
	
	public License get(String licenseTextToFind) {
		License license = null;
		Software software = null;
		Device device = null;
     	try {
	     	 PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_BY_NAME);
	     	 ps.setString(1, "%" + licenseTextToFind + "%");
	   		 controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
	   		 ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
   		 
			 if(resultSet.next()) {
					
				license = new License();
			 	software = new Software();
			 	device = controller.getManagerDAO().getDeviceDAO().get(resultSet.getInt("id_device"));
			 
			 	software.setSoftwareID(resultSet.getInt("id_assigned_software"));
			 	software.setSoftwareName(resultSet.getString("software_name"));
			 	software.setSoftwareNotes(resultSet.getString("software_notes"));
			 	
			 	license.setSoftware(software);
			 	license.setLicenseID(resultSet.getInt("id_license"));
			 	license.setLicenseKey(resultSet.getString("license_key"));
			 	license.setLicenseMainKey(resultSet.getString("license_main_key"));
			 	license.setInvoiceNumber(resultSet.getString("invoice_number"));
			 	license.setPurchaseDate(resultSet.getString("purchase_date"));
			 	license.setLastInstallationDate(resultSet.getString("last_installation_date"));
			 	license.setAssignedEmail(resultSet.getString("assigned_email"));
			 	license.setLicenseNotes(resultSet.getString("license_notes"));
			 	license.setDevice(device);
			 	license.setUsed((resultSet.getObject("id_device") != null && resultSet.getBoolean("id_device")));
			 }
			 
		  ps.close();
		  resultSet.close();
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas odczytu wszystkich danych licencji z bazy", e);
		}
      return license;
	}

	@Override
	public List<License> getAll() {
		 licenses = new ArrayList<License>();
	 
         	try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_ALL);
				controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
				ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
				License license = null;
				Software software = null;
				Device device = null;
			while (resultSet.next()) {
						
					license = new License();
				 	software = new Software();
				 	device = controller.getManagerDAO().getDeviceDAO().get(resultSet.getInt("id_device"));
				 
				 	software.setSoftwareID(resultSet.getInt("id_assigned_software"));
				 	software.setSoftwareName(resultSet.getString("software_name"));
				 	software.setSoftwareNotes(resultSet.getString("software_notes"));
				 	
				 	license.setSoftware(software);
				 	license.setLicenseID(resultSet.getInt("id_license"));
				 	license.setLicenseKey(resultSet.getString("license_key"));
				 	license.setLicenseMainKey(resultSet.getString("license_main_key"));
				 	license.setInvoiceNumber(resultSet.getString("invoice_number"));
				 	license.setPurchaseDate(resultSet.getString("purchase_date"));
				 	license.setLastInstallationDate(resultSet.getString("last_installation_date"));
				 	license.setAssignedEmail(resultSet.getString("assigned_email"));
				 	license.setLicenseNotes(resultSet.getString("license_notes"));
				 	license.setDevice(device);
				 	license.setUsed((resultSet.getObject("id_device") != null && resultSet.getBoolean("id_device")));
				 	
				 	licenses.add(license);
				}
			  ps.close();
			  resultSet.close();
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
			}
	      return licenses;
	}
	
	public List<License> getAllByDeviceID(int deviceID) {
		 licenses = new ArrayList<License>();
	 
         	try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(FIND_ALL_BY_DEVICE);
				ps.setInt(1, deviceID);
				controller.getDatabaseProvider().executePreparedStatementWithResult(ps);
				ResultSet resultSet = controller.getDatabaseProvider().getResultSet();
				License license = null;
				Software software = null;
				Device device = null;
			 while (resultSet.next()) {
					
					license = new License();
				 	software = new Software();
				 	device = controller.getManagerDAO().getDeviceDAO().get(resultSet.getInt("id_device"));
				 
				 	software.setSoftwareID(resultSet.getInt("id_assigned_software"));
				 	software.setSoftwareName(resultSet.getString("software_name"));
				 	software.setSoftwareNotes(resultSet.getString("software_notes"));
				 	
				 	license.setSoftware(software);
				 	license.setLicenseID(resultSet.getInt("id_license"));
				 	license.setLicenseKey(resultSet.getString("license_key"));
				 	license.setLicenseMainKey(resultSet.getString("license_main_key"));
				 	license.setInvoiceNumber(resultSet.getString("invoice_number"));
				 	license.setPurchaseDate(resultSet.getString("purchase_date"));
				 	license.setLastInstallationDate(resultSet.getString("last_installation_date"));
				 	license.setAssignedEmail(resultSet.getString("assigned_email"));
				 	license.setLicenseNotes(resultSet.getString("license_notes"));
				 	license.setDevice(device);
				 	
				 	licenses.add(license);
				}
			  ps.close();
			  resultSet.close();
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas odczytu wszystkich urządzeń z bazy", e);
			}
	      return licenses;
	}

	@Override
	public void insert(License license) {
			try {
				PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(INSERT);
				 
	            ps.setString(1, license.getLicenseKey());
	            ps.setString(2, license.getLicenseMainKey());
	            ps.setInt(3, license.getSoftware().getSoftwareID());
	            ps.setString(4, license.getInvoiceNumber());
	            ps.setString(5, license.getPurchaseDate());
	            ps.setString(6, license.getLastInstallationDate());
	            ps.setString(7, license.getLicenseNotes());
	            ps.setString(8, license.getAssignedEmail());
	            ps.setObject(9, (license.getDevice().getDeviceID() == 0) ? null : license.getDevice().getDeviceID());
	            
	            ps.executeUpdate();
				ps.close();
				
	            System.out.println("License with following details was saved in DB: " + license.toString());
			} catch (SQLException e) {
				new SystemOperationException("Błąd podczas zapisywania danych urządzenia do bazy.", e);
			}
	}

	@Override
	public void update(License license) {

        System.out.println("test: " + ((license.getDevice().getDeviceID() == 0) ? null : license.getDevice().getDeviceID()));
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(UPDATE);
			 
		 	ps.setString(1, license.getLicenseKey());
            ps.setString(2, license.getLicenseMainKey());
            ps.setInt(3, license.getSoftware().getSoftwareID());
            ps.setString(4, license.getInvoiceNumber());
            ps.setString(5, license.getPurchaseDate());
            ps.setString(6, license.getLastInstallationDate());
            ps.setString(7, license.getLicenseNotes());
            ps.setString(8, license.getAssignedEmail());
            ps.setObject(9, (license.getDevice().getDeviceID() == 0) ? null : license.getDevice().getDeviceID());
            ps.setInt(10, license.getLicenseID());

           
            
            ps.executeUpdate();
			ps.close();
			
            System.out.println("License with id " + license.getLicenseID() + " was updated in DB with following details: " + license.toString());
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

	@Override
	public void delete(License license) {
		try {
			PreparedStatement ps = controller.getDatabaseProvider().getDatabaseConnection().prepareStatement(DELETE);
			 
            ps.setInt(1, license.getLicenseID());
            
            ps.executeUpdate();
			ps.close();
			
			System.out.println("License with id: " +  license.getLicenseID() + " was sucesfully deleted from DB.");
		} catch (SQLException e) {
			new SystemOperationException("Błąd podczas aktualizacji danych urządzenia do bazy.", e);
		}
	}

}
