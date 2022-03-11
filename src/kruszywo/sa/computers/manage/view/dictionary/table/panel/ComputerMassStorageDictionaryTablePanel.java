package kruszywo.sa.computers.manage.view.dictionary.table.panel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.model.ComputerMassStorage;


public class ComputerMassStorageDictionaryTablePanel extends DictionaryTablePanel<ComputerMassStorage> {

	private static final long serialVersionUID = 1698570024703870348L;
	private Controller controller;

	public ComputerMassStorageDictionaryTablePanel(Controller controller) {
		super();
		this.controller = controller;
		this.controller.setComputerMassStorageDictionaryTable(this);
		this.createTable();
		this.setButtonEventListeners();
		setPanelTitle("Słownik procesorów");
	}
	
	@Override
	public void createTable() {
		this.setTableModelAndSorter(new Class[] { 
				java.lang.Integer.class,
				java.lang.String.class,
				java.lang.String.class,
				java.lang.String.class,
				java.lang.Integer.class
		});
		this.setTableColumnNames(new String[] { 
				"ID pamięci masowej",
				"Nazwa pamięci masowej",
				"Typ pamięci masowej",
				"Numer seryjny",
				"Pojemność pamięci masowej"
		});
	}

	@Override
	public void updateTable(List<ComputerMassStorage> computerMassStorages) {
		clearTable();
		if(isEmptyData(computerMassStorages)) return;
		for( ComputerMassStorage computerMassStorage : computerMassStorages){
			addRowToTable(new Object[] {computerMassStorage.getMassStorageID(), computerMassStorage.getMassStorageName(), 
							 computerMassStorage.getMassStorageType(), computerMassStorage.getMassStorageSerialNumber(), computerMassStorage.getMassStorageCapacityMB()});
		}
		resizeTable();
	}

	@Override
	public void setButtonEventListeners() {
		getInsertButton().removeActionListener(getInsertButton().getActionListeners()[0]);
		getInsertButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getComputerMassStorageServiceDAO().openComputerMassStorageWindowToAddNew();
			}
		});
		getUpdateButton().removeActionListener(getUpdateButton().getActionListeners()[0]);
		getUpdateButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getComputerMassStorageServiceDAO().openComputerMassStorageWindowToUpdate(getIdFromTable());
			}
		});
		getDeleteButton().removeActionListener(getDeleteButton().getActionListeners()[0]);
		getDeleteButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getComputerMassStorageServiceDAO().deleteComputerMassStorageWithPrompt(getIdFromTable());
			}
		});
		getTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if(getParentWindow() == null) getController().getManagerDAO().getComputerMassStorageServiceDAO().openComputerMassStorageWindowToOnlyShowDetails(getIdFromTable());
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
