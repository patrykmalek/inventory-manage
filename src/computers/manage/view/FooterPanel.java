package computers.manage.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import computers.manage.controller.Controller;

public class FooterPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	private JLabel databaseInfoLabel;
	
	public FooterPanel(Controller controller) {
		
		this.controller = controller;
		this.controller.setFooterPanel(this);
		this.createVisuals();
		this.controller.updateDatabasePathInfo();
	}

	private void createVisuals() {
		setLayout(new BorderLayout(10, 10));
		setPreferredSize(new Dimension(100, 25));
		setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		setBackground(Color.WHITE);
		
		
		this.databaseInfoLabel = new JLabel();
		this.databaseInfoLabel.setForeground(Color.gray);
		add(this.databaseInfoLabel, BorderLayout.CENTER);
	}
	
	public void updateDatabasePathInfo(String databaseInfo) {
		this.databaseInfoLabel.setText("Plik bazy danych: " + databaseInfo);
	}
	
}
