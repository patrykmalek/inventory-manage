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

import inventory.manage.exception.SystemOperationException;
import jnafilechooser.api.JnaFileChooser;

public class ApplicationConfig {

	private MyCipher cipher;
	private String databasePath;
	
	public ApplicationConfig() {
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
			
			JnaFileChooser fileChooser = new JnaFileChooser();
			fileChooser.addFilter("SQLite Database", "db");
			if (fileChooser.showOpenDialog(null)) {
				File file = fileChooser.getSelectedFile();
				setDatabasePathAndSaveToConfigFile(file.getPath());
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
			return getAbsoluteApplicationPath() + "\\config_"+ getComputerName() +".dat";
	}
	
	public String getDefaultDatabasePath() {
		return getAbsoluteApplicationPath() + "\\database\\database.db";
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
	
	public String getAbsoluteApplicationPath() {
		return System.getProperty("user.dir");
	}

	public String getLocalDirectoryPath() {
		return getAbsoluteApplicationPath() + getDefaultRelativeLocalDirectoryPath();
	}

	public String getDefaultRelativeLocalDirectoryPath() {
		return "\\files\\";
	}

}
