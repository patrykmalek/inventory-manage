package kruszywo.sa.computers.manage.view.util;

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
	
}
