package kruszywo.sa.computers.manage.view.dictionary.table.panel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		setPanelTitle("Słownik procesorów");
	}
	
	@Override
	public void createTable() {
		this.setTableModelAndSorter(new Class[] { 
				java.lang.Integer.class,
				java.lang.Integer.class,
				java.lang.String.class,
				java.lang.Integer.class
		});
		this.setTableColumnNames(new String[] { 
				"ID pamięci",
				"Lp.",
				"Typ pamięci",
				"Pojemność pamięci MB"
		});
	}

	@Override
	public void updateTable(List<ComputerRAM> computerRAMs) {
		clearTable();
		if(isEmptyData(computerRAMs)) return;
		for( ComputerRAM computerRam : computerRAMs){
			addRowToTable(new Object[] {computerRam.getMemoryRamID(), getTable().getRowCount() + 1, computerRam.getMemoryRamType(), computerRam.getMemoryRamCapacityMB()});
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
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}


}
