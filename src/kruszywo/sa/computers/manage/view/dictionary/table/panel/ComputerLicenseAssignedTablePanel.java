package kruszywo.sa.computers.manage.view.dictionary.table.panel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.model.Device;
import kruszywo.sa.computers.manage.model.License;


public class ComputerLicenseAssignedTablePanel extends DictionaryTablePanel<License> {

	private static final long serialVersionUID = 1698570024703870348L;
	private Controller controller;
	
	private Device device;
	private JButton assignLicenseButton;
	private JButton deleteAssignLicenseButton;
	
	public ComputerLicenseAssignedTablePanel(Controller controller) {
		super();
		this.controller = controller;
		this.controller.setComputerLicenseAssignedTable(this);
		this.createTable();
		this.createCustomButton();
		this.setButtonEventListeners();
		setPanelTitle("Powiazane licencje");
	}
	
	private void createCustomButton() {
		
		getButtonPanel().removeAllButtons();
		assignLicenseButton = new JButton();
		assignLicenseButton.setText("Powiąż");
		
		deleteAssignLicenseButton = new JButton();
		deleteAssignLicenseButton.setText("Odłącz");
		
		getButtonPanel().addButton(assignLicenseButton, new ImageIcon(getClass().getResource("/handshake-solid.png")));
		getButtonPanel().addButton(deleteAssignLicenseButton, new ImageIcon(getClass().getResource("/handshake-slash-solid.png")));
	}
	
	@Override
	public void createTable() {
		this.setTableModelAndSorter(new Class[] { 
				java.lang.Integer.class,
				java.lang.String.class,
				java.lang.String.class,
				java.lang.String.class,
				java.lang.String.class,
				java.lang.String.class,
				java.util.Date.class,
				java.util.Date.class
		});
		this.setTableColumnNames(new String[] { 
				"ID licencji",
				"Oprogramowanie",
				"Klucz główny",
				"Klucz",
				"Przypisany e-mail",
				"Powiązana faktura",
				"Data zakupu",
				"Data ost. instalacji"
		});
	}

	@Override
	public void updateTable(List<License> licenses) {
		clearTable();
		if(isEmptyData(licenses)) return;
		
		for( License license : licenses){
			addRowToTable(new Object[] {license.getLicenseID(), license.getSoftware().toString(), license.getLicenseMainKey(),
					license.getLicenseKey(), license.getAssignedEmail(), license.getInvoiceNumber(), 
					license.getPurchaseDate(), license.getLastInstallationDate()});
		}
		resizeTable();
	}

	@Override
	public void setButtonEventListeners() {
		
		assignLicenseButton = getButtonPanel().getButtons().get(0);
		assignLicenseButton.removeActionListener(assignLicenseButton.getActionListeners()[0]);
		assignLicenseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				getController().getManagerDAO().getLicenseServiceDAO().openAssignedLicenseToDevice(getDevice());
				updateTable(getController().getManagerDAO().getLicenseDAO().getAllByDeviceID(getDevice().getDeviceID()));
			}
		});
		deleteAssignLicenseButton = getButtonPanel().getButtons().get(1);
		deleteAssignLicenseButton.removeActionListener(deleteAssignLicenseButton.getActionListeners()[0]);
		deleteAssignLicenseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				getController().getManagerDAO().getLicenseServiceDAO().deleteAssignedLicense(getIdFromTable(), getDevice().getDeviceID());
				updateTable(getController().getManagerDAO().getLicenseDAO().getAllByDeviceID(getDevice().getDeviceID()));
			}
		});
		
		getTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if(getParentWindow() == null) getController().getManagerDAO().getLicenseServiceDAO().openLicenseWindowToOnlyShowDetails(getIdFromTable());
				}
			}
		});
		getTable().addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F5) {
					
				}
			}
		});
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}


}
