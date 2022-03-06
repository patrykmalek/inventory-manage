package kruszywo.sa.computers.manage.view.dictionary.table.panel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.model.Employee;


public class EmployeeDictionaryTablePanel extends DictionaryTablePanel<Employee> {

	private static final long serialVersionUID = 1698570024703870348L;
	private Controller controller;

	public EmployeeDictionaryTablePanel(Controller controller) {
		super();
		this.controller = controller;
		this.controller.setEmployeeDictionaryTable(this);
		this.createTable();
		this.setButtonEventListeners();
		setPanelTitle("Słownik pracowników");
	}
	
	@Override
	public void createTable() {
		this.setTableModelAndSorter(new Class[] { 
				java.lang.Integer.class,
				java.lang.String.class,
				java.lang.String.class
		});
		this.setTableColumnNames(new String[] { 
				"ID pracownika",
				"Imię pracownika",
				"Nazwisko pracownika"
		});
	}

	@Override
	public void updateTable(List<Employee> employees) {
		clearTable();
		if(isEmptyData(employees)) return;
		for( Employee employee : employees){
			addRowToTable(new Object[] {employee.getEmployeeID(), employee.getFirstName(), employee.getLastName()});
		}
		resizeTable();
	}

	@Override
	public void setButtonEventListeners() {
		getInsertButton().removeActionListener(getInsertButton().getActionListeners()[0]);
		getInsertButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getEmployeeServiceDAO().openEmployeeWindowToAddNew();
			}
		});
		getUpdateButton().removeActionListener(getUpdateButton().getActionListeners()[0]);
		getUpdateButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getEmployeeServiceDAO().openEmployeeWindowToUpdate(getIdFromTable());
			}
		});
		getDeleteButton().removeActionListener(getDeleteButton().getActionListeners()[0]);
		getDeleteButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getEmployeeServiceDAO().deleteEmployeeWithPrompt(getIdFromTable());
			}
		});
		getTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if(getParentWindow() == null) getController().getManagerDAO().getEmployeeServiceDAO().openEmployeeWindowToOnlyShowDetails(getIdFromTable());
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
