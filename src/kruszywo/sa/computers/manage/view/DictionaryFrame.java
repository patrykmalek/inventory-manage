package kruszywo.sa.computers.manage.view;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import kruszywo.sa.computers.manage.controller.Controller;

public class DictionaryFrame<T> extends JDialog {

	private static final long serialVersionUID = 8317657436243369156L;

	private Controller controller;

	private DictionaryTablePanel<T> mainPanel;
	
	public DictionaryFrame(Controller controller, DictionaryTablePanel<T> mainPanel) {
		super(controller.getMainFrame(), "Słownik", true);
		setController(controller);
		setMainPanel(mainPanel);
		createVisuals();
	}
	
	public void showWindow() {
		setVisible(true);
	}

	private void createVisuals() {
		setTitle(mainPanel.getPanelTitle());
		setSize(600, 300);
		getContentPane().add(this.mainPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(DictionaryTablePanel<T> mainPanel) {
		this.mainPanel = mainPanel;
	}
	
}
