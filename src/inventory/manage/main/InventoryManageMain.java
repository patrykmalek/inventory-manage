package inventory.manage.main;

import java.awt.Insets;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import inventory.manage.config.ApplicationConfig;
import inventory.manage.controller.Controller;
import inventory.manage.provider.DatabaseProvider;
import inventory.manage.view.MainFrame;

public class InventoryManageMain {

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
				
				
				ApplicationConfig appConfig = new ApplicationConfig();
				
				DatabaseProvider databaseProvider = new DatabaseProvider(appConfig.getDatabaseURL());
				databaseProvider.connect();
				
				Controller controller = new Controller(databaseProvider);
				controller.setApplicationConfig(appConfig);
				controller.createFolderForFilesIfNotExist();
				
				MainFrame mainFrame = new MainFrame(controller);
				controller.addDevicesPanel();
				mainFrame.displayWindow();
			}
		});
	}
}