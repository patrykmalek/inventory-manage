package kruszywo.sa.computers.manage.view;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.model.Department;


public class DepartmentDictionaryTablePanel extends DictionaryTablePanel<Department> {

	private static final long serialVersionUID = 1698570024703870348L;
	private Controller controller;

	public DepartmentDictionaryTablePanel(Controller controller) {
		super();
		this.controller = controller;
		this.controller.setDepartmentDictionaryTable(this);
		this.createTable();
		this.setButtonEventListeners();
		setPanelTitle("Słownik oddziałów");
	}
	
	@Override
	public void createTable() {
		this.setTableModelAndSorter(new Class[] { 
				java.lang.Integer.class,
				java.lang.String.class
		});
		this.setTableColumnNames(new String[] { 
				"ID oddziału",
				"Nazwa oddziału"
		});
	}

	@Override
	public void updateTable(List<Department> department) {
		clearTable();
		if(isEmptyData(department)) return;
		for( Department deviceType : department){
			addRowToTable(new Object[] {deviceType.getDepartmentID(), deviceType.getDepartmentName()});
		}
		resizeTable();
	}

	@Override
	public void setButtonEventListeners() {
		getInsertButton().removeActionListener(getInsertButton().getActionListeners()[0]);
		getInsertButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("test32132");
			}
		});
		getUpdateButton().removeActionListener(getUpdateButton().getActionListeners()[0]);
		getUpdateButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("test32132");
			}
		});
		getDeleteButton().removeActionListener(getDeleteButton().getActionListeners()[0]);
		getDeleteButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("test32132");
			}
		});
	}


}
