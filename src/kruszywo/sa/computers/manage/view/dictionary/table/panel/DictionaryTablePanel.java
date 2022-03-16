package kruszywo.sa.computers.manage.view.dictionary.table.panel;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import kruszywo.sa.computers.manage.exception.SystemOperationException;
import kruszywo.sa.computers.manage.view.TablePanel;
import kruszywo.sa.computers.manage.view.util.ButtonPanel;
import kruszywo.sa.computers.manage.view.util.ClipboardKeyAdapter;
import kruszywo.sa.computers.manage.view.util.PMJTable;
import kruszywo.sa.computers.manage.view.util.TableColumnManager;

public abstract class DictionaryTablePanel<T> extends JPanel implements TablePanel<T> {

	private static final long serialVersionUID = 1698570024703870348L;
	
	private JDialog parentWindow;
	
	private JScrollPane tableContainer;
	private PMJTable table;

	private TableColumnManager tableColumnManager;
	
	private JPanel centerPanel;
	private JPanel northPanel;
	private ButtonPanel buttonPanel;
	
	private JButton insertButton;
	private JButton updateButton;
	private JButton deleteButton;
	
	private String panelTitle = "Słownik";
	
	private int choosenID = -1;
	
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
		this.tableColumnManager = new TableColumnManager(this.table);
		
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
		
		this.table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int modelRow = getTable().convertRowIndexToModel(getTable().getSelectedRow());
					int choosenID = (int) getTable().getModel().getValueAt(modelRow, 0);
					setChoosenID(choosenID);
					closeParentWindow();
				}
			}
		});
		
		this.table.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.isControlDown()) { 
                    if (e.getKeyCode()==KeyEvent.VK_L) {
                            int rowCount = table.getRowCount();
                            JOptionPane.showMessageDialog(new JPanel(), "Ilość wierszy: " + rowCount, "Wiersze", JOptionPane.INFORMATION_MESSAGE);
                    }
	            }
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
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
		getTableColumnManager().hideColumn(0);
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
//			JOptionPane.showMessageDialog(new JFrame(), "Brak danych do załadowania");
			return true;
		}
		return false;
	}
	
	@Override
	public int getIdFromTable() {
		int rowID;
		if(table.getSelectedRow() < 0 ) {
			rowID = table.getSelectedRow();
		} else {
			int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
			rowID = (int) table.getModel().getValueAt(modelRow, 0);
		}
		
		return rowID;
	}
	
	private void closeParentWindow() {
		if(getParentWindow() != null) getParentWindow().dispose();
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

	public String getPanelTitle() {
		return panelTitle;
	}

	public void setPanelTitle(String panelTitle) {
		this.panelTitle = panelTitle;
	}

	public int getChoosenID() {
		return choosenID;
	}

	public void setChoosenID(int choosenID) {
		this.choosenID = choosenID;
	}
	
	public void resetChoosenID() {
		this.choosenID = -1;
	}

	public JDialog getParentWindow() {
		return parentWindow;
	}

	public void setParentWindow(JDialog parentWindow) {
		this.parentWindow = parentWindow;
	}

	public TableColumnManager getTableColumnManager() {
		return tableColumnManager;
	}

	public void setTableColumnManager(TableColumnManager tableColumnManager) {
		this.tableColumnManager = tableColumnManager;
	}

}
