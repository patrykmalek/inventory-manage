package inventory.manage.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Properties;

import javax.swing.JFileChooser;

import inventory.manage.exception.SystemOperationException;

public class DatabaseConfig {

	private MyCipher cipher;
	private String databasePath;
	
	public DatabaseConfig() {
		try {
			this.cipher = new MyCipher("kluczszyfrujacy", "123456789");
			if(!isConfigFileExists()) createDefaultConfigFile();
			this.loadEncryptedConfig(this.cipher);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			new SystemOperationException("Błąd podczas tworzenia instancji konfiguracji bazy danych.", e);
		}
	}
	
	public String getDatabasePath() {
		
		if(!isFileExits(databasePath)) {
			JFileChooser file = new JFileChooser();
		      file.setMultiSelectionEnabled(true);
		      file.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		      file.setFileHidingEnabled(false);
		      if (file.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		         java.io.File f = file.getSelectedFile();
		         setDatabasePathAndSaveToConfigFile(f.getPath());
		      } else {
		    	  System.exit(1);
		      }
		}
		
		return databasePath;
	}
	
	public String getDatabaseURL() {
		return "jdbc:sqlite:" + getDatabasePath() + "?foreign_keys=on;";
	}
	
	private String getComputerName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String getConfigFilePath(){
			return System.getProperty("user.dir") + "\\config_"+ getComputerName() +".dat";
	}
	
	public String getDefaultDatabasePath() {
		return System.getProperty("user.dir") + "\\database\\computers.db";
	}
	
	public void createDefaultConfigFile() {
			Properties prop = new Properties();
			prop.put("databasePath", getDefaultDatabasePath());
			saveEncryptedConfig(prop, getCipher());
	}
	
	public void setDatabasePathAndSaveToConfigFile(String databasePath) {
		Properties prop = new Properties();
		prop.put("databasePath", databasePath);
		setDatabasePath(databasePath);
		saveEncryptedConfig(prop, getCipher());
}
	
	public void saveConfig(Properties propToSave) {
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(this.getConfigFilePath());
			propToSave.store(fileWriter, "Properties");
			fileWriter.close();
		} catch (IOException e) {
			new SystemOperationException("Błąd podczas zapisu konfiguracji do pliku", e);
		}
	}
	
	public void saveEncryptedConfig(Properties propToSave, MyCipher cipher) {
		FileWriter fileWriter;
		
		propToSave.forEach((key, value) -> {
			propToSave.replace(key, value, cipher.encrypt((String) value));
		});
			
			try {
				fileWriter = new FileWriter(this.getConfigFilePath());
				propToSave.store(fileWriter, "Properties");
				fileWriter.close();
			} catch (IOException e) {
				new SystemOperationException("Błąd podczas zapisu szyfrowanej konfiguracji do pliku", e);
			}
	}
	
	public Properties getConfig() {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(this.getConfigFilePath())) {
			prop.load(input);
		} catch (IOException ex) {
			new SystemOperationException("Nie znaleziono pliku konfiguracyjnego bazy danych", ex);
		} catch (Exception ex) {
			new SystemOperationException("Wystąpił nieoczekiwany wyjątek", ex);
		}
		
		return prop;
	}
	
	public Properties getFileProperties(String filePath) {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(filePath)) {
			prop.load(input);
		} catch (Exception ex) {
			new SystemOperationException("Wystąpił nieoczekiwany wyjątek podczas odczytywania konfiguracji", ex);
		}
		
		return prop;
	}
	
	
	public void loadEncryptedConfig(MyCipher cipher)  {
		Properties prop = getConfig();
		if(prop.isEmpty()) return;
		setDatabasePath(cipher.decrypt(prop.getProperty("databasePath")));
	}
	
	public void loadConfig() {
		Properties prop = getConfig();
		setDatabasePath(prop.getProperty("databasePath"));
	}
	
	public boolean isConfigFileExists() {
		File file = new File(this.getConfigFilePath());
		return file.exists();
	}
	
	public boolean isFileExits(String path) {
		File file = new File(path);
		return file.exists();
	}


	public MyCipher getCipher() {
		return cipher;
	}

	public void setDatabasePath(String databasePath) {
		this.databasePath = databasePath;
	}

	public String getLocalDirectoryPath() {
		return System.getProperty("user.dir") + "\\files\\";
	}


}
