package kruszywo.sa.computers.manage.view.dictionary.table.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.RowSorter;
import javax.swing.SortOrder;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.model.Device;

public class DeviceTablePanel extends DictionaryTablePanel<Device> {

	private static final long serialVersionUID = -3463179337882985534L;
	private Controller controller;
	
	public DeviceTablePanel(Controller controller) {
		super();
		this.controller = controller;
		this.controller.setDeviceTablePanel(this);
		this.createTable();
		this.setButtonEventListeners();
		setPanelTitle("Urządzenia");
	}
	
	@Override
	public void setButtonEventListeners() {
		
		getShowButton().removeActionListener(getShowButton().getActionListeners()[0]);
		getShowButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				getController().getManagerDAO().getDeviceServiceDAO().openDeviceWindowToOnlyShowDetails(getIdFromTable());
			}
		});
		getShowButton().setVisible(true);
		
		getInsertButton().removeActionListener(getInsertButton().getActionListeners()[0]);
		getInsertButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				getController().getManagerDAO().getDeviceServiceDAO().openDeviceWindowToAddNew();
			}
		});
		
		getUpdateButton().removeActionListener(getUpdateButton().getActionListeners()[0]);
		getUpdateButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				getController().getManagerDAO().getDeviceServiceDAO().openDeviceWindowToUpdate(getIdFromTable());
			}
		});
		
		getDeleteButton().removeActionListener(getDeleteButton().getActionListeners()[0]);
		getDeleteButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				getController().getManagerDAO().getDeviceServiceDAO().deleteDeviceWithPrompt(getIdFromTable());
			}
		});
		
		getTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						if(getParentWindow() == null) getController().getManagerDAO().getDeviceServiceDAO().openDeviceWindowToOnlyShowDetails(getIdFromTable());
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
					updateTable(getController().getManagerDAO().getDeviceDAO().getAll());
				}
			}
		});
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
				java.lang.String.class,
				java.lang.String.class,
				java.lang.String.class,
				java.lang.String.class,
				java.lang.String.class,
				java.lang.String.class,
				java.util.Date.class,
				java.util.Date.class,
				java.util.Date.class,
				java.lang.Boolean.class
		});
		this.setTableColumnNames(new String[] { 
				"ID urządzenia",
				"Numer unikalny",
				"Nazwa",
				"Numer inwentarzowy",
				"Typ urządzenia",
				"Miejsce użytk.",
				"Pracownik",
				"Powiązana faktura",
				"Procesor",
				"Pamięć RAM",
				"Pamięć Masowa",
				"Dodatkowy Opis",
				"Data zakupu",
				"Ostatnia instalacja",
				"Ostatnia modyfikacja",
				"Użyty"
		});
		getTableColumnManager().hideColumn(11);
	}
	
	@Override
	public void updateTable(List<Device> devices) {
		clearTable();
		if(isEmptyData(devices)) return;
		
		for( Device device : devices){
			addRowToTable(new Object[] {device.getDeviceID(), device.getDeviceUniqueNumber(), device.getDeviceName(), device.getDeviceInventoryNumber(), 
						device.getDeviceType().getDeviceTypeName(), device.getAssignedDepartment().toString(), 
						device.getAssignedEmployee().toString(), device.getInvoiceNumber(),
						device.getComputerComponent().getComputerCPU().toString(), device.getComputerComponent().getComputerRAM().toString(), 
						device.getComputerComponent().getComputerMassStorageFirst().toString(), device.getNotes(),
						device.getPurchaseDate(), device.getLastInstallationDate(), device.getLastModificationDate(), device.isUsed()});
		}
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		int columnIndexToSort = 14;
		sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));
		getTable().getRowSorter().setSortKeys(sortKeys);
		
		resizeTable();
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

}
