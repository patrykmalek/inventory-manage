package inventory.manage.view.dictionary.table.panel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import inventory.manage.controller.Controller;
import inventory.manage.model.ComputerRAM;


public class ComputerRAMDictionaryTablePanel extends DictionaryTablePanel<ComputerRAM> {

	private static final long serialVersionUID = 1698570024703870348L;
	private Controller controller;

	public ComputerRAMDictionaryTablePanel(Controller controller) {
		super();
		this.controller = controller;
		this.controller.setComputerRAMDictionaryTable(this);
		this.createTable();
		this.setButtonEventListeners();
		setPanelTitle("Słownik pamieci RAM");
	}
	
	@Override
	public void createTable() {
		this.setTableModelAndSorter(new Class[] { 
				java.lang.Integer.class,
				java.lang.String.class,
				java.lang.String.class,
				java.lang.String.class,
				java.lang.Integer.class,
				java.lang.Boolean.class
		});
		this.setTableColumnNames(new String[] { 
				"ID pamięci",
				"Nazwa pamięci RAM",
				"Numer seryjny",
				"Typ pamięci RAM",
				"Pojemność pamięci MB",
				"Przypisano"
		});
	}

	@Override
	public void updateTable(List<ComputerRAM> computerRAMs) {
		clearTable();
		if(isEmptyData(computerRAMs)) return;
		for( ComputerRAM computerRam : computerRAMs){
			addRowToTable(new Object[] {computerRam.getMemoryRamID(),computerRam.getMemoryRamName(), computerRam.getMemoryRamSerialNumber(), computerRam.getMemoryRamType(), computerRam.getMemoryRamCapacityMB(), computerRam.isUsed()});
		}
		resizeTable();
	}

	@Override
	public void setButtonEventListeners() {
		getInsertButton().removeActionListener(getInsertButton().getActionListeners()[0]);
		getInsertButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getComputerRAMServiceDAO().openComputerRAMWindowToAddNew();
			}
		});
		getUpdateButton().removeActionListener(getUpdateButton().getActionListeners()[0]);
		getUpdateButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getComputerRAMServiceDAO().openComputerRAMWindowToUpdate(getIdFromTable());
			}
		});
		getDeleteButton().removeActionListener(getDeleteButton().getActionListeners()[0]);
		getDeleteButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getComputerRAMServiceDAO().deleteComputerRAMWithPrompt(getIdFromTable());
			}
		});
		getTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if(getParentWindow() == null) getController().getManagerDAO().getComputerRAMServiceDAO().openComputerRAMWindowToOnlyShowDetails(getIdFromTable());
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
					updateTable(getController().getManagerDAO().getComputerRAMDAO().getAll());
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


}
