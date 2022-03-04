package kruszywo.sa.computers.manage.view;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.exception.SystemOperationException;
import kruszywo.sa.computers.manage.model.Device;
import kruszywo.sa.computers.manage.view.util.ButtonPanel;
import kruszywo.sa.computers.manage.view.util.ClipboardKeyAdapter;
import kruszywo.sa.computers.manage.view.util.PMJTable;

public class DeviceTablePanel extends JPanel implements TablePanel<Device> {

	private static final long serialVersionUID = -3463179337882985534L;
	private Controller controller;
	private JScrollPane tableContainer;
	private PMJTable table;
	private JPanel centerPanel;
	private JPanel northPanel;
	private ButtonPanel buttonPanel;
	
	public DeviceTablePanel(Controller controller) {
		this.controller = controller;
		this.controller.setDeviceTablePanel(this);
		this.createVisuals();
		this.createTable();
		this.createEventListeners();
	}
	
	@Override
	public void createVisuals() {
		this.setBackground(new Color(245, 245, 245));
		this.setLayout(new BorderLayout());
		
		this.table = new PMJTable(false);

		this.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		this.tableContainer = new JScrollPane(table);
		
		this.buttonPanel = new ButtonPanel(FlowLayout.LEADING);
		this.buttonPanel.addInsertButton(new JButton());
		this.buttonPanel.addEditButton(new JButton());
		this.buttonPanel.addDeleteButton(new JButton());
		
		this.northPanel = new JPanel();
		this.northPanel.setLayout(new BorderLayout());
		this.northPanel.add(buttonPanel, BorderLayout.CENTER);
		this.northPanel.add(table.getSearchTablePanel(), BorderLayout.LINE_END);

		this.centerPanel = new JPanel();
		this.centerPanel.setLayout(new BorderLayout(10, 10));
		this.centerPanel.add(this.tableContainer, BorderLayout.CENTER);

		this.add(this.northPanel, BorderLayout.NORTH);
		this.add(this.centerPanel, BorderLayout.CENTER);
	}
	
	@Override
	public void createEventListeners() {
		if (buttonPanel.getButtons().size() <= 0) return;
		
		JButton insertButton = buttonPanel.getButtons().get(0);
		insertButton.removeActionListener(insertButton.getActionListeners()[0]);
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				getController().getManagerDAO().getDeviceServiceDAO().openDeviceWindowToAddNew();
			}
		});
		
		JButton editButton = buttonPanel.getButtons().get(1);
		editButton.removeActionListener(editButton.getActionListeners()[0]);
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				getController().getManagerDAO().getDeviceServiceDAO().openDeviceWindowToUpdate(getID());
			}
		});
		
		JButton deleteButton = buttonPanel.getButtons().get(2);
		deleteButton.removeActionListener(deleteButton.getActionListeners()[0]);
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				getController().getManagerDAO().getDeviceServiceDAO().deleteDeviceWithPrompt(getID());
			}
		});
		
		this.table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						getController().getManagerDAO().getDeviceServiceDAO().openDeviceWindowToOnlyShowDetails(getID());
				}
			}
		});
		
		this.table.addKeyListener(new ClipboardKeyAdapter(this.table));
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
				java.lang.String.class,
				java.util.Date.class,
				java.util.Date.class
		});
		this.setTableColumnNames(new String[] { 
				"ID urządzenia",
				"Numer unikalny",
				"Nazwa",
				"Numer inwentarzowy",
				"Typ urządzenia",
				"Miejsce użytk.",
				"Pracownik",
				"Powiązana faktura",
				"Data zakupu",
				"Ostatnia instalacja"
		});
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setTableModelAndSorter(Class[] types) {
		this.table.setDefaultTableModel(types);
		this.table.setTableSorter(this.table.getDefaultTableModel());
		this.table.setRowSorter(this.table.getTableSorter());
	}
	
	@Override
	public void setTableColumnNames(String[] columnNames) {
		this.table.getDefaultTableModel().setColumnIdentifiers(columnNames);
	}
	
	@Override
	public void updateTable(List<Device> devices) {
		clearTable();
		if(isEmptyData(devices)) return;
		
		for( Device device : devices){
			((DefaultTableModel) table.getModel()).addRow(
				new Object[] {device.getDeviceID(), device.getDeviceUniqueNumber(), device.getDeviceName(), device.getDeviceInventoryNumber(), 
						device.getDeviceType().getDeviceTypeName(), device.getAssignedDepartment().getDepartmentName(), 
						device.getAssignedEmployee().getFirstName() + " " + device.getAssignedEmployee().getLastName(), device.getInvoiceNumber(),
						device.getPurchaseDate(), device.getLastInstallationDate()});
		}
		resizeTable();
	}

	@Override
	public JScrollPane getTableContainer() {
		return tableContainer;
	}

	@Override
	public void clearTable() {
		((DefaultTableModel) table.getModel()).setRowCount(0);
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public int getID() {
		int rowID;
		if(table.getSelectedRow() < 0 ) {
			rowID = table.getSelectedRow();
		} else {
			int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
			rowID = (int) table.getModel().getValueAt(modelRow, 0);
		}
		
		return rowID;
	}

	@Override
	public void resizeTable() {
		 this.table.resizeColumnWidth();
	}

	@Override
	public void addRowToTable(Object[] rowData) {
		((DefaultTableModel) table.getModel()).addRow(rowData);
	}

	@Override
	public boolean isEmptyData(List<Device> data) {
		if (data == null) {
			new SystemOperationException("Brak danych.", new NullPointerException().getCause());
			return true;
		}
		
		if (data.isEmpty()) {
			JOptionPane.showMessageDialog(controller.getMainFrame(), "Brak danych do załadowania");
			return true;
		}
		return false;
	}

}
