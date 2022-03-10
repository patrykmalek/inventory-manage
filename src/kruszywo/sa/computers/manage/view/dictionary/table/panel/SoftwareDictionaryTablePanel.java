package kruszywo.sa.computers.manage.view.dictionary.table.panel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.model.Software;


public class SoftwareDictionaryTablePanel extends DictionaryTablePanel<Software> {

	private static final long serialVersionUID = 1698570024703870348L;
	private Controller controller;

	public SoftwareDictionaryTablePanel(Controller controller) {
		super();
		this.controller = controller;
		this.controller.setSoftwareDictionaryTable(this);
		this.createTable();
		this.setButtonEventListeners();
		setPanelTitle("Słownik oprogramowania");
	}
	
	@Override
	public void createTable() {
		this.setTableModelAndSorter(new Class[] { 
				java.lang.Integer.class,
				java.lang.String.class
		});
		this.setTableColumnNames(new String[] { 
				"ID oprogramowania",
				"Nazwa oprogramowania"
		});
	}

	@Override
	public void updateTable(List<Software> softwares) {
		clearTable();
		if(isEmptyData(softwares)) return;
		for( Software software : softwares){
			addRowToTable(new Object[] {software.getSoftwareID(), software.getSoftwareName()});
		}
		resizeTable();
	}

	@Override
	public void setButtonEventListeners() {
		getInsertButton().removeActionListener(getInsertButton().getActionListeners()[0]);
		getInsertButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getSoftwareServiceDAO().openSoftwareWindowToAddNew();
			}
		});
		getUpdateButton().removeActionListener(getUpdateButton().getActionListeners()[0]);
		getUpdateButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getSoftwareServiceDAO().openSoftwareWindowToUpdate(getIdFromTable());
			}
		});
		getDeleteButton().removeActionListener(getDeleteButton().getActionListeners()[0]);
		getDeleteButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getSoftwareServiceDAO().deleteSoftwareWithPrompt(getIdFromTable());
			}
		});
		getTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if(getParentWindow() == null) getController().getManagerDAO().getSoftwareServiceDAO().openSoftwareWindowToOnlyShowDetails(getIdFromTable());
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