package kruszywo.sa.computers.manage.view.dictionary.table.panel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.model.License;


public class LicenseDictionaryTablePanel extends DictionaryTablePanel<License> {

	private static final long serialVersionUID = 1698570024703870348L;
	private Controller controller;

	public LicenseDictionaryTablePanel(Controller controller) {
		super();
		this.controller = controller;
		this.controller.setLicenseDictionaryTable(this);
		this.createTable();
		this.setButtonEventListeners();
		setPanelTitle("Licencje");
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
				java.util.Date.class,
				java.util.Date.class
		});
		this.setTableColumnNames(new String[] { 
				"ID licencji",
				"Oprogramowanie",
				"Klucz główny",
				"Klucz",
				"Przypisany e-mail",
				"Powiązana faktura",
				"Data zakupu",
				"Data ost. instalacji"
		});
	}

	@Override
	public void updateTable(List<License> licenses) {
		clearTable();
		if(isEmptyData(licenses)) return;
		for( License license : licenses){
			addRowToTable(new Object[] {license.getLicenseID(), license.getSoftware().toString(), license.getLicenseMainKey(),
					license.getLicenseKey(), license.getAssignedEmail(), license.getInvoiceNumber(), 
					license.getPurchaseDate(), license.getLastInstallationDate()});
		}
		resizeTable();
	}

	@Override
	public void setButtonEventListeners() {
		getInsertButton().removeActionListener(getInsertButton().getActionListeners()[0]);
		getInsertButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getLicenseServiceDAO().openLicenseWindowToAddNew();
			}
		});
		getUpdateButton().removeActionListener(getUpdateButton().getActionListeners()[0]);
		getUpdateButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getLicenseServiceDAO().openLicenseWindowToUpdate(getIdFromTable());
			}
		});
		getDeleteButton().removeActionListener(getDeleteButton().getActionListeners()[0]);
		getDeleteButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getLicenseServiceDAO().deleteLicenseWithPrompt(getIdFromTable());
			}
		});
		getTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if(getParentWindow() == null) getController().getManagerDAO().getLicenseServiceDAO().openLicenseWindowToOnlyShowDetails(getIdFromTable());
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
