package computers.manage.view.dictionary.table.panel;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import computers.manage.controller.Controller;
import computers.manage.model.ComputerComponent;


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
				java.lang.String.class,
				java.lang.String.class
		});
		this.setTableColumnNames(new String[] { 
				"ID komponentu",
				"Nazwa przypisanego komputera",
				"Nazwa systemowa komputera",
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
						computerComponent.getComputerSystemName(),
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
				
				if (e.getKeyCode() == KeyEvent.VK_F3) {
					getController().getManagerDAO().getComputerComponentServiceDAO().openComputerComponentWindowToAddNew();
				}
				
				if (e.getKeyCode() == KeyEvent.VK_F5) {
					getController().getManagerDAO().getComputerComponentServiceDAO().openComputerComponentWindowToUpdate(getIdFromTable());
				}
				
				if (e.getKeyCode() == KeyEvent.VK_F11) {
					updateTable(getController().getManagerDAO().getComputerComponentDAO().getAll());
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
