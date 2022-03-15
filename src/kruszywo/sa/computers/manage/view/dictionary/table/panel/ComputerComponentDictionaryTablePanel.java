package kruszywo.sa.computers.manage.view.dictionary.table.panel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.model.ComputerComponent;


public class ComputerComponentDictionaryTablePanel extends DictionaryTablePanel<ComputerComponent> {

	private static final long serialVersionUID = 1698570024703870348L;
	private Controller controller;

	public ComputerComponentDictionaryTablePanel(Controller controller) {
		super();
		this.controller = controller;
		this.controller.setComputerComponentDictionaryTable(this);
		this.createTable();
		this.setButtonEventListeners();
		setPanelTitle("Słownik komponentów");
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
				java.lang.String.class
		});
		this.setTableColumnNames(new String[] { 
				"ID komponentu",
				"Nazwa przypisanego komputera",
				"Procesor",
				"Pamięć RAM",
				"Pamięć masowa - 1",
				"Pamięć masowa - 2",
				"Pamięć masowa - 3"
		});
	}

	@Override
	public void updateTable(List<ComputerComponent> computerComponents) {
		clearTable();
		if(isEmptyData(computerComponents)) return;
		for( ComputerComponent computerComponent : computerComponents){
			addRowToTable(new Object[] {computerComponent.getComputerComponentID(), computerComponent.getDevice().getDeviceName(), 
					computerComponent.getComputerCPU().toString(), computerComponent.getComputerRAM().toString(), 
					computerComponent.getComputerMassStorageFirst().toString(), computerComponent.getComputerMassStorageSecond().toString(),
					computerComponent.getComputerMassStorageThird().toString()});
		}
		resizeTable();
	}

	@Override
	public void setButtonEventListeners() {
		getInsertButton().removeActionListener(getInsertButton().getActionListeners()[0]);
		getInsertButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getComputerComponentServiceDAO().openComputerComponentWindowToAddNew();
			}
		});
		getUpdateButton().removeActionListener(getUpdateButton().getActionListeners()[0]);
		getUpdateButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getComputerComponentServiceDAO().openComputerComponentWindowToUpdate(getIdFromTable());
			}
		});
		getDeleteButton().removeActionListener(getDeleteButton().getActionListeners()[0]);
		getDeleteButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getComputerComponentServiceDAO().deleteComputerComponentWithPrompt(getIdFromTable());
			}
		});
		getTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if(getParentWindow() == null) getController().getManagerDAO().getComputerComponentServiceDAO().openComputerComponentWindowToOnlyShowDetails(getIdFromTable());
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