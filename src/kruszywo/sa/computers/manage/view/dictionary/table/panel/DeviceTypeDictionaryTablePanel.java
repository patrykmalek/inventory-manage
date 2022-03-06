package kruszywo.sa.computers.manage.view.dictionary.table.panel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.model.DeviceType;


public class DeviceTypeDictionaryTablePanel extends DictionaryTablePanel<DeviceType> {

	private static final long serialVersionUID = 1698570024703870348L;
	private Controller controller;

	public DeviceTypeDictionaryTablePanel(Controller controller) {
		super();
		this.controller = controller;
		this.controller.setDeviceTypeDictionaryTable(this);
		this.createTable();
		this.setButtonEventListeners();
		setPanelTitle("Słownik typów urządzeń");
	}
	
	@Override
	public void createTable() {
		this.setTableModelAndSorter(new Class[] { 
				java.lang.Integer.class,
				java.lang.String.class
		});
		this.setTableColumnNames(new String[] { 
				"ID typu urządzenia",
				"Nazwa typu urządzenia"
		});
	}

	@Override
	public void updateTable(List<DeviceType> devicesType) {
		clearTable();
		if(isEmptyData(devicesType)) return;
		for( DeviceType deviceType : devicesType){
			addRowToTable(new Object[] {deviceType.getDeviceTypeID(), deviceType.getDeviceTypeName()});
		}
		resizeTable();
	}

	@Override
	public void setButtonEventListeners() {
		getInsertButton().removeActionListener(getInsertButton().getActionListeners()[0]);
		getInsertButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getDeviceTypeServiceDAO().openDeviceTypeWindowToAddNew();
			}
		});
		getUpdateButton().removeActionListener(getUpdateButton().getActionListeners()[0]);
		getUpdateButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getDeviceTypeServiceDAO().openDeviceTypeWindowToUpdate(getIdFromTable());
			}
		});
		getDeleteButton().removeActionListener(getDeleteButton().getActionListeners()[0]);
		getDeleteButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getController().getManagerDAO().getDeviceTypeServiceDAO().deleteDeviceTypeWithPrompt(getIdFromTable());
			}
		});
		getTable().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if(getParentWindow() == null) getController().getManagerDAO().getDeviceTypeServiceDAO().openDeviceTypeWindowToOnlyShowDetails(getIdFromTable());
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
