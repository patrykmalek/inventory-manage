package computers.manage.view.dictionary.table.panel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;

import computers.manage.controller.Controller;
import computers.manage.model.Device;
import computers.manage.model.License;
import computers.manage.model.OperationType;


public class ComputerLicenseAssignedTablePanel extends DictionaryTablePanel<License> {

	private static final long serialVersionUID = 1698570024703870348L;
	private Controller controller;
	
	private Device device;
	private JButton assignButton;
	private JButton detachButton;
	
	private OperationType operationType;
	private boolean editable;
	
	public ComputerLicenseAssignedTablePanel(Controller controller) {
		super();
		this.controller = controller;
		this.controller.setComputerLicenseAssignedTable(this);
	}
	
	public void createPanel() {
		this.createTable();
		this.createCustomButton();
		this.setButtonEventListeners();
		setPanelTitle("Powiazane licencje");
	}
	
	private void createCustomButton() {
		
		assignButton = new JButton();
		assignButton.setEnabled(isEditable());
		
		getButtonPanel().removeAllButtons();
		
		detachButton = new JButton();
		detachButton.setEnabled(isEditable());
		
		getButtonPanel().addAtachButton(assignButton);
		getButtonPanel().addDetachButton(detachButton);
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
		
		assignButton = getButtonPanel().getButtons().get(0);
		assignButton.removeActionListener(assignButton.getActionListeners()[0]);
		assignButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				getController().getManagerDAO().getLicenseServiceDAO().openAssignedLicenseToDevice(getDevice());
				updateTable(getController().getManagerDAO().getLicenseDAO().getAllByDeviceID(getDevice().getDeviceID()));
			}
		});
		detachButton = getButtonPanel().getButtons().get(1);
		detachButton.removeActionListener(detachButton.getActionListeners()[0]);
		detachButton.addActionListener(new ActionListener() {
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

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		setEditable(operationType.isWindowEditable());
		this.operationType = operationType;
	}
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}


}
