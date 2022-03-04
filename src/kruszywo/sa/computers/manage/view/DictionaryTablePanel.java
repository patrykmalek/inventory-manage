package kruszywo.sa.computers.manage.view;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import kruszywo.sa.computers.manage.exception.SystemOperationException;
import kruszywo.sa.computers.manage.view.util.ButtonPanel;
import kruszywo.sa.computers.manage.view.util.ClipboardKeyAdapter;
import kruszywo.sa.computers.manage.view.util.PMJTable;

public abstract class DictionaryTablePanel<T> extends JPanel implements TablePanel<T> {

	private static final long serialVersionUID = 1698570024703870348L;
	private JScrollPane tableContainer;
	private PMJTable table;
	private JPanel centerPanel;
	private JPanel northPanel;
	private ButtonPanel buttonPanel;
	
	private JButton insertButton;
	private JButton updateButton;
	private JButton deleteButton;
	
	public DictionaryTablePanel() {
		this.createVisuals();
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
		
		insertButton = buttonPanel.getButtons().get(0);
		insertButton.removeActionListener(insertButton.getActionListeners()[0]);
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(new JFrame(), "Przycisk jeszcze nie ma podpiętej akcji.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		updateButton = buttonPanel.getButtons().get(1);
		updateButton.removeActionListener(updateButton.getActionListeners()[0]);
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(new JFrame(), "Przycisk jeszcze nie ma podpiętej akcji.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		deleteButton = buttonPanel.getButtons().get(2);
		deleteButton.removeActionListener(deleteButton.getActionListeners()[0]);
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(new JFrame(), "Przycisk jeszcze nie ma podpiętej akcji.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		this.table.addKeyListener(new ClipboardKeyAdapter(this.table));
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
	public abstract void createTable();
	
	@Override
	public abstract void updateTable(List<T> devices);
	
	public abstract void setButtonEventListeners();

	@Override
	public JScrollPane getTableContainer() {
		return tableContainer;
	}

	@Override
	public void clearTable() {
		((DefaultTableModel) table.getModel()).setRowCount(0);
	}
	
	@Override
	public void addRowToTable(Object[] rowData) {
		((DefaultTableModel) table.getModel()).addRow(rowData);
	}
	
	@Override
	public void resizeTable() {
		this.table.resizeColumnWidth();
	}
	

	@Override
	public boolean isEmptyData(List<T> data) {
		if (data == null) {
			new SystemOperationException("Brak danych.", new NullPointerException().getCause());
			return true;
		}
		
		if (data.isEmpty()) {
			JOptionPane.showMessageDialog(new JFrame(), "Brak danych do załadowania");
			return true;
		}
		return false;
	}

	public PMJTable getTable() {
		return table;
	}

	public void setTable(PMJTable table) {
		this.table = table;
	}

	public JPanel getCenterPanel() {
		return centerPanel;
	}

	public void setCenterPanel(JPanel centerPanel) {
		this.centerPanel = centerPanel;
	}

	public JPanel getNorthPanel() {
		return northPanel;
	}

	public void setNorthPanel(JPanel northPanel) {
		this.northPanel = northPanel;
	}

	public ButtonPanel getButtonPanel() {
		return buttonPanel;
	}

	public void setButtonPanel(ButtonPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	public JButton getInsertButton() {
		return insertButton;
	}

	public void setInsertButton(JButton insertButton) {
		this.insertButton = insertButton;
	}

	public JButton getUpdateButton() {
		return updateButton;
	}

	public void setUpdateButton(JButton updateButton) {
		this.updateButton = updateButton;
	}

	public JButton getDeleteButton() {
		return deleteButton;
	}

	public void setDeleteButton(JButton deleteButton) {
		this.deleteButton = deleteButton;
	}

	public void setTableContainer(JScrollPane tableContainer) {
		this.tableContainer = tableContainer;
	}
	
	

}
