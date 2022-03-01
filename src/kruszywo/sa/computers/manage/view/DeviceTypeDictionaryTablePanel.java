package kruszywo.sa.computers.manage.view;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class DeviceTypeDictionaryTablePanel extends JPanel implements TablePanel<Device> {

	private static final long serialVersionUID = 1698570024703870348L;
	private Controller controller;
	private JScrollPane tableContainer;
	private PMJTable table;
	private JPanel centerPanel;
	private JPanel northPanel;
	private ButtonPanel buttonPanel;
	
	public DeviceTypeDictionaryTablePanel(Controller controller) {
		this.controller = controller;
		this.controller.setDeviceTypeDictionaryTable(this);
		this.createVisuals();
//		this.createEventListeners();
	}
	
	@Override
	public void createVisuals() {
		this.setBackground(new Color(245, 245, 245));
		this.setLayout(new BorderLayout());
		
		this.table = new PMJTable(false);
		this.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.table.setDefaultTableModel(new Class[] { 
				java.lang.Integer.class,
				java.lang.String.class,
				java.lang.String.class,
				java.lang.String.class,
				java.lang.Integer.class,
				java.lang.Integer.class,
				java.lang.Integer.class,
				java.lang.String.class,
				java.lang.String.class,
				java.lang.String.class
		});
		this.table.getDefaultTableModel().setColumnIdentifiers(new String[] { 
				"ID urządzenia",
				"Numer unikalny",
				"Nazwa",
				"Numer inwentarzowy",
				"ID typu urządzenia",
				"ID miejsca użytk.",
				"ID pracownika",
				"Powiązana faktura",
				"Data zakupu",
				"Ostatnia instalacja"
		});
		
		this.table.setTableSorter(this.table.getDefaultTableModel());
		this.table.setRowSorter(this.table.getTableSorter());
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
				
			}
		});
		
		JButton editButton = buttonPanel.getButtons().get(1);
		editButton.removeActionListener(editButton.getActionListeners()[0]);
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
			}
		});
		
		JButton deletButton = buttonPanel.getButtons().get(2);
		deletButton.removeActionListener(deletButton.getActionListeners()[0]);
		deletButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
			}
		});
		
		this.table.addKeyListener(new ClipboardKeyAdapter(this.table));
	}
	
	@Override
	public void updateTable(List<Device> devices) {
		((DefaultTableModel) table.getModel()).setRowCount(0);
		
		if (devices == null) {
			new SystemOperationException("Brak danych.", new NullPointerException().getCause());
			return;
		}
		
		if (devices.isEmpty()) {
			JOptionPane.showMessageDialog(controller.getMainFrame(), "Brak danych do załadowania");
			return;
		}
		
		for( Device device : devices){
			((DefaultTableModel) table.getModel()).addRow(
				new Object[] {device.getDeviceID(), device.getDeviceUniqueNumber(), device.getDeviceName(), device.getDeviceInventoryNumber(), 
						device.getDeviceTypeID(), device.getAssignedDepartmentID(), device.getAssignedEmployeeID(), device.getInvoiceNumber(),
						device.getPurchaseDate(), device.getLastInstallationDate()});
		}
		
		this.table.resizeColumnWidth();
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
}
