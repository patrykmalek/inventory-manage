package inventory.manage.view.util;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PMFileChooser extends JFileChooser {

	
	private static final long serialVersionUID = -1727395005618965714L;

	public PMFileChooser() {
		super();
	}
	
	public File getCSVFileOnly() {
		File selectedFile = null;
		FileNameExtensionFilter filter = new FileNameExtensionFilter("(*.csv) CSV - Dane oddzielone przecinkami", "csv");
		setFileFilter(filter);
		
		int returnValue = showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			selectedFile = getSelectedFile();
		}
		
		return selectedFile;
	}
	
	public File getFile() {
		File selectedFile = null;
		
		int returnValue = showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			selectedFile = getSelectedFile();
		}
		
		return selectedFile;
	}
	
	public String getDirectoryForSaveFile() {
		String directorySavePath = "";
		File selectedFile = null;
		
		setDialogTitle("Okreś ścieżkę zapisu pliku");
		setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int returnValue = showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			selectedFile = getSelectedFile();
			directorySavePath = selectedFile.getName();
		}

		return directorySavePath;
	}
	
}
