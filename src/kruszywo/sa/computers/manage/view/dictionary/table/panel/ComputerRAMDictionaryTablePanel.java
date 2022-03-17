package kruszywo.sa.computers.manage.view.dictionary.table.panel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.model.ComputerRAM;


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
				java.lang.Integer.class,
				java.lang.Boolean.class
		});
		this.setTableColumnNames(new String[] { 
				"ID pamięci",
				"Typ pamięci",
				"Pojemność pamięci MB",
				"Użyty"
		});
	}

	@Override
	public void updateTable(List<ComputerRAM> computerRAMs) {
		clearTable();
		if(isEmptyData(computerRAMs)) return;
		for( ComputerRAM computerRam : computerRAMs){
			addRowToTable(new Object[] {computerRam.getMemoryRamID(), computerRam.getMemoryRamType(), computerRam.getMemoryRamCapacityMB(), computerRam.isUsed()});
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
