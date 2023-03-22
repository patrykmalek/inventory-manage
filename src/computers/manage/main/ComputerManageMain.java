package computers.manage.main;

import java.awt.Insets;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import computers.manage.config.DatabaseConfig;
import computers.manage.controller.Controller;
import computers.manage.provider.DatabaseProvider;
import computers.manage.view.MainFrame;

public class ComputerManageMain {

	public static void main(String[] args) throws ParseException, IOException {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Insets insets = UIManager.getInsets("TabbedPane.contentBorderInsets");
			insets.top = -1;
			UIManager.put("TabbedPane.contentBorderInsets", insets);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
			return;
		}		
		javax.swing.SwingUtilities.invokeLater(new Runnable()  {
			public void run() {
				
				
				DatabaseConfig dbConfig = new DatabaseConfig();
				
				DatabaseProvider databaseProvider = new DatabaseProvider(dbConfig.getDatabaseURL());
				databaseProvider.connect();
				
				Controller controller = new Controller(databaseProvider);
				controller.setDatabaseConfig(dbConfig);
				controller.createFolderForFilesIfNotExist();
				
				MainFrame mainFrame = new MainFrame(controller);
				controller.addDevicesPanel();
				mainFrame.displayWindow();
			}
		});
	}
}