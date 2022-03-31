package kruszywo.sa.computers.manage.main;

import java.awt.Insets;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.provider.DatabaseProvider;
import kruszywo.sa.computers.manage.view.MainFrame;

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
				String databasePath = System.getProperty("user.dir") + "\\database\\computers.db?foreign_keys=on;";
//				String databasePath = System.getProperty("user.dir") + "\\database\\computers_test.db?foreign_keys=on;";
				DatabaseProvider databaseProvider = new DatabaseProvider("jdbc:sqlite:" + databasePath);
				databaseProvider.connect();
				Controller controller = new Controller(databaseProvider);
				MainFrame mainFrame = new MainFrame(controller);
				controller.addDevicesPanel();
				mainFrame.displayWindow();
			}
		});
	}
}
