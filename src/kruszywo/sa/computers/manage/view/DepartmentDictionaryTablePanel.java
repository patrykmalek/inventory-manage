package kruszywo.sa.computers.manage.view;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import kruszywo.sa.computers.manage.controller.Controller;
import kruszywo.sa.computers.manage.model.Device;
import kruszywo.sa.computers.manage.view.util.ButtonPanel;
import kruszywo.sa.computers.manage.view.util.ClipboardKeyAdapter;
import kruszywo.sa.computers.manage.view.util.PMJTable;

public class DepartmentDictionaryTablePanel extends JPanel implements TablePanel<Device> {

	private static final long serialVersionUID = 1698570024703870348L;
	private Controller controller;
	private JScrollPane tableContainer;
	private PMJTable table;
	private JPanel centerPanel;
	private JPanel northPanel;
	private ButtonPanel buttonPanel;
	
	public DepartmentDictionaryTablePanel(Controller controller) {
		this.controller = controller;
		this.controller.setDepartmentDictionaryTable(this);
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
				java.lang.String.class,
				java.lang.String.class,
				java.lang.String.class,
				java.lang.String.class,
				java.lang.String.class,
				java.lang.Integer.class,
				java.lang.Double.class,
				java.lang.Double.class
		});
		this.table.getDefaultTableModel().setColumnIdentifiers(new String[] { 
				"Właściel",
				"Nazwa urządzenia",
				"Źródłowy Adres IP",
				"Docelowy Adres IP",
				"Nazwa serwera",
				"Ilość połączeń",
				"Suma pobrania MB",
				"Suma wysyłania MB"
				
		});
		
		this.table.setTableSorter(this.table.getDefaultTableModel());
		this.table.setRowSorter(this.table.getTableSorter());
		this.tableContainer = new JScrollPane(table);
		
		this.buttonPanel = new ButtonPanel();
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
		System.out.println("ładowanie danych..");
//		ArrayList<String[]> data = null;
//		((DefaultTableModel) table.getModel()).setRowCount(0);
//		
//		if (data == null) {
//			new SystemOperationException("Brak danych.", new NullPointerException().getCause());
//			return;
//		}
//		
//		for( String[] row : data ){
//			((DefaultTableModel) table.getModel()).addRow(
//				new Object[] {row[0], row[1], row[2], row[3],row[4], Integer.parseInt(row[5]), Double.parseDouble(row[6]), Double.parseDouble(row[7])});
//		}
//		
//		this.table.resizeColumnWidth();
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
