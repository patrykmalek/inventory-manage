package kruszywo.sa.computers.manage.view.dictionary.table.panel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.model.ComputerCPU;


public class ComputerCPUDictionaryTablePanel extends DictionaryTablePanel<ComputerCPU> {

	private static final long serialVersionUID = 1698570024703870348L;
	private Controller controller;

	public ComputerCPUDictionaryTablePanel(Controller controller) {
		super();
		this.controller = controller;
		this.controller.setComputerCPUDictionaryTable(this);
		this.createTable();
		this.setButtonEventListeners();
		setPanelTitle("Słownik procesorów");
	}
	
	@Override
	public void createTable() {
		this.setTableModelAndSorter(new Class[] { 
				java.lang.Integer.class,
				java.lang.String.class,
				java.lang.Integer.class,
				java.lang.Integer.class
		});
		this.setTableColumnNames(new String[] { 
				"ID procesora",
				"Nazwa procesora",
				"Ilość rdzeni",
				"Szybkość zegara"
		});
	}

	@Override
	public void updateTable(List<ComputerCPU> computerCPUs) {
		clearTable();
		if(isEmptyData(computerCPUs)) return;
		for( ComputerCPU computerCpu : computerCPUs){
			addRowToTable(new Object[] {computerCpu.getComputerCpuID(), computerCpu.getComputerCpuName(), computerCpu.getComputerCpuCoresNumber(), computerCpu.getComputerCpuClockSpeed()});
		}
		resizeTable();
	}

	@Override
	public void setButtonEventListeners() {
		getInsertButton().removeActionListener(getInsertButton().getActionListeners()[0]);
		getInsertButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getComputerCPUServiceDAO().openComputerCPUWindowToAddNew();
			}
		});
		getUpdateButton().removeActionListener(getUpdateButton().getActionListeners()[0]);
		getUpdateButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getComputerCPUServiceDAO().openComputerCPUWindowToUpdate(getIdFromTable());
			}
		});
		getDeleteButton().removeActionListener(getDeleteButton().getActionListeners()[0]);
		getDeleteButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getComputerCPUServiceDAO().deleteComputerCPUWithPrompt(getIdFromTable());
			}
		});
		getTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if(getParentWindow() == null) getController().getManagerDAO().getComputerCPUServiceDAO().openComputerCPUWindowToOnlyShowDetails(getIdFromTable());
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
