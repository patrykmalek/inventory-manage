package kruszywo.sa.computers.manage.view.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

public class PMJTable extends JTable{


	private static final long serialVersionUID = 7352425134966455629L;
	
	private DefaultTableModel tableModel;
	private TableRowSorter<DefaultTableModel> tableSorter;
	private boolean isEditable;


	public PMJTable (Boolean isEditable) {
		super();
		this.isEditable = isEditable;
		this.setEditable(this.isEditable);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	
	public void resizeColumnWidth() {
	    final TableColumnModel columnModel = this.getColumnModel();
	    for (int column = 0; column < this.getColumnCount(); column++) {
	        int width = 50; // Min width
	        for (int row = 0; row < this.getRowCount(); row++) {
	            TableCellRenderer renderer = this.getCellRenderer(row, column);
	            Component comp = this.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width + 10 , width);
	        }
	        if(width > 300) width=300;
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}
	
	public void setEditable(boolean isEditable) {
		if (!isEditable) this.setDefaultEditor(Object.class, null);
	}
	
	public void setDefaultTableModel(@SuppressWarnings("rawtypes") Class[] types) {
		DefaultTableModel tableModel = new DefaultTableModel() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}	
			
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return isEditable;
		    }
			
			
		};
		
		this.tableModel = tableModel;
		this.setModel(this.tableModel);
	}
	
	public JPanel getSearchTablePanel() {
		JPanel searchPanel = new JPanel();
		searchPanel.setBackground(new Color(245,245,245));
		searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		PMJTextField searchTableField = new PMJTextField("Wyszukaj");
		searchTableField.setPreferredSize(new Dimension(125, 25));
		searchTableField.setSearchField(this);
		
		Component verticalStrut = Box.createVerticalStrut(30);
		searchPanel.add(verticalStrut);
		searchPanel.add(searchTableField);
		
		return searchPanel;
	}
	
	public DefaultTableModel getDefaultTableModel() {
		return this.tableModel;
	}

	public TableRowSorter<DefaultTableModel> getTableSorter() {
		return tableSorter;
	}

	public void setTableSorter(DefaultTableModel tableModel) {
		this.tableSorter = new TableRowSorter<DefaultTableModel>(tableModel);
	}
	
}
